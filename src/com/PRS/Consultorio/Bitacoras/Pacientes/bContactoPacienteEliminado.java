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
 * @created 22-nov-2014 05:02:35 p.m.
 */
public class bContactoPacienteEliminado extends clsBitacora {
    
    public bContactoPacienteEliminado() throws Exception
    {super();}
    
    public bContactoPacienteEliminado(ResultSet rs) throws SQLException
    {super(rs);}

    public bContactoPacienteEliminado(clsPaciente paciente, clsContactoPaciente contacto) throws Exception{
        super();
    }

    @Override
    public String getNombre() {return "Contacto de paciente agregado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ContactoPacienteAgregado;}
}//end bContactoPacienteEliminado