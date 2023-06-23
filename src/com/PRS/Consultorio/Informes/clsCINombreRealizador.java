package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:24 p.m.
 */
public class clsCINombreRealizador implements iComponenteInforme {

    public clsCINombreRealizador(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "nomrea"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Nombre de realizador";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getRealizacion()
                    .getRealizador().getNombreCompleto());
    }

    @Override
    public String toString(){return this.getNombre();}
}//end clsCINombreRealizador