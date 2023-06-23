package com.PRS.Consultorio.Reportes;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 04:07:06 p.m.
 */
public class clsPMes extends clsParametroReporte {

    private byte Mes;
    public static final byte DEFAULTVALUE = 0;

    private final String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", 
        "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
        "Diciembre"};

    public clsPMes(String sqlField){
        this((byte)0, sqlField);
    }

    public clsPMes(byte mes, String sqlField)
    {
        super(sqlField);
        this.Mes = mes;
    }

    public byte getMes(){
            return Mes;
    }

    public void setMes(byte newVal){Mes = newVal;}

    @Override
    public String toString() {
        if(Mes == 0)return super.toString() + " todos";
        else return super.toString() + monthNames[Mes-1];
    }

    @Override
    String getSQL() {
        if(Mes == DEFAULTVALUE){return "";}
        else return "MONTH(" + SQLField + ") =" + String.valueOf(this.Mes);
    }

    @Override
    public enTipoParametroReporte getTipo() {return enTipoParametroReporte.Mes;}

    @Override
    public void setValue(Object valor) {this.setMes((byte)valor);}
        
    
}//end clsPMes