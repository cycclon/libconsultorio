package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsTrabajoLazy;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:12 a.m.
 */
public class clsATrabajoNoRealizado extends clsAlertaTrabajo {

    public clsATrabajoNoRealizado(clsTrabajoLazy trabajo){
        super(trabajo);
    }

    @Override
    public enAlerta getAlerta() {return enAlerta.TrabajoNoRealizado;}

    @Override
    public String getNombreAlerta() {
        return "Trabajo no realizado";
    }

}//end clsATrabajoNoRealizado