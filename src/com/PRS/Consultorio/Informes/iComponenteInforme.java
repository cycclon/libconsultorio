package com.PRS.Consultorio.Informes;

import com.PRS.Consultorio.Practicas.clsTrabajo;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 26-oct-2014 08:11:20 p.m.
 */
public interface iComponenteInforme {

	public String getCodigo() throws Exception;

	public String getNombre();

	public String Procesar(String plantilla, clsTrabajo trabajo) throws Exception;

}