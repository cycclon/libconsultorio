package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 27-oct-2014 10:22:49 a.m.
 */
public class clsCINombreSolicitante implements iComponenteInforme {

    public clsCINombreSolicitante(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "nomsol"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Nombre del solicitante";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
        return plantilla.replace(getCodigo(), trabajo.getSolicitud()
                .getSolicitante().getNombreCompleto());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCINombreSolicitante