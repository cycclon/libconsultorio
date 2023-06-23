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
 * @created 04-sep-2014 09:10:10 p.m.
 */
public class clsPracticante extends clsProfesional {

    public clsPracticante(){
        super();
    }
    
    clsPracticante(ResultSet prRS) throws Exception
    {
        super(prRS);
    }
    
    clsPracticante(ResultSet prRS, String prefix) throws Exception
    {super(prRS, prefix);}

    @Override
    public enTipoProfesional getTipo() {
        return enTipoProfesional.Practicante;
    }

    @Override
    public String getIcono() {
        return "practicante.png";
    }

    @Override
    public List<clsPermiso> pdPermisos() {
        List<clsPermiso> Permisos = new ArrayList<>();
        clsGestorPermisos xGP = clsGestorPermisos.Instanciar();

        Permisos.add(xGP.getPermiso(10));

        Permisos.add(xGP.getPermiso(21));
        Permisos.add(xGP.getPermiso(22));
        Permisos.add(xGP.getPermiso(23));
        Permisos.add(xGP.getPermiso(24));
        
        return Permisos;
    }

    @Override
    public enTipoUsuario getTipoUsuario() {return enTipoUsuario.Practicante;}
        

}//end clsPracticante