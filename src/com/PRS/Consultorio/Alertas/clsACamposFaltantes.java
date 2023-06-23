package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsTrabajoLazy;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:13 a.m.
 */
public class clsACamposFaltantes extends clsAlertaTrabajo {
    
    private String NombreCampo;

    public clsACamposFaltantes(clsTrabajoLazy trabajo, String nombre){
        super(trabajo);
        this.NombreCampo = nombre;
    }
    
    @Override
    public enAlerta getAlerta() 
    {return enAlerta.CamposFaltantes;}

    @Override
    public String getNombreAlerta() {
        return "Campos obligatorios faltantes: " + this.NombreCampo;
    }
}//end clsACamposFaltantes