package com.PRS.Consultorio.Bitacoras.CentrosCosto;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.CentrosCosto.clsCentroCosto;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:00:30 p.m.
 */
public class bCentroDeCostoEliminado extends clsBitacora {
    
    public bCentroDeCostoEliminado() throws Exception
    {super();}
    
    public bCentroDeCostoEliminado(ResultSet rs) throws SQLException
    {super(rs);}

    public bCentroDeCostoEliminado(clsCentroCosto cc) throws Exception{
        super();
        
        this.Accion = "Centro de costos eliminado: " + cc.getNombreConCodigo();
    }

    @Override
    public String getNombre(){return "Centro de Costos Eliminado";}

    @Override
    public enTipoBitacora getTipo(){return enTipoBitacora.CentroDeCostoEliminado;}


}//end bCentroDeCostoEliminado