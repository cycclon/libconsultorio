package com.PRS.Consultorio.Bitacoras.Profesionales;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Profesionales.clsProfesional;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:18 p.m.
 */
public class bProfesionalRestaurado extends clsBitacora {
    
    public bProfesionalRestaurado() throws Exception
    {super();}
    
    public bProfesionalRestaurado(ResultSet rs) throws SQLException
    {super(rs);}

    public bProfesionalRestaurado(clsProfesional profesional) throws Exception{
        super();
        this.Accion = "Se restauró un profesional eliminado " + profesional.getNombreCompleto();
    }

    @Override
    public String getNombre() {return "Profesional restaurado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.ProfesionalRestaurado;}
    
    

}//end bProfesionalRestaurado