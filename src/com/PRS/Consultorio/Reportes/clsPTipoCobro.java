package com.PRS.Consultorio.Reportes;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 04:07:06 p.m.
 */
public class clsPTipoCobro extends clsParametroReporte {

    private enTipoCobro Tipo;
    public static final enTipoCobro DEFAULTVALUE = enTipoCobro.Todos;

    public clsPTipoCobro(String sqlField){this(enTipoCobro.Todos, sqlField);} 

    clsPTipoCobro(enTipoCobro tipo, String sqlField)
    {super(sqlField);this.Tipo = tipo;}

    public enTipoCobro getTipoCobro(){return Tipo;}

    public void setTipo(enTipoCobro newVal){Tipo = newVal;}

    @Override
    public String toString() {
        return super.toString() + this.Tipo.toString();
    }

    @Override
    String getSQL() {
        if(this.Tipo == DEFAULTVALUE)return "";
        else return this.SQLField + " = '" + Tipo + "'";
    }

    @Override
    public enTipoParametroReporte getTipo() 
    {return enTipoParametroReporte.TipoCobro;}

    @Override
    public void setValue(Object valor) {this.setTipo((enTipoCobro)valor);}
    
    
}//end clsPTipoCobro