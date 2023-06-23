package com.PRS.Consultorio.Bitacoras.Profesionales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Profesionales.clsProfesional;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:16 p.m.
 */
public class bProfesionalRegistrado extends clsBitacora {
    
    public bProfesionalRegistrado() throws Exception
    {super();}
    
    public bProfesionalRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

	public bProfesionalRegistrado(clsProfesional profesional) throws Exception{
            super();
            this.Accion = "Nuevo profesional registrado: " + profesional.getNombreCompleto();
	}

    @Override
    public String getNombre() {return "Profesional registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ProfesionalRegistrado;}

}//end bProfesionalRegistrado