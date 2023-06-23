package com.PRS.Consultorio.Personas;
import com.PRS.Framework.Identificacion.clsIdentificacion;
import com.PRS.Framework.Identificacion.enTipoIdentificacion;
import com.PRS.Framework.Personas.clsPersonaFisica;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 04-sep-2014 09:10:29 p.m.
 */
public abstract class clsPersonaConsultorio extends clsPersonaFisica {

    protected clsIdentificacion Documento;

    public clsPersonaConsultorio(){
        super();
        this.Documento = clsIdentificacion.Instanciar(enTipoIdentificacion.DNI);
    }    
    
    public clsIdentificacion getDocumento(){return Documento;}

    public void setDocumento(clsIdentificacion newVal){Documento = newVal;}
		
}//end clsPersonaConsultorio