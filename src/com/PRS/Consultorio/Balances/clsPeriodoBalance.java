package com.PRS.Consultorio.Balances;

import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 03:35:17 p.m.
 */
public abstract class clsPeriodoBalance {

    protected Short Ano;

    public clsPeriodoBalance(short ano){
        this.Ano = ano;
    }

    public Short getAno(){
            return Ano;
    }

    public abstract enPeriodoBalance getPeriodo();

    abstract List<iEgreso> ObtenerEgresos() throws Exception;

    abstract List<iIngreso> ObtenerIngresos() throws Exception;

    public void setAno(Short newVal){Ano = newVal;}
    
    public abstract String getNombre();
}//end clsPeriodoBalance