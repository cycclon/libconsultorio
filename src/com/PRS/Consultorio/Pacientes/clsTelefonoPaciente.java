package com.PRS.Consultorio.Pacientes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 31-oct-2014 03:11:47 p.m.
 */
public abstract class clsTelefonoPaciente extends clsContactoPaciente {

    public static clsTelefonoPaciente Fabricar(enTipoTelefono tipo) {
        switch(tipo)
        {
            case Celular:
                return new clsTelefonoPacienteCelular();
            case Fijo:
                return new clsTelefonoPacienteFijo();
            default:
                return new clsTelefonoPacienteFijo();
        }
    }

    protected String Telefono;
    
    static clsTelefonoPaciente Fabricar(ResultSet rs, enTipoTelefono tipo) 
            throws SQLException
    {
        switch(tipo)
        {
            case Celular:
                return new clsTelefonoPacienteCelular(rs);
            case Fijo:
                return new clsTelefonoPacienteFijo(rs);
            default:
                return new clsTelefonoPacienteFijo(rs);                    
        }
    }

    public clsTelefonoPaciente(){
        this.Telefono = "";
    }
    
    protected clsTelefonoPaciente(ResultSet rs) throws SQLException
    {
        this.ID = rs.getInt("tepa_id");
        this.Telefono = rs.getString("tepa_telefono");
    }
    
    public String getTelefono(){return Telefono;}

    public abstract enTipoTelefono getTipoTelefono();

    public void setTelefono(String newVal){Telefono = newVal;}
    
    @Override
    public enTipoContacto getTipo() {return enTipoContacto.Telefono;}
    
    @Override
    public String getContactoStr() {
        return "Teléfono " + this.getTipoTelefono().toString().toLowerCase() + 
                ": " + this.Telefono;        
    } 
    
    @Override
    public void Registrar(int idPaciente) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int) xGB.ObtenerClave("telefono_paciente");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarTelefonoPaciente(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, getTipoTelefono().toString());
        xGB.CrearParametro(3, enTipoParametro.Cadena, Telefono);
        xGB.CrearParametro(4, enTipoParametro.Entero, idPaciente);
        
        xGB.EjecutarComando();
    }
    
    @Override
    public void Eliminar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarTelefonoPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        xGB.EjecutarComando();
    }
}//end clsTelefonoPaciente