package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:07 p.m.
 */
public class bPracticaModificada extends clsBitacora {
    
    public bPracticaModificada() throws Exception
    {super();}
    
    public bPracticaModificada(ResultSet rs) throws SQLException
    {super(rs);}

	public bPracticaModificada(clsPractica practica) throws Exception{
            super();
            this.Accion = "Se modificó la práctica " + practica.getNombreyCodigo();
	}

    @Override
    public String getNombre() {return "Práctica modificada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PracticaModificada;}


}//end bPracticaModificada