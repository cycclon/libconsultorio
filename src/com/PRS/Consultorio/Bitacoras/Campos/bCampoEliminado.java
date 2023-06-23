package com.PRS.Consultorio.Bitacoras.Campos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsCampoTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:50 p.m.
 */
public class bCampoEliminado extends clsBitacora {
    
    public bCampoEliminado() throws Exception
    {super();}
    
    public bCampoEliminado(ResultSet rs) throws SQLException
    {super(rs);}
    
    public bCampoEliminado(clsCampoTrabajo campo) throws Exception
    {
        super();
        this.Accion = "Campo de trabajo eliminado: " + campo.getNombreyCodigo();
    }

    @Override
    public String getNombre() {return "Campo Eliminado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.CampoEliminado;}
	
}//end bCampoEliminado