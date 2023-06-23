package com.PRS.Consultorio.Pacientes;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 31-oct-2014 03:11:50 p.m.
 */
public class clsTelefonoPacienteCelular extends clsTelefonoPaciente {

	public clsTelefonoPacienteCelular(){
            this("");
	}
        
        clsTelefonoPacienteCelular(ResultSet rs) throws SQLException
        {super(rs);}
        
        public clsTelefonoPacienteCelular(String telefono)
        {super(); this.Telefono = telefono;}

    @Override
    public enTipoTelefono getTipoTelefono() {return enTipoTelefono.Celular;}   

}//end clsTelefonoPacienteCelular