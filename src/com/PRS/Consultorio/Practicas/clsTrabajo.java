package com.PRS.Consultorio.Practicas;

import com.PRS.Consultorio.Bitacoras.Trabajos.bTrabajoFirmado;
import com.PRS.Consultorio.Bitacoras.Trabajos.bTrabajoRealizado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoCancelado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoRegistrado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoReprogramado;
import com.PRS.Consultorio.Cuentas.clsCobroCoseguro;
import com.PRS.Consultorio.Cuentas.clsCobroOS;
import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.Pacientes.clsPaciente;
import com.PRS.Consultorio.Pacientes.clsParticular;
import com.PRS.Consultorio.Profesionales.*;
import com.PRS.Consultorio.Usuarios.clsGestorUsuariosConsultorio;
import com.PRS.Framework.Acceso.clsGestorAcceso;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Excepciones.Envios.exProgramar;
import com.PRS.Framework.Horarios.clsHorario;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 24-sep-2014 03:26:28 p.m.
 */
public class clsTrabajo {

    
// <editor-fold defaultstate="collapsed" desc="Clases anidadas">
    public enum enOpcionTrabajo
    {
        Pagable,
        Programable,
        Trabajable,
        NoTrabajable,
        Cancelable,
        Firmable,
        Informable, 
        ComprobanteImprimible,
        Duplicable
    }

    private abstract class clsEstadoTrabajo
    {
        public abstract void Programar(clsTrabajo trabajo, 
                Date fecha, clsHorario hora) throws Exception;
        
        public abstract void Realizar(clsTrabajo trabajo, clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception;
        
        public abstract void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception;
        
        public abstract void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception;
        
        public abstract void NoRealizado(clsTrabajo trabajo, 
                String motivo) throws Exception;
        
        public abstract enEstadoTrabajo getEstado();
        
        public abstract String getEstadoStr(clsTrabajo trabajo);
        
        public boolean isPagable()
        {return false;}
    
        public boolean isProgramable()
        {return false;}

        public boolean isTrabajable()
        {return false;}

        public boolean isFirmable()
        {return false;}

        public boolean isCancelable()
        {return false;}
        
        public boolean isNoRealizable()
        {return false;}
        
        public boolean isInformable()
        {return false;}
        
        public boolean isComprobanteImprimible()
        {return false;}
        
        public boolean isDuplicable()
        {return false;}
    }

    public class clsETSolicitado extends clsEstadoTrabajo
    {

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception 
        {trabajo.CambiarEstado(new clsETProgramado(trabajo, fecha, hora));}

        @Override
        public void Realizar(clsTrabajo trabajo, clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception 
        {trabajo.CambiarEstado(new clsETRealizado(trabajo, realizador, fecha, observaciones));}

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception {
            throw new exFirmarSolicitado();
        }

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception 
        {trabajo.CambiarEstado(new clsETCancelado(trabajo, fecha, motivo));}

        @Override
        public void NoRealizado(clsTrabajo trabajo, String motivo) throws Exception 
        {trabajo.CambiarEstado(new clsETNoRealizado(trabajo, motivo));}

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.Solicitado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) {
            return "Solicitado por " + trabajo.Solicitud.getSolicitante()
                    .getNombreCompleto();
        }
        
        @Override
        public boolean isProgramable()
        {return true;}

        @Override
        public boolean isCancelable()
        {return true;}
    }
    
    private class clsETProgramado extends clsEstadoTrabajo
    {
        private clsETProgramado()
        {}
        
        clsETProgramado(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception
        {
            clsProgramacion xProg = new clsProgramacion(fecha, hora, trabajo.ID);
            trabajo.Programacion = xProg;
            
        }

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception 
        {trabajo.CambiarEstado(new clsETProgramado(trabajo, fecha, hora));
        new bTurnoReprogramado(trabajo).Registrar();}

        @Override
        public void Realizar(clsTrabajo trabajo,  clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception 
        {trabajo.CambiarEstado(new clsETRealizado(trabajo, realizador, fecha, observaciones));}

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception {
            throw new exFirmarProgramado();
        }

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception 
        {trabajo.CambiarEstado(new clsETCancelado(trabajo, fecha, motivo));}

        @Override
        public void NoRealizado(clsTrabajo trabajo, String motivo) throws Exception 
        {trabajo.CambiarEstado(new clsETNoRealizado(trabajo, motivo));}

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.Programado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) 
        {return "Programado para el día: " + new SimpleDateFormat("dd/MM/yyyy hh:mm")
                .format(trabajo.Programacion.getFecha());}
        
        @Override
        public boolean isPagable()
        {return true;}
        
        @Override
        public boolean isProgramable()
        {return true;}

        @Override
        public boolean isTrabajable()
        {return true;}

        @Override
        public boolean isCancelable()
        {return true;}
        
        @Override
        public boolean isNoRealizable()
        {return true;}
        
        @Override
        public boolean isComprobanteImprimible()
        {return true;}
        
        @Override
        public boolean isDuplicable()
        {return true;}
    }
    
    private class clsETRealizado extends clsEstadoTrabajo
    {
        private clsETRealizado(){}
        
        clsETRealizado(clsTrabajo trabajo,  clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception
        {
            trabajo.Realizacion = new clsRealizacion(realizador, fecha, 
                    observaciones, trabajo.ID);
            new bTrabajoRealizado(trabajo).Registrar();
        }

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception {
            throw new exProgramarRealizado();
        }

        @Override
        public void Realizar(clsTrabajo trabajo, clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception {
            throw new exRealizarRealizado();
        }

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception 
        {trabajo.CambiarEstado(new clsETFirmado(trabajo, fecha, firmante));}

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception {
            //BORRAR REALIZACION
            clsRealizacion.Eliminar(trabajo.getID());
            trabajo.Realizacion = new clsRealizacion();
            //BORRAR PAGOS SI EXISTEN
            clsPago.Eliminar(trabajo.getID());
            
            //BORRAR COBROS SI EXISTEN
            clsCobroCoseguro.Eliminar(trabajo.getID());            
            trabajo.CobroCoseguro = new clsCobroCoseguro(0, new Date());
            
            //CAMBIAR ESTADO
            trabajo.CambiarEstado(new clsETCancelado(trabajo, fecha, motivo));
        }

        @Override
        public void NoRealizado(clsTrabajo trabajo, 
                String motivo) throws Exception {
            throw new exNoRealizadoRealizado();
        }

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.Realizado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) {
            return "Trabajo realizado por " + trabajo.Realizacion.getRealizador()
                    .getNombreCompleto() + " el día " + 
                    new SimpleDateFormat("dd/MM/yyyy hh:mm")
                        .format(trabajo.Realizacion.getFecha());
        }
        
        @Override
        public boolean isPagable()
        {return true;}
        
        @Override
        public boolean isFirmable()
        {return true;}
        
        @Override
        public boolean isCancelable()
        {return true;}

    }
    
    private class clsETCancelado extends clsEstadoTrabajo
    {
        public clsETCancelado()
        {}
        
        clsETCancelado(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception
        {
            trabajo.Cancelacion = new clsCancelacion(fecha, motivo, trabajo.ID);
            trabajo.CobroCoseguro.Cancelar();
            trabajo.CobroOS.Cancelar();
            new bTurnoCancelado(trabajo).Registrar();
        }

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception 
        {
            //BORRAR CANCELACION
            clsCancelacion.Eliminar(trabajo.getID());
            trabajo.Cancelacion = new clsCancelacion();
            
            //PROGRAMAR
            trabajo.CambiarEstado(new clsETProgramado(trabajo, fecha, hora));
            new bTurnoReprogramado(trabajo).Registrar();
        }

        @Override
        public void Realizar(clsTrabajo trabajo,  clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception {
            throw new exRealizarCancelado();
        }

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception {
            throw new exFirmarCancelado();
        }

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception {
            throw new exCancelarCancelado();
        }

        @Override
        public void NoRealizado(clsTrabajo trabajo, 
                String motivo) throws Exception {
            throw new exNoRealizadoCancelado();
        }

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.Cancelado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) {
            return "Cancelado el " + new SimpleDateFormat("dd/MM/yyyy hh:mm")
                    .format(trabajo.Cancelacion.getFecha());
        }   

        @Override
        public boolean isProgramable() {
            return true;
        }
        
        

    }
    
    private class clsETFirmado extends clsEstadoTrabajo
    {
        private clsETFirmado(){}
        
        clsETFirmado(clsTrabajo trabajo, Date fecha,
                clsFirmante firmante) throws Exception
        {
            trabajo.Firma = new clsFirma(fecha, firmante, trabajo.ID);
            new bTrabajoFirmado(trabajo).Registrar();
        }

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception {
            throw new exProgramarFirmado();
        }

