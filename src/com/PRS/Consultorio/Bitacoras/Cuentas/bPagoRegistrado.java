package com.PRS.Consultorio.Bitacoras.Cuentas;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Cuentas.clsPago;
import com.PRS.Consultorio.Practicas.clsTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author cycclon
 * @version 1.0
 * @created 22-nov-2014 05:02:19 p.m.
 */
public class bPagoRegistrado extends clsBitacora {
    
    public bPagoRegistrado() throws Exception
    {super();}
    
    public bPagoRegistrado(ResultSet rs) throws SQLException
    {super(rs);}
    
    public bPagoRegistrado(clsTrabajo trabajo, clsPago pago) throws Exception
    {super();this.Accion = "Pago de" + pago.getMonto().pdValorString() + " registrado para " 
            + trabajo.getPractica().getNombre() + " de " 
            + trabajo.getPaciente().getNombreCompleto() + " del día " 
            + new SimpleDateFormat("dd/MM/yyyy").format(trabajo.getProgramacion().getFecha()) 
            + " a las " + trabajo.getProgramacion().getHorario().getHoraStr();}

    @Override
    public String getNombre() {return "Pago registrado";}

    @Override
    public enTipoBitacora getTipo() {return enTipoBitacora.PagoRegistrado;}


}//end bPagoRegistrado