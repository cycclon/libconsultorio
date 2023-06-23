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
public class exFirmarSolicitado extends clsExcepcionControlada {
    exFirmarSolicitado()
    {super("No se puede firmar un trabajo que aún no fue realizado.");}
}
