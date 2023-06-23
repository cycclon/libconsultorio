package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 27-oct-2014 10:22:50 a.m.
 */
public class clsCIDocumentoFirmante implements iComponenteInforme {

    public clsCIDocumentoFirmante(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "docfir"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Número de documento del firmante";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getFirma()
                    .getFirmante().getDocumento().toString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIDocumentoFirmante