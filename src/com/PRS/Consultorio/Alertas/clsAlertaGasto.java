package com.PRS.Consultorio.Alertas;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 18-nov-2014 02:59:47 p.m.
 */
public class clsAlertaGasto extends clsAlerta {
    
    private int IDGasto;
    private String ConceptoGasto;
    private Date FechaGasto;

    public clsAlertaGasto(){

    }

    @Override
    public enAlerta getAlerta() {return enAlerta.GastoNoEfectivo;}

    @Override
    public String getNombreAlerta() {
        return "No efectivizado";
    }

    @Override
    public String getAlertaStr() {
        return "Gasto en concepto de " + ConceptoGasto + " planificado para el: " 
                + new SimpleDateFormat("dd/MM/yyyy").format(FechaGasto) + ": " + this.getNombreAlerta();
    }

    @Override
    public int getIDElemento() {return IDGasto;}
}//end clsAlertaGasto