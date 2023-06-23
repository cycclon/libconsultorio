package com.PRS.Consultorio.Usuarios;
import com.PRS.Consultorio.Acceso.clsGestorPermisos;
import com.PRS.Framework.Acceso.*;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-ago-2014 02:37:57 p.m.
 */
public class clsAdministrador implements iAccesor, iUsuarioConsultorio {

    private String Apellido;
    private byte ID;
    private String Nombre;
    private clsUsuario Usuario;

    public clsAdministrador(){
        this("","","Guest");
    }
    
    public clsAdministrador(String prNombre, String prApellido, String prUsername)
    {
        this.Apellido = prApellido;
        this.Nombre = prNombre;
        this.Usuario = new clsUsuario();
        this.Usuario.pdUsername(prUsername);
    }
    
    private clsAdministrador(ResultSet prRS, clsUsuario prUsuario) 
            throws SQLException
    {
        this.Nombre = prRS.getString("adm_nombre");
        this.Apellido = prRS.getString("adm_apellido");
        this.Usuario = prUsuario;
    }

    public String getApellido(){return Apellido;}

    public byte getID(){return ID;}

    public String getNombre(){return Nombre;}

    public clsUsuario getUsuario(){return Usuario;}

    public void setApellido(String newVal){Apellido = newVal;}

    public void setID(byte newVal){ID = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public static clsAdministrador Obtener(clsUsuario prUsuario) throws Exception
    {
        clsAdministrador xA = null;
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "SELECT * FROM administrador "
                + "WHERE adm_usr_id = ?");
        xGB.CrearParametro(1, enTipoParametro.Entero, prUsuario.pdID());
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            xA = new clsAdministrador(xRS, prUsuario);
        }
        
        return xA;
    }

    @Override
    public List<clsPermiso> pdPermisos() {
        List<clsPermiso> Permisos = new ArrayList<>();
        clsGestorPermisos xGP = clsGestorPermisos.Instanciar();
        Permisos.add(xGP.getPermiso(1));
        Permisos.add(xGP.getPermiso(2));
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
        Permisos.add(xGP.getPermiso(13));
        Permisos.add(xGP.getPermiso(14));
        Permisos.add(xGP.getPermiso(15));
        Permisos.add(xGP.getPermiso(16));
        Permisos.add(xGP.getPermiso(17));
        Permisos.add(xGP.getPermiso(18));
        Permisos.add(xGP.getPermiso(19));
        Permisos.add(xGP.getPermiso(20));
        Permisos.add(xGP.getPermiso(21));
        Permisos.add(xGP.getPermiso(22));
        Permisos.add(xGP.getPermiso(23));
        Permisos.add(xGP.getPermiso(24));
        Permisos.add(xGP.getPermiso(25));
        Permisos.add(xGP.getPermiso(26));
        Permisos.add(xGP.getPermiso(27));
        Permisos.add(xGP.getPermiso(28));
        Permisos.add(xGP.getPermiso(29));
        return Permisos;
    }

    @Override
    public String getNombreCompletoUC() 
    {return this.getApellido() + ", " + this.getNombre();}

    @Override
    public enTipoUsuario getTipoUsuario() {return enTipoUsuario.Administrador;}
}//end clsAdministrador