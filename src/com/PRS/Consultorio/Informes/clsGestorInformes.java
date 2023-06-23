package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsCampoTrabajo;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import com.PRS.Framework.DynamicComparer.clsDynamicComparer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:20 p.m.
 */
public class clsGestorInformes {

    private static clsGestorInformes Instancia;
    private final List<iComponenteInforme> ComponentesDisponibles;
    
    public final void ActualizarComponentes() throws Exception    {
        
        this.ComponentesDisponibles.clear();
        
        this.ComponentesDisponibles.add(new clsCIDiaProgramacion());
        this.ComponentesDisponibles.add(new clsCIDocumentoPaciente());
        this.ComponentesDisponibles.add(new clsCIHoraTurno());
        this.ComponentesDisponibles.add(new clsCINombrePaciente());
        this.ComponentesDisponibles.add(new clsCINombrePractica());
        this.ComponentesDisponibles.add(new clsCINombreRealizador());
        this.ComponentesDisponibles.add(new clsCIObraSocialPaciente());
        this.ComponentesDisponibles.add(new clsCINombreSolicitante());
        this.ComponentesDisponibles.add(new clsCIObservaciones());
        this.ComponentesDisponibles.add(new clsCIDocumentoFirmante());
        this.ComponentesDisponibles.add(new clsCINombreFirmante());
        this.ComponentesDisponibles.add(new clsCIMontoAbonado());
        this.ComponentesDisponibles.add(new clsCIMontoCoseguro());
        this.ComponentesDisponibles.add(new clsCIMontoRestante());
        
        List<clsCampoTrabajo> xCs = clsCampoTrabajo.Listar(false);
        for(clsCampoTrabajo xC : xCs)
        {this.ComponentesDisponibles.add(new clsCICampoAdicional(xC));}
        
        Collections.sort(ComponentesDisponibles, new clsDynamicComparer<>(iComponenteInforme.class, 
                            "getNombre", 1));
    }

    private clsGestorInformes() throws Exception{
        this.ComponentesDisponibles = new ArrayList<>();
        this.ActualizarComponentes();
    }

    public List<iComponenteInforme> getComponentesDisponibles(){
        return ComponentesDisponibles;
    }

    public static clsGestorInformes Instanciar() throws Exception{
        if(Instancia == null){Instancia = new clsGestorInformes();}
        return Instancia;
    }

    public clsInforme ObtenerInforme(clsTrabajo trabajo, String path, clsPlantilla plantilla)
            throws Exception{
        return new clsInforme(trabajo, path, plantilla);
    }
    
    public String getSufijoComponente(){return "-*";}
    public String getPrefijoComponente(){return "*-";}
}//end clsGestorInformes