package com.PRS.Consultorio.Gastos;

import com.PRS.Consultorio.Balances.iEgreso;
import com.PRS.Consultorio.Bitacoras.Gastos.bGastoEliminado;
import com.PRS.Consultorio.Bitacoras.Gastos.bGastoRegistrado;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.DynamicComparer.clsDynamicComparer;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 05-nov-2014 10:39:06 p.m.
 */
public class clsGasto implements iEgreso {  
    
    
    private abstract class clsFrecuenciaGasto
    {
        protected byte Intervalo;
        
        public abstract enFrecuencia getFrecuencia();
        
        public abstract String getFrecuenciaStr();
        
        public boolean Corresponde(byte mes, int ano, List<clsGasto> gastos, clsGasto gasto)
        {
            boolean flagEfec = false;
            boolean flagCorr = false;
            for(clsGasto xG : gastos)
            {                
                if(xG.ID == gasto.ID)
                {
                    flagEfec = true;
                }
            }
            if(!flagEfec)
            {
                Calendar cal = Calendar.getInstance();
                cal.setTime(gasto.Fecha);

                byte xM = (byte)(cal.get(Calendar.MONTH)+1);
                int xA = cal.get(Calendar.YEAR);

                while(xA <= ano)
                {
                    while(xM <= 12)
                    {
                        if(mes == xM && ano == xA)
                        {flagCorr = true;}
                        xM += Intervalo;                        
                    }

                    xM = (byte)(xM - 12);

                    xA++;
                }
            }
            return !flagEfec && flagCorr;
        }
    }  
    
    public class clsFGMensual extends clsFrecuenciaGasto
    {
        public clsFGMensual()
        {this.Intervalo = 1;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Mensual;}

        @Override
        public String getFrecuenciaStr() {return "Todos los meses";}

        
    }
    
    public class clsFGBimestral extends clsFrecuenciaGasto
    {
        
        public clsFGBimestral()
        {this.Intervalo = 2;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Bimestral;}

        @Override
        public String getFrecuenciaStr() {
            return "Cada dos meses";
        }      
        
    }
    
    public class clsFGTrimestral extends clsFrecuenciaGasto
    {
        public clsFGTrimestral()
        {this.Intervalo = 2;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Trimestral;}

        @Override
        public String getFrecuenciaStr() {return "Cada tres meses";}

        
    }
    
    public class clsFGCuatrimestral extends clsFrecuenciaGasto
    {
        
        public clsFGCuatrimestral()
        {this.Intervalo = 2;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Cuatrimestral;}

        @Override
        public String getFrecuenciaStr() {return "Cada cutro meses";}

        
    }
    
    public class clsFGSemestral extends clsFrecuenciaGasto
    {
        
        public clsFGSemestral()
        {this.Intervalo = 2;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Semestral;}

        @Override
        public String getFrecuenciaStr() {return "Cada seis meses";}

        
    }
    
    public class clsFGAnual extends clsFrecuenciaGasto
    {
        
        public clsFGAnual()
        {this.Intervalo = 12;}

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Anual;}

