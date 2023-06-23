/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Acceso;

import com.PRS.Framework.Acceso.clsPermiso;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ARSPIDALIERI
 */
public class clsGestorPermisos {
    
    List<clsPermiso> Permisos;
    
    private static clsGestorPermisos Instancia;
    
    public static clsGestorPermisos Instanciar()
    {
        if(Instancia == null){Instancia = new clsGestorPermisos();}
        return Instancia;
    }
    
    private clsGestorPermisos()
    {
        Permisos = new ArrayList<>();
        Permisos.add(new clsPermiso((short)1, "Administrar Obras Sociales"));
        Permisos.add(new clsPermiso((short)2, "Administrar centros de costos"));
        Permisos.add(new clsPermiso((short)3, "Ver Balance Colegio"));
        Permisos.add(new clsPermiso((short)4, "Administrar Gastos"));
        Permisos.add(new clsPermiso((short)5, "Ver Reportes"));
        Permisos.add(new clsPermiso((short)6, "Administrar Prácticas"));
        Permisos.add(new clsPermiso((short)7, "Registrar Práctica"));
        Permisos.add(new clsPermiso((short)8, "Administrar Profesionales"));
        Permisos.add(new clsPermiso((short)9, "Registrar Solicitante"));
        Permisos.add(new clsPermiso((short)10, "Administrar turnos"));
        Permisos.add(new clsPermiso((short)11, "Ver Balances"));
        Permisos.add(new clsPermiso((short)12, "Administrar Pacientes"));
        Permisos.add(new clsPermiso((short)13, "Registrar paciente"));
        Permisos.add(new clsPermiso((short)14, "Administrar plantilla de informes"));
        Permisos.add(new clsPermiso((short)15, "Administrar campos de turnos"));
        Permisos.add(new clsPermiso((short)16, "Configurar comprobante de turno"));
        Permisos.add(new clsPermiso((short)17, "Imprimir Informe"));
        Permisos.add(new clsPermiso((short)18, "Cancelar turno"));
        Permisos.add(new clsPermiso((short)19, "Reprogramar turno"));
        Permisos.add(new clsPermiso((short)20, "Realizar trabajo"));
        Permisos.add(new clsPermiso((short)21, "Firmar trabajo"));
        Permisos.add(new clsPermiso((short)22, "Ver trabajos pasados"));
        Permisos.add(new clsPermiso((short)23, "Ver detalle de trabajo"));
        Permisos.add(new clsPermiso((short)24, "Eliminar gastos"));
        Permisos.add(new clsPermiso((short)25, "Registrar gasto"));
        Permisos.add(new clsPermiso((short)26, "Crear turno"));
        Permisos.add(new clsPermiso((short)27, "Registrar pago"));
        Permisos.add(new clsPermiso((short)28, "Imprimir comprobante de turno"));
        Permisos.add(new clsPermiso((short)29, "Ver Registro de Actividad"));
    }
    
    public clsPermiso getPermiso(int ID)
    {
        clsPermiso Permiso = new clsPermiso();
        for(clsPermiso xP : Permisos)
        {
            if(xP.pdID() == ID)
            {Permiso = xP;}
        }
        
        return Permiso;
    }
}
