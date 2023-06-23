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
public class exRealizarCancelado extends clsExcepcionControlada {
    exRealizarCancelado()
    {super("No se puede llevar a cabo un trabajo cancelado");}
}
