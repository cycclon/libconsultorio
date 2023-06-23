package com.PRS.Consultorio.Bitacoras.Pacientes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Pacientes.clsPaciente;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:31 p.m.
 */
public class bPacienteRegistrado extends clsBitacora {
    
    public bPacienteRegistrado() throws Exception
    {super();}
    
    public bPacienteRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

	public bPacienteRegistrado(clsPaciente paciente) throws Exception{ 
            super();
            this.Accion = "Nuevo paciente registrado " + paciente.getNombreCompleto();
	}

    @Override
    public String getNombre() {return "Paciente Registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PacienteRegistrado;}


}//end bPacienteRegistrado