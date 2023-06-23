package com.PRS.Consultorio.Bitacoras;

import com.PRS.Consultorio.Bitacoras.Campos.bCampoEliminado;
import com.PRS.Consultorio.Bitacoras.Campos.bCampoRegistrado;
import com.PRS.Consultorio.Bitacoras.Campos.bCampoRestaurado;
import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoEliminado;
import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoRegistrado;
import com.PRS.Consultorio.Bitacoras.CentrosCosto.bCentroDeCostoRestaurado;
import com.PRS.Consultorio.Bitacoras.Colegio.bPagoColegio;
import com.PRS.Consultorio.Bitacoras.Comprobantes.bComprobanteEditado;
import com.PRS.Consultorio.Bitacoras.Cuentas.bPagoRegistrado;
import com.PRS.Consultorio.Bitacoras.Gastos.bGastoEliminado;
import com.PRS.Consultorio.Bitacoras.Gastos.bGastoRegistrado;
import com.PRS.Consultorio.Bitacoras.Informes.bEncabezadoYPie;
import com.PRS.Consultorio.Bitacoras.Informes.bInformeImpreso;
import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialEliminada;
import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialRegistrada;
import com.PRS.Consultorio.Bitacoras.ObrasSociales.bObraSocialRestaurada;
import com.PRS.Consultorio.Bitacoras.Pacientes.bAfiliacionModificada;
import com.PRS.Consultorio.Bitacoras.Pacientes.bContactoPacienteAgregado;
import com.PRS.Consultorio.Bitacoras.Pacientes.bContactoPacienteEliminado;
import com.PRS.Consultorio.Bitacoras.Pacientes.bPacienteModificado;
import com.PRS.Consultorio.Bitacoras.Pacientes.bPacienteRegistrado;
import com.PRS.Consultorio.Bitacoras.Practicas.bPlantillaEditada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaEliminada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaModificada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaRegistrada;
import com.PRS.Consultorio.Bitacoras.Practicas.bPracticaRestaurada;
import com.PRS.Consultorio.Bitacoras.Practicas.bVigenciaRegistrada;
import com.PRS.Consultorio.Bitacoras.Profesionales.bProfesionalEliminado;
import com.PRS.Consultorio.Bitacoras.Profesionales.bProfesionalRegistrado;
import com.PRS.Consultorio.Bitacoras.Profesionales.bProfesionalRestaurado;
import com.PRS.Consultorio.Bitacoras.Trabajos.bTrabajoFirmado;
import com.PRS.Consultorio.Bitacoras.Trabajos.bTrabajoRealizado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoCancelado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoRegistrado;
import com.PRS.Consultorio.Bitacoras.Turnos.bTurnoReprogramado;
import com.PRS.Framework.Acceso.clsGestorAcceso;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:04 p.m.
 */
public abstract class clsBitacora {

        private long ID;
	protected String Accion;
	private final Date Fecha;
	private final String IP;
	private final String Usuario;
	private final String WorkCenter;
        
        public static final String DEFAULTUSERNAME = "Anónimo";
        
        protected clsBitacora() throws Exception{
            this.Fecha = new Date();
            this.Accion = "";
            this.IP = InetAddress.getLocalHost().getHostAddress();
            if(clsGestorAcceso.Instanciar().getSesion() != null)
            {this.Usuario = clsGestorAcceso.Instanciar().getSesion().pdUsuario()
                    .pdUsername();}
            else{this.Usuario = DEFAULTUSERNAME;}
            this.WorkCenter = InetAddress.getLocalHost().getHostName();
            ID = 0;
	}
        
        protected clsBitacora(ResultSet rs) throws SQLException
        {
            this.Accion = rs.getString("bit_accion");
            this.Fecha = rs.getTimestamp("bit_fecha");
            this.ID = rs.getLong("bit_id");
            this.IP = rs.getString("bit_ip");
            this.Usuario = rs.getString("bit_usuario");
            this.WorkCenter = rs.getString("bit_workcenter");
        }

	public final String getAccion(){return Accion;}

	public final Date getFecha(){return Fecha;}

	public final String getIP(){return IP;}

