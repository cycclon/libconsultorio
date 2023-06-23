package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:24 p.m.
 */
public class clsCIDocumentoPaciente implements iComponenteInforme {

    public clsCIDocumentoPaciente(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
                "docpac"
                + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Número de documento de paciente";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
        return plantilla.replace(this.getCodigo(), trabajo.getPaciente()
                .getDocumento().toString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIDocumentoPaciente