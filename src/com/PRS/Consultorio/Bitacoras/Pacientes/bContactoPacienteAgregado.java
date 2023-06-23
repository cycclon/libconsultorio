package com.PRS.Consultorio.Bitacoras.Pacientes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Pacientes.clsContactoPaciente;
import com.PRS.Consultorio.Pacientes.clsPaciente;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:34 p.m.
 */
public class bContactoPacienteAgregado extends clsBitacora {
    
    public bContactoPacienteAgregado() throws Exception
    {super();}
    
    public bContactoPacienteAgregado(ResultSet rs) throws SQLException
    {super(rs);}

    public bContactoPacienteAgregado(clsPaciente paciente, 
            clsContactoPaciente contacto) throws Exception{
        super();
        this.Accion = "Se eliminó " + contacto.getContactoStr() + " del paciente " 
                + paciente.getNombreCompleto();
    }

    @Override
    public String getNombre() {return "Contacto de paciente eliminado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ContactoPacienteAgregado;}

}//end bContactoPacienteAgregado