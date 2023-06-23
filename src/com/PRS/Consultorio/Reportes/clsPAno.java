package com.PRS.Consultorio.Reportes;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 04:07:07 p.m.
 */
public class clsPAno extends clsParametroReporte {

    private int Ano;
    public static final int DEFAULTVALUE = 0;

    public clsPAno(String sqlField){
        this(0, sqlField);
    }

    public clsPAno(int ano, String sqlField){super(sqlField);this.Ano = ano;}

    public int getAno(){return Ano;}

    public void setAno(int newVal){Ano = newVal;}

    @Override
    String getSQL() {
        if(Ano == DEFAULTVALUE)return "";
        else return "YEAR(" + SQLField + ") = " + String.valueOf(Ano);
    }

    @Override
    public enTipoParametroReporte getTipo() {return enTipoParametroReporte.Ano;}

    @Override
    public void setValue(Object valor) {this.setAno((int)valor);}
    
    
}//end clsPAno