package com.PRS.Consultorio.Reportes;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 04:07:05 p.m.
 */
public abstract class clsParametroReporte {

    protected String Nombre;
    protected String SQLField;
    
    public clsParametroReporte(String sqlField){
        this.Nombre = "";
        SQLField = sqlField;
    }

    public String getNombre(){return Nombre;}
    
    abstract String getSQL();

    @Override
    public String toString() {return this.Nombre + ": ";}

	public abstract enTipoParametroReporte getTipo();
        
    public abstract void setValue(Object valor);
    
    
}//end clsParametroReporte