        @Override
        public String getFrecuenciaStr() {return "Una vez por año";}

        
    }
    
    public class clsFGUnico extends clsFrecuenciaGasto
    {

        @Override
        public enFrecuencia getFrecuencia() {return enFrecuencia.Único;}

        @Override
        public String getFrecuenciaStr() {return "Eventual";}

    }

    private List<clsAsignacionGasto> Asignacion;
    private String Concepto;
    private Date Fecha;
    private int ID;
    private clsValor Monto;
    private clsFrecuenciaGasto Frecuencia;
    private boolean Efectivo;
    private int IDEfectivizacion;
    private boolean EfectivizacionActiva;
    

    public clsGasto(){
        this.Asignacion = new ArrayList<>();
        this.Concepto = "";
        this.Fecha = new Date();
        this.ID = 0;
        this.Monto = new clsValor(0);
        this.Frecuencia = FabricarFrecuencia(enFrecuencia.Único);
        this.IDEfectivizacion = 0;
        this.EfectivizacionActiva = true;
    }
    
    public clsGasto(ResultSet rs) throws Exception
    {
        this();
        this.Concepto = rs.getString("gas_concepto");
        this.Fecha = rs.getTimestamp("gas_fecha");
        this.Frecuencia = FabricarFrecuencia(enFrecuencia
                .valueOf(rs.getString("gas_frecuencia")));
        this.ID = rs.getInt("gas_id");
        this.Monto = new clsValor(rs.getFloat("gas_monto"));
        this.Efectivo = true;
        this.ObtenerAsignacion();
    }
    
    private clsGasto(ResultSet rs, boolean efectivo) throws Exception
    {
        this();
        this.Concepto = rs.getString("gas_concepto");
        this.Fecha = rs.getTimestamp("efe_fecha");
        this.Frecuencia = FabricarFrecuencia( enFrecuencia
                .valueOf(rs.getString("gas_frecuencia")));
        this.ID = rs.getInt("gas_id");
        this.Monto = new clsValor(rs.getFloat("efe_monto"));
        this.Efectivo = true;
        this.IDEfectivizacion = rs.getInt("efe_id");
        this.EfectivizacionActiva = rs.getBoolean("efe_activo");
        this.ObtenerAsignacion();
    }
    
    private void ObtenerAsignacion() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getAsignacionGasto(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {this.Asignacion.add(new clsAsignacionGasto(crs));}
        
        
    }

    public List<clsAsignacionGasto> getAsignacion(){return Asignacion;}

    public String getConcepto(){return Concepto;}

    public Date getFecha(){return Fecha;}

    public int getID(){return ID;}

    public clsValor getMonto(){return Monto;}

    public void setAsignacion(List<clsAsignacionGasto> newVal){Asignacion = newVal;}

    public void setConcepto(String newVal){Concepto = newVal;}

    public void setFecha(Date newVal){Fecha = newVal;}

    public void setID(int newVal){ID = newVal;}

    public void setMonto(clsValor newVal){Monto = newVal;}
    
    public enFrecuencia getFrecuencia(){return this.Frecuencia.getFrecuencia();}
    
    public boolean isEfectivo(){return this.Efectivo;}
    
    public void Registrar(enFrecuencia frecuencia) throws Exception
    {
        this.Frecuencia = FabricarFrecuencia(frecuencia);
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int) xGB.ObtenerClave("gasto");
        xGB.CrearComando(CommandType.StoredProcedure, "{call RegistrarGasto(?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Moneda, Monto.pdValor());
        xGB.CrearParametro(3, enTipoParametro.Cadena, Concepto);
        xGB.CrearParametro(4, enTipoParametro.Fecha, Fecha);
        xGB.CrearParametro(5, enTipoParametro.Cadena, frecuencia.toString());
        
        xGB.EjecutarComando();
        
        for(clsAsignacionGasto xAG : Asignacion)
        {xAG.Registrar(ID);}
        
        new bGastoRegistrado(this).Registrar();
    }
    
    public static List<clsGasto> ListarEfectivos(Date dia) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosEfectivosFecha(?)}");
        xGB.CrearParametro(1, enTipoParametro.Fecha, dia);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xGs.add(new clsGasto(crs));}
        
        List<clsGasto> xEfectivos = ListarProgramadosEfectivos(dia);
        
        for(clsGasto xG : xEfectivos)
        {xGs.add(xG);}
        
        List<clsGasto> xPendientes = ListarProgramadosPendientes(dia, xGs);
        
        for(clsGasto xG : xPendientes)
        {xGs.add(xG);}
        
        Collections.sort(xGs, new clsDynamicComparer<>(clsGasto.class, 
                            "getFecha", 1));
        
        List<Integer> Indices = new ArrayList<>();
        for(int i = 0; i<xGs.size(); i++)
        {if(!xGs.get(i).EfectivizacionActiva){Indices.add(i);}}
        
        for(int i : Indices)xGs.remove(i);
        
        return xGs;
    }
    
    public static List<clsGasto> ListarEfectivos(byte mes, int ano) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosEfectivosMes(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, mes);
        xGB.CrearParametro(2, enTipoParametro.Entero, ano);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xGs.add(new clsGasto(crs));}
        
        List<clsGasto> xEfectivos = ListarProgramadosEfectivos(mes, ano);
        
        for(clsGasto xG : xEfectivos)
        {xGs.add(xG);}
        
        List<clsGasto> xPendientes = ListarProgramadosPendientes(mes, ano, xGs);
        
        for(clsGasto xG : xPendientes)
        {xGs.add(xG);}
        
        Collections.sort(xGs, new clsDynamicComparer<>(clsGasto.class, 
                            "getFecha", 1));
        
        List<Integer> Indices = new ArrayList<>();
        for(int i = 0; i<xGs.size(); i++)
        {if(!xGs.get(i).EfectivizacionActiva){Indices.add(i);}}
        
        for(int i : Indices)xGs.remove(i);
        
        return xGs;
    }
    
    private static List<clsGasto> ListarProgramadosEfectivos(Date dia) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosProgramadosEfectivosFecha(?)}");
        xGB.CrearParametro(1, enTipoParametro.Fecha, dia);
                
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xGs.add(new clsGasto(crs, true));}
        
        return xGs;
    }
    
    private static List<clsGasto> ListarProgramadosEfectivos(byte mes, int ano) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosProgramadosEfectivosMes(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, mes);
        xGB.CrearParametro(2, enTipoParametro.Entero, ano);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xGs.add(new clsGasto(crs, true));}
        
        return xGs;
    }
    
    private static List<clsGasto> ListarProgramadosPendientes(byte mes, int ano, 
            List<clsGasto> efectivos) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosProgramadosPendientes()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {
            clsGasto xG = new clsGasto(crs);
            if(xG.Corresponde(mes, ano, efectivos))
            {
                xG.Efectivo = false;
                Calendar cal = Calendar.getInstance();
                cal.setTime(xG.Fecha);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                
                cal.set(ano, mes-1, dia);
                xG.Fecha = cal.getTime();
                xGs.add(xG);
            }
        }
        
        return xGs;
    }
    
    private static List<clsGasto> ListarProgramadosPendientes(Date dia, 
            List<clsGasto> efectivos) throws Exception
    {
        List<clsGasto> xGs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getGastosProgramadosPendientes()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dia);
        
        byte mes = (byte)cal2.get(Calendar.MONTH);
        int ano = cal2.get(Calendar.YEAR);
        
        while(crs.next())
        {
            clsGasto xG = new clsGasto(crs);
            if(xG.Corresponde(mes, ano, efectivos))
            {
                xG.Efectivo = false;
                Calendar cal = Calendar.getInstance();
                cal.setTime(xG.Fecha);
                int dia2 = cal.get(Calendar.DAY_OF_MONTH);
                
                cal.set(ano, mes-1, dia2);
                xG.Fecha = cal.getTime();
                xGs.add(xG);
            }
        }
        
        return xGs;
    }
   
    public String getFrecuenciaStr()
    {return this.Frecuencia.getFrecuenciaStr();}
    
    private clsFrecuenciaGasto FabricarFrecuencia(enFrecuencia frecuencia)
    {
        switch(frecuencia)
        {
            case Anual:
                return new clsFGAnual();
            case Bimestral:
                return new clsFGBimestral();
            case Cuatrimestral:
                return new clsFGCuatrimestral();
            case Mensual:
                return new clsFGMensual();
            case Semestral:
                return new clsFGSemestral();
            case Trimestral:
                return new clsFGTrimestral();
            case Único:
                return new clsFGUnico();
            default:
                return new clsFGUnico();
        }
    }
    
    private boolean Corresponde(byte mes, int ano, List<clsGasto> efectivos)
    {return this.Frecuencia.Corresponde(mes, ano, efectivos, this);}
    
    public void Efectivizar(Date fecha, float monto) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        int IDEfectivizacion = (int)xGB.ObtenerClave("efectivizacion_gasto");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarEfectivizacionGasto(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, IDEfectivizacion);
        xGB.CrearParametro(2, enTipoParametro.Entero, ID);
        xGB.CrearParametro(3, enTipoParametro.Fecha, fecha);
        xGB.CrearParametro(4, enTipoParametro.Moneda, monto);
        
        xGB.EjecutarComando();
    }
    
    public boolean isProgramado()
    {return this.Frecuencia.getFrecuencia() != enFrecuencia.Único;}
    
    public void Delete() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        if(IDEfectivizacion > 0)
        {
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call EliminarEfectivizacionGasto(?)}");
            xGB.CrearParametro(1, enTipoParametro.Entero, IDEfectivizacion);
        }
        else
        {
            if(this.Frecuencia.getFrecuencia() != enFrecuencia.Único)
            {
                xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call EliminarGasto(?)}");
                xGB.CrearParametro(1, enTipoParametro.Entero, ID);
            }
            else
            {
                xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call EliminarGastoDefinitivo(?)}");
                xGB.CrearParametro(1, enTipoParametro.Entero, ID);
            }
        }
        
        xGB.EjecutarComando();
        
        new bGastoEliminado(this).Registrar();
    }
    
    public void DeleteAll() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call EliminarGasto(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.EjecutarComando();
        
        new bGastoEliminado(this).Registrar();
    }
    
    public boolean isPlantilla()
    {return this.isProgramado() && this.IDEfectivizacion == 0;}
    
    @Override
    public String getConceptoBalance() {return this.Concepto;}
    
    @Override
    public String getConceptoAbajo() {return "";}


    @Override
    public Date getFechaBalance() {return this.Fecha;}

    @Override
    public clsValor getMontoBalance() {return this.Monto;}
}//end clsGasto