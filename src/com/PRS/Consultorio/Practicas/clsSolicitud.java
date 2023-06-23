package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Profesionales.clsProfesional;
import com.PRS.Consultorio.Profesionales.clsSolicitante;
import java.sql.ResultSet;
import java.util.Date;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 03:19:16 p.m.
 */
public class clsSolicitud {

    private Date Fecha;
    private int ID;
    private clsSolicitante Solicitante;

    public clsSolicitud(clsProfesional solicitante){
        this.Fecha = new Date();
        this.ID = 0;
        this.Solicitante = (clsSolicitante)solicitante;
    }
    
    clsSolicitud(ResultSet prRS) throws Exception
    {
        this.Fecha = prRS.getTimestamp("sol_fecha");
        this.ID = prRS.getInt("sol_id");
        this.Solicitante = new clsSolicitante(prRS, "soli");
    }

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}
    
    public void setID(int id){ID = id;}

    public clsSolicitante getSolicitante(){return Solicitante;}

    public void setFecha(Date newVal){Fecha = newVal;}

    public void setSolicitante(clsSolicitante newVal){Solicitante = newVal;}
}//end clsSolicitud