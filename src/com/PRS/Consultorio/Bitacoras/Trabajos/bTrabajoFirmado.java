package com.PRS.Consultorio.Bitacoras.Trabajos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:43 p.m.
 */
public class bTrabajoFirmado extends clsBitacora {
    
    public bTrabajoFirmado() throws Exception
    {super();}
    
    public bTrabajoFirmado(ResultSet rs) throws SQLException
    {super(rs);}

    public bTrabajoFirmado(clsTrabajo trabajo) throws Exception{
        super();
        this.Accion = "Trabajo de " + trabajo.getPractica().getNombre() + " de " 
        + trabajo.getPaciente().getNombreCompleto() + " del día " 
        + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
        + " a las " + trabajo.getProgramacion().getHorario().getHoraStr() 
                + " firmado por " + trabajo.getFirma().getFirmanteStr();
    }

    @Override
    public String getNombre() {return "Trabajo firmado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.TrabajoFirmado;}

        
}//end bTrabajoFirmado