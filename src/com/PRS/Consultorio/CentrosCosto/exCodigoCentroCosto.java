/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PRS.Consultorio.CentrosCosto;

import com.PRS.Framework.GestionExcepciones.clsExcepcionControlada;

/**
 *
 * @author ARSpidalieri
 */
public class exCodigoCentroCosto extends clsExcepcionControlada {
    public exCodigoCentroCosto()
    {
        super("El código seleccionado para este centro de costo ya está en uso");
    }
}
