package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 01-nov-2014 08:47:04 p.m.
 */
public class clsValorCampo {

    private clsCampoTrabajo Campo;
    private int ID;
    private String Valor;

    public clsValorCampo(){
        this.Campo = new clsCampoTrabajo();
        this.ID = 0;
        this.Valor = "";
    }
    
    clsValorCampo(ResultSet rs) throws SQLException
    {
        this.Campo = new clsCampoTrabajo(rs);
        this.ID = rs.getInt("vaca_id");
        this.Valor = rs.getString("vaca_valor");
    }
    
    public clsValorCampo(clsCampoTrabajo campo)
    {this();this.Campo = campo;}

    public clsCampoTrabajo getCampo(){return Campo;}

    public int getID(){return ID;}

    public String getValor(){return Valor;}

    public void setCampo(clsCampoTrabajo newVal){Campo = newVal;}

    public void setValor(String newVal){Valor = newVal;}
    
    public void Registrar(int IDTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int)xGB.ObtenerClave("valor_campo");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarValorCampo(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroPequeno, Campo.getID());
        xGB.CrearParametro(3, enTipoParametro.Entero, IDTrabajo);
        xGB.CrearParametro(4, enTipoParametro.Cadena, Valor);
        
        xGB.EjecutarComando();
    }
    
    public boolean isDefinido()
    {return !this.Valor.isEmpty();}
    
    public String getEstadoStr()
    {
        if(isDefinido())
        {return "Valor definido";}
        else{
            if(this.Campo.isObligatorio())
            {return "Valor no definido";}
            else{return "";}
        }
    }
    
    public String getIcono()
    {
        if(isDefinido())
        {return "ok.png";}
        else{
            if(this.Campo.isObligatorio())
            {return "warning.png";}
            else{return "";}
        }
    }

    public void Actualizar() throws Exception {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call ActualizarValorCampo(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Valor);
        
        xGB.EjecutarComando();
    }
}//end clsValorCampo