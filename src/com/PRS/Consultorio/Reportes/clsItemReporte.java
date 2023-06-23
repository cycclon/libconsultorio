package com.PRS.Consultorio.Reportes;

/**
 * @author cycclon
 * @version 1.0
 * @created 15-dic-2014 12:31:41 p.m.
 */
public class clsItemReporte {

    private String Nombre;
    private double Valor;

    public clsItemReporte(){
        this("", 0);
    }

    public clsItemReporte(String nombre, double valor)
    {
        this.Nombre = nombre;
        this.Valor = valor;
    }

    public String getNombre(){return Nombre;}

    public double getValor(){return Valor;}

    public void setNombre(String newVal){Nombre = newVal;}

    public void setValor(double newVal){Valor = newVal;}
}//end clsItemReporte