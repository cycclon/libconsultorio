package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:23 p.m.
 */
public class clsCIObraSocialPaciente implements iComponenteInforme {

    public clsCIObraSocialPaciente(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "os"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Obra social del paciente";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getPaciente()
                    .getOS().toString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIObraSocialPaciente