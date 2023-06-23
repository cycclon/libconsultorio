package com.PRS.Consultorio.Pacientes;
import com.PRS.Consultorio.Bitacoras.Pacientes.bAfiliacionModificada;
import com.PRS.Consultorio.Bitacoras.Pacientes.bContactoPacienteAgregado;
import com.PRS.Consultorio.Bitacoras.Pacientes.bPacienteModificado;
import com.PRS.Consultorio.Cuentas.clsCobro;
import com.PRS.Consultorio.Cuentas.clsCobroCoseguro;
import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import com.PRS.Consultorio.Personas.clsPersonaConsultorio;
import com.PRS.Consultorio.Practicas.clsProgramador;
import com.PRS.Consultorio.Practicas.clsTrabajoLazy;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Cuentas.clsCuenta;
import com.PRS.Framework.Cuentas.clsMovimiento;
import com.PRS.Framework.DynamicComparer.clsDynamicComparer;
import com.PRS.Framework.Identificacion.clsIdentificacion;
import com.PRS.Framework.Identificacion.enTipoIdentificacion;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 05-oct-2014 01:16:26 p.m.
 */
public abstract class clsPaciente extends clsPersonaConsultorio {

    public static clsPaciente Fabricar(ResultSet prRS) throws Exception {
        if(prRS.getShort("afi_os_id") == 0)
        {return new clsParticular(prRS);}
        else{return new clsAfiliado(prRS);}
    }

    protected clsCuenta Cuenta;
    private List<clsContactoPaciente> Contacto;
    

    public clsPaciente(){
        super();
        this.Cuenta = new clsCuenta();
        this.Contacto = new ArrayList<>();
        
    }

    protected clsPaciente(ResultSet prRS) throws Exception
    {
        this();
        this.Apellido = prRS.getString("pac_apellido");
        this.Documento = clsIdentificacion.Instanciar(enTipoIdentificacion
                .valueOf(prRS.getString("pac_tipo_documento")), 
                prRS.getLong("pac_numero_documento"));
        this.ID = prRS.getInt("pac_id");
        this.Nombre = prRS.getString("pac_nombre");
        this.ObtenerCuenta();
        this.ObtenerDirecciones();
        this.ObtenerEmails();
        this.ObtenerTelefonos();
    }

    private void ObtenerCuenta() throws Exception
    {
        this.Cuenta =  new clsCuenta();

        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call getCuentaPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        

        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);

        List<clsMovimiento> xMs = new ArrayList<>();

        while(crs.next())
        {
            this.Cuenta.setID(crs.getInt("cta_id"));
            this.Cuenta.setSaldoInicial(new clsValor(crs.getFloat("cta_saldo_inicial")));
            clsCobroCoseguro xCob = new clsCobroCoseguro(crs);
            xMs.add(xCob);

            for(clsPago xP : xCob.getPagos())
            {xMs.add(xP);}
        }

        this.Cuenta.setMovimientos(xMs);

        Collections.sort(Cuenta.getMovimientos(), new clsDynamicComparer<>(clsMovimiento.class, 
                        "getFechaMovimiento", clsDynamicComparer.DESCENDING));

        if(xMs.isEmpty())
        {
            xGB.CrearComando(CommandType.StoredProcedure, "{call getIDCuentaPaciente(?)}");
            xGB.CrearParametro(1, enTipoParametro.Entero, ID);

            xRS = xGB.EjecutarConsulta();
            while(xRS.next())
            {this.Cuenta.setID(xRS.getInt("cta_id"));
            this.Cuenta.setSaldoInicial(new clsValor(xRS.getFloat("cta_saldo_inicial")));}
        }
    }

    public clsCuenta getCuenta(){return Cuenta;}

    public abstract String getAfiliacionStr();

    public abstract clsOS getOS();
    
    public List<clsTrabajoLazy> getHistoriaClinica() throws Exception
    {return clsProgramador.Instanciar().getHistoriaClinica(ID);}

    public void AgregarDireccion(clsDireccionPaciente direccion) throws Exception{
        direccion.Registrar(this.ID);
        this.Contacto.add(direccion);
        new bContactoPacienteAgregado(this, direccion).Registrar();
    }

    public void AgregarEmail(clsEmailPaciente email) throws Exception{
        email.Registrar(this.ID);
        this.Contacto.add(email);
        new bContactoPacienteAgregado(this, email).Registrar();
    }

    public void AgregarTelefono(clsTelefonoPaciente telefono) throws Exception{
        telefono.Registrar(this.ID);
        this.Contacto.add(telefono);
        new bContactoPacienteAgregado(this, telefono).Registrar();
    }
    
    private void ObtenerDirecciones() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getDireccionesPacientes(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        while(xRS.next())
        {this.Contacto.add(new clsDireccionPaciente(xRS));}
        
    }
    
    private void ObtenerTelefonos() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getTelefonosPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        while(xRS.next())
        {this.Contacto.add(clsTelefonoPaciente.Fabricar(xRS, enTipoTelefono
                .valueOf(xRS.getString("tepa_tipo"))));}
    }
    
    private void ObtenerEmails() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getEmailsPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        while(xRS.next())
        {this.Contacto.add(new clsEmailPaciente(xRS));}
    }
    
    public List<clsContactoPaciente> getContacto(){return this.Contacto;}

    public void ActualizarContacto() throws Exception{
        this.Contacto.clear();
        this.ObtenerDirecciones();
        this.ObtenerEmails();
        this.ObtenerTelefonos();
    }
    
    public void SaldarCuenta() throws Exception
    {
        /*PONER EN CERO EL SALDO INCIAL*/
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call RestablecerSaldoInicial(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, Cuenta.getID());
        
        xGB.EjecutarComando();
        
        /*PONER EN CERO LOS COBROS DE MAS*/
        xGB.CrearComando(CommandType.StoredProcedure, 
               "{call getCuentaPaciente(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        


        ResultSet xRS = xGB.EjecutarConsulta();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);

        List<clsMovimiento> xMs = new ArrayList<>();

        while(crs.next())
        {
            clsCobro xC = new clsCobroCoseguro(crs);
            if(xC.getSaldo().pdValor() > 0)
            {xC.Balancear();}
        }
        
    }
    
    public void GuardarCambios() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        xGB.CrearComando(CommandType.StoredProcedure, "{call ModificarPaciente(?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Nombre);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Apellido);
        xGB.CrearParametro(4, enTipoParametro.EnteroGrande, Documento.pdNumero());
        
        xGB.EjecutarComando();
        
        new bPacienteModificado(this).Registrar();
    }
    
    public void CambiarAfiliacion(clsOS nuevaAfiliacion) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        
        xGB.EstablecerBaseActiva((byte)1);
        xGB.CrearComando(CommandType.StoredProcedure, "{call CambiarAfiliacion(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, nuevaAfiliacion.getID());
        
        xGB.EjecutarComando();
        new bAfiliacionModificada(this, nuevaAfiliacion).Registrar();
    }
}//end clsPaciente