        @Override
        public void Realizar(clsTrabajo trabajo,  clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception {
            throw new exRealizarFirmado();
        }

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception {
            throw new exFirmarFirmado();
        }

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception {
            throw new exCancelarFirmado();
        }

        @Override
        public void NoRealizado(clsTrabajo trabajo, 
                String motivo) throws Exception {
            throw new exNoRealizarFirmado();
        }

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.Firmado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) {
            return "Trabajo firmado por: " + trabajo.Firma.getFirmante()
                    .getNombreCompleto() + " el " + 
                    new SimpleDateFormat("dd/MM/yyyy hh:mm")
                            .format(trabajo.Firma.getFecha());
        }
        
        @Override
        public boolean isPagable()
        {return true;}
        
        @Override
        public boolean isInformable()
        {return true;}
    }
    
    private class clsETNoRealizado extends clsEstadoTrabajo
    {
        private clsETNoRealizado(){}
        
        clsETNoRealizado(clsTrabajo trabajo, String motivo) throws Exception
        {trabajo.NoRealizacion = new clsNoRealizacion(motivo, trabajo.ID);}

        @Override
        public void Programar(clsTrabajo trabajo, Date fecha, clsHorario hora) throws Exception 
        {trabajo.CambiarEstado(new clsETProgramado(trabajo, fecha, hora));}

        @Override
        public void Realizar(clsTrabajo trabajo,  clsProfesional realizador, Date fecha, 
                String observaciones) throws Exception 
        {trabajo.CambiarEstado(new clsETRealizado(trabajo, realizador, fecha, observaciones));}

        @Override
        public void Firmar(clsTrabajo trabajo, Date fecha, 
                clsFirmante firmante) throws Exception {
            throw new exFirmarNoRealizado();
        }

        @Override
        public void Cancelar(clsTrabajo trabajo, Date fecha, 
                String motivo) throws Exception 
        {trabajo.CambiarEstado(new clsETCancelado(trabajo, fecha, motivo));}

        @Override
        public void NoRealizado(clsTrabajo trabajo, 
                String motivo) throws Exception {
            throw new exNoRealizarNoRealizado();
        }

        @Override
        public enEstadoTrabajo getEstado() {return enEstadoTrabajo.NoRealizado;}

        @Override
        public String getEstadoStr(clsTrabajo trabajo) {
            return "No realizado";
        }
        
        @Override
        public boolean isProgramable()
        {return true;}

        @Override
        public boolean isTrabajable()
        {return true;}

        @Override
        public boolean isCancelable()
        {return true;}
    }
// </editor-fold>    
    
    private int ID;
    /*private clsInforme Informe;*/
    private clsPractica Practica;
    private clsPaciente Paciente;
    private clsCobroCoseguro CobroCoseguro;
    private clsCobroOS CobroOS;
    private clsEstadoTrabajo Estado;
    private clsFirma Firma;
    private clsProgramacion Programacion;
    private clsRealizacion Realizacion;
    private clsSolicitud Solicitud;
    private clsCancelacion Cancelacion;
    private clsNoRealizacion NoRealizacion;
    private List<clsValorCampo> Campos;

    public clsTrabajo(){
        this.CobroCoseguro = new clsCobroCoseguro(0, new Date());
        this.CobroOS = new clsCobroOS(0, new Date());
        this.Estado = new clsETSolicitado();
        this.ID = 0; 
        this.Paciente = new clsParticular();
        this.Practica = new clsPractica();
        this.Cancelacion = new clsCancelacion();
        this.NoRealizacion = new clsNoRealizacion();
        this.Firma = new clsFirma();
        this.Programacion = new clsProgramacion();
        this.Realizacion = new clsRealizacion();
        this.Solicitud = new clsSolicitud(new clsSolicitante());   
        this.Campos = new ArrayList<>();
    }
    
