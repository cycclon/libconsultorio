package com.PRS.Consultorio.ObrasSociales;

import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialEliminada;
import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialRegistrada;
import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialRestaurada;
import com.PRS.Framework.AccesoADatos.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-ago-2014 07:10:20 p.m.
 */
public class clsOS {

    private int Codigo;
    private short ID;
    private String Nombre;
    private boolean Activa;
    private String Imagen;

    public clsOS(){
        this(0, "Particular");
    }

    public clsOS(int prCodigo, String prNombre)
    {
        this.ID = 0;
        this.Nombre = prNombre;
        this.Codigo = prCodigo;
        this.Activa = true;
        this.Imagen = "defaultos.jpg";
    }
    
    public clsOS(ResultSet prRS) throws SQLException
    {
        this.ID = prRS.getShort("os_id");
        this.Codigo = prRS.getInt("os_codigo");
        this.Nombre = prRS.getString("os_Nombre");
        this.Activa = prRS.getBoolean("os_activa");
        this.Imagen = prRS.getString("os_imagen");
    }

    public int getCodigo(){return Codigo;}

    public short getID(){return ID;}

    public String getNombre(){return Nombre;}
    
    public boolean getActiva(){return this.Activa;}
    
    public String getImagen(){return this.Imagen;}

    public void setCodigo(int newVal){Codigo = newVal;}

    public void setID(short newVal){ID = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public void setImagen(String newVal){Imagen = newVal;}
    
    public void Registrar() throws Exception
    {
        if(!this.VerificarCodigo(Codigo)){
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (short) xGB.ObtenerClave("obrasocial");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarObraSocial(?, ?, ?, ?)}");
        
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, this.Nombre);
        xGB.CrearParametro(3, enTipoParametro.Entero, this.Codigo);
        xGB.CrearParametro(4,  enTipoParametro.Cadena, Imagen);
        
        xGB.EjecutarComando();
        new bObraSocialRegistrada(this).Registrar();
        }
        else{throw new exCodigoEnUso();}
    }
    
    public void Eliminar() throws Exception
    {
       
        this.CambiarEstado();
        new bObraSocialEliminada(this).Registrar();
    }
    
    public void Restaurar() throws Exception
    {
        
        this.CambiarEstado();
        new bObraSocialRestaurada(this).Registrar();
    }
    
    private void CambiarEstado() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        if(this.Activa){
            this.Activa = false;
            xGB.CrearComando(CommandType.StoredProcedure, 
                "{call EliminarObraSocial(?)}");
        }
        else
        {
            this.Activa = true;
            xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RestaurarObraSocial(?)}");
        }
        
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        
        xGB.EjecutarComando();
    }
    
    public static List<clsOS> Listar(boolean prActiva) throws Exception
    {
        List<clsOS> xOS = new ArrayList<>();
        List<clsOS> xTodos = Listar();
        for(int i = 0; i< xTodos.size(); i++)
        {
            if(xTodos.get(i).Activa == prActiva)
            {xOS.add(xTodos.get(i));}
        }
        
        return xOS;
    }
    
    public static List<clsOS> Listar() throws Exception
    {
       List<clsOS> xOS = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getObrasSociales()}");
                
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            xOS.add(new clsOS(xRS));
        }
        
        return xOS;
    }
    
    public void RegistrarNuevaImagen(String prArchivo) throws Exception
    {
        this.Imagen = prArchivo;
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        
            xGB.CrearComando(CommandType.StoredProcedure, 
                "{call CambiarImagenOS(?, ?)}");
               
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Imagen);
        
        
        xGB.EjecutarComando();
    }
    
    private boolean VerificarCodigo(int prCodigo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getObraSocialCodigo(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, prCodigo);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        boolean xF = false;
        while(xRS.next())
        {xF = true;}
        return xF;      
    }
    
    public String getNombreConCodigo()
    {
        if(this.Nombre.equals("Particular")){return "Particular";}
        else{return this.Nombre + " ("+ this.Codigo + ")";}
    }
    
    @Override
    public String toString()
    {return this.getNombreConCodigo();}
    
    public static clsOS ObtenerPorPaciente(int IDPaciente) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getObraSocialPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, IDPaciente);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        clsOS xOS = new clsOS();
        
        while(xRS.next())
        {xOS = new clsOS(xRS);}
        
        return xOS;
    }
    
    public String getIcono()
    {
        if(Activa)
        {return "remove.png";}
        else
        {return "add.png";}
    }
}//end clsOS