package com.PRS.Consultorio.Colegio;

import com.PRS.Consultorio.Bitacoras.Colegio.bPagoColegio;
import com.PRS.Consultorio.Cuentas.clsCobroOS;
import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Cuentas.clsCuenta;
import com.PRS.Framework.Cuentas.clsMovimiento;
import com.PRS.Framework.Cuentas.enTipoMovimiento;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 13-dic-2014 12:30:53 p.m.
 */
public class clsColegio {

    private clsCuenta Cuenta;
    private static clsColegio Instancia;
    private final String Nombre;
     private List<clsPagoColegio> Pagos;

    private clsColegio() throws Exception{
        this.Nombre = "Colegio";
        this.Pagos = new ArrayList<>();
        this.Cuenta = new clsCuenta();
        
        this.ObtenerCuenta(); 
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPagosColegio()}");
        
        ResultSet rs = xGB.EjecutarConsulta();
        
        short idPago = 0;
        float total = 0;
        clsPagoColegio pc = new clsPagoColegio(new Date());
        while(rs.next())
        {
            if(rs.getShort("paco_id")!=idPago)
            {
                if(pc.getID() != 0)
                {
                    pc.setTotal(new clsValor(total));
                    this.Pagos.add(pc);
                    total = 0;
                }
                pc = new clsPagoColegio(rs);
                idPago = pc.getID();
                total+=rs.getFloat("cob_monto");
            }
            else
            {total+=rs.getFloat("cob_monto");}
        }
        pc.setTotal(new clsValor(total));
        this.Pagos.add(pc);
    }
    
    private void ObtenerCuenta() throws Exception
    {
        List<clsCobroOS> coss = clsCobroOS.Listar();
        Cuenta.getMovimientos().clear();
        for(clsCobroOS cos : coss)
        {
            Cuenta.getMovimientos().add(cos);
            for(clsPago p : cos.getPagos())Cuenta.getMovimientos().add(p);
        }
    }
    

    public clsCuenta getCuenta(){return Cuenta;}

    public String getNombre(){return Nombre;}

    public static clsColegio Instanciar() throws Exception
    {if(Instancia == null) Instancia = new clsColegio();return Instancia;}
    
    public void addEgreso(clsCobroOS cobro) 
    {this.Cuenta.getMovimientos().add(cobro);}
    
    public List<clsCobroOS> getNoSaldados()
    {
        List<clsCobroOS> ns = new ArrayList<>();
        
        for(clsMovimiento c : Cuenta.getMovimientos())
        {
            if(c.getTipoMovimiento()== enTipoMovimiento.Egreso)
            {if(!((clsCobroOS)c).isSaldado())ns.add((clsCobroOS)c);}
        }
        
        return ns;
    }
    
    public List<clsCobroOS> getNoSaldados(byte mes, int ano)
    {
        List<clsCobroOS> ns = new ArrayList<>();
        getNoSaldados().stream().forEach((cos) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cos.getFecha());
            if (cal.get(Calendar.MONTH)==mes &&
                    cal.get(Calendar.YEAR)==ano) {
                ns.add(cos);
            }
        });
        return ns;
    }
    
    public void Saldar(List<clsCobroOS> cobros, Date fecha) throws Exception
    {
        clsPagoColegio p = new clsPagoColegio(fecha);
        p.Registrar(cobros);
        this.Pagos.add(p);
        new bPagoColegio(p).Registrar();
        this.ObtenerCuenta();
    }
    
    public String getUltimoPago()
    {
        if(!this.Pagos.isEmpty()){return Pagos.get(Pagos.size()-1).toString();}
        else{return "Aún no hay pagos registrados";}
    }
}//end clsColegio