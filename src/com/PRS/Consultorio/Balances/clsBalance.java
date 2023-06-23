package com.PRS.Consultorio.Balances;

import com.PRS.Framework.Monedas.clsValor;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 03:35:13 p.m.
 */
public class clsBalance {

    private final List<iEgreso> Egresos;
    private final List<iIngreso> Ingresos;
    private final clsPeriodoBalance Periodo;

    public clsBalance(clsPeriodoBalance periodo) throws Exception{
        this.Periodo = periodo;
        this.Egresos = Periodo.ObtenerEgresos();
        this.Ingresos = Periodo.ObtenerIngresos();
    }

    public clsValor getBalance(){
        return new clsValor(this.getTotalIngresos().pdValor() 
                - this.getTotalEgresos().pdValor());
    }

    public List<iEgreso> getEgresos(){return Egresos;}

    public List<iIngreso> getIngresos(){return Ingresos;}

    public String getNombre(){return "Balance de " + Periodo.getNombre();}
        
    public clsValor getTotalIngresos()
    {
        float ingresos = 0;        
        for(iIngreso i : Ingresos)ingresos+=i.getMontoBalance().pdValor();        
        return new clsValor(ingresos);
    }
    
    public clsValor getTotalEgresos()
    {
        float egresos = 0;        
        for(iEgreso i : Egresos)egresos+=i.getMontoBalance().pdValor();        
        return new clsValor(egresos);
    }
}//end clsBalance