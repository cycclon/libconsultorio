package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:23 p.m.
 */
public class clsCIHoraTurno implements iComponenteInforme {

    public clsCIHoraTurno(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
                "horatur"
                + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Hora del turno";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getProgramacion()
                    .getHorario().getHoraStr());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIHoraTurno