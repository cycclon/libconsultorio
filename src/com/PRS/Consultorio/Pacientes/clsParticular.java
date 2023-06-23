package com.PRS.Consultorio.Pacientes;

import com.PRS.Consultorio.Bitacoras.Pacientes.bAfiliacionModificada;
import com.PRS.Consultorio.Bitacoras.Pacientes.bPacienteRegistrado;
import com.PRS.Consultorio.ObrasSociales.clsOS;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 05-oct-2014 01:16:24 p.m.
 */
public class clsParticular extends clsPaciente {

    public clsParticular(){
        super();
    }
    
    public clsParticular(ResultSet prRS) throws Exception
    {super(prRS);}

    public clsAfiliado Afiliar(clsOS prOS) throws Exception
    {
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarAfiliacion(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.EnteroChico, prOS.getID());
        
        xGB.EjecutarComando();
        
        new bAfiliacionModificada(this, prOS).Registrar();
        
        return new clsAfiliado(this, prOS);
    }
    
    public void Registrar() throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        ID = (int) xGB.ObtenerClave("paciente");
        Cuenta.setID((int)xGB.ObtenerClave("cuenta"));
        
        xGB.CrearComando(CommandType.StoredProcedure, 
                "{call RegistrarParticular(?, ?, ?, ?, ?, ?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, ID);
        xGB.CrearParametro(2, enTipoParametro.Cadena, Nombre);
        xGB.CrearParametro(3, enTipoParametro.Cadena, Apellido);
        xGB.CrearParametro(4, enTipoParametro.Cadena, Documento.pdTipo().toString());
        xGB.CrearParametro(5, enTipoParametro.EnteroGrande, Documento.pdNumero());
        xGB.CrearParametro(6, enTipoParametro.Entero, Cuenta.getID());
        xGB.CrearParametro(7, enTipoParametro.Moneda, this.Cuenta.getSaldoInicial().pdValor());
        
        xGB.EjecutarComando();
        
        new bPacienteRegistrado(this).Registrar();
    }

    @Override
    public String getAfiliacionStr() {return "Particular";}

    @Override
    public clsOS getOS() {return new clsOS();}

    
}//end clsParticular