package com.PRS.Consultorio.Cuentas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Cuentas.clsEgreso;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 12:07:01 a.m.
 */
public abstract class clsCobro extends clsEgreso implements iMovimientoConsultorio {
    
    private abstract class clsEstadoCobro
    {
        public abstract enEstadoCobro getEstado();
        
        public abstract void Pagar(clsCobro cobro, float monto, Date fecha) throws Exception;
        
        public abstract String getEstadoStr(clsCobro cobro);
    }
    
    private class clsECPendiente extends clsEstadoCobro
    {

        @Override
        public enEstadoCobro getEstado() {return enEstadoCobro.Pendiente;}

        @Override
        public void Pagar(clsCobro cobro, float monto, Date fecha) throws Exception 
        {
            clsPago xP = new clsPago(monto, fecha);
            xP.Registrar(cobro.ID);
            
            cobro.Pagos.add(xP);
            cobro.DeterminarEstado();
        }

        @Override
        public String getEstadoStr(clsCobro cobro) {
           return "Restante por pagar: " + new clsValor(Math.abs(cobro.getSaldo().pdValor())).pdValorString();
        }
    }
    
    private class clsECPagado extends clsEstadoCobro
    {

        @Override
        public enEstadoCobro getEstado() {return enEstadoCobro.Pagado;}

        @Override
        public void Pagar(clsCobro cobro, float monto, Date fecha) throws Exception 
        {throw new exPagarPagado();}

        @Override
        public String getEstadoStr(clsCobro cobro) {return "Pagado";}
    }

    protected int ID;
    protected clsValor Monto;
    protected List<clsPago> Pagos;
    protected clsEstadoCobro Estado;
    protected Date Fecha;
    protected String Concepto;

    public clsCobro(clsValor monto, Date fechaCobro){
        this.Estado = new clsECPendiente();
        this.Monto = monto;
        this.ID = 0;
        this.Pagos = new ArrayList<>();
        this.Fecha = new Date();
    }
    
    protected clsCobro(ResultSet prRS) throws Exception
    {
        this(0, new Date());
        this.Fecha = prRS.getTimestamp("cob_fecha");
        this.ID = prRS.getInt("cob_id");
        this.Monto = new clsValor(prRS.getFloat("cob_monto"));
        this.Concepto = prRS.getString("pra_nombre") + " (" 
                + String.valueOf(prRS.getInt("pra_codigo")) + ") " + 
                new SimpleDateFormat("dd/MM/yyyy hh:mm").format(prRS
                        .getTimestamp("prog_fecha")) + " para " + 
                prRS.getString("pac_apellido") + ", " + prRS.getString("pac_nombre");
        this.Pagos = clsPago.Listar(this.ID, Concepto);
        this.DeterminarEstado();
    }
    
    protected void DeterminarEstado() throws Exception
    {
        if(this.isSaldado())
        {this.CambiarEstado(enEstadoCobro.Pagado);}
        else{this.CambiarEstado(enEstadoCobro.Pendiente);}
    }
    
    public clsCobro(float monto, Date fechaCobro)
    {this(new clsValor(monto), fechaCobro);}

    public abstract enConceptoCobro getConcepto();
    
    public abstract String getConceptoStr();

    public int getID(){return ID;}

    public clsValor getMonto(){return Monto;}

    public List<clsPago> getPagos(){return Pagos;}
    
    public Date getFecha(){return this.Fecha;}
    
    private void CambiarEstado(enEstadoCobro nuevoEstado) throws Exception
    {
        switch(nuevoEstado)
        {
            case Pagado:
                this.Estado = new clsECPagado();
                break;
            case Pendiente:
                this.Estado = new clsECPendiente();
                break;
            default:
                this.Estado = new clsECPendiente();
                break;
        }
    }
    
    public void Pagar(float monto, Date fecha) throws Exception
    {this.Estado.Pagar(this, monto, fecha);}
    
    public void Pagar(float monto) throws Exception
    {this.Estado.Pagar(this, monto, new Date());}
    
    public enEstadoCobro getEstado(){return this.Estado.getEstado();}
    
    public String getEstadoStr(){return this.Estado.getEstadoStr(this);}
    
    public void Balancear() throws Exception
    {
        float balance = this.Monto.pdValor();
        for(clsPago xP : Pagos)
        {
            if(balance-xP.getMonto().pdValor() < 0)
            {xP.setMonto(new clsValor(balance));xP.Modificar();}
            balance-=xP.getMonto().pdValor();
        }
    }
    
    public clsValor getSaldo()
    {
        float saldo = 0;
        saldo = saldo - this.Monto.pdValor();
        for(clsPago xP : Pagos)
        {saldo += xP.getMonto().pdValor();}
        
        return new clsValor(saldo);
    }
    
    public void Cancelar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call CancelarCobro(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        xGB.EjecutarComando();
        
        this.Monto = new clsValor(0);
    }
    
    public clsValor getRestante()
    {
        return new clsValor(this.getMonto().pdValor() - this.getTotalPagado().pdValor());
    }
    
    public boolean isSaldado(){return this.getSaldo().pdValor() >= 0;}
    
    public void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int)xGB.ObtenerClave("cobro");
        
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call RegistrarCobro(?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, this.getConcepto().toString());
        xGB.CrearParametro(3, enTipoParametro.Moneda, Monto.pdValor());
        xGB.CrearParametro(4, enTipoParametro.Fecha, Fecha);
        xGB.CrearParametro(5, enTipoParametro.Entero, IDTrabajo);
        
        xGB.EjecutarComando();
        
    }
    
    public clsValor getTotalPagado() {
        float monto = 0;
        
        for(clsPago xP : Pagos)
        {monto += xP.getMonto().pdValor();}
        
        return new clsValor(monto);
    }
    
    @Override
    public String getConcepetoMovimiento() {return this.getConceptoStr();}

    @Override
    public Date getFechaMovimiento() {return this.Fecha;}

    @Override
    public clsValor getMontoMovimiento() {return this.Monto;}

    @Override
	public String getColorMonto(){return "#cc0033";}

    @Override
    public String getIcono(){return "egreso.png";}
}//end clsCobro