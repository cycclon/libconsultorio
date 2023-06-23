package com.PRS.Consultorio.Profesionales;

import com.PRS.Consultorio.Acceso.clsGestorPermisos;
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
public class clsFirmante extends clsProfesional {

    public clsFirmante(){

    }
    
    public clsFirmante(ResultSet prRS) throws Exception
    {
        super(prRS);
    }
    
    public clsFirmante(ResultSet prRS, String prefix) throws Exception
    {
        super(prRS, prefix);
    }

    @Override
    public enTipoProfesional getTipo() {
        return enTipoProfesional.Firmante;
    }

    @Override
    public String getIcono() {
        return "firmante.png";
    }

    @Override
    public List<clsPermiso> pdPermisos() {
        List<clsPermiso> Permisos = new ArrayList<>();
        clsGestorPermisos xGP = clsGestorPermisos.Instanciar();

        Permisos.add(xGP.getPermiso(3));
        Permisos.add(xGP.getPermiso(4));
        Permisos.add(xGP.getPermiso(5));
        Permisos.add(xGP.getPermiso(6));
        Permisos.add(xGP.getPermiso(7));
        Permisos.add(xGP.getPermiso(8));
        Permisos.add(xGP.getPermiso(9));
        Permisos.add(xGP.getPermiso(10));
        Permisos.add(xGP.getPermiso(11));
        Permisos.add(xGP.getPermiso(12));

        Permisos.add(xGP.getPermiso(14));

        Permisos.add(xGP.getPermiso(16));


        Permisos.add(xGP.getPermiso(19));
        Permisos.add(xGP.getPermiso(20));
        Permisos.add(xGP.getPermiso(21));
        Permisos.add(xGP.getPermiso(22));
        Permisos.add(xGP.getPermiso(23));
        Permisos.add(xGP.getPermiso(24));
        Permisos.add(xGP.getPermiso(25));
        
        return Permisos;
    }

    @Override
    public enTipoUsuario getTipoUsuario() {return enTipoUsuario.Firmante;}
    
    

}//end clsFirmante