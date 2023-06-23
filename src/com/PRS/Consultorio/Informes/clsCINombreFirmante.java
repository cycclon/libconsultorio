package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 27-oct-2014 10:22:50 a.m.
 */
public class clsCINombreFirmante implements iComponenteInforme {

    public clsCINombreFirmante(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "nomfir"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Nombre del firmante";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getFirma()
                    .getFirmante().getNombreCompleto());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCINombreFirmante