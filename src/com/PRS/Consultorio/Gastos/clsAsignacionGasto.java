package com.PRS.Consultorio.Gastos;

import com.PRS.Consultorio.CentrosCosto.clsCentroCosto;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 05-nov-2014 10:39:08 p.m.
 */
public class clsAsignacionGasto {

    private clsCentroCosto CC;
    private int ID;
    private float Porcentaje;

    public clsAsignacionGasto(){
        this.CC = new clsCentroCosto();
        this.ID = 0;
        this.Porcentaje = 0;
    }
    
    clsAsignacionGasto(ResultSet rs) throws SQLException
    {
        this.CC = new clsCentroCosto(rs);
        this.ID = rs.getInt("asi_id");
        this.Porcentaje = rs.getFloat("asi_porcentaje");
    }

    public clsCentroCosto getcentro(){return CC;}

    public int getID(){return ID;}

    public float getPorcentaje(){return Porcentaje;}

    public void setcentro(clsCentroCosto newVal){CC = newVal;}

    public void setID(int newVal){ID = newVal;}

    public void setPorcentaje(float newVal){Porcentaje = newVal;}
    
    @Override
    public String toString()
    {return this.CC.getNombreConCodigo() + " " + this.Porcentaje + "%";}
    
    public void Registrar(int IDGasto) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int) xGB.ObtenerClave("asignacion_gasto");
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call RegistrarAsignacionGasto(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Entero, IDGasto);
        xGB.CrearParametro(3, enTipoParametro.EnteroPequeno, CC.getID());
        xGB.CrearParametro(4, enTipoParametro.xDecimal, Porcentaje);
        
        xGB.EjecutarComando();
    }
}//end clsAsignacionGasto