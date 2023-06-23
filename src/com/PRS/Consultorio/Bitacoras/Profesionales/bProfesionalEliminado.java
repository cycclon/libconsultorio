package com.PRS.Consultorio.Bitacoras.Profesionales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Profesionales.clsProfesional;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:17 p.m.
 */
public class bProfesionalEliminado extends clsBitacora {
    
    public bProfesionalEliminado() throws Exception
    {super();}
    
    public bProfesionalEliminado(ResultSet rs) throws SQLException
    {super(rs);}

	public bProfesionalEliminado(clsProfesional profesional) throws Exception{
            super();
            this.Accion = "Se eliminó el profesinal " + profesional.getNombreCompleto();
	}

    @Override
    public String getNombre() {return "Profesional Eliminado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ProfesionalEliminado;}

}//end bProfesionalEliminado