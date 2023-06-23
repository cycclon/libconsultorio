package com.PRS.Consultorio.Bitacoras.Turnos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:27 p.m.
 */
public class bTurnoRegistrado extends clsBitacora {
    
    public bTurnoRegistrado() throws Exception
    {super();}
    
    public bTurnoRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

	public bTurnoRegistrado(clsTrabajo trabajo) throws Exception{
            super();
            this.Accion = "Turno para " + trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " del día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr() + " registrado.";
	}

    @Override
    public String getNombre() {return "Turno registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.TurnoRegistrado;}

}//end bTurnoRegistrado