package com.PRS.Consultorio.Profesionales;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 13-oct-2014 07:12:21 p.m.
 */
public class clsProfesionalLazy {

    private short ID;
    private String NombreCompleto;
    private enTipoProfesional Tipo;

    public clsProfesionalLazy(){
        this.ID = 0;
        this.NombreCompleto = "";
        this.Tipo = enTipoProfesional.Practicante;
    }
    
    clsProfesionalLazy(ResultSet prRS) throws SQLException
    {
        this.NombreCompleto = prRS.getString("pro_abreviatura") + " " 
                + prRS.getString("pro_apellidos") + ", " 
                + prRS.getString("pro_nombres");
        this.ID = prRS.getShort("pro_id");
        this.Tipo = enTipoProfesional.valueOf(prRS.getString("pro_tipo"));
    }

    public short getID(){return ID;}

    public String getNombreCompleto(){return NombreCompleto;}
    
    @Override
    public String toString()
    {return this.getNombreCompleto();}

    public enTipoProfesional getTipo() {return Tipo;}
    
    public clsProfesional getFullProfesional() throws Exception
    {
        clsProfesional xP = new clsSolicitante();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getProfesionalFull(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
                
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xP = clsProfesional.Fabricar(enTipoProfesional.valueOf(
                xRS.getString("pro_tipo")), xRS);}
        
        return xP;
    }
}//end clsProfesionalLazy