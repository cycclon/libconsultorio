package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:59 p.m.
 */
public class bPlantillaEditada extends clsBitacora {
    
    public bPlantillaEditada() throws Exception
    {super();}
    
    public bPlantillaEditada(ResultSet rs) throws SQLException
    {super(rs);}

	public bPlantillaEditada(clsPractica practica) throws Exception{
            super();
            this.Accion = "Se editó la plantilla de " + practica.getNombreyCodigo();
	}

    @Override
    public String getNombre() {
        return "Plantilla Editada";
    }

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PlantillaEditada;}

}//end bPlantillaEditada