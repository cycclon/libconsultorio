package com.PRS.Consultorio.Pacientes;

import com.PRS.Consultorio.ObrasSociales.clsOS;
import java.sql.ResultSet;

/**
 * @author ARSpidalieri
 * @version 1.0
 * @created 05-oct-2014 01:16:25 p.m.
 */
public class clsAfiliado extends clsPaciente {

	protected clsOS OS;

	public clsAfiliado(){
            super();
	}     
        
        clsAfiliado(clsParticular particular, clsOS os)
        {
            this.Apellido = particular.getApellido();
            this.Cuenta = particular.getCuenta();
            this.Documento = particular.getDocumento();
            this.ID = particular.getID();
            this.Nombre = particular.getNombre();
            this.OS = os;
        }
        
        public clsAfiliado(ResultSet prRS) throws Exception
        {
            super(prRS);
            this.OS = clsOS.ObtenerPorPaciente(this.ID);
        }
        
        @Override
	public clsOS getOS(){return OS;}
	public void setOS(clsOS newVal){OS = newVal;}

    @Override
    public String getAfiliacionStr() {return this.OS.getNombreConCodigo();}
}//end clsAfiliado