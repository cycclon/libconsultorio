package com.PRS.Consultorio.Alertas;

import com.PRS.Consultorio.Practicas.clsProgramador;
import com.PRS.Consultorio.Practicas.clsTrabajoLazy;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 12-nov-2014 11:37:10 a.m.
 */
public class clsGestorAlertas {

    private List<clsAlerta> Alertas;
    private static clsGestorAlertas Instancia;


    private clsGestorAlertas() throws Exception{
        Alertas = new ArrayList<>();
        this.ActualizarAlertasTrabajo();
        this.ActualizarAlertasGastos();
    }

    private void ActualizarAlertasTrabajo() throws Exception{
        Alertas.clear();
        
        List<clsTrabajoLazy> Pasados = clsProgramador.Instanciar().ListarPasados();
        for(clsTrabajoLazy xTL : Pasados)
        {
            if(xTL.isPagable()){Alertas.add(new clsATrabajoNoPagado(xTL));}
            if(xTL.isTrabajable()){Alertas.add(new clsATrabajoNoRealizado(xTL));}
            if(xTL.isFirmable()){Alertas.add(new clsATrabajoNoFirmado(xTL));}            
        }
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getCamposFaltantes()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            for(clsTrabajoLazy xTL : Pasados)
            {
                if(xTL.getID() == xRS.getInt("vaca_tra_id"))
                {Alertas.add(new clsACamposFaltantes(xTL, xRS.getString("catr_nombre")));}
            }
        }
        
    }
    
    private void ActualizarAlertasGastos() throws Exception
    {       
        /*SELECT * FROM gasto WHERE gas_id NOT IN (SELECT efe_gas_id FROM 
        efectivizacion_gasto WHERE MONTH(efe_fecha) = 11 AND YEAR(efe_fecha)=2014)*/
        
    }

    public static clsGestorAlertas Instanciar() throws Exception{
        if(Instancia == null){Instancia = new clsGestorAlertas();}
        return Instancia;
    }
    
    public List<clsAlerta> ObtenerAlertas(enAlerta alerta) throws Exception
    {
        List<clsAlerta> xAs = new ArrayList<>();
        
        for(clsAlerta xA : Alertas)
        {if(xA.getAlerta() == alerta){xAs.add(xA);}}
        
        return xAs;
    }
    
    public List<clsAlerta> ObtenerAlertasGastos() throws Exception
    {
        this.ActualizarAlertasGastos();
        List<clsAlerta> xAs = ObtenerAlertas(enAlerta.GastoNoEfectivo);
        
        return xAs;
    }
    
    public List<clsAlerta> ObtenerAlertasTrabajo() throws Exception
    {
        this.ActualizarAlertasTrabajo();
        List<clsAlerta> xAs = new ArrayList<>();
        
        List<clsAlerta> xACF = ObtenerAlertas(enAlerta.CamposFaltantes);
        List<clsAlerta> xATNF = ObtenerAlertas(enAlerta.TrabajoNoFirmado);
        List<clsAlerta> xATNP = ObtenerAlertas(enAlerta.TrabajoNoPagado);
        List<clsAlerta> xATNR = ObtenerAlertas(enAlerta.TrabajoNoRealizado);
        
        for(clsAlerta xA : xACF)
        {xAs.add(xA);}
        for(clsAlerta xA : xATNF)
        {xAs.add(xA);}
        for(clsAlerta xA : xATNP)
        {xAs.add(xA);}
        for(clsAlerta xA : xATNR)
        {xAs.add(xA);}
        
        return xAs;
    }
}//end clsGestorAlertas