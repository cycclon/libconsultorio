package com.PRS.Consultorio.Pacientes;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 06-oct-2014 03:06:56 p.m.
 */
public class clsBuscadorPacientes {

    private class clsPDocumento extends clsParametro {

        public clsPDocumento(){

        }

        @Override
        public List<clsPacienteLazy> Filtrar(List<clsPacienteLazy> todos, 
                String valor){
            List<clsPacienteLazy> xPs = new ArrayList<>();
            for(clsPacienteLazy xP : todos)
            {
                if(xP.getNroDocumento().contains(valor))
                {xPs.add(xP);}
            }
            return xPs;
        }

        @Override
        public String getNombrePropiedad(){return "Número de documento";}
    }//end clsPDocumento


    public enum enParametroBusquedaPaciente {
            Apellido,
            Documento
    }

    private abstract class clsParametro {

        public clsParametro(){

        }

        public abstract List<clsPacienteLazy> Filtrar(
                List<clsPacienteLazy> todos, String valor);

        public abstract String getNombrePropiedad();
    }//end clsParametro

    public class clsPApellido extends clsParametro {

        public clsPApellido(){

        }

        @Override
        public List<clsPacienteLazy> Filtrar(List<clsPacienteLazy> todos, 
                String valor){
            List<clsPacienteLazy> xPs = new ArrayList<>();
            for(clsPacienteLazy xP : todos)
            {
                if(Normalizer.normalize(xP.getApellido().toLowerCase(), 
                        Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").contains(Normalizer.normalize(
                                valor.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")))
                {xPs.add(xP);}
            }
            return xPs;
        }

        @Override
        public String getNombrePropiedad(){
                return "Apellido";
        }
    }//end clsPApellido

    private static clsBuscadorPacientes Instancia;
    private final List<clsPacienteLazy> Listado;

    private clsBuscadorPacientes(){
        Listado = new ArrayList<>();
    }


    private void ActualizarListado() throws Exception{
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.CrearComando(CommandType.StoredProcedure, "{call getPacientesLazy()}");

        ResultSet xRS = xGB.EjecutarConsulta();

        this.Listado.clear();
        while(xRS.next())
        {this.Listado.add(new clsPacienteLazy(xRS));}
    }

    public List<clsPacienteLazy> Buscar(enParametroBusquedaPaciente parametro , String valor){
        return FabricarParametro(parametro).Filtrar(Listado, valor);
    }

    private clsParametro FabricarParametro(enParametroBusquedaPaciente parametro){
        switch(parametro)
        {
            case Apellido:
                return new clsPApellido();
            case Documento:
                return new clsPDocumento();
            default:
                return new clsPApellido();
        }
    }

    public static clsBuscadorPacientes Instanciar() throws Exception{
        if(Instancia == null){Instancia = new clsBuscadorPacientes();
        Instancia.ActualizarListado();}
        
        return Instancia;
    }
}//end clsBuscadorPacientes