package com.PRS.Consultorio.Practicas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-dic-2014 11:13:03 p.m.
 */
public class clsHojaTurnos {

    private Date Fecha;
    private List<clsTrabajoLazy> TrabajosFirmados;

    public clsHojaTurnos(Date fecha) throws Exception{
        this.Fecha = fecha;
        this.TrabajosFirmados = new ArrayList<>();
        List<clsTrabajoLazy> ts = clsProgramador.Instanciar().getTurnos(fecha);
        for(clsTrabajoLazy t : ts)
        {
            if(t.getEstado().equals(enEstadoTrabajo.Firmado))TrabajosFirmados.add(t);
        }
    }
    public Date getFecha(){return Fecha;}

    public List<clsTrabajoLazy> getTrabajosFirmados(){return TrabajosFirmados;}
}//end clsHojaTurnos