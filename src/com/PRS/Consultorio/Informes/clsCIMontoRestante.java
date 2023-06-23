package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 05-nov-2014 09:49:25 a.m.
 */
public class clsCIMontoRestante implements iComponenteInforme {

    public clsCIMontoRestante(){

    }

    @Override
    public String getCodigo() throws Exception{
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
            "monres" + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){
            return "Monto restante de coseguro";
    }

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo)
      throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getCobroCoseguro()
                    .getRestante().pdValorString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIMontoRestante