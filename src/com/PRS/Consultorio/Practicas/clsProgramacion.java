package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Horarios.clsHorario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 03:19:13 p.m.
 */
public class clsProgramacion {

    private Date Fecha;
    private clsHorario Horario;
    private int ID;

    public clsProgramacion(){
        this.Fecha = new Date();
        this.ID = 0;
        this.Horario = new clsHorario();
    }

    clsProgramacion(Date fecha,clsHorario hora, int IDTrabajo) throws Exception
    {
        this();
        this.Fecha = fecha;        
        this.Horario = hora;
        this.Registrar(IDTrabajo); 
    }
    
    clsProgramacion(ResultSet prRS) throws SQLException
    {
        this();
        Calendar cal = Calendar.getInstance();
        cal.setTime(prRS.getTimestamp("prog_fecha"));
        this.Fecha = cal.getTime();
        this.Horario.setHoras((byte)cal.get(Calendar.HOUR_OF_DAY));
        this.Horario.setMinutos((byte)cal.get(Calendar.MINUTE));
        this.ID = prRS.getInt("prog_id");
    }
    
    private void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        this.ID = (int)xGB.ObtenerClave("programacion_trabajo");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarProgramacionTrabajo(?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(Fecha);
        cal.set(Calendar.HOUR, Horario.getHoras());
        cal.set(Calendar.MINUTE, Horario.getMinutos());
        xGB.CrearParametro(2, enTipoParametro.Fecha, cal.getTime());
        xGB.CrearParametro(3, enTipoParametro.Entero, IDTrabajo);
        
        xGB.EjecutarComando();
    }

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}

    public void setFecha(Date newVal){Fecha = newVal;}
    
    public clsHorario getHorario(){return Horario;}
    
    public void setHorario(clsHorario hora){this.Horario = hora;}
}//end clsProgramacion