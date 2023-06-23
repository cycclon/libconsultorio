package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaEliminada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaRegistrada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaRestaurada;
import com.PRS.Consultorio.Bitacoras.Practicas.bVigenciaRegistrada;
import com.PRS.Consultorio.CentrosCosto.clsCentroCosto;
import com.PRS.Consultorio.Informes.clsPlantilla;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Horarios.clsDuracion;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 24-sep-2014 03:26:25 p.m.
 */
public class clsPractica {   
    
    abstract class clsEstadoPractica
    {
        public abstract boolean getActiva();
        
        public abstract String getAyudaBoton();
        
        public abstract String getIconoBoton();
        
        public abstract String getTituloConfirmacion();
        
        public abstract String getPregunta();
        
        public abstract String getConfirmacion();
        
        public abstract void CambiarEstado(clsPractica practica) throws Exception;
        
        public abstract String getNombreEstado();
        
        public abstract String getColorEstado();
    }
    
    class clsEPActiva extends clsEstadoPractica
    {

        @Override
        public boolean getActiva() {return true;}

        @Override
        public String getAyudaBoton() {
            return "Presione este botón para eliminar esta práctica.";
        }

        @Override
        public String getIconoBoton() {
            return "remove.png";
        }

        @Override
        public String getTituloConfirmacion() {
            return "Eliminar práctica";
        }

        @Override
        public String getPregunta() {
            return "¿Está seguro que desea eliminar esta práctica?";
        }

        @Override
        public String getConfirmacion() {
            return "La práctica se restauró correctamente";
        }

        @Override
        public void CambiarEstado(clsPractica practica) throws Exception {
            practica.Estado = new clsEPDesactivada(); new bPracticaEliminada(practica).Registrar();
        }

        @Override
        public String getNombreEstado() {return "Activa";}

        @Override
        public String getColorEstado() {return "#E2FFBF";}
    }
    
    class clsEPDesactivada extends clsEstadoPractica
    {

        @Override
        public boolean getActiva() {return false;}

        @Override
        public String getAyudaBoton() {
            return "Presione este botón para restaurar esta práctica";
        }

        @Override
        public String getIconoBoton() {
            return "add.png";
        }

        @Override
        public String getTituloConfirmacion() {
            return "Restaurar práctica";
        }

        @Override
        public String getPregunta() {
            return "¿Está seguro que desea restaurar esta práctica?";
        }

        @Override
        public String getConfirmacion() {
            return "Se eliminó la práctica correctamente";
        }

        @Override
        public void CambiarEstado(clsPractica practica) throws Exception {
            practica.Estado = new clsEPActiva(); new bPracticaRestaurada(practica).Registrar();
        }

        @Override
        public String getNombreEstado() {return "Eliminada";}
        
        @Override
        public String getColorEstado() {return "#FFAFB1";}
    }

    private clsCentroCosto CC;
    private int Codigo;
    private clsHistorialValorPractica Costo;
    private clsDuracion Duracion;
    private short ID;
    private String Nombre;
    private clsEstadoPractica Estado;

    public clsPractica(){
        this.CC = new clsCentroCosto();
        this.Codigo = 0;
        this.Costo = new clsHistorialValorPractica();
        this.Duracion = new clsDuracion((short)0);
        this.Estado = new clsEPActiva();
        this.ID = 0;
        this.Nombre = "";
    }
    
    public clsPractica(int prCodigo, String prNombre, int prDuracionHoras, 
            int prDuracionMinutos, clsCentroCosto prCC )
    {
        this.CC = prCC;
        this.Codigo = prCodigo;
        this.Duracion = new clsDuracion(prDuracionHoras * 60 + prDuracionMinutos);
        this.ID = 0;
        this.Nombre = prNombre;
        this.Costo = new clsHistorialValorPractica();
        this.Estado = new clsEPActiva();
    }
    
    clsPractica(ResultSet prRS) throws Exception
    {
        this();
        this.ID = prRS.getShort("pra_id");
        this.CC = new clsCentroCosto(prRS);
        this.Codigo = prRS.getInt("pra_codigo");        
        this.Duracion.setMinutosTotales(prRS.getShort("pra_duracion"));
        this.Estado = Fabricar(prRS.getBoolean("pra_activa"));
        this.Nombre = prRS.getString("pra_nombre");
        this.Costo = new clsHistorialValorPractica(this.ID);
    }

