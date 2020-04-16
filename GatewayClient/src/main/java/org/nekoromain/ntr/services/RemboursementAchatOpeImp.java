package org.nekoromain.ntr.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RemboursementAchatOpeImp {
	@WebMethod
	public String testHello(String nom);
	
	@WebMethod
	public String remboursement(int id_client, double amount);
	
	@WebMethod
	public String achat(int id_client, double amount);
}
