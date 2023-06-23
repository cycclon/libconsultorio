package com.PRS.Consultorio.Balances;

import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.Gastos.clsGasto;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 03:35:20 p.m.
 */
public class clsPeriodoSemanal extends clsPeriodoBalance {

    private byte Semana;

    public clsPeriodoSemanal(byte semana, short ano){
        super(ano);
        this.Semana = semana;
    }

    public byte getSemana(){return Semana;}

    public void setSemana(byte newVal){Semana = newVal;}

    @Override
    public enPeriodoBalance getPeriodo() {return enPeriodoBalance.Semanal;}

    @Override
    List<iEgreso> ObtenerEgresos() throws Exception {
        byte Mes = 12;
        Calendar cal = Calendar.getInstance();
        cal.set(Ano, 1, 1);
        
        for(int i = 1; i<=12; i++)
        {            
            cal.set(Ano, i, 1);            
            if(Semana <= cal.get(Calendar.WEEK_OF_YEAR))Mes = (byte)(i-1);
            
        }
        
        List<clsGasto> xGs = clsGasto.ListarEfectivos(Mes, this.Ano);
        List<iEgreso> Es = new ArrayList<>();        
               
        for(clsGasto xG : xGs)
        {
            cal.setTime(xG.getFecha());
            if(cal.get(Calendar.WEEK_OF_YEAR) == Semana)Es.add(xG);
        }
        
        return Es;
    }

    @Override
    List<iIngreso> ObtenerIngresos() throws Exception {
        List<iIngreso> Is = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getIngresosSemana(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, Semana);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, Ano);
        
        ResultSet rs = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(rs);
        
        while(crs.next())Is.add(new clsPago(crs, "Pago de " + 
                crs.getString("cob_concepto") + " de " + crs.getString("pra_nombre") 
                + " (" + crs.getString("pra_codigo") + ")", "Paciente " 
                + crs.getString("pac_apellido") + ", " + crs.getString("pac_nombre") 
                + " (" + crs.getString("pac_tipo_documento") + ": " + String.valueOf(
                        crs.getLong("pac_numero_documento")) + ") - " + crs.getString("os")));
        
        return Is;
    }

    @Override
    public String getNombre() {return "semana " + String.valueOf(this.Semana) 
            + " del " + String.valueOf(Ano);}
}//end clsPeriodoSemanal