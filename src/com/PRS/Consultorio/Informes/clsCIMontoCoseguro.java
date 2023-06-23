package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 05-nov-2014 09:49:25 a.m.
 */
public class clsCIMontoCoseguro implements iComponenteInforme {

    public clsCIMontoCoseguro(){

    }

    @Override
    public String getCodigo()
      throws Exception{
            return clsGestorInformes.Instanciar().getPrefijoComponente() + 
                "moncos" + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Monto de coseguro";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo)
      throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getCobroCoseguro()
                    .getMonto().pdValorString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIMontoCoseguro