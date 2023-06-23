package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;
import java.text.SimpleDateFormat;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:22 p.m.
 */
public class clsCIDiaProgramacion implements iComponenteInforme {

    public clsCIDiaProgramacion(){

    }

    @Override
    public String getCodigo() throws Exception{
            return clsGestorInformes.Instanciar().getPrefijoComponente() + 
                    "fechaturno"
                    + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Día del turno";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception{
        return plantilla.replace(this.getCodigo(), 
                new SimpleDateFormat("dd/MM/yyyy").format(trabajo
                        .getProgramacion().getFecha()));
    }
    
    @Override
    public String toString(){return this.getNombre();}
    
}//end clsCIDiaProgramacion