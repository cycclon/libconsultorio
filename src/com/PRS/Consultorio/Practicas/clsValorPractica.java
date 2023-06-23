package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.ObrasSociales.clsOS;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Monedas.clsValor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 24-sep-2014 03:26:26 p.m.
 */
public class clsValorPractica {

    private clsValor Coseguro;
    private clsOS OS;
    private clsValor CostoOS;
    private Date Vigencia;
    private short ID;

    public clsValorPractica(){
        this(new clsOS(), (float)0.0, (float)0.0, new Date());
    }
    
    public clsValorPractica(clsOS prOS, float prCostoOS, float prCoseguro, 
            Date prVigenteDesde)
    {
        this.OS = prOS;
        this.Coseguro = new clsValor(prCoseguro);
        this.CostoOS = new clsValor(prCostoOS);
        this.Vigencia = prVigenteDesde;      
        this.ID = 0;
    }
    
    clsValorPractica(clsOS prOS) throws Exception
    {
        this(prOS, (float)0.0, (float)0.0, new SimpleDateFormat("dd/MM/yyyy")
                .parse("01/01/1900"));
    }
    
    clsValorPractica(ResultSet prRS) throws SQLException
    {
        this.Coseguro = new clsValor(prRS.getFloat("vapra_coseguro_monto"));
        this.CostoOS = new clsValor(prRS.getFloat("vapra_os_monto"));
        this.ID = prRS.getShort("vapra_id");
        this.OS = new clsOS(prRS);
        this.Vigencia = prRS.getTimestamp("vapra_vigencia_inicio");
    }
    
    clsValorPractica(ResultSet prRS, boolean esParticular) throws SQLException
    {
        this.Coseguro = new clsValor(prRS.getFloat("copa_monto"));
        this.CostoOS = new clsValor(0);
        this.ID = prRS.getShort("copa_id");
        this.OS = new clsOS();
        this.Vigencia = prRS.getTimestamp("copa_vigencia_inicio");
    }

    public clsValor getCoseguro(){return Coseguro;}

    public clsValor getCostoOS(){return CostoOS;}
    
    public clsOS getOS(){return OS;}
    
    public void setOS(clsOS newVal){OS = newVal;}

    public void setCoseguro(clsValor newVal){Coseguro = newVal;}

    public void setCostoOS(clsValor newVal){CostoOS = newVal;}

    public Date getVigencia(){return Vigencia;}

    public void setVigencia(Date newVal){Vigencia = newVal;}
    
    public short getID(){return this.ID;}
    
    public void setID(short newVal){this.ID = newVal;}
    
    void Registrar(short IDPractica) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        if(this.OS.getID() != 0){       
        
            this.ID = (short)xGB.ObtenerClave("valor_practica");
        
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call RegistrarValorPractica(?, ?, ?, ?, ?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
            xGB.CrearParametro(2, enTipoParametro.Moneda, Coseguro.pdValor());
            xGB.CrearParametro(3, enTipoParametro.Moneda, CostoOS.pdValor());
            xGB.CrearParametro(4, enTipoParametro.EnteroChico, OS.getID());
            xGB.CrearParametro(5, enTipoParametro.Fecha, Vigencia);
            xGB.CrearParametro(6, enTipoParametro.EnteroChico, IDPractica);

            xGB.EjecutarComando();
        }
        else
        {
            this.ID = (short)xGB.ObtenerClave("costo_particular");
            
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call RegistrarCostoParticular(?, ?, ?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
            xGB.CrearParametro(2, enTipoParametro.Moneda, this.Coseguro.pdValor());
            xGB.CrearParametro(3, enTipoParametro.Fecha, this.Vigencia);
            xGB.CrearParametro(4, enTipoParametro.EnteroChico, IDPractica);
            
            xGB.EjecutarComando();
        }
    }
    
    public void Actualizar() throws Exception
    {
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        if(this.OS.getID() != 0){
            xGB.CrearComando(CommandType.StoredProcedure,
                    "{call ActualizarValorPractica(?, ?, ?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
            xGB.CrearParametro(2, enTipoParametro.Moneda, this.Coseguro.pdValor());
            xGB.CrearParametro(3, enTipoParametro.Moneda, CostoOS.pdValor());
            xGB.CrearParametro(4, enTipoParametro.Fecha, Vigencia);

            xGB.EjecutarComando();
        }
        else
        {
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call ActualizarCostoParticular(?, ?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
            xGB.CrearParametro(2, enTipoParametro.Moneda, this.Coseguro.pdValor());
            xGB.CrearParametro(3, enTipoParametro.Fecha, Vigencia);
            
            xGB.EjecutarComando();
        }
    }
}//end clsValorPractica