package com.PRS.Consultorio.Balances;

import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.Gastos.clsGasto;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 03:35:19 p.m.
 */
public class clsPeriodoMensual extends clsPeriodoBalance {

	private byte Mes;

	public clsPeriodoMensual(byte mes, short ano){
            super(ano);
            this.Mes = mes;
	}

	public byte getMes(){return Mes;}

	public void setMes(byte newVal){Mes = newVal;}

    @Override
    public enPeriodoBalance getPeriodo() {return enPeriodoBalance.Mensual;}

    @Override
    List<iEgreso> ObtenerEgresos() throws Exception {
        List<clsGasto> xGs = clsGasto.ListarEfectivos(Mes, this.Ano);
        List<iEgreso> Es = new ArrayList<>();        
               
        for(clsGasto xG : xGs)Es.add(xG);
        
        return Es;
    }

    @Override
    List<iIngreso> ObtenerIngresos() throws Exception {
        List<iIngreso> Is = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getIngresosMensuales(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, Mes);
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
    public String getNombre() {
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", 
            "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
            "Diciembre"};
        return monthNames[Mes-1] + " de " + String.valueOf(this.Ano);
    }
}//end clsPeriodoMensual