    public clsCentroCosto getCC(){return CC;}

    public int getCodigo(){return Codigo;}

    public clsHistorialValorPractica getCosto(){return Costo;}

    public clsDuracion getDuracion(){return Duracion;}

    public short getID(){return ID;}

    public String getNombre(){return Nombre;}

    public void setCC(clsCentroCosto newVal){CC = newVal;}

    public void setCodigo(int newVal){Codigo = newVal;}

    public void setCosto(clsHistorialValorPractica newVal){Costo = newVal;}

    public void setDuracion(clsDuracion newVal){Duracion = newVal;}

    public void setID(short newVal){ID = newVal;}

    public void setNombre(String newVal){Nombre = newVal;}
    
    public void Registrar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (short) xGB.ObtenerClave("practica");
        
        xGB.CrearComando(CommandType.StoredProcedure,
                "{call RegistrarPractica(?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroPequeno, CC.getID());
        xGB.CrearParametro(3, enTipoParametro.Entero, Codigo);
        xGB.CrearParametro(4, enTipoParametro.EnteroChico, (short)Duracion.getMinutosTotales());
        xGB.CrearParametro(5, enTipoParametro.Cadena, Nombre);
        
        xGB.EjecutarComando();
        
        new bPracticaRegistrada(this).Registrar();
    }
    
    public void AgregarCosto(clsValorPractica Costo) throws Exception
    {
        if(Costo.getID() == 0)
        {
            Costo.Registrar(this.ID);
            this.Costo.getHistorial().add(Costo);
        }
        else
        {
            Costo.Actualizar();
        }      
        
        new bVigenciaRegistrada(Costo, this).Registrar();
    }
    
    clsEstadoPractica Fabricar(boolean activa)
    {
        if(activa){return new clsEPActiva();}
        else{return new clsEPDesactivada();}
    }
    
    public String getAyudaBoton()
    {return this.Estado.getAyudaBoton();}
        
    public  String getIconoBoton()
    {return this.Estado.getIconoBoton();}

    public  String getTituloConfirmacion()
    {return this.Estado.getTituloConfirmacion();}

    public String getPregunta()
    {return this.Estado.getPregunta();}
    
    public String getConfirmacion()
    {return this.Estado.getConfirmacion();}
    
    public boolean getActiva(){return this.Estado.getActiva();}
    
    public static List<clsPractica> Listar(boolean inactivas) throws Exception
    {
        List<clsPractica> xPS = new ArrayList<>();
        List<clsPractica> xAll = Listar();
        
        for(clsPractica xP : xAll)
        {
            if(inactivas || xP.getActiva())
            {xPS.add(xP);}
        }
        
        return xPS;
    }
    
    public static List<clsPracticaLazy> ListarLazy(boolean inactivas) throws Exception
    {
        
        List<clsPracticaLazy> xPS = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPracticasLazy()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {
            if(crs.getBoolean("pra_activa") || inactivas)
                xPS.add(new clsPracticaLazy(crs));
        }
        
        return xPS;
        
    }
    
    private static List<clsPractica> Listar() throws Exception
    {
        List<clsPractica> xPS = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPracticasCC()}");
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {
            xPS.add(new clsPractica(crs));
        }
        
        return xPS;
    }
    
    public void CambiarEstado() throws Exception
    {
        this.Estado.CambiarEstado(this);
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call CambiarEstadoPractica(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Logico, Estado.getActiva());      
        
        
        xGB.EjecutarComando();
    }
    
    public String getNombreEstado()
    {return this.Estado.getNombreEstado();}   
    
    public void Actualizar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call ActualizarPractica(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, (short)Duracion.getMinutosTotales());
        
        xGB.EjecutarComando();
    }
    
    public String getColorEstado(){return this.Estado.getColorEstado();}
    
    public String getNombreyCodigo()
    {return this.Nombre + " (" + this.Codigo + ")";}
    
    static clsPractica Obtener(short IDPractica) throws Exception
    {
        clsPractica xP = new clsPractica();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPractica(?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, IDPractica);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xP = new clsPractica(crs);}

        return xP;
    }
    
    public String getNombrePlantilla(String sufix) {
        return "plantilla-" + String.valueOf(this.Codigo) + sufix + ".html";
    }
    
   
}//end clsPractica