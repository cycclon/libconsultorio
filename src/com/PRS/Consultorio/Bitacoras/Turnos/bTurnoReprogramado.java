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
 * @created 22-nov-2014 05:01:58 p.m.
 */
public class bTurnoReprogramado extends clsBitacora {
    
    public bTurnoReprogramado() throws Exception
    {super();}
    
    public bTurnoReprogramado(ResultSet rs) throws SQLException
    {super(rs);}

	public bTurnoReprogramado(clsTrabajo trabajo) throws Exception{
            super();
            this.Accion = "Turno para " +  trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " reprogramado para el día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr();
	}

    @Override
    public String getNombre() {return "Turno reprogramado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.TurnoReprogramado;}

}//end bTurnoReprogramado