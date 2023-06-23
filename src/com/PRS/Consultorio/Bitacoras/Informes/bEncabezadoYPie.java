package com.PRS.Consultorio.Bitacoras.Informes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:41 p.m.
 */
public class bEncabezadoYPie extends clsBitacora {
    
    public bEncabezadoYPie(ResultSet rs) throws SQLException
    {super(rs);}

    public bEncabezadoYPie() throws Exception
    {super();this.Accion = getNombre();}

    @Override
    public final String getNombre() {return "Encabezado y pie de plantilla editados";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.EncabezadoYPie;}
    
    
}//end bEncabezadoYPie