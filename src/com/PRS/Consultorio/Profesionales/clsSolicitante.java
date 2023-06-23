package com.PRS.Consultorio.Profesionales;

import com.PRS.Consultorio.Usuarios.enTipoUsuario;
import com.PRS.Framework.Acceso.clsPermiso;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 04-sep-2014 09:10:11 p.m.
 */
public class clsSolicitante extends clsProfesional {

    public clsSolicitante(){

    }
    
    public clsSolicitante(ResultSet prRS) throws Exception
    {
        super(prRS);
    }
    
    public clsSolicitante(ResultSet prRS, String prefix) throws Exception
    {
        super(prRS, prefix);
    }

    @Override
    public enTipoProfesional getTipo() {
        return enTipoProfesional.Solicitante;
    }

    @Override
    public String getIcono() {
        return "profesionales_sm.png";
    }

    @Override
    public List<clsPermiso> pdPermisos() {return new ArrayList<>();}

    @Override
    public enTipoUsuario getTipoUsuario() {return enTipoUsuario.Solicitante;}
        
        
}//end clsSolicitante