package com.PRS.Consultorio.Cuentas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.Date;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 12:06:50 a.m.
 */
public class clsCobroCoseguro extends clsCobro {

    public static clsCobroCoseguro Obtener(int IDTrabajo) throws Exception {
        clsCobroCoseguro xCS = new clsCobroCoseguro(0, new Date());
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getCoseguroTrabajo(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, IDTrabajo);
        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xCS = new clsCobroCoseguro(crs);}
        
        return xCS;
    }
    
    public clsCobroCoseguro(ResultSet rs) throws Exception
    {
        super(rs);        
    }
    
    
    
    public clsCobroCoseguro(clsValor monto, Date fecha){
        super(monto, fecha);
    }

    public clsCobroCoseguro(float monto, Date fecha)
    {super(monto, fecha);}

    @Override
    public enConceptoCobro getConcepto() {return enConceptoCobro.Coseguro;}  

    @Override
    public String getConceptoStr() {return "Coseguro: " + this.Concepto;}

    public static void Eliminar(int idTrabajo) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        xGB.CrearComando(CommandType.StoredProcedure, "{call EliminarCobro(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, idTrabajo);        

        xGB.EjecutarComando();
    }
    
    public void Modificar(float nuevoValor) throws Exception
    {
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        this.Monto = new clsValor(nuevoValor);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call ModificarMontoCoseguro(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, this.ID);
        xGB.CrearParametro(2, enTipoParametro.Moneda, nuevoValor);
        
        xGB.EjecutarComando();
        
        this.DeterminarEstado();
    }
}//end clsCobroCoseguro