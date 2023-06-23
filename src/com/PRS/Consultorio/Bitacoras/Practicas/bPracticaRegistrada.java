package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:48 p.m.
 */
public class bPracticaRegistrada extends clsBitacora {
    
    public bPracticaRegistrada() throws Exception
    {super();}
    
    public bPracticaRegistrada(ResultSet rs) throws SQLException
    {super(rs);}

	public bPracticaRegistrada(clsPractica practica) throws Exception{
            super();
            this.Accion = "Se registró una nueva práctica: " + practica.getNombreyCodigo();
	}

    @Override
    public String getNombre() {return "Práctica registrada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PracticaRegistrada;}

}//end bPracticaRegistrada