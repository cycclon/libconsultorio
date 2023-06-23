package com.PRS.Consultorio.Pacientes;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 31-oct-2014 03:11:49 p.m.
 */
public class clsTelefonoPacienteFijo extends clsTelefonoPaciente {

    public clsTelefonoPacienteFijo(){
        this("");
    }
    
    clsTelefonoPacienteFijo(ResultSet rs) throws SQLException
    {super(rs);}

    public clsTelefonoPacienteFijo(String telefono)
    {super(); this.Telefono = telefono;}
    
    @Override
    public enTipoTelefono getTipoTelefono() {return enTipoTelefono.Fijo;}

}//end clsTelefonoPacienteFijo