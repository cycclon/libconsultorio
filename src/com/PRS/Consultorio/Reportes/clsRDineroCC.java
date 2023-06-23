package com.PRS.Consultorio.Reportes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;

/**
 * @author cycclon
 * @version 1.0
 * @created 21-dic-2014 11:49:30 a.m.
 */
public class clsRDineroCC extends clsReporte {

    public clsRDineroCC(){
        super();
        this.Parametros.add(new clsPTipoCobro("cob_concepto"));
        this.Parametros.add(new clsPMes("retr_fecha"));
        this.Parametros.add(new clsPAno("retr_fecha"));
    }

    @Override
    public void Generar()
      throws Exception{
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        String sql = "SELECT ceco_nombre, "
                + "sum(cob_monto) as total FROM centrocosto, "
                + "cobro, trabajo, realizacion_trabajo, practica WHERE ceco_id = "
                + "pra_ceco_id AND tra_pra_id = pra_id AND tra_id = retr_tra_id AND cob_tra_id = "
                + "tra_id ";
        for(clsParametroReporte p : Parametros)if(!"".equals(p.getSQL()))sql += "AND " + p.getSQL() +  " ";

        sql += "group by ceco_nombre";

        xGB.CrearComando(CommandType.Text, sql);

        ResultSet rs = xGB.EjecutarConsulta();

        while(rs.next())Items.add(new clsItemReporte(
                rs.getString("ceco_nombre") + ": " +
                        new clsValor((float)rs.getDouble("total"))
                                .pdValorString(), rs.getDouble("total")));
    }

    @Override
    public String getFXML(){return "/jfxconsultorio/Reportes/fxDineroCC.fxml";}

    @Override
    public enTipoReporte getTipo(){return enTipoReporte.DineroCC;}

    @Override
    public String getTitulo(){return "Ingresos por centro de costos";}
    
     public void setMes(byte mes)
    {this.DefinirParametro(enTipoParametroReporte.Mes, mes);}
    
    public void setAno(int ano)
    {this.DefinirParametro(enTipoParametroReporte.Ano, ano);}
    
    public void setTipoCobro(enTipoCobro tipo)
    {this.DefinirParametro(enTipoParametroReporte.TipoCobro, tipo);}
}//end clsRDineroCC