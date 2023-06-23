package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:50 p.m.
 */
public class bPracticaRestaurada extends clsBitacora {
    
    public bPracticaRestaurada() throws Exception
    {super();}
    
    public bPracticaRestaurada(ResultSet rs) throws SQLException
    {super(rs);}

	public bPracticaRestaurada(clsPractica practica) throws Exception{
            super();
            Accion = "Se restauró la práctica eliminada " + practica.getNombreyCodigo();
	}

    @Override
    public String getNombre() {return "Práctica restaurada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PracticaRestaurada;}

}//end bPracticaRestaurada