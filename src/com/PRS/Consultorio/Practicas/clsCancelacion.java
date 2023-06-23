package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 03:19:15 p.m.
 */
public class clsCancelacion {

    private Date Fecha;
    private int ID;
    private String Motivo;

    public clsCancelacion(){
        this.Fecha = new Date();
        this.ID = 0;
        this.Motivo = "";
    }
    
    clsCancelacion(Date fecha, String motivo, int IDTrabajo) throws Exception
    {
        this.Fecha = fecha;
        this.Motivo = motivo;
        this.Registrar(IDTrabajo);
    }
    
    clsCancelacion(ResultSet prRS) throws SQLException
    {
        this.Fecha = prRS.getTimestamp("can_fecha");
        this.ID = prRS.getInt("can_id");
        this.Motivo = prRS.getString("can_motivo");
    }
    
    private void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int)xGB.ObtenerClave("cancelacion_trabajo");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarCancelacion(?, ?, ?, ?)}}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Fecha, Fecha);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Motivo);
        xGB.CrearParametro(4, enTipoParametro.Entero, IDTrabajo);
        
        xGB.EjecutarComando();
        
    }     
    
    public static void Eliminar(int idTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarCancelacion(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, idTrabajo);
        
        xGB.EjecutarComando();
    }

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}

    public String getMotivo(){return Motivo;}

    public void setFecha(Date newVal){Fecha = newVal;}

    public void setMotivo(String newVal){Motivo = newVal;}
    
    public static List<String> ListarMotivos() throws Exception
    {
        List<String> xMotivos = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getMotivosCancelacion()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xMotivos.add(xRS.getString("can_motivo"));}
        
        return xMotivos;
    }
}//end clsCancelacion