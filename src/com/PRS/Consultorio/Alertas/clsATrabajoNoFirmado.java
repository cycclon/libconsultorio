package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsTrabajoLazy;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:12 a.m.
 */
public class clsATrabajoNoFirmado extends clsAlertaTrabajo {

    public clsATrabajoNoFirmado(clsTrabajoLazy trabajo){
        super(trabajo);
    }

    @Override
    public enAlerta getAlerta() {return enAlerta.TrabajoNoFirmado;}

    @Override
    public String getNombreAlerta() {
        return "Trabajo no firmado";
    }

}//end clsATrabajoNoFirmado