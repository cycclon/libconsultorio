package com.PRS.Consultorio.Practicas;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 10:08:00 p.m.
 */
public class clsNoRealizacion {

    private int ID;
    private String Motivo;

    public clsNoRealizacion(){
        Motivo = "";
    }

    clsNoRealizacion(String motivo, int IDTrabajo) throws Exception
    {
        this.Motivo = motivo;
        this.Registrar(IDTrabajo);
    }
    
    clsNoRealizacion(ResultSet prRS) throws SQLException
    {
        this.ID = prRS.getInt("nore_id");
        this.Motivo = prRS.getString("nore_motivo");
    }

    private void Registrar(int IDTrabajo) throws Exception
    {}

    public int getID(){return ID;}

    public String getMotivo(){return Motivo;}

    public void setMotivo(String newVal){Motivo = newVal;}
}//end clsNoRealizacion