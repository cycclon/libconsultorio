package com.PRS.Consultorio.Gastos;

import java.util.List;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 05-nov-2014 10:39:09 p.m.
 */
public class clsGestorGastos {

	private static clsGestorGastos Instancia;

	private clsGestorGastos(){
            
	}

	public static clsGestorGastos Instanciar(){
            if(Instancia == null){Instancia = new clsGestorGastos();}
            return Instancia;
	}

    public float ComprobarAsignaciones(List<clsAsignacionGasto> asignaciones) {
        float xF = 0;
        for(clsAsignacionGasto xAG : asignaciones)
        {xF += xAG.getPorcentaje();}
        return xF;
    }
}//end clsGestorGastos