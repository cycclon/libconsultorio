package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;
import com.PRS.Framework.Archivos.clsGestorArchivos;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:19 p.m.
 */
public class clsInforme {

    private String NombreArchivo;
    private String TextoHTML;
    private clsTrabajo Trabajo;
    private File file;
    private clsPlantilla Plantilla;
   
    clsInforme(clsTrabajo trabajo, String path, clsPlantilla plantilla) throws Exception
    {        
        this.Plantilla = plantilla;
        this.Trabajo = trabajo;
        this.NombreArchivo = this.generarNombreArchivo();
        file = new File(path + this.NombreArchivo);
        if(file.exists()){this.CargarHTML();}
        else{GenerarHTML(path);}
    }
    
    public clsInforme(String textoHTML, clsTrabajo trabajo) throws Exception
    {this.TextoHTML = textoHTML; this.Trabajo =trabajo;}
    
    private void CargarHTML() throws Exception
    {
        this.TextoHTML = clsGestorArchivos.Instanciar().readFile(file);
    }
    
    private void GenerarHTML(String path) throws Exception
    {
        /*CARGAR ENCABEZADO*/
        File fileEnc = new File(path + "encabezado.html");
        if(fileEnc.exists())
        {this.TextoHTML = clsGestorArchivos.Instanciar().readFile(fileEnc);}
        
        /*CARGAR CUERPO*/
        File fileCuerpo = new File(path + this.Plantilla.getArchivo());
        if(fileCuerpo.exists())
        {this.TextoHTML += clsGestorArchivos.Instanciar().readFile(fileCuerpo);}
        
        /*CARGAR PIE*/
        File filePie = new File(path + "pie.html");
        this.TextoHTML += clsGestorArchivos.Instanciar().readFile(filePie);

    }
    
    private void ReemplazarCampos() throws Exception
    {
        List<iComponenteInforme> xCs = clsGestorInformes.Instanciar().getComponentesDisponibles();
        for(iComponenteInforme xC : xCs)
        {this.TextoHTML = xC.Procesar(TextoHTML, this.Trabajo);}
    }
    
    private String generarNombreArchivo()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Trabajo.getProgramacion().getFecha());
        return String.valueOf(Trabajo.getPractica().getCodigo()) + 
               String.valueOf(Plantilla.getID()) +
               String.valueOf(Trabajo.getPaciente().getDocumento().pdNumero()) + 
                String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) +  
                String.valueOf(cal.get(Calendar.MONTH)) +  
                String.valueOf(cal.get(Calendar.YEAR)) + 
                String.valueOf(Trabajo.getProgramacion().getHorario().getMinutesFromStartOfDay()) + 
                ".html";
    }

    public clsInforme() throws Exception{this(new clsTrabajo(), "", new clsPlantilla());}

    public String getNombreArchivo(){return NombreArchivo;}

    public String getTextoHTML()throws Exception {this.ReemplazarCampos(); return TextoHTML;}

    public void Guardar() throws Exception{
        
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(this.TextoHTML);
            fileWriter.close();
        }
        
    }

    public void setTextoHTML(String newVal){TextoHTML = newVal;}
}//end clsInforme