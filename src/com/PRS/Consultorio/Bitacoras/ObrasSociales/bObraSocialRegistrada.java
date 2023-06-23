package com.PRS.Consultorio.Bitacoras.ObrasSociales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:17 p.m.
 */
public class bObraSocialRegistrada extends clsBitacora {
    
    public bObraSocialRegistrada() throws Exception
    {super();}
    
    public bObraSocialRegistrada(ResultSet rs) throws SQLException
    {super(rs);}

    public bObraSocialRegistrada(clsOS os) throws Exception {
        super();
        this.Accion = "Obra social " + os.getNombre() + " registrada";
    }

    @Override
    public String getNombre() {return "Obra Social Registrada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ObraSocialRegistrada;}
}//end bObraSocialRegistrada