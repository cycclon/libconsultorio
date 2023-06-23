package com.PRS.Consultorio.Reportes;

import java.util.ArrayList;
import java.util.List;



/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 12:31:40 p.m.
 */
public class clsGestorReportes {
    
    private final List<clsReporte> Reportes;
    
    private static clsGestorReportes Instancia;

	private clsGestorReportes(){
            Reportes = new ArrayList<>();
            Reportes.add(new clsRDineroProfesional());
            Reportes.add(new clsRDineroCC());
	}
        
    public static clsGestorReportes Instanciar()
    {if(Instancia == null){Instancia = new clsGestorReportes();} return Instancia;}
    
    public List<clsReporte> getReportes()
    {return Reportes;}
}//end clsGestorReportes