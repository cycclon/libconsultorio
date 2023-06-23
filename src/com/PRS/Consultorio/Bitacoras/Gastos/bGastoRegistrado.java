package com.PRS.Consultorio.Bitacoras.Gastos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Gastos.clsGasto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:37 p.m.
 */
public class bGastoRegistrado extends clsBitacora {
    
    public bGastoRegistrado() throws Exception
    {super();}
    
    public bGastoRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

    public bGastoRegistrado(clsGasto gasto) throws Exception
    {super();this.Accion = "Gasto en concepto de " + gasto.getConcepto() + " por " 
            + gasto.getMonto().pdValorString() + "(" 
            + gasto.getFrecuencia().toString() + ") registrado";}

    @Override
    public String getNombre() {return "Gasto Registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.GastoRegistrado;}
}//end bGastoRegistrado