package com.PRS.Consultorio.Bitacoras.Gastos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Gastos.clsGasto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:38 p.m.
 */
public class bGastoEliminado extends clsBitacora {
    
    public bGastoEliminado() throws Exception
    {super();}
    
    public bGastoEliminado(ResultSet rs) throws SQLException
    {super(rs);}

    public bGastoEliminado(clsGasto gasto) throws Exception
    {super();this.Accion = "Gasto en concepto de " + gasto.getConcepto() + " por " 
            + gasto.getMonto().pdValorString() + "(" 
            + gasto.getFrecuencia().toString() + ") eliminado";}

    @Override
    public String getNombre() {return "Gasto Eliminado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.GastoEliminado;}
}//end bGastoEliminado