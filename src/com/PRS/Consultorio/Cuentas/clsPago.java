package com.PRS.Consultorio.Cuentas;

import com.PRS.Consultorio.Balances.iIngreso;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Cuentas.clsIngreso;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 12:07:03 a.m.
 */
public class clsPago extends clsIngreso implements iMovimientoConsultorio, iIngreso {

    static List<clsPago> Listar(int IDCobro, String concepto) throws Exception {
        List<clsPago> xPs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPagosCobro(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, IDCobro);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xPs.add(new clsPago(xRS, concepto, ""));}
        
        return xPs;
    }

    private Date Fecha;
    private int ID;
    private clsValor Monto;
    private String Concepto;
    private String ConceptoAbajo;

    public clsPago(clsValor monto, Date fecha){
        this.Monto = monto;
        this.ID = 0;
        this.Fecha = fecha;
    }

    public clsPago(float monto, Date fecha)
    {
        this(new clsValor(monto), fecha);
    }

    public clsPago(ResultSet prRS, String concepto, String conceptoAbajo) throws SQLException
    {
        this.Fecha = prRS.getTimestamp("pag_fecha");
        this.ID = prRS.getInt("pag_id");
        this.Monto = new clsValor(prRS.getFloat("pag_monto"));
        Concepto = concepto;
        ConceptoAbajo = conceptoAbajo;
    }

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}

    public clsValor getMonto(){return Monto;}

    public void setFecha(Date newVal){Fecha = newVal;}

    public void setMonto(clsValor newVal){Monto = newVal;}

    public void Registrar(int IDCobro) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        ID = (int) xGB.ObtenerClave("pago");

        xGB.CrearComando(CommandType.StoredProcedure, "{call RegistrarPago(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Moneda, Monto.pdValor());
        xGB.CrearParametro(3,  enTipoParametro.Fecha, Fecha);
        xGB.CrearParametro(4, enTipoParametro.Entero, IDCobro);

        xGB.EjecutarComando();          

    }

    public void Modificar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        xGB.CrearComando(CommandType.StoredProcedure, "{call ModificarPago(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Moneda, Monto.pdValor());

        xGB.EjecutarComando();
    }
    
    public static void Eliminar(int idTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarPago(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, idTrabajo);
        

        xGB.EjecutarComando();
    }

    @Override
    public String getConcepetoMovimiento() {
        return "Pago de: " + Concepto;
    }

    @Override
    public Date getFechaMovimiento() {return this.Fecha;}

    @Override
    public clsValor getMontoMovimiento() {return this.Monto;}

    @Override
    public String getColorMonto(){return "#66cc00";}

    @Override
    public String getIcono(){return "ingreso.png";}

    @Override
    public String getConceptoBalance() {return this.Concepto;}

    @Override
    public Date getFechaBalance() {return this.Fecha;}

    @Override
    public clsValor getMontoBalance() {return this.Monto;}
    
    @Override
    public String getConceptoAbajo() {return this.ConceptoAbajo;}

}//end clsPago