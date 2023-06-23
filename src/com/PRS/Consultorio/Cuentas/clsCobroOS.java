package com.PRS.Consultorio.Cuentas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 07-oct-2014 12:07:02 a.m.
 */
public class clsCobroOS extends clsCobro {

    public static clsCobroOS Obtener(int IDTrabajo) throws Exception {
    
        clsCobroOS xCS = new clsCobroOS(0, new Date());
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
       xGB.CrearComando(CommandType.StoredProcedure, "{call getCobroOSTrabajo(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, IDTrabajo);
        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())
        {xCS = new clsCobroOS(crs);}
        
        return xCS;
    }

    public clsCobroOS(clsValor monto, Date fecha){
        super(monto, fecha);
    }
    
    public clsCobroOS(ResultSet rs) throws Exception
    {super(rs);}
    
    public clsCobroOS(float monto, Date fecha)
    {super(monto, fecha);}

    @Override
    public enConceptoCobro getConcepto() {return enConceptoCobro.OS;}

   @Override
    public String getConceptoStr() {return "Cobertura de obra social: " + this.Concepto;}
    
    public static List<clsCobroOS> Listar() throws Exception
    {
        List<clsCobroOS> cos = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getCobrosOSTodos()}");

        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())cos.add(new clsCobroOS(crs));
        
        return cos;
    }

}//end clsCobroOS