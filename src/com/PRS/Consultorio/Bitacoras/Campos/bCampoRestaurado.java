package com.PRS.Consultorio.Bitacoras.Campos;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsCampoTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:51 p.m.
 */
public class bCampoRestaurado extends clsBitacora {
    
    public bCampoRestaurado() throws Exception
    {super();}
    
    public bCampoRestaurado(ResultSet rs) throws SQLException
    {super(rs);}

    public bCampoRestaurado(clsCampoTrabajo campo) throws Exception
    {
        super();
        this.Accion = "Campo de trabajo restaurado: " + campo.getNombreyCodigo();
    }

    @Override
    public String getNombre() {return "Campo Restaurado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.CampoRestaurado;}
}//end bCampoRestaurado