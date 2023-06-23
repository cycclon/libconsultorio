package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:49 p.m.
 */
public class bPracticaEliminada extends clsBitacora {
    
    public bPracticaEliminada() throws Exception
    {super();}
    
    public bPracticaEliminada(ResultSet rs) throws SQLException
    {super(rs);}

    public bPracticaEliminada(clsPractica practica) throws Exception{
        super();
        this.Accion = "Se elminó la práctica " + practica.getNombreyCodigo();
    }

    @Override
    public String getNombre() {return "Práctica eliminada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PracticaEliminada;}


        
}//end bPracticaEliminada