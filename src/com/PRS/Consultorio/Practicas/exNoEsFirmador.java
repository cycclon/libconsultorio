/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.GestionExcepciones.clsExcepcionControlada;

/**
 *
 * @author cycclon
 */
public class exNoEsFirmador  extends clsExcepcionControlada {

    public exNoEsFirmador() {
        super("El usuario con el que inició sesión no está habilitado para firmar trabajos");
    }
    
}
