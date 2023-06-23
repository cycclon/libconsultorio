package com.PRS.Consultorio.Bitacoras.Pacientes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Pacientes.clsPaciente;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:32 p.m.
 */
public class bPacienteModificado extends clsBitacora {
    
    public bPacienteModificado() throws Exception
    {super();}
    
    public bPacienteModificado(ResultSet rs) throws SQLException
    {super(rs);}

    public bPacienteModificado(clsPaciente paciente) throws Exception{
        super();
        this.Accion = "Datos de paciente editados " + paciente.getNombreCompleto();
    }

    @Override
    public String getNombre() {return "Paciente modificado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PacienteModificado;}

}//end bPacienteModificado