	public abstract String getNombre();

	public abstract enTipoBitacora getTipo();

	public final String getUsuario(){return Usuario;}

	public final String getWorkCenter(){return WorkCenter;}

	public final  void Registrar() throws Exception
        {
            clsGestorBases xGB = clsGestorBases.Instanciar();
            xGB.EstablecerBaseActiva((byte)1);
            
            ID = xGB.ObtenerClave("bitacora");
            
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call RegistrarBitacora(?, ?, ?, ?, ?, ?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroGrande, ID);
            xGB.CrearParametro(2, enTipoParametro.Fecha, Fecha);
            xGB.CrearParametro(5, enTipoParametro.Cadena, Accion);
            xGB.CrearParametro(6, enTipoParametro.Cadena, IP);
            xGB.CrearParametro(3, enTipoParametro.Cadena, Usuario);
            xGB.CrearParametro(7, enTipoParametro.Cadena, WorkCenter);
            xGB.CrearParametro(4, enTipoParametro.Cadena, getTipo().toString());
            
            xGB.EjecutarComando();
        }
        
        public static enTipoBitacora getTipoPorNombre(String nombre) throws Exception
        {
            enTipoBitacora tipo = null;
            
            for(enTipoBitacora tb : enTipoBitacora.values())
            {
                if(getNombrePorTipo(tb).equals(nombre))tipo = tb;                
            }
            
            return tipo;
        }
        
        public static String getNombrePorTipo(enTipoBitacora tipo) throws Exception
        {
            switch(tipo)
            {
                case AfilicacionModificada:
                    return new bAfiliacionModificada().getNombre();
                case CampoEliminado:
                    return new bCampoEliminado().getNombre();
                case CampoRegistrado:
                    return new bCampoRegistrado().getNombre();
                case CampoRestaurado:
                    return new bCampoRestaurado().getNombre();
                case CentroDeCostoEliminado:
                    return new bCentroDeCostoEliminado().getNombre();
                case CentroDeCostoRegistrado:
                    return new bCentroDeCostoRegistrado().getNombre();
                case CentroDeCostoRestaurado:
                    return new bCentroDeCostoRestaurado().getNombre();
                case ComprobanteEditado:
                    return new bComprobanteEditado().getNombre();
                case ContactoPacienteAgregado:
                    return new bContactoPacienteAgregado().getNombre();
                case ContactoPacienteEliminado:
                    return new bContactoPacienteEliminado().getNombre();
                case EncabezadoYPie:
                    return new bEncabezadoYPie().getNombre();
                case GastoEliminado:
                    return new bGastoEliminado().getNombre();
                case GastoRegistrado:
                    return new bGastoRegistrado().getNombre();
                case InformeImpreso: 
                    return new bInformeImpreso().getNombre();
                case ObraSocialEliminada:
                    return new bObraSocialEliminada().getNombre();
                case ObraSocialRegistrada:
                    return new bObraSocialRegistrada().getNombre();
                case ObraSocialRestaurada:
                    return new bObraSocialRestaurada().getNombre();
                case PacienteModificado:
                    return new bPacienteModificado().getNombre();
                case PacienteRegistrado: 
                    return new bPacienteRegistrado().getNombre();
                case PagoRegistrado:
                    return new bPagoRegistrado().getNombre();
                case PlantillaEditada:
                    return new bPlantillaEditada().getNombre();
                case PracticaEliminada:
                    return new bPracticaEliminada().getNombre();
                case PracticaModificada:
                    return new bPracticaModificada().getNombre();
                case PracticaRegistrada:
                    return new bPracticaRegistrada().getNombre();
                case PracticaRestaurada:
                    return new bPracticaRestaurada().getNombre();
                case ProfesionalEliminado:
                    return new bProfesionalEliminado().getNombre();
                case ProfesionalRegistrado:
                    return new bProfesionalRegistrado().getNombre();
                case ProfesionalRestaurado:
                    return new bProfesionalRestaurado().getNombre();
                case TrabajoFirmado:
                    return new bTrabajoFirmado().getNombre();
                case TrabajoRealizado:
                    return new bTrabajoRealizado().getNombre();
                case TurnoCancelado:
                    return new bTurnoCancelado().getNombre();
                case TurnoRegistrado:
                    return new bTurnoRegistrado().getNombre();
                case TurnoReprogramado:
                    return new bTurnoReprogramado().getNombre();
                case VigenciaRegistrada:
                    return new bVigenciaRegistrada().getNombre();
                case PagoColegio:
                    return new bPagoColegio().getNombre();
                default:
                    return "";
            }
        }
        
