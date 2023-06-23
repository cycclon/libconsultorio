package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 05-nov-2014 09:49:26 a.m.
 */
public class clsCIMontoAbonado implements iComponenteInforme {

    public clsCIMontoAbonado(){

    }

    @Override
    public String getCodigo() throws Exception
    {
        return clsGestorInformes.Instanciar().getPrefijoComponente() + 
                "monabo" + clsGestorInformes.Instanciar().getSufijoComponente();
    }

    @Override
    public String getNombre(){return "Monto abonado de coseguro";}

    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo)
      throws Exception{
            return plantilla.replace(getCodigo(), trabajo.getCobroCoseguro()
                    .getTotalPagado().pdValorString());
    }
    
    @Override
    public String toString(){return this.getNombre();}
}//end clsCIMontoAbonado