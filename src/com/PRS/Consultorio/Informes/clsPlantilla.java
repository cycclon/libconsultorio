package com.PRS.Consultorio.Informes;

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
 * @created 31-may-2015 12:20:38 a.m.
 */
public class clsPlantilla {

    private short ID;
    private String Archivo;
    private String Nombre;

    public clsPlantilla(){
        ID = 0;
        this.Archivo = "no creado";
        this.Nombre = "";
    }
    
    private clsPlantilla(ResultSet rs) throws SQLException
    {
        ID = rs.getShort("inf_id");
        Archivo = rs.getString("inf_archivo");
        Nombre = rs.getString("inf_nombre");
    }

    public String getArchivo(){return Archivo;}

    public String getNombre(){return Nombre;}

    public static List<clsPlantilla> getPlantillas(short practica) throws Exception{
        List<clsPlantilla> ps = new ArrayList<>();
        
        clsGestorBases gb = clsGestorBases.Instanciar();
        gb.EstablecerBaseActiva((byte)1);
        
        gb.CrearComando(CommandType.StoredProcedure, "{call getPlantillas(?)}");
        gb.CrearParametro(1, enTipoParametro.EnteroChico, practica);
        
        ResultSet rs = gb.EjecutarConsulta();
        
        while(rs.next())ps.add(new clsPlantilla(rs));
        
        return ps;
    }

    public void setArchivo(String newVal){Archivo = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public void Modificar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call ModificarPlantilla(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Nombre);
        
        xGB.EjecutarComando();
        
    }
    
    public void Eliminar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarPlantilla(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        
        xGB.EjecutarComando();
    }
    
    public short getID(){return ID;}
    
    public short getIDToBe() throws Exception
    {
        if(ID == 0)
        {
            clsGestorBases xGB = clsGestorBases.Instanciar();
            xGB.EstablecerBaseActiva((byte)1);
            return (short)xGB.ObtenerClave("informe");
        }
        else
        {return ID;}
    }
    
    public void Registrar(short idPractica) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        ID = (short)xGB.ObtenerClave("informe");
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call RegistrarPlantilla(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Nombre);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Archivo);
        xGB.CrearParametro(4, enTipoParametro.EnteroChico, idPractica);
        
        xGB.EjecutarComando();
    }
}//end clsPlantilla