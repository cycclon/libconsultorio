package com.PRS.Consultorio.Pacientes;

/**
 * @author ARSPIDALIERI
 * @version 1.0
 * @created 31-oct-2014 03:11:49 p.m.
 */
public abstract class clsContactoPaciente {

    protected int ID;

    public clsContactoPaciente(){
        this.ID = 0;
    }

    public abstract String getContactoStr();

    public int getID(){return ID;}

    public abstract enTipoContacto getTipo();

    public void setID(int newVal){ID = newVal;}
    
    public abstract void Registrar(int idPaciente) throws Exception;
    
    public abstract void Eliminar() throws Exception;
            
    @Override
    public String toString()
    {return this.getContactoStr();}
}//end clsContactoPaciente