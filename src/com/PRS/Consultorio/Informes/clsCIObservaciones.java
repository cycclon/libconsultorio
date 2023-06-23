package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 27-oct-2014 10:22:51 a.m.
 */
public class clsCIObservaciones implements iComponenteInforme {

    public clsCIObservaciones(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "obs"
            + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Observaciones del trabajo";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
        String rtn;
        rtn = plantilla.replace(getCodigo(),  
                trabajo.getRealizacion().getObservaciones());
        
        return rtn;
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIObservaciones