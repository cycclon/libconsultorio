package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.ObrasSociales.clsOS;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 24-sep-2014 03:42:10 p.m.
 */
public class clsHistorialValorPractica {

    private List<clsValorPractica> Historial;

    public clsHistorialValorPractica(){
        this.Historial = new ArrayList<>();
    }
    
    public clsHistorialValorPractica(short IDPractica) throws Exception
    {
        this();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getValoresPracticaOS(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, IDPractica);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {this.Historial.add(new clsValorPractica(xRS));}
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getCostoParticular(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, IDPractica);
        
        xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {this.Historial.add(new clsValorPractica(xRS, true));}
        
    }

    public List<clsValorPractica> getHistorial(){return Historial;}
    
    public List<clsValorPractica> getValoresVigentes(Date prFecha) throws Exception
    {
        List<clsValorPractica> Vigentes = new ArrayList<>();
        List<clsOS> xOSs = clsOS.Listar(true);
        xOSs.add(0, new clsOS());
        Calendar cal = Calendar.getInstance();
        cal.setTime(prFecha);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        
        for(clsOS xOS : xOSs)
        {
            clsValorPractica valor = new clsValorPractica(xOS);
            for(clsValorPractica xVP : Historial)
            {
                
                if(xVP.getVigencia().before(cal.getTime())&&
                        xVP.getVigencia().after(valor.getVigencia())&&
                        xOS.getID() == xVP.getOS().getID())
                {valor = xVP;}
            }
            Vigentes.add(valor);
        }
        
        return Vigentes;
    }
    
    public clsValorPractica getValorVigente(clsOS os, Date fecha) throws Exception
    {
        List<clsValorPractica> xValoresFecha = this.getValoresVigentes(fecha);
        clsValorPractica xValor = new clsValorPractica();
        for(clsValorPractica xVP : xValoresFecha)
        {
            if(xVP.getOS().getID()==os.getID())
            {xValor = xVP;}
        }
        
        return xValor;
    }
}//end clsHistorialValorPractica