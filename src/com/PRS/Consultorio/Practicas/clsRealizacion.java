package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Profesionales.clsPracticante;
import com.PRS.Consultorio.Profesionales.clsProfesional;
import com.PRS.Consultorio.Profesionales.enTipoProfesional;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.util.Date;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 03:19:14 p.m.
 */
public class clsRealizacion {

    private Date Fecha;
    private int ID;
    private clsProfesional Realizador;
    private String Observaciones;

    public clsRealizacion(){
        this.Fecha = new Date();
        this.ID = 0;
        this.Realizador = new clsPracticante();
        this.Observaciones = "";
    }

    clsRealizacion(Date fecha, String observaciones, 
            int IDTrabajo) throws Exception
    {
        this();
        this.ControlarRealizador();
        this.Fecha = fecha;
        this.Observaciones = observaciones;            
        this.Registrar(IDTrabajo);
    }

    clsRealizacion(clsProfesional realizador, Date fecha, String observaciones, 
            int IDTrabajo) throws Exception
    {
        this.Fecha = fecha;
        this.ID = 0;
        this.Observaciones = observaciones;
        this.Realizador = realizador;
        this.Registrar(IDTrabajo);
    }

    clsRealizacion(ResultSet prRS) throws Exception
    {
        this.Fecha = prRS.getTimestamp("retr_fecha");
        this.ID = prRS.getInt("retr_id");
        this.Observaciones = prRS.getString("retr_observaciones");
        this.Realizador = clsProfesional.Fabricar(enTipoProfesional.valueOf(
                prRS.getString("prac_tipo")), prRS, "prac");            
    }

    private void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        this.ID = (int)xGB.ObtenerClave("realizacion_trabajo");

        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarRealizacion(?, ?, ? , ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        xGB.CrearParametro(2, enTipoParametro.Fecha, this.Fecha);
        xGB.CrearParametro(3, enTipoParametro.Entero, this.Realizador.getID());
        xGB.CrearParametro(4, enTipoParametro.Cadena, Observaciones);
        xGB.CrearParametro(5, enTipoParametro.Entero, IDTrabajo);

        xGB.EjecutarComando();
    }

    private void ControlarRealizador() throws exDefinirRealizador
    {if(this.Realizador.getID() == 0){throw new exDefinirRealizador();}}

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}

    public clsProfesional getRealizador(){return Realizador;}

    public void setFecha(Date newVal){Fecha = newVal;}

    public void setRealizador(clsProfesional newVal){Realizador = newVal;}

    public String getObservaciones(){return this.Observaciones;}

    public String getRealizadorStr()
    {
        if(this.Realizador.getID()>0)
        {return this.Realizador.getNombreCompleto();}
        else
        {return "No definido";}
    }
    
    public static void Eliminar(int idTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarRealizacion(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, idTrabajo);
        
        xGB.EjecutarComando();
    }
}//end clsRealizacion