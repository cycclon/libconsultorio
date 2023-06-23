/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Bitacoras;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cycclon
 */
public class clsGestorBitacoras {
    
    
    public static List<clsBitacora> Listar(Date fechaInicio, Date fechaFinal,  
            String usuario) throws Exception
    {
        List<clsBitacora> Bitacoras = new ArrayList<>();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        
        String sql = "SELECT * FROM bitacora ";
        if(!usuario.equals("Todos"))sql += "WHERE bit_usuario = ? ";
        sql += "ORDER BY bit_fecha DESC";
        
        xGB.CrearComando(CommandType.Text, sql);

        if(!usuario.equals("Todos"))xGB.CrearParametro(1, enTipoParametro.Cadena, usuario);
        
        ResultSet rs = xGB.EjecutarConsulta();
        
        LocalDate xI = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate xF = fechaFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        while(rs.next())
        {
            clsBitacora b = clsBitacora.Fabricar(rs);
            LocalDate xBF = b.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(xBF.compareTo(xI) >= 0 && xBF.compareTo(xF) <= 0)
            {Bitacoras.add(b);}
        }        
        
        return Bitacoras;
    }
    
    public static List<clsBitacora> Listar(Date fechaInicio, Date fechaFinal, 
            String usuario, enTipoBitacora tipo) throws Exception
    {
        List<clsBitacora> Bitacoras = Listar(fechaInicio, fechaFinal, usuario);
        List<clsBitacora> bs = new ArrayList<>();
        for(clsBitacora b : Bitacoras)
        {if(b.getTipo() == tipo){bs.add(b);}}
        
        return bs;
    }
}
