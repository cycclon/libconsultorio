package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Profesionales.clsFirmante;
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
public class clsFirma {

    private clsFirmante Firmante;
    private int ID;
    private Date Fecha;

    public clsFirma(){
        this.Firmante = new clsFirmante();
        this.ID = 0;
        this.Fecha = new Date();
    }
    
    clsFirma(Date fecha, clsFirmante firmante, int IDTrabajo) throws Exception
    {
        this();
        this.Fecha = fecha;
        this.Firmante = firmante;
        this.Registrar(IDTrabajo);
    }
    
    clsFirma(ResultSet prRS) throws Exception
    {
        this.Fecha = prRS.getTimestamp("fir_fecha");
        this.Firmante = new clsFirmante(prRS, "firm");
        this.ID = prRS.getInt("fir_id");
    }
    
    private void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int)xGB.ObtenerClave("firma_trabajo");
        
        xGB.CrearComando(CommandType.Text, "{call RegistrarFirma(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(3, enTipoParametro.Fecha, Fecha);
        xGB.CrearParametro(2, enTipoParametro.Entero, Firmante.getID());
        xGB.CrearParametro(4, enTipoParametro.Entero, IDTrabajo);
        
        xGB.EjecutarComando();
    }

    public clsFirmante getFirmante(){return Firmante;}

    public int getID(){return ID;}

    public void setFirmante(clsFirmante newVal){Firmante = newVal;}
    
    public Date getFecha(){return Fecha;}
    
    public String getFirmanteStr()
    {
        if(this.Firmante.getID()>0)
        {return this.Firmante.getNombreCompleto();}
        else{return "No definido";}
    }
}//end clsFirma