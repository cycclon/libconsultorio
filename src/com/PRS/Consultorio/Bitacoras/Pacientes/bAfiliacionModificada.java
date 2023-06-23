package com.PRS.Consultorio.Bitacoras.Pacientes;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import com.PRS.Consultorio.Pacientes.clsPaciente;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 10:43:08 a.m.
 */
public class bAfiliacionModificada extends clsBitacora {

    public bAfiliacionModificada() throws Exception
    {super();}
    
    public bAfiliacionModificada(ResultSet rs) throws SQLException
    {super(rs);}

    public bAfiliacionModificada(clsPaciente paciente, 
            clsOS os) throws Exception{
        super();
        this.Accion = "Se cambió la afiliación de " + paciente.getNombreCompleto() 
                + " a " + os.getNombreConCodigo();
    }

    @Override
    public String getNombre(){
        return "Afiliación modificada";
    }

    @Override
    public enTipoBitacora getTipo(){
        return enTipoBitacora.AfilicacionModificada;
    }
}//end bAfiliacionModificada