    clsTrabajo(ResultSet prRS) throws Exception
    {
        this();
        this.ID = prRS.getInt("tra_id");
        this.Estado = FabricarEstado(enEstadoTrabajo.valueOf(prRS.getString("tra_estado")));
        this.Practica = new clsPractica(prRS);
        this.Paciente = clsPaciente.Fabricar(prRS);
        if(prRS.getInt("prog_id") != 0)
        {this.Programacion = new clsProgramacion(prRS);}
        if(prRS.getInt("can_id") != 0)
        {this.Cancelacion = new clsCancelacion(prRS);}
        if(prRS.getInt("sol_id") != 0)
        {this.Solicitud = new clsSolicitud(prRS);}
        if(prRS.getInt("fir_id")!=0)
        {this.Firma = new clsFirma(prRS);}
        if(prRS.getInt("retr_id") != 0)
        {this.Realizacion = new clsRealizacion(prRS);}
        if(prRS.getInt("nore_id") != 0)
        {this.NoRealizacion = new clsNoRealizacion(prRS);}
        
        this.CobroCoseguro = clsCobroCoseguro.Obtener(this.ID);
        this.CobroOS = clsCobroOS.Obtener(this.ID);
        
        this.ObtenerValores();
    }
    
    private void ObtenerValores() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.Campos = new ArrayList<>();
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getValorCamposTrabajo(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {this.Campos.add(new clsValorCampo(xRS));}
        
    }

    public int getID(){return ID;}

    public clsPaciente getPaciente(){return Paciente;}
    
    public clsPractica getPractica(){return Practica;}

    public void setID(int newVal){ID = newVal;}

    public void setPaciente(clsPaciente newVal){Paciente = newVal;}

    public void setPractica(clsPractica newVal){Practica = newVal;}

    public clsCobroCoseguro getCobroCoseguro(){return CobroCoseguro;}

    public clsCobroOS getCobroOS(){return CobroOS;}

    public void setCobroCoseguro(clsCobroCoseguro newVal){CobroCoseguro = newVal;}
    
    public void Programar(Date fecha, clsHorario hora) throws Exception
    {this.Estado.Programar(this, fecha, hora);}
        
    public void Realizar(clsProfesional realizador, Date fecha, 
            String observaciones) throws Exception
    {this.Estado.Realizar(this, realizador, fecha, observaciones);}

    public void Firmar(Date fecha, clsFirmante firmante) throws Exception
    {this.Estado.Firmar(this, fecha, firmante);}
    
    public void Firmar(Date fecha) throws Exception
    {
        try
        {
            clsProfesional p = clsGestorUsuariosConsultorio.Instanciar().
                    ObtenerProfesionalLogueado(clsGestorAcceso.Instanciar().getSesion().pdUsuario());
            if(p.getTipo() != enTipoProfesional.Firmante)throw new exNoEsFirmador();
            Firmar(fecha, (clsFirmante)p);
        }
        catch(Exception ex){throw new exNoEsFirmador();}
        
    }

    public void Cancelar(Date fecha, String motivo) throws Exception
    {this.Estado.Cancelar(this, fecha, motivo);}

    public void NoRealizado(String motivo) throws Exception
    {this.Estado.NoRealizado(this, motivo);}

    public enEstadoTrabajo getEstado(){return this.Estado.getEstado();}

    public String getEstadoStr(){return this.Estado.getEstadoStr(this);}

    public clsFirma getFirma(){return Firma;}

    public clsProgramacion getProgramacion(){return Programacion;}

    public clsRealizacion getRealizacion(){return Realizacion;}

    public clsSolicitud getSolicitud(){return Solicitud;}

    public clsCancelacion getCancelacion(){return Cancelacion;}
   
    public clsNoRealizacion getNoRealizacion(){return this.NoRealizacion;}
    
