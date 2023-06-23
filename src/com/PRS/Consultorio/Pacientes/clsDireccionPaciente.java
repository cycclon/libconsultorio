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
public class clsDireccionPaciente extends clsContactoPaciente {

    private String Altura;
    private String Calle;
    private String Localidad;
    private String PisoDpto;

    public clsDireccionPaciente(){this("", "", "", "");}
    
    public clsDireccionPaciente(String calle, String altura, String localidad)
    {this(calle, altura, localidad, "");}
    
    clsDireccionPaciente(ResultSet rs) throws SQLException
    {
        this.Altura = rs.getString("dipa_altura");
        this.Calle = rs.getString("dipa_calle");
        this.ID = rs.getInt("dipa_id");
        this.Localidad = rs.getString("dipa_localidad");
        this.PisoDpto = rs.getString("dipa_piso_dpto");
    }
    
    public clsDireccionPaciente(String calle, String altura, String pisoDpto, String localidad)
    {
        super();
        this.Altura = altura;
        this.Calle = calle;
        this.Localidad = localidad;
        this.PisoDpto = pisoDpto;
    }

    public String getAltura(){return Altura;}

    public String getCalle(){return Calle;}

    public String getLocalidad(){return Localidad;}

    public String getPisoDpto(){return PisoDpto;}

    public void setAltura(String newVal){Altura = newVal;}

    public void setCalle(String newVal){Calle = newVal;}

    public void setLocalidad(String newVal){Localidad = newVal;}

    public void setPisoDpto(String newVal){PisoDpto = newVal;}

    @Override
    public String getContactoStr() {
        return this.Calle + " " + this.Altura + " " + this.PisoDpto + ", " + this.Localidad;
    }

    @Override
    public enTipoContacto getTipo() {return enTipoContacto.Direccion;}

    @Override
    public void Registrar(int idPaciente) throws Exception {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int) xGB.ObtenerClave("direccion_paciente");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarDireccionPaciente(?, ?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Calle);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Altura);
        xGB.CrearParametro(4, enTipoParametro.Cadena, this.PisoDpto);
        xGB.CrearParametro(5, enTipoParametro.Cadena, Localidad);
        xGB.CrearParametro(6, enTipoParametro.Entero, idPaciente);
        
        xGB.EjecutarComando();
    }

    @Override
    public void Eliminar() throws Exception{
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarDireccionPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        xGB.EjecutarComando();
    }
    
    
}//end clsDireccionPaciente