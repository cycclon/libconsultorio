package com.PRS.Consultorio.Pacientes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 31-oct-2014 03:11:48 p.m.
 */
public class clsEmailPaciente extends clsContactoPaciente {

    private String Direccion;
    private String Servidor;
    
    clsEmailPaciente(ResultSet rs) throws SQLException
    {
        this.Direccion = rs.getString("empa_direccion");
        this.Servidor = rs.getString("empa_servidor");
        this.ID = rs.getInt("empa_id");
    }

    public clsEmailPaciente(){this("","");}
    
    public clsEmailPaciente(String direccion, String servidor)
    {
        super();
        this.Direccion = direccion;
        this.Servidor = servidor;
    }

    public String getDireccion(){return Direccion;}

    public String getServidor(){return Servidor;}

    public void setDireccion(String newVal){Direccion = newVal;}

    public void setServidor(String newVal){Servidor = newVal;}

    @Override
    public String getContactoStr() {return this.Direccion + "@" + this.Servidor;}

    @Override
    public enTipoContacto getTipo() {return enTipoContacto.Email;}

    @Override
    public void Registrar(int idPaciente) throws Exception {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int) xGB.ObtenerClave("email_paciente");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarEmailPaciente(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, this.Direccion);
        xGB.CrearParametro(3, enTipoParametro.Cadena, this.Servidor);
        xGB.CrearParametro(4, enTipoParametro.Entero, idPaciente);
        
        xGB.EjecutarComando();
    }

    @Override
    public void Eliminar() throws Exception {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarEmailPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        xGB.EjecutarComando();
    }
    
    
}//end clsEmailPaciente