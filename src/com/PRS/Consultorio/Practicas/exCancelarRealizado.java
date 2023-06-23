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
public class exCancelarRealizado extends clsExcepcionControlada {
    exCancelarRealizado()
    {super("No se puede cancelar un trabajo que ya se llevó a cabo");}
}
