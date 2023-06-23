package com.PRS.Consultorio.Balances;

import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.Gastos.clsGasto;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 10-mar-2015 04:39:24 p.m.
 */
public class clsPeriodoDiario extends clsPeriodoBalance {

    private Date Dia;

    public clsPeriodoDiario(Date dia){
        super((short)0);
        this.Ano = getYear(dia);
        Dia = dia;
    }
    
    private short getYear(Date dia)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dia);
        return (short)cal.get(Calendar.YEAR);
    }

    public Date getDia(){return Dia;}

    @Override
    public String getNombre(){
            return "día " + new SimpleDateFormat("dd/MM/yyyy").format(Dia);
    }

    @Override
    public enPeriodoBalance getPeriodo(){return enPeriodoBalance.Diario;}

    @Override
    List<iEgreso> ObtenerEgresos()
      throws Exception{
        List<iEgreso> es = new ArrayList<>();
        List<clsGasto> gs = clsGasto.ListarEfectivos(Dia);
        
        for(clsGasto g : gs)
        {es.add(g);}
        
        return es;
    }

    @Override
    List<iIngreso> ObtenerIngresos()
      throws Exception{
        List<iIngreso> Is = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getIngresosDiarios(?)}");
        xGB.CrearParametro(1, enTipoParametro.Fecha, Dia);
                
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

    public void setDia(Date newVal){Dia = newVal;}
}//end clsPeriodoDiario