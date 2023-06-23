package com.PRS.Consultorio.Bitacoras.Practicas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Practicas.clsPractica;
import com.PRS.Consultorio.Practicas.clsValorPractica;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:01:04 p.m.
 */
public class bVigenciaRegistrada extends clsBitacora {
    
    public bVigenciaRegistrada() throws Exception
    {super();}
    
    public bVigenciaRegistrada(ResultSet rs) throws SQLException
    {super(rs);}

	public bVigenciaRegistrada(clsValorPractica valor, clsPractica practica) throws Exception{
            super();
            this.Accion = "Se registró un nuevo valor de práctica para " 
                    + practica.getNombreyCodigo() + ". Obra social: " 
                    + valor.getOS().getNombre() + " " + valor.getCostoOS()
                            .pdValorString() + " coseguro " + valor
                                    .getCoseguro().pdValorString();
	}

    @Override
    public String getNombre() {return "Vigencia de valores registrada";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.VigenciaRegistrada;}

        
}//end bVigenciaRegistrada