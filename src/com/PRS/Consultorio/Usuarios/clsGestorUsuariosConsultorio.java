/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Usuarios;

import com.PRS.Consultorio.Profesionales.clsProfesional;
import static com.PRS.Consultorio.Profesionales.clsProfesional.Fabricar;
import com.PRS.Consultorio.Profesionales.enTipoProfesional;
import com.PRS.Framework.Acceso.clsGestorAcceso;
import com.PRS.Framework.Acceso.clsUsuario;
import com.PRS.Framework.Acceso.iAccesor;
import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import com.PRS.Framework.AccesoADatos.enTipoParametro;
import java.sql.ResultSet;

/**
 *
 * @author ARSPIDALIERI
 */
public class clsGestorUsuariosConsultorio {
    
    private static clsGestorUsuariosConsultorio Instancia;
    
    private iAccesor xA;
    
    private clsProfesional xP;
    
    private clsGestorUsuariosConsultorio()
    {xA = null;}
    
    public static clsGestorUsuariosConsultorio Instanciar()
    {
        if(Instancia == null){Instancia = new clsGestorUsuariosConsultorio();}
        return Instancia;
    }
    
    public clsProfesional ObtenerProfesionalLogueado(clsUsuario usuario) throws Exception
    {
        if (xP == null)
        {
            clsGestorBases xGB = clsGestorBases.Instanciar();
        
            xGB.EstablecerBaseActiva((byte)1);

            xGB.CrearComando(CommandType.StoredProcedure, "{call getProfesionalUsuario(?)}");
                    xGB.CrearParametro(1, enTipoParametro.Entero, usuario.pdID());

            ResultSet xRS = xGB.EjecutarConsulta();

            while(xRS.next())
            {
                xP = Fabricar(enTipoProfesional.valueOf(xRS.getString("pro_tipo")), xRS);
            }
        }        
        
        return xP;
    }
    
    public iAccesor ObtenerAccesor() throws Exception
    {
        if(xA == null){
            clsGestorAcceso xGA = clsGestorAcceso.Instanciar();

            clsGestorBases xGB = clsGestorBases.Instanciar();
            xGB.EstablecerBaseActiva((byte)1);

            xGB.CrearComando(CommandType.Text, "{call getAccesor(?)}");
            xGB.CrearParametro(1, enTipoParametro.Entero, xGA.getSesion().pdUsuario().pdID());

            ResultSet xRS = xGB.EjecutarConsulta();

            while(xRS.next())
            {
                /*Profesional*/
                if(xRS.getInt("pro_id") > 0)
                {xA = this.ObtenerProfesionalLogueado(xGA.getSesion().pdUsuario());break;}
                /*Administrador*/
                if(xRS.getByte("adm_id")>0)
                {xA = clsAdministrador.Obtener(xGA.getSesion().pdUsuario());break;}
                /*Recepcionista*/
                if(xRS.getByte("rece_id")>0)
                {xA = clsRecepcionista.Obtener(xGA.getSesion().pdUsuario());break;}
            }
        }
        return xA;
    }
            
    
    public iUsuarioConsultorio ObtenerUsuarioConsultorio() throws Exception
    {
        clsGestorAcceso xGA = clsGestorAcceso.Instanciar();
        iUsuarioConsultorio xUC = null;
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);
        
        xGB.CrearComando(CommandType.Text, "{call getUsuarioConsultorio(?)}");
        xGB.CrearParametro(1, enTipoParametro.Entero, xGA.getSesion().pdUsuario().pdID());
        
        ResultSet xRS = xGB.EjecutarConsulta();
        
        while(xRS.next())
        {
            /*Profesional*/
            if(xRS.getInt("pro_id") > 0)
            {xUC = this.ObtenerProfesionalLogueado(xGA.getSesion().pdUsuario());break;}
            /*Administrador*/
            if(xRS.getByte("adm_id")>0)
            {xUC = clsAdministrador.Obtener(xGA.getSesion().pdUsuario());break;}
            /*Recepcionista*/
            if(xRS.getByte("rece_id")>0)
            {xUC = clsRecepcionista.Obtener(xGA.getSesion().pdUsuario());break;}
        }
        
        return xUC;
    }
}
