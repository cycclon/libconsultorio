/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Usuarios;

import com.PRS.Consultorio.Acceso.clsGestorPermisos;
import com.PRS.Framework.Acceso.clsPermiso;
import com.PRS.Framework.Acceso.clsUsuario;
import com.PRS.Framework.Acceso.iAccesor;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ARSPIDALIERI
 */
public class clsRecepcionista implements iAccesor, iUsuarioConsultorio {
    private String Apellido;
    private byte ID;
    private String Nombre;
    private clsUsuario Usuario;     

    public clsRecepcionista(){
        this("","","Guest");
    }
    
    public clsRecepcionista(String prNombre, String prApellido, String prUsername)
    {
        this.Apellido = prApellido;
        this.Nombre = prNombre;
        this.Usuario = new clsUsuario();
        this.Usuario.pdUsername(prUsername);
    }
    
    private clsRecepcionista(ResultSet prRS, clsUsuario prUsuario) 
            throws SQLException
    {
        this.Nombre = prRS.getString("rece_nombre");
        this.Apellido = prRS.getString("rece_apellido");
        this.Usuario = prUsuario;
    }

    public String getApellido(){return Apellido;}

    public byte getID(){return ID;}

    public String getNombre(){return Nombre;}

    public clsUsuario getUsuario(){return Usuario;}

    public void setApellido(String newVal){Apellido = newVal;}

    public void setID(byte newVal){ID = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public static clsRecepcionista Obtener(clsUsuario prUsuario) throws Exception
    {
        clsRecepcionista xA = null;
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "SELECT * FROM recepcionista "
                + "WHERE rece_usr_id = ?");
        xGB.CrearParametro(1, enTipoParametro.Entero, prUsuario.pdID());
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            xA = new clsRecepcionista(xRS, prUsuario);
        }
        
        return xA;
    }

    @Override
    public List<clsPermiso> pdPermisos() {
        List<clsPermiso> Permisos = new ArrayList<>();
        clsGestorPermisos xGP = clsGestorPermisos.Instanciar();
        
        Permisos.add(xGP.getPermiso(4));
        Permisos.add(xGP.getPermiso(6));
        Permisos.add(xGP.getPermiso(7));

        Permisos.add(xGP.getPermiso(9));
        Permisos.add(xGP.getPermiso(10));

        Permisos.add(xGP.getPermiso(12));
        Permisos.add(xGP.getPermiso(13));

        Permisos.add(xGP.getPermiso(17));
        Permisos.add(xGP.getPermiso(18));
        Permisos.add(xGP.getPermiso(19));
        Permisos.add(xGP.getPermiso(20));
        Permisos.add(xGP.getPermiso(21));
        
        Permisos.add(xGP.getPermiso(23));
        
        Permisos.add(xGP.getPermiso(25));
        Permisos.add(xGP.getPermiso(26));
        Permisos.add(xGP.getPermiso(27));
        Permisos.add(xGP.getPermiso(28));
        
        return Permisos;
    }
    
    @Override
    public String getNombreCompletoUC() 
    {return this.getApellido() + ", " + this.getNombre();}

    @Override
    public enTipoUsuario getTipoUsuario() {return enTipoUsuario.Recepcionista;}
}
