package com.PRS.Consultorio.Bitacoras.CentrosCosto;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.CentrosCosto.clsCentroCosto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:29 p.m.
 */
public class bCentroDeCostoRegistrado extends clsBitacora {
    
    public bCentroDeCostoRegistrado() throws Exception
    {super();}
    
    public bCentroDeCostoRegistrado(ResultSet rs) throws SQLException
    {super(rs);}

    public bCentroDeCostoRegistrado(clsCentroCosto cc) throws Exception{
        super();
        
        this.Accion = "Centro de costos registrado: " + cc.getNombreConCodigo();
    }

    @Override
    public String getNombre(){return "Centro de Costos Registrado";}

    @Override
    public enTipoBitacora getTipo(){return enTipoBitacora.CentroDeCostoRegistrado;}
}//end bCentroDeCostoRegistrado