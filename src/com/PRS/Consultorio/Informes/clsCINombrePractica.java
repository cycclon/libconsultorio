package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:22 p.m.
 */
public class clsCINombrePractica implements iComponenteInforme {

    public clsCINombrePractica(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "nompra"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Nombre de práctica";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getPractica().getNombreyCodigo());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCINombrePractica