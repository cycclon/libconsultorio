package com.PRS.Consultorio.Profesionales;
import com.PRS.Consultorio.Bitacoras.Profesionales.bProfesionalEliminado;
import com.PRS.Consultorio.Bitacoras.Profesionales.bProfesionalRestaurado;
import com.PRS.Consultorio.Personas.clsPersonaConsultorio;
import com.PRS.Consultorio.Usuarios.iUsuarioConsultorio;
import com.PRS.Framework.Acceso.clsUsuario;
import com.PRS.Framework.Acceso.iAccesor;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.DynamicComparer.clsDynamicComparer;
import com.PRS.Framework.Identificacion.clsIdentificacion;
import com.PRS.Framework.Identificacion.enTipoIdentificacion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 04-sep-2014 09:10:10 p.m.
 */
public abstract class clsProfesional extends clsPersonaConsultorio implements iUsuarioConsultorio, iAccesor {
    
    private abstract class clsEstadoProfesional
    {
        protected abstract String getIconoBoton();
        
        protected abstract String getAyudaBoton();
        
        protected abstract String getTituloConfirmacion();
        
        protected abstract String getPregunta();
        
        protected abstract boolean getEstado();
        
        protected abstract void CambiarEstado(clsProfesional prProfesional) throws Exception;
        
        protected abstract String getMensajeConfirmacion();
        
    }
    
    private class clsEPActivo extends clsEstadoProfesional
    {

        @Override
        protected String getIconoBoton() {return "remove.png";}

        @Override
        protected String getAyudaBoton() {return "Presione este botón para "
                + "eliminar este profesional.";}

        @Override
        protected boolean getEstado() {return true;}

        @Override
        protected String getTituloConfirmacion() {return "Eliminar profesional";}

        @Override
        protected String getPregunta() {return "¿Está seguro que desea "
                + "eliminar este profesional?";}

        @Override
        protected void CambiarEstado(clsProfesional prProfesional) throws Exception {
            prProfesional.Estado = new clsEPInactivo();
            new bProfesionalEliminado(prProfesional).Registrar();
        }

        @Override
        protected String getMensajeConfirmacion() {return "Se restauró "
                + "correctamente al profesioanl";}
    }
    
    private class clsEPInactivo extends clsEstadoProfesional
    {
        @Override
        protected String getIconoBoton() {return "add.png";}
        
        @Override
        protected String getAyudaBoton() {return "Presione este botón para "
                + "restaurar este profesional.";}
        
        @Override
        protected boolean getEstado() {return false;}
        @Override
        protected String getTituloConfirmacion() {return "Restaurar profesional";}

        @Override
        protected String getPregunta() {return "¿Está seguro que desea restaurar"
                + " este profesional?";}
    
        @Override
        protected void CambiarEstado(clsProfesional prProfesional) throws Exception {
            prProfesional.Estado = new clsEPActivo();
            new bProfesionalRestaurado(prProfesional).Registrar();
        }
        
        @Override
        protected String getMensajeConfirmacion() {return "Se eliminó "
                + "correctamente al profesioanl";}
    }

    protected String Abreviatura;
    protected String Titulo;
    protected clsUsuario Usuario;
    protected clsEstadoProfesional Estado;

    public clsProfesional(){
        super();
        this.Abreviatura = "";
        this.Titulo = "";   
        Usuario = new clsUsuario();
        Estado = new clsEPActivo();
        
    }
    
    protected clsProfesional(ResultSet prRS) throws Exception
    {
        this();
        DefinirProfesional(prRS, "pro");
        DefinirUsuario(prRS, "usr");
    }
    
    protected clsProfesional(ResultSet prRS, String prefix) throws Exception
    {        
        this();
        this.DefinirProfesional(prRS, prefix);
        if(prRS.getInt(prefix + "_usr_id") != 0)
        {this.DefinirUsuario(prRS, "u" + prefix);}
        this.Estado = FabricarEstado(prRS.getBoolean(prefix + "_activo"));
    }
    
    protected final void DefinirProfesional(ResultSet prRS, String prefix) throws Exception
    {
        this.Abreviatura = prRS.getString(prefix + "_abreviatura");
        this.Apellido = prRS.getString(prefix + "_apellidos");
        this.Documento = clsIdentificacion.Instanciar(enTipoIdentificacion.
                valueOf(prRS.getString(prefix + "_tipo_documento")), 
                prRS.getLong(prefix + "_numero_documento"));
        this.ID = prRS.getShort(prefix + "_id");
        this.Nombre = prRS.getString(prefix + "_nombres");
        this.Titulo = prRS.getString(prefix + "_titulo");
        this.Estado = FabricarEstado(prRS.getBoolean(prefix + "_activo"));
    }
    
    protected final void DefinirUsuario(ResultSet prRS, String prefix) throws Exception
    {
        this.Usuario = new clsUsuario(prRS, prefix);
    }
    
    public String getAbreviatura(){return Abreviatura;}
    
    public boolean getActivo(){return this.Estado.getEstado();}
    
    public String getIconoBoton(){return this.Estado.getIconoBoton();}
    
    public String getAyudaBoton(){return this.Estado.getAyudaBoton();}
    
    public String getTituloConfirmacion(){return this.Estado.getTituloConfirmacion();}
    
    public String getPregunta(){return this.Estado.getPregunta();}
    
