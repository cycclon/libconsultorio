/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Practicas;

import com.PRS.Framework.GestionExcepciones.clsExcepcionControlada;

/**
 *
 * @author ARSpidalieri
 */
public class exCancelarFirmado extends clsExcepcionControlada {
    exCancelarFirmado()
    {super("No se puede cancelar un trabajo que ya fue firmado.");}
}
