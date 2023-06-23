package com.PRS.Consultorio.Alertas;
import com.PRS.Consultorio.Practicas.clsTrabajoLazy;
import java.text.SimpleDateFormat;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 18-nov-2014 02:59:44 p.m.
 */
public abstract class clsAlertaTrabajo extends clsAlerta {

    protected clsTrabajoLazy Trabajo;

    public clsAlertaTrabajo(clsTrabajoLazy trabajo){
         this.Trabajo = trabajo;
    }
    
    @Override
    public String getAlertaStr()
    {return this.Trabajo.getNombrePractica() +  " del día " + 
            new SimpleDateFormat("dd/MM/yyyy").format(Trabajo.getDia()) + 
                    " a las " + Trabajo.getHorario() + " para " 
            + this.Trabajo.getPaciente() + ": " + this.getNombreAlerta();}
    
    @Override
    public int getIDElemento(){return Trabajo.getID();}
}//end clsAlertaTrabajo