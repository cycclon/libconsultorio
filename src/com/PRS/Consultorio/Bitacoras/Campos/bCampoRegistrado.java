package com.PRS.Consultorio.Bitacoras.Campos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsCampoTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:49 p.m.
 */
public class bCampoRegistrado extends clsBitacora {
    
    public bCampoRegistrado() throws Exception
    {super();}
    
    public bCampoRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

    public bCampoRegistrado(clsCampoTrabajo campo) throws Exception
    {
        super();
        this.Accion = "Campo de trabajo registrado: " + campo.getNombreyCodigo();
    }

    @Override
    public String getNombre() {return "Campo Registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.CampoRegistrado;}
}//end bCampoRegistrado