    public String getMensajeConfirmacion(){return Estado.getMensajeConfirmacion();}

    public abstract enTipoProfesional getTipo();

    public String getTitulo(){return Titulo;}

    public void setAbreviatura(String newVal){Abreviatura = newVal;}

    public void setTitulo(String newVal){Titulo = newVal;}
	
    public static clsProfesional Fabricar(enTipoProfesional prTipo){
       switch(prTipo)
       {
           case Firmante:
               return new clsFirmante();
           case Practicante:
               return new clsPracticante();
           case Solicitante:
               return new clsSolicitante();
           default:
               return null;
       }
    }
    
    public static clsProfesional Fabricar(enTipoProfesional prTipo, 
            ResultSet prRS) throws Exception{
       switch(prTipo)
       {
           case Firmante:
               return new clsFirmante(prRS);
           case Practicante:
               return new clsPracticante(prRS);
           case Solicitante:
               return new clsSolicitante(prRS);
           default:
               return null;
       }
    }
    
    public static clsProfesional Fabricar(enTipoProfesional prTipo,
            ResultSet prRS, String prefix) throws Exception
    {
        switch(prTipo)
       {
           case Firmante:
               return new clsFirmante(prRS, prefix);
           case Practicante:
               return new clsPracticante(prRS, prefix);
           case Solicitante:
               return new clsSolicitante(prRS, prefix);
           default:
               return null;
       }
    }
    
    public void Registrar() throws Exception
    {
         Usuario.Registrar();
        
         clsGestorBases xGB = clsGestorBases.Instanciar();
         
         xGB.EstablecerBaseActiva((byte)1);
         
         this.ID = (int) xGB.ObtenerClave("profesional");
         
         xGB.CrearComando( CommandType.StoredProcedure, 
                 "{call RegistrarProfesional(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
         
         xGB.CrearParametro(1, enTipoParametro.Entero, ID);
         xGB.CrearParametro(2, enTipoParametro.Cadena, this.getTipo().toString());
         xGB.CrearParametro(3, enTipoParametro.Cadena, Titulo);
         xGB.CrearParametro(4, enTipoParametro.Cadena, Abreviatura);
         xGB.CrearParametro(5, enTipoParametro.Cadena, Nombre);
         xGB.CrearParametro(6, enTipoParametro.Cadena, Apellido);
         xGB.CrearParametro(7, enTipoParametro.Cadena, Documento.pdTipo().toString());
         xGB.CrearParametro(8, enTipoParametro.EnteroGrande, Documento.pdNumero());
         xGB.CrearParametro(9, enTipoParametro.Entero, Usuario.pdID());
         
         xGB.EjecutarComando();
         
    }
    
    @Override
    public String getNombreCompleto()
    {
        String NC = super.getNombreCompleto();
        if(this.Abreviatura.compareTo("") != 0){NC = Abreviatura + " " + NC;}
        return NC;
    }
    
    public abstract String getIcono();
    
    public clsUsuario getUsuario(){return Usuario;}
    public void setUsuario(clsUsuario newVal){Usuario = newVal;}
    
    public static List<clsProfesional> Listar(boolean Activo) throws Exception
    {
        List<clsProfesional> xPs = new ArrayList<>();
        List<clsProfesional> xAll = Listar();
        
        for(int i = 0; i < xAll.size(); i++)
        {
            if(Activo || xAll.get(i).getActivo())
            {xPs.add(xAll.get(i));}
        }
        
        return xPs;
    }
    
    private static List<clsProfesional> Listar() throws Exception
    {
        List<clsProfesional> xPs = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getProfesionales()}");
                
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            xPs.add(Fabricar(enTipoProfesional.valueOf(xRS.getString("pro_tipo")), xRS));
        }
        
        return xPs;
    }
    
    private clsEstadoProfesional FabricarEstado(boolean prEstado)
    {
        if(prEstado){return new clsEPActivo();}
        else{return new clsEPInactivo();}
    }
    
    public void CambiarEstado() throws Exception
    {
        this.Estado.CambiarEstado(this);
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call CambiarEstadoProfesional(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Logico, this.Estado.getEstado());
        
        xGB.EjecutarComando();
    }
    
    public static List<clsProfesionalLazy> Listar(boolean activo, 
            enTipoProfesional tipo) throws Exception
    {
        List<clsProfesionalLazy> xPs = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getProfesionalesLazyTipo(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Cadena, tipo.toString());
        xGB.CrearParametro(2, enTipoParametro.Logico, activo);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {xPs.add(new clsProfesionalLazy(xRS));}
        
        return xPs;
    }
    
    public static List<clsProfesionalLazy> ListarEncargados(boolean activo) throws Exception
    {
        List<clsProfesionalLazy> xPs = Listar(activo, enTipoProfesional.Practicante);
        List<clsProfesionalLazy> xFs = Listar(activo, enTipoProfesional.Firmante);
        
        for(clsProfesionalLazy xP : xFs)
        {xPs.add(xP);}
        
        Collections.sort(xPs, new clsDynamicComparer<>(clsProfesionalLazy.class, 
                            "getNombreCompleto", 1));
        
        return xPs;
    }
    
    @Override
    public String getNombreCompletoUC() 
    {return this.getNombreCompleto();}
    
    
}//end clsProfesional