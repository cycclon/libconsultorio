package com.PRS.Consultorio.ObrasSociales;

import com.PRS.Framework.GestionExcepciones.clsExcepcionControlada;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 02-sep-2014 08:37:56 p.m.
 */
public class exCodigoEnUso extends clsExcepcionControlada {

	public exCodigoEnUso(){
            super("El código seleccionado para la nueva obra social ya está en uso");
	}

	
}//end exCodigoEnUso