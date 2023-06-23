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
 * @created 22-nov-2014 05:02:00 p.m.
 */
public class bTurnoCancelado extends clsBitacora {
    
    public bTurnoCancelado() throws Exception
    {super();}
    
    public bTurnoCancelado(ResultSet rs) throws SQLException
    {super(rs);}

	public bTurnoCancelado(clsTrabajo trabajo) throws Exception{
            super();
            this.Accion = "Se canceló el turno para " + trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " del día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr();
	}

    @Override
    public String getNombre() {return "Turno cancelado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.TurnoCancelado;}


}//end bTurnoCancelado