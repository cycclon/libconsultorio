package com.PRS.Consultorio.CentrosCosto;

import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoEliminado;
import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoRegistrado;
import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoRestaurado;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Cuentas.clsCuenta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clsCentroCosto {
    
    private abstract class clsEstadoCentroCosto
    {
        protected abstract boolean getActivo();
        
        protected abstract String getIconoBoton();
        
        protected abstract String getAyudaBoton();
        
        protected abstract String getConfirmacion(clsCentroCosto prCC);
        
        protected abstract String getTituloConfirmacion();
        
        protected abstract String getPregunta(clsCentroCosto prCC);
        
        protected abstract void CambiarEstado(clsCentroCosto prCC) throws Exception;
    }
    
    private class clsECCActivo extends clsEstadoCentroCosto
    {

        @Override
        protected boolean getActivo() {return true;}

        @Override
        protected String getIconoBoton() {return "remove.png";}

        @Override
        protected String getAyudaBoton() {return "Presione este botón para "
                + "eliminar este centro de costos";}

        @Override
        protected String getConfirmacion(clsCentroCosto prCC) 
        {return "Se restauró el centro de costo " + prCC.Nombre + 
                " ("+prCC.Codigo+")";}

        @Override
        protected String getTituloConfirmacion() 
        {return "Eliminar centro de costos";}

        @Override
        protected String getPregunta(clsCentroCosto prCC) 
        {return "¿Está seguro que desea eliminar el centro de costos " 
                + prCC.Nombre + " ("+prCC.Codigo+")?";}

        @Override
        protected void CambiarEstado(clsCentroCosto prCC) throws Exception
        {prCC.Estado = new clsECCInactivo();new bCentroDeCostoEliminado(prCC).Registrar();}
    }
    
    private class clsECCInactivo extends clsEstadoCentroCosto
    {
        @Override
        protected boolean getActivo() {return false;}
        
        @Override
        protected String getIconoBoton() {return "add.png";}

        @Override
        protected String getAyudaBoton() {return "Presione este botón para "
                + "restaurar este centro de costos";}

        @Override
        protected String getConfirmacion(clsCentroCosto prCC) 
        {return "Se eliminó el centro de costo " + prCC.Nombre + 
                " ("+prCC.Codigo+")";}

        @Override
        protected String getTituloConfirmacion() 
        {return "Restaurar centro de costos";}

        @Override
        protected String getPregunta(clsCentroCosto prCC) 
        {return "¿Está seguro que desea restaurar el centro de costos " 
                + prCC.Nombre + " ("+prCC.Codigo+")?";}
        
        @Override
        protected void CambiarEstado(clsCentroCosto prCC) throws Exception
        {prCC.Estado = new clsECCActivo();new bCentroDeCostoRestaurado(prCC).Registrar();}
    }

    private String Codigo;
    private clsCuenta Cuenta;
    private byte ID;
    private String Nombre;
    private clsEstadoCentroCosto Estado;

    public clsCentroCosto(){
        this("", "");
    }
    
    public clsCentroCosto(String prCodigo, String prNombre)
    {
        this.Codigo = prCodigo;
        this.ID = 0;
        this.Nombre = prNombre;
        Estado = new clsECCActivo();
        this.Cuenta = new clsCuenta();
    }
    
    public clsCentroCosto(ResultSet prRS) throws SQLException
    {
        this.Codigo = prRS.getString("ceco_codigo");
        this.Estado = FabricarEstado(prRS.getBoolean("ceco_activo"));
        this.ID = prRS.getByte("ceco_id");
        this.Nombre = prRS.getString("ceco_nombre");
    }
    

    public String getCodigo(){return Codigo;}

    public clsCuenta getCuenta(){return Cuenta;}

    public byte getID(){return ID;}
    
    public String getIconoBoton(){return this.Estado.getIconoBoton();}
    
    public String getAyudaBoton(){return this.Estado.getAyudaBoton();}
    
    public String getTituloConfirmacion(){return this.Estado.getTituloConfirmacion();}
    
    public String getPregunta(){return this.Estado.getPregunta(this);}
    
    public String getConfirmacion(){return this.Estado.getConfirmacion(this);}    
    
    public String getNombre(){return Nombre;}

    public void setCodigo(String newVal){Codigo = newVal;}

    public void setID(byte newVal){ID = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public boolean getActivo(){return this.Estado.getActivo();}
    
    private clsEstadoCentroCosto FabricarEstado(boolean activo)
    {
        if(activo){return new clsECCActivo();}
        else{return new clsECCInactivo();}
    }
    
    public void CambiarEstado() throws Exception
    {
        this.Estado.CambiarEstado(this);
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte) 1);
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call CambiarEstadoCentroCosto(?, ?)}");
        
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, ID);
        xGB.CrearParametro(2, enTipoParametro.Logico, Estado.getActivo());
        
        xGB.EjecutarComando();
    }
    
    public static List<clsCentroCosto> Listar(boolean prInactivos) throws Exception
    {
        List<clsCentroCosto> xCCs = new ArrayList<>();
        List<clsCentroCosto> xAll = Listar();
        
        for(int i = 0; i<xAll.size(); i++)
        {
            if (xAll.get(i).getActivo() == true || prInactivos)
            {xCCs.add(xAll.get(i));}
        }
        
        return xCCs;
    }
    
    private static List<clsCentroCosto> Listar() throws Exception
    {
        List<clsCentroCosto> xCCs = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "SELECT * FROM centrocosto");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xCCs.add(new clsCentroCosto(xRS));}
        
        return xCCs;
    }
    
    public void Registrar() throws Exception
    {
        if(ControlarCodigo()){throw new exCodigoCentroCosto();}
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (byte)xGB.ObtenerClave("centrocosto");
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarCentroCosto(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroPequeno, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Codigo);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Nombre);
        xGB.CrearParametro(4, enTipoParametro.Entero, this.Cuenta.getID());
        
        xGB.EjecutarComando();
        
        new bCentroDeCostoRegistrado(this).Registrar();
    }
    
    private boolean ControlarCodigo() throws Exception
    {
        boolean xF = false;
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "SELECT * FROM centrocosto WHERE ceco_codigo = ?");
        xGB.CrearParametro(1, enTipoParametro.Cadena, Codigo);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xF = true;}
        
        return xF;
    }
    
    public String getNombreConCodigo()
    {return this.Nombre + " ("+this.Codigo+")";}
    
    @Override
    public String toString(){return this.getNombreConCodigo();}
}//end clsCentroCosto