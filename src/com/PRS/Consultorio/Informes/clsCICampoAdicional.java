package com.PRS.Consultorio.Informes;
import com.PRS.Consultorio.Practicas.clsCampoTrabajo;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import com.PRS.Consultorio.Practicas.clsValorCampo;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 04-nov-2014 11:13:16 p.m.
 */
public class clsCICampoAdicional implements iComponenteInforme {

    private clsCampoTrabajo Campo;
    
    public clsCICampoAdicional(clsCampoTrabajo campo){
        this.Campo = campo;
    }

    @Override
    public String getCodigo()
      throws Exception{return Campo.getCodigoInforme();}

    @Override
    public String getNombre(){return Campo.getNombre();}


    @Override
    public String Procesar(String plantilla, clsTrabajo trabajo)
      throws Exception{
            String str = plantilla;
            
            for(clsValorCampo xVC : trabajo.getCampos())
            {
                if(xVC.getCampo().getID() == this.Campo.getID())
                {str = plantilla.replace(this.getCodigo(), xVC.getValor());}
            }
            
            return str;
    }
    
    @Override
    public String toString()
    {return this.getNombre();}
}//end clsCICampoAdicional