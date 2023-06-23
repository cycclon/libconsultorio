package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Horarios.clsDuracion;
import com.PRS.Framework.Horarios.clsHorario;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

public class clsTrabajoLazy 
{

    private Date Dia;
    private clsDuracion Duracion;
    private clsHorario Horario;
    private int ID;
    private String NombrePractica;
    private String Paciente;
    private String Practicante;
    private String Solicitante;
    private clsValor Saldo;
    private clsTrabajo trabajoLazy;
    private enEstadoTrabajo Estado;
    private int IDPaciente;
    private String DocumentoPaciente;
    private String CC;

    public clsTrabajoLazy(){
        this.Duracion = new clsDuracion();
        this.Horario = new clsHorario();
        this.NombrePractica = "";
        this.Paciente = "";
        this.Practicante = "Por disponibilidad";
        this.Solicitante = "";
        Dia = new Date();
        ID = 0;
        Saldo = new clsValor(0);
        trabajoLazy = new clsTrabajo();
        Estado = enEstadoTrabajo.Solicitado;
        IDPaciente = 0;
        this.DocumentoPaciente = "";
        CC = "";
    }

    clsTrabajoLazy(ResultSet rs) throws Exception
    {
        this();
        Calendar cal = Calendar.getInstance();
        cal.setTime( rs.getTimestamp("prog_fecha"));
        this.Dia = cal.getTime();
        this.Duracion.setMinutosTotales(rs.getShort("pra_duracion"));
        this.Horario.setHoras((byte)cal.get(Calendar.HOUR_OF_DAY));
        this.Horario.setMinutos((byte)cal.get(Calendar.MINUTE));
        this.ID = rs.getInt("tra_id");
        this.NombrePractica = rs.getString("pra_nombre") + " (" 
                + rs.getString("pra_codigo") + ")";
        this.Paciente = rs.getString("pac_apellido") + ", " 
                + rs.getString("pac_nombre");
        if(rs.getString("practicante_nombre") != null)
        {this.Practicante = rs.getString("practicante_abreviatura") + " " 
                + rs.getString("practicante_apellido") + ", " 
                + rs.getString("practicante_nombre");}
        this.Solicitante = rs.getString("solicitante_abreviatura") + " " 
                + rs.getString("solicitante_apellidos") + ", " 
                + rs.getString("solicitante_nombre");
        this.Saldo = new clsValor(rs.getFloat("saldo"));
        this.Estado = enEstadoTrabajo.valueOf(rs.getString("tra_estado"));
        this.IDPaciente = rs.getInt("pac_id");
        this.DocumentoPaciente = rs.getString("pac_tipo_documento") + ": " 
                + rs.getString("pac_numero_documento");
        this.CC = rs.getString("ceco_nombre");
    }
    
    public String getCC(){return CC;}

    public clsDuracion getDuracion(){return Duracion;}

    public String getHorario(){return Horario.getHoraStr();}

    public int getID(){return ID;}

    public String getNombrePractica(){return NombrePractica;}

    public String getPaciente(){return Paciente;}

    public clsTrabajo getTrabajoFull() throws Exception
    {
        clsTrabajo t = new clsTrabajo();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getTrabajoFull(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        ResultSet rs = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(rs);
        
        while(crs.next())
        {t = new clsTrabajo(crs);}
        
        return t;
    }

    public String getPracticante(){return Practicante;}

    public String getSolicitante(){return Solicitante;}
    
    public String getSaldo(){return this.Saldo.pdValorString();}
        
    public String getHorasDuracion()
    {
        return String.valueOf(this.Horario.getHoras());
    }
    
    public String getMinutosDuracion()
    {
        return String.valueOf(this.Horario.getMinutos());
    }
    
    public String getDuracionStr()
    {return this.getHorasDuracion() + ":" 
            + this.getMinutosDuracion();}
    
    public String getHoraFinalizacion()
    {return this.Duracion.getFinalTime(Horario).getHoraStr();}
    
    public boolean isPagable()
    {
        return (this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo
            .Pagable, this.Estado) && this.Saldo.pdValor() > 0);}
    
    public boolean isProgramable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo
            .Programable, this.Estado);}

    public boolean isTrabajable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo
            .Trabajable, this.Estado);}
    
    public boolean isFirmable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo
            .Firmable, this.Estado);}
    
    public boolean isCancelable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo
            .Cancelable, this.Estado);}
    
    public boolean isInformable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo.Informable, Estado);}
    
    public boolean isComprobanteImprimible()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo.ComprobanteImprimible, Estado);}
    
    public boolean isDuplicable()
    {return this.trabajoLazy.checkOpcion(clsTrabajo.enOpcionTrabajo.Duplicable, Estado);}
    
    public boolean isSaldado(){return this.Saldo.pdValor() <= 0;}
    
    public Date getDia(){return Dia;}
    
    public int getIDPaciente(){return this.IDPaciente;}
    
    public enEstadoTrabajo getEstado(){return this.Estado;}
    
    public String getDocumentoPaciente(){return this.DocumentoPaciente;}
}