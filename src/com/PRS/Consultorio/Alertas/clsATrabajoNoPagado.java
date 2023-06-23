package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsTrabajoLazy;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:14 a.m.
 */
public class clsATrabajoNoPagado extends clsAlertaTrabajo {

    public clsATrabajoNoPagado(clsTrabajoLazy trabajo){
        super(trabajo);
    }

    @Override
    public enAlerta getAlerta() {return enAlerta.TrabajoNoPagado;}

    @Override
    public String getNombreAlerta() 
    {return "Saldo deudor";}

}//end clsATrabajoNoPagado