package com.PRS.Consultorio.Reportes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 03:55:55 p.m.
 */
public abstract class clsReporte {

    protected List<clsItemReporte> Items;
    protected List<clsParametroReporte> Parametros;

    public clsReporte(){
        this.Items = new ArrayList<>();
        this.Parametros = new ArrayList<>();
    }

    public abstract void Generar() throws Exception;

    public List<clsItemReporte> getItems(){return Items;}

    public String getResumenParametros()
    {
        String s = "Parámetros: ";
        for(clsParametroReporte p : Parametros)s += p.toString() + " - ";
        return s;
    }

    public abstract String getTitulo();

    public List<clsParametroReporte> getParametros(){return Parametros;}

    public void setParametros(List<clsParametroReporte> newVal){Parametros = newVal;}

    public abstract enTipoReporte getTipo();
    
    public abstract String getFXML();
    
    protected void DefinirParametro(enTipoParametroReporte parametro, Object valor)
    {
        for(clsParametroReporte pr : Parametros)
        {
            if(pr.getTipo()==parametro)
            {pr.setValue(valor);}
        }
    }
}//end clsReporte