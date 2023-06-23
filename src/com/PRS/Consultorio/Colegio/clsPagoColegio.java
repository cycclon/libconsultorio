/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Colegio;

import com.PRS.Consultorio.Cuentas.clsCobroOS;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import com.PRS.Framework.Monedas.clsValor;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cycclon
 */
public class clsPagoColegio {
    
    private short ID;
    private final Date Fecha;
    private clsValor Total;
   
    
    public clsPagoColegio(Date fecha) throws Exception
    {
        this.Fecha = fecha;
    }
    
    clsPagoColegio(ResultSet rs) throws Exception
    {
        this(rs.getTimestamp("paco_fecha"));
        this.ID = rs.getShort("paco_id");
    }
    
    void Registrar(List<clsCobroOS> cobrosPagados) throws Exception
    {     
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        ID = (short)xGB.ObtenerClave("pago_colegio");
        
        xGB.CrearComando(CommandType.StoredProcedure,
                "{call RegistrarPagoColegio(?, ?)}");
        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        xGB.CrearParametro(2, enTipoParametro.Fecha, Fecha);
        xGB.EjecutarComando();
        
        xGB.CrearTransaccion();
        for(clsCobroOS cos : cobrosPagados)
        {
            xGB.CrearComando(CommandType.StoredProcedure, 
                    "{call RegistrarItemPagoColegio(?, ?)}");
            xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
            xGB.CrearParametro(2, enTipoParametro.Entero, cos.getID());
            xGB.AgregarComando();
        }
        xGB.EjecutarTransaccion();
        
        float total = 0;
        for(clsCobroOS cos : cobrosPagados)
        {cos.Pagar(cos.getRestante().pdValor(), Fecha);
        total += cos.getMonto().pdValor();}
        
        this.Total = new clsValor(total);
    }    
    
    public List<clsCobroOS> getDetalleCobros() throws Exception
    {
        List<clsCobroOS> coss = new ArrayList<>();
        
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.StoredProcedure, "{call getCobrosOS(?)}");

        xGB.CrearParametro(1, enTipoParametro.EnteroChico, ID);
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(xRS);
        
        while(crs.next())coss.add(new clsCobroOS(crs));
        
        return coss;
    }
    
    public clsValor getTotal(){return this.Total;}
    
    public short getID(){return ID;}
    
    void setTotal(clsValor total){Total = total;}

    public Date getFecha(){return Fecha;}

    @Override
    public String toString() {
        return this.Total.pdValorString() + " el " 
                + new SimpleDateFormat("dd/MM/yyyy").format(this.Fecha);
    }   
    
}
