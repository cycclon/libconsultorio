package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsTrabajoLazy;
import java.text.SimpleDateFormat;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:11 a.m.
 */
public abstract class clsAlerta {
     

    public abstract String getAlertaStr();    

    public abstract int getIDElemento();
    

    public abstract enAlerta getAlerta();
    
    public abstract String getNombreAlerta();
}//end clsAlerta