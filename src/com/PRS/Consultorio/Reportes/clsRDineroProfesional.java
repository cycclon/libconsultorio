package com.PRS.Consultorio.Reportes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 03:55:56 p.m.
 */
public class clsRDineroProfesional extends clsReporte {

	public clsRDineroProfesional(){
            super();
            this.Parametros.add(new clsPTipoCobro("cob_concepto"));
            this.Parametros.add(new clsPMes("retr_fecha"));
            this.Parametros.add(new clsPAno("retr_fecha"));
	}

        @Override
        public void Generar() throws Exception{
            clsGestorBases xGB = clsGestorBases.Instanciar();
            xGB.EstablecerBaseActiva((byte)1);
            
            String sql = "SELECT pro_nombres, "
                    + "pro_apellidos, sum(cob_monto) as total FROM profesional, "
                    + "cobro, trabajo, realizacion_trabajo WHERE pro_id = "
                    + "retr_pro_id AND tra_id = retr_tra_id AND cob_tra_id = "
                    + "tra_id ";
            for(clsParametroReporte p : Parametros)if(!"".equals(p.getSQL()))sql += "AND " + p.getSQL() +  " ";
                    
            sql += "group by pro_nombres, pro_apellidos";
            
            xGB.CrearComando(CommandType.Text, sql);
            
            ResultSet rs = xGB.EjecutarConsulta();
            
            while(rs.next())Items.add(new clsItemReporte(
                    rs.getString("pro_apellidos") + ", " + 
                            rs.getString("pro_nombres") + ": " +
                            new clsValor((float)rs.getDouble("total"))
                                    .pdValorString(), rs.getDouble("total")));
	}

        @Override
        public String getTitulo(){return "Ingresos generados por profesional";}

    @Override
    public enTipoReporte getTipo() {return enTipoReporte.DineroProfesional;}

    @Override
    public String getFXML() {return "/jfxconsultorio/Reportes/fxDineroProfesional.fxml";}       

    public void setMes(byte mes)
    {this.DefinirParametro(enTipoParametroReporte.Mes, mes);}
    
    public void setAno(int ano)
    {this.DefinirParametro(enTipoParametroReporte.Ano, ano);}
    
    public void setTipoCobro(enTipoCobro tipo)
    {this.DefinirParametro(enTipoParametroReporte.TipoCobro, tipo);}
    
    
}//end clsRDineroProfesional