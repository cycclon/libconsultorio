/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.Cuentas;

import com.PRS.Framework.GestionExcepciones.clsExcepcionControlada;

/**
 *
 * @author ARSpidalieri
 */
public class exPagarPagado extends clsExcepcionControlada {
    public exPagarPagado()
    {super("Este cobro ya se encuentra saldado");}
}