    public void Registrar(clsProfesional solicitante, Date fechaProgramacion, 
            clsHorario horaProgramacion, float coseguro, float montoAbonado, 
            Date fechaPago, clsProfesional encargado, clsProfesional firmante, 
            List<clsValorCampo> camposAdicionales) throws Exception
    {
        this.Solicitud = new clsSolicitud(solicitante);
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.ID = (int)xGB.ObtenerClave("trabajo");
        this.Solicitud.setID((int)xGB.ObtenerClave("solicitud_trabajo"));
                
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarTrabajoSolicitado(?, ?, ?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, Practica.getID());
        xGB.CrearParametro(3, enTipoParametro.Cadena, Estado.getEstado().toString());
        xGB.CrearParametro(4, enTipoParametro.Entero, Paciente.getID());
        xGB.CrearParametro(5, enTipoParametro.Entero, Solicitud.getID());
        xGB.CrearParametro(6, enTipoParametro.Fecha, Solicitud.getFecha());
        xGB.CrearParametro(7, enTipoParametro.Entero, solicitante.getID());        
        
        xGB.EjecutarComando();
        
        /*PROGRAMAR TRABAJO*/
        this.Programar(fechaProgramacion, horaProgramacion);     
        
        /*REGISTRAR COBRO COSEGURO*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaProgramacion);
        cal.set(Calendar.HOUR, horaProgramacion.getHoras());
        cal.set(Calendar.MINUTE, horaProgramacion.getMinutos());
        this.CobroCoseguro = new clsCobroCoseguro(coseguro, cal.getTime());
        this.CobroCoseguro.Registrar(ID);
        
        /*REGISTRAR COBRO OBRA SOCIAL*/
        if(this.Practica.getCosto().getValorVigente(Paciente.getOS(), fechaProgramacion)
                .getCostoOS().pdValor() > 0){
        this.CobroOS = new clsCobroOS(this.Practica.getCosto()
                .getValorVigente(Paciente.getOS(), fechaProgramacion)
                .getCostoOS().pdValor(), cal.getTime());
        this.CobroOS.Registrar(ID);}
        
        /*REGISTRAR PAGO COSEGURO(SI EXISTE)*/
        if(montoAbonado > 0)
        {this.CobroCoseguro.Pagar(montoAbonado);}
        
        /*REGISTRAR ENCARGADO (SI EXISTE)*/
        if(encargado.getID() > 0)
        {            
            this.Realizacion = new clsRealizacion(encargado, new Date(), "", ID);
        }
        
        /*REGISTRAR FIRMANTE (SI EXISTE)*/
        if(firmante.getID() > 0)
        {this.Firma = new clsFirma(new Date(),(clsFirmante) firmante, ID);}
        
        /*REGISTRAR CAMPOS ADICIONALES*/
        for(clsValorCampo xVC : camposAdicionales)
        {xVC.Registrar(ID);}
        this.Campos = camposAdicionales;
        
        new bTurnoRegistrado(this).Registrar();
    }
    
    private void CambiarEstado(clsEstadoTrabajo nuevoEstado) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call CambiarEstadoTrabajo(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, nuevoEstado.getEstado().toString());
        
        xGB.EjecutarComando();
        
        this.Estado = nuevoEstado;
    }
    
    public boolean isPagable()
    {return this.Estado.isPagable() && !this.CobroCoseguro.isSaldado();}
    
    public boolean isProgramable()
    {return this.Estado.isProgramable();}

    public boolean isTrabajable()
    {return this.Estado.isTrabajable();}
    
    public boolean isFirmable()
    {return this.Estado.isFirmable();}
    
    public boolean isCancelable()
    {return this.Estado.isCancelable();}
    
    public boolean isInformable()
    {return this.Estado.isInformable();}
    
    public boolean isComprobanteImprimible()
    {return this.Estado.isComprobanteImprimible();}
    
    public boolean isDuplicable()
    {return this.Estado.isDuplicable();}
    
    public boolean checkOpcion(enOpcionTrabajo opcion, enEstadoTrabajo estado)
    {
        clsEstadoTrabajo xE = FabricarEstado(estado);
        switch(opcion)
        {
            case Cancelable:
                return xE.isCancelable();
            case Firmable:
                return xE.isFirmable();
            case NoTrabajable:
                return xE.isNoRealizable();
            case Pagable:
                return xE.isPagable();
            case Programable:
                return xE.isProgramable();
            case Trabajable:
                return xE.isTrabajable();
            case Informable:
                return xE.isInformable();
            case ComprobanteImprimible:
                    return xE.isComprobanteImprimible();
            case Duplicable:
                return xE.isDuplicable();
            default:
                return false;
                
        }        
    }
    
    private clsEstadoTrabajo FabricarEstado(enEstadoTrabajo estado)
    {
        switch(estado)
        {
            case Cancelado:
                return new clsTrabajo.clsETCancelado();
            case Firmado:
                    return new clsETFirmado();
            case NoRealizado:
                return new clsETNoRealizado();
            case Programado:
                return new clsETProgramado();
            case Realizado:
                return new clsETRealizado();
            case Solicitado:
                return new clsETSolicitado();
            default:
                return new clsETSolicitado();
        }
    }
    
    public List<clsValorCampo> getCampos()
    {return Campos;}
    
    public void ModificarPractica()throws Exception {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call ModificarPracticaTrabajo(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, Practica.getID());
        
        xGB.EjecutarComando();
    }
}//end clsTrabajo