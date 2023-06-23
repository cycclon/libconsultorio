package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Bitacoras.Campos.bCampoEliminado;
import com.PRS.Consultorio.Bitacoras.Campos.bCampoRegistrado;
import com.PRS.Consultorio.Bitacoras.Campos.bCampoRestaurado;
import com.PRS.Consultorio.Informes.clsGestorInformes;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 01-nov-2014 08:46:58 p.m.
 */
public class clsCampoTrabajo {

    private String CodigoInforme;
    private byte ID;
    private String Nombre;
    private boolean Obligatorio;
    private boolean Activo;

    public clsCampoTrabajo(){
        this.CodigoInforme = "";
        this.ID = 0;
        this.Nombre = "";
        this.Obligatorio = false;
        Activo = true;
    }
    
    clsCampoTrabajo(ResultSet rs) throws SQLException
    {
        this();
        this.CodigoInforme = rs.getString("catr_codigo");
        this.ID = rs.getByte("catr_id");
        this.Nombre = rs.getString("catr_nombre");
        this.Obligatorio = rs.getBoolean("catr_obligatorio");
        this.Activo = rs.getBoolean("catr_activo");
    }

    public String getCodigoInforme(){return CodigoInforme;}

    public byte getID(){return ID;}

    public String getNombre(){return Nombre;}

    public boolean isObligatorio(){return Obligatorio;}

    public void setCodigoInforme(String newVal){CodigoInforme = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}

    public void setObligatorio(boolean newVal){Obligatorio = newVal;}
    
    public void Registrar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        ID = (byte)xGB.ObtenerClave("campo_trabajo");
        
        xGB.CrearComando(CommandType.StoredProcedure,
                "{call RegistrarCampoTrabajo(?, ?, ?, ?)}");
        xGB.CrearParametro(1,  enTipoParametro.EnteroPequeno, ID);
        xGB.CrearParametro(2,  enTipoParametro.Cadena, Nombre);
        xGB.CrearParametro(3, enTipoParametro.Cadena, CodigoInforme);
        xGB.CrearParametro(4, enTipoParametro.Logico, Obligatorio);
        
        xGB.EjecutarComando();
        
        clsGestorInformes.Instanciar().ActualizarComponentes();
        
        new bCampoRegistrado(this).Registrar();
    }
    
    public static List<clsCampoTrabajo> Listar(boolean incluirInactivos) throws Exception
    {
        List<clsCampoTrabajo> xCs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getCampos()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            if(incluirInactivos || xRS.getBoolean("catr_activo"))
            {xCs.add(new clsCampoTrabajo(xRS));}
        }
        
        return xCs;
    }
    
    public void Eliminar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure,
                "{call EliminarCampoTrabajo(?)}");
        xGB.CrearParametro(1,  enTipoParametro.EnteroPequeno, ID);
        
        xGB.EjecutarComando();
        
        clsGestorInformes.Instanciar().ActualizarComponentes();
        
        new bCampoEliminado(this).Registrar();
    }
    
    public void Restaurar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure,
                "{call RestaurarCampoTrabajo(?)}");
        xGB.CrearParametro(1,  enTipoParametro.EnteroPequeno, ID);
        
        xGB.EjecutarComando();
        
        clsGestorInformes.Instanciar().ActualizarComponentes();
        
        new bCampoRestaurado(this).Registrar();
    }

    public String getNombreyCodigo() {
        String str = Nombre;
        if(!CodigoInforme.isEmpty()){str += " (" + this.CodigoInforme + ")";}
        return str;        
    }

    public boolean isActivo() {return this.Activo;}
}//end clsCampoTrabajo1