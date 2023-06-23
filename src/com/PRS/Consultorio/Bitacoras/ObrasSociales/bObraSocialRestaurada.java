package com.PRS.Consultorio.Bitacoras.ObrasSociales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:20 p.m.
 */
public class bObraSocialRestaurada extends clsBitacora {
    
    public bObraSocialRestaurada() throws Exception
    {super();}
        
    public bObraSocialRestaurada(ResultSet rs) throws SQLException
    {super(rs);}

    public bObraSocialRestaurada(clsOS os) throws Exception {
        super();
        this.Accion = "Obra social " + os.getNombre() + " restaurada";
    }

    @Override
    public String getNombre() {return "Obra Social Restaurada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ObraSocialRestaurada;}
}//end bObraSocialRestaurada