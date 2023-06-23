package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:21 p.m.
 */
public class clsCINombrePaciente implements iComponenteInforme {

    public clsCINombrePaciente(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "nompac"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Nombre del paciente";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(this.getCodigo(), trabajo.getPaciente()
                    .getNombreCompleto());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCINombrePaciente