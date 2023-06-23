package com.PRS.Consultorio.Practicas;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 11-oct-2014 12:35:40 p.m.
 */
public class clsPracticaLazy {

    private final String Codigo;
    private final short ID;
    private final String Nombre;
    private final boolean Activa;

    public clsPracticaLazy(){
        this.ID = 0;
        this.Nombre = "";
        this.Codigo = "";
        this.Activa = false;
    }

    clsPracticaLazy(ResultSet prRS) throws SQLException
    {
        this.Codigo = String.valueOf(prRS.getInt("pra_codigo"));
        this.ID = prRS.getShort("pra_id");
        this.Nombre = prRS.getString("pra_nombre");
        this.Activa = prRS.getBoolean("pra_activa");
    }

    public String getCodigo(){return Codigo;}

    public short getID(){return ID;}

    public String getNombre(){return Nombre;}

    public clsPractica getFullPractica() throws Exception
    {
        return clsPractica.Obtener(this.ID);

    }

    @Override
    public String toString()
    {return this.Nombre + " (" + this.Codigo + ")";}

    public String getAyudaBoton()
    {
        clsPractica p = new clsPractica();
        return p.Fabricar(Activa).getAyudaBoton();
    }
        
    public  String getIconoBoton()
    {
        clsPractica p = new clsPractica();
        return p.Fabricar(Activa).getIconoBoton();
    }
    
    public  String getTituloConfirmacion()
    {clsPractica p = new clsPractica();
        return p.Fabricar(Activa).getTituloConfirmacion();
    }

    public String getPregunta()
    {clsPractica p = new clsPractica();
        return p.Fabricar(Activa).getPregunta();
    }
    
    public String getConfirmacion()
    {
        clsPractica p = new clsPractica();
        return p.Fabricar(Activa).getConfirmacion();
    }
}//end clsPracticaLazy