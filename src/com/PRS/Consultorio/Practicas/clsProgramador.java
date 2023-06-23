package com.PRS.Consultorio.Practicas;

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
 * @created 11-oct-2014 09:58:55 p.m.
 */
public class clsProgramador {
    
    private static final String sql = "";
    
    public static abstract class clsFiltoTrabajoLazy
    {
              
        public List<clsTrabajoLazy> Filtrar(List<clsTrabajoLazy> trabajos)
        {
            List<clsTrabajoLazy> xTs = new ArrayList<>();
            for(clsTrabajoLazy xTL : trabajos)
            {if(this.Filtro(xTL)){xTs.add(xTL);}}
            return xTs;
        }
        
        protected abstract boolean Filtro(clsTrabajoLazy trabajo);
    }
    
    public static class clsFTLCC extends clsFiltoTrabajoLazy
    {
        protected String CC;
        
        public clsFTLCC(String cc)
        {CC = cc;}

        @Override
        protected boolean Filtro(clsTrabajoLazy trabajo) {
            return trabajo.getCC().contains(CC);
        }
        
    }
    
    public static class clsFTLRealizador extends clsFiltoTrabajoLazy
    {
        
        protected String Realizador;
        
        public clsFTLRealizador(String realizador)
        {Realizador = realizador;}

        @Override
        protected boolean Filtro(clsTrabajoLazy trabajo) {
            return trabajo.getPracticante().contains(Realizador);
        }
    }
    
    public static class clsFTLPaciente extends clsFiltoTrabajoLazy
    {

        protected int IDPaciente;
        
        public clsFTLPaciente(int paciente)
        {IDPaciente = paciente;}
        
        @Override
        protected boolean Filtro(clsTrabajoLazy trabajo) 
        {
            return trabajo.getIDPaciente() == IDPaciente;
        }
    }
    
    public static class clsFTLFechaMaxima extends clsFiltoTrabajoLazy
    {
        
        protected Date Fecha;
        
        public clsFTLFechaMaxima(Date fecha)
        {Fecha = fecha;}

        @Override
        protected boolean Filtro(clsTrabajoLazy trabajo) {
            return trabajo.getDia().before(Fecha);
        }
        
    }

    private static clsProgramador Instancia;
    private final List<clsTrabajoLazy> Turnos;

    private clsProgramador(){
        this.Turnos = new ArrayList<>();
    }

    public static clsProgramador Instanciar(){
        if(Instancia == null){Instancia = new clsProgramador();}
        return Instancia;
    }
    
    public List<clsTrabajoLazy> getHistoriaClinica(int IDPaciente) throws Exception
    {
        List<clsTrabajoLazy> xTs = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getHistoriaClinica(?)}");

        xGB.CrearParametro(1, enTipoParametro.Entero, IDPaciente);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xTs.add(new clsTrabajoLazy(xRS));}
        
        return xTs;
    }
    
    public List<clsTrabajoLazy> getTurnos(Date dia) throws Exception
    {
        List<clsTrabajoLazy> xTs = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);     
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getTurnos(?, ?)}");
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dia);
        xGB.CrearParametro(1, enTipoParametro.Fecha, dia);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        xGB.CrearParametro(2, enTipoParametro.Fecha, cal.getTime());
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xTs.add(new clsTrabajoLazy(xRS));}
        
        return xTs;
    }
    
    public List<clsTrabajoLazy> Filtrar(List<clsTrabajoLazy> trabajos, 
            List<clsFiltoTrabajoLazy> filtros)
    {
        List<clsTrabajoLazy> xTs = trabajos;
        for(clsFiltoTrabajoLazy xF : filtros)
        {xTs = xF.Filtrar(xTs);}
        return xTs;
    }
    
    private void ActualizarListado() throws Exception
    {
        this.Turnos.clear();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getTrabajosLazy()}");      
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {Turnos.add(new clsTrabajoLazy(xRS));}
    }
    
    public List<clsTrabajoLazy> ListarPasados() throws Exception
    {
        ActualizarListado();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        clsFTLFechaMaxima xFecha = new clsFTLFechaMaxima(cal.getTime());
        List<clsFiltoTrabajoLazy> xFs = new ArrayList<>();
        xFs.add(xFecha);
        
        return this.Filtrar(Turnos, xFs);
    }
}//end clsProgramador