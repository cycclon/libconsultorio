package com.PRS.Consultorio.Bitacoras.Colegio;
import com.PRS.Consultorio.Bitacoras.enTipoBitacora;
import com.PRS.Consultorio.Bitacoras.clsBitacora;
import com.PRS.Consultorio.Colegio.clsPagoColegio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author cycclon
 * @version 1.0
 * @created 13-dic-2014 02:33:09 p.m.
 */
public class bPagoColegio extends clsBitacora {

    public bPagoColegio()throws Exception{super();}

    public bPagoColegio(ResultSet rs)throws SQLException{super(rs);}

    public bPagoColegio(clsPagoColegio pago) throws Exception
    {
        super();
        this.Accion = "Pago de colegio efectuado el día " + 
                new SimpleDateFormat("dd/MM/yyyy").format(pago.getFecha()) 
                + " por un total de " + pago.getTotal().pdValorString();
    }


    @Override
    public String getNombre(){return "Pago de colegio";}

    @Override
    public enTipoBitacora getTipo(){return enTipoBitacora.PagoColegio;}
}//end bPagoColegio