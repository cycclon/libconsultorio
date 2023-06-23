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
 * @created 22-nov-2014 05:04:21 p.m.
 */
public class bTrabajoRealizado extends clsBitacora {
    
    public bTrabajoRealizado() throws Exception
    {super();}
    
    public bTrabajoRealizado(ResultSet rs) throws SQLException
    {super(rs);}

	public bTrabajoRealizado(clsTrabajo trabajo) throws Exception{
            super();
            this.Accion = "Trabajo de " +  trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " del día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr() 
                    + " realizado por " + trabajo.getRealizacion().getRealizadorStr();
	}

    @Override
    public String getNombre() {return "Trabajo realizado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.TrabajoRealizado;}


}//end bTrabajoRealizado