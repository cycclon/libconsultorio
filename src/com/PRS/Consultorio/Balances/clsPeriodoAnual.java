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
 * @created 30-nov-2014 03:35:18 p.m.
 */
public class clsPeriodoAnual extends clsPeriodoBalance {

	public clsPeriodoAnual(short ano){
            super(ano);
	}

    @Override
    public enPeriodoBalance getPeriodo() {return enPeriodoBalance.Anual;}

    @Override
    List<iEgreso> ObtenerEgresos() throws Exception{
        List<iEgreso> Es = new ArrayList<>();
        
        for(int i = 1; i<=12; i++)
        {            
            List<clsGasto> xGs = clsGasto.ListarEfectivos((byte)i, this.Ano);                    

            for(clsGasto xG : xGs)Es.add(xG);        
            
        }       
        
        
        return Es;
    }

    @Override
    List<iIngreso> ObtenerIngresos() throws Exception {
         List<iIngreso> Is = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getIngresosAno(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, Ano);
        
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
    public String getNombre() {return "año " + String.valueOf(this.Ano);}

}//end clsPeriodoAnual