        static clsBitacora Fabricar(ResultSet rs) throws SQLException
        {
            switch(enTipoBitacora.valueOf(rs.getString("bit_tipo")))
            {
                case AfilicacionModificada:
                    return new bAfiliacionModificada(rs);
                case CampoEliminado:
                    return new bCampoEliminado(rs);
                case CampoRegistrado:
                    return new bCampoRegistrado(rs);
                case CampoRestaurado:
                    return new bCampoRestaurado(rs);
                case CentroDeCostoEliminado:
                    return new bCentroDeCostoEliminado(rs);
                case CentroDeCostoRegistrado:
                    return new bCentroDeCostoRegistrado(rs);
                case CentroDeCostoRestaurado:
                    return new bCentroDeCostoRestaurado(rs);
                case ComprobanteEditado:
                    return new bComprobanteEditado(rs);
                case ContactoPacienteAgregado:
                    return new bContactoPacienteAgregado(rs);
                case ContactoPacienteEliminado:
                    return new bContactoPacienteEliminado(rs);
                case EncabezadoYPie:
                    return new bEncabezadoYPie(rs);
                case GastoEliminado:
                    return new bGastoEliminado(rs);
                case GastoRegistrado:
                    return new bGastoRegistrado(rs);
                case InformeImpreso: 
                    return new bInformeImpreso(rs);
                case ObraSocialEliminada:
                    return new bObraSocialEliminada(rs);
                case ObraSocialRegistrada:
                    return new bObraSocialRegistrada(rs);
                case ObraSocialRestaurada:
                    return new bObraSocialRestaurada(rs);
                case PacienteModificado:
                    return new bPacienteModificado(rs);
                case PacienteRegistrado: 
                    return new bPacienteRegistrado(rs);
                case PagoRegistrado:
                    return new bPagoRegistrado(rs);
                case PlantillaEditada:
                    return new bPlantillaEditada(rs);
                case PracticaEliminada:
                    return new bPracticaEliminada(rs);
                case PracticaModificada:
                    return new bPracticaModificada(rs);
                case PracticaRegistrada:
                    return new bPracticaRegistrada(rs);
                case PracticaRestaurada:
                    return new bPracticaRestaurada(rs);
                case ProfesionalEliminado:
                    return new bProfesionalEliminado(rs);
                case ProfesionalRegistrado:
                    return new bProfesionalRegistrado(rs);
                case ProfesionalRestaurado:
                    return new bProfesionalRestaurado(rs);
                case TrabajoFirmado:
                    return new bTrabajoFirmado(rs);
                case TrabajoRealizado:
                    return new bTrabajoRealizado(rs);
                case TurnoCancelado:
                    return new bTurnoCancelado(rs);
                case TurnoRegistrado:
                    return new bTurnoRegistrado(rs);
                case TurnoReprogramado:
                    return new bTurnoReprogramado(rs);
                case VigenciaRegistrada:
                    return new bVigenciaRegistrada(rs);
                case PagoColegio:
                    return new bPagoColegio(rs);
                default:
                    return null;
            }
        }
        
        public static List<String> ListarUsuarios() throws Exception
        {
            List<String> xUs = new ArrayList<>();
            xUs.add(clsBitacora.DEFAULTUSERNAME);
            
            clsGestorBases xGB = clsGestorBases.Instanciar();
            xGB.EstablecerBaseActiva((byte)1);

            xGB.CrearComando(CommandType.StoredProcedure, "{call getUsuarios()}");
            
            ResultSet xRS = xGB.EjecutarConsulta();
            
            while(xRS.next()){xUs.add(xRS.getString("usr_username"));}
            
            
            
            return xUs;
        }
}//end clsBitacora