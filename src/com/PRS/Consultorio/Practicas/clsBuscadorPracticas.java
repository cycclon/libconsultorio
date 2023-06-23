package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.AccesoADatos.CommandType;
import com.PRS.Framework.AccesoADatos.clsGestorBases;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 11-oct-2014 12:37:38 p.m.
 */
public class clsBuscadorPracticas {
    
    public enum enParametroBusquedaPractica
    {
        Codigo,
        Nombre
    }
    
    private abstract class clsParametro {

        public clsParametro(){

        }

        public abstract List<clsPracticaLazy> Filtrar(
                List<clsPracticaLazy> todos, String valor);

        public abstract String getNombrePropiedad();
    }//end clsParametro
    
    private class clsPCodigo extends clsParametro
    {

        @Override
        public List<clsPracticaLazy> Filtrar(List<clsPracticaLazy> todos, String valor) {
            List<clsPracticaLazy> xPs = new ArrayList<>();
            for(clsPracticaLazy xP : todos)
            {
                if(xP.getCodigo().contains(valor))
                {xPs.add(xP);}
            }
            return xPs;
        }

        @Override
        public String getNombrePropiedad() {return "Código";}
    }
    
    private class clsPNombre extends clsParametro
    {

        @Override
        public List<clsPracticaLazy> Filtrar(List<clsPracticaLazy> todos, String valor) {
        
            List<clsPracticaLazy> xPs = new ArrayList<>();
            for(clsPracticaLazy xP : todos)
            {
                if(Normalizer.normalize(xP.getNombre().toLowerCase(), 
                        Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").contains(Normalizer.normalize(
                                valor.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")))
                {xPs.add(xP);}
            }
            return xPs;
        }

        @Override
        public String getNombrePropiedad() {return "Nombre";}
    }

    private static clsBuscadorPracticas Instancia;
    private final List<clsPracticaLazy> Practicas;


    private clsBuscadorPracticas(){this.Practicas = new ArrayList<>();}

    private void ActualizarListado() throws Exception
    {
        Practicas.clear();
        clsGestorBases xGB = clsGestorBases.Instanciar();
        xGB.EstablecerBaseActiva((byte)1);

        xGB.CrearComando(CommandType.StoredProcedure, "{call getPracticas()}");

        ResultSet xRS = xGB.EjecutarConsulta();

        while(xRS.next())
        {Practicas.add(new clsPracticaLazy(xRS));}
    }

    public static clsBuscadorPracticas Instanciar() throws Exception{
        if(Instancia == null){Instancia = new clsBuscadorPracticas();
        Instancia.ActualizarListado();}        
        return Instancia;
    }
    
    private clsParametro FabricarParametro(enParametroBusquedaPractica parametro){
        switch(parametro)
        {
            case Codigo:
                return new clsPCodigo();
            case Nombre:
                return new clsPNombre();
            default:
                return new clsPNombre();
        }
    }
    
    public List<clsPracticaLazy> Buscar(enParametroBusquedaPractica parametro, String valor)
    {return FabricarParametro(parametro).Filtrar(Practicas, valor);}
}//end clsBuscadorPracticas