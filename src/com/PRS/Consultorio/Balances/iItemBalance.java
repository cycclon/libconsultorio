package com.PRS.Consultorio.Balances;

import com.PRS.Framework.Monedas.clsValor;
import java.util.Date;

/**
 * @author cycclon
 * @version 1.0
 * @created 30-nov-2014 03:35:16 p.m.
 */
public interface iItemBalance {

	public String getConceptoBalance();
        
        public String getConceptoAbajo();

	public Date getFechaBalance();

	public clsValor getMontoBalance();

}