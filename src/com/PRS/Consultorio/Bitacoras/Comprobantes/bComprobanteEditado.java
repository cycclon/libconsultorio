package com.PRS.Consultorio.Bitacoras.Comprobantes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:59 p.m.
 */
public class bComprobanteEditado extends clsBitacora {

    public bComprobanteEditado(ResultSet rs) throws SQLException
    {super(rs);}

    public bComprobanteEditado() throws Exception
    {super();this.Accion = "Plantilla de comprobante de turno editada";}
    
    @Override
    public String getNombre() {return "Comprobante editado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ComprobanteEditado;}

    
}//end bComprobanteEditado