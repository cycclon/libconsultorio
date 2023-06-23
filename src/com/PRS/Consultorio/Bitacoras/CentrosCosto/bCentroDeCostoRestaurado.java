package com.PRS.Consultorio.Bitacoras.CentrosCosto;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.CentrosCosto.clsCentroCosto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:31 p.m.
 */
public class bCentroDeCostoRestaurado extends clsBitacora {
    
    public bCentroDeCostoRestaurado() throws Exception
    {super();}
    
    public bCentroDeCostoRestaurado(ResultSet rs) throws SQLException
    {super(rs);}

    public bCentroDeCostoRestaurado(clsCentroCosto cc) throws Exception{
        super();
        
        this.Accion = "Centro de costos restaurado: " + cc.getNombreConCodigo();
    }

    @Override
    public String getNombre(){return "Centro de Costos Restaurado";}

    @Override
    public enTipoBitacora getTipo(){return enTipoBitacora.CentroDeCostoRestaurado;}
}//end bCentroDeCostoRestaurado