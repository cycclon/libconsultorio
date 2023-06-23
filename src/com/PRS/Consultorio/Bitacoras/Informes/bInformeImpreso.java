package com.PRS.Consultorio.Bitacoras.Informes;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:54 p.m.
 */
public class bInformeImpreso extends clsBitacora {
    
    public bInformeImpreso() throws Exception
    {super();}
    
    public bInformeImpreso(ResultSet rs) throws SQLException
    {super(rs);}

    public bInformeImpreso(clsTrabajo trabajo) throws Exception{
        super();
        this.Accion = "Informe de trabajo impreso: " + trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " del día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr();
    }

    @Override
    public String getNombre() {return "Informe impreso";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.InformeImpreso;}

}//end bInformeImpreso