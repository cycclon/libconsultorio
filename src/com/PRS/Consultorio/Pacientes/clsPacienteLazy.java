package com.PRS.Consultorio.Pacientes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 06-oct-2014 03:06:55 p.m.
 */
public class clsPacienteLazy {

    private final int ID;
    private final String Apellido;
    private final String Nombre;
    private final String NroDocumento;
    private final String ObraSocial;

    public clsPacienteLazy(){
        this.Apellido = "";
        this.Nombre = "";
        this.NroDocumento = "";
        this.ObraSocial = "";
        this.ID = 0;
    }

    clsPacienteLazy(ResultSet prRS) throws SQLException
    {
        this.Apellido = prRS.getString("pac_apellido");
        this.Nombre = prRS.getString("pac_nombre");
        this.NroDocumento = String.valueOf(prRS.getLong("pac_numero_documento"));
        this.ObraSocial = prRS.getString("os");
        this.ID = prRS.getInt("pac_id");
    }

    public String getApellido(){return Apellido;}

    public String getNombre(){return Nombre;}

    public String getNroDocumento(){return NroDocumento;}
    
    public String getObraSocial(){return ObraSocial;}
    
    @Override
    public String toString()
    {
        return this.Apellido + ", " + this.Nombre + " (" + this.NroDocumento 
                + ") - " + this.ObraSocial;
    }

    public clsPaciente getPacienteFull() throws Exception {
        clsPaciente xP = new clsParticular();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPacienteFull(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {
            xP = clsPaciente.Fabricar(crs);
        }
        return xP;
    }
}//end clsPacienteLazy