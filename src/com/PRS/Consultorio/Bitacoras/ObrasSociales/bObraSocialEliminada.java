package com.PRS.Consultorio.Bitacoras.ObrasSociales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:18 p.m.
 */
public class bObraSocialEliminada extends clsBitacora {
    
    public bObraSocialEliminada() throws Exception
    {super();}
    
    public bObraSocialEliminada(ResultSet rs) throws SQLException
    {super(rs);}

    public bObraSocialEliminada(clsOS os) throws Exception {
        super();
        this.Accion = "Obra social " + os.getNombre() + " Eliminada";
    }

    @Override
    public String getNombre() {return "Obra Social Eliminada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ObraSocialEliminada;}

}//end bObraSocialEliminada