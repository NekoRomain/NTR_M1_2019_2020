package org.nekoromain.ntr.services;

import java.io.StringReader;

import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.nekoromain.ntr.entities.Operation;
import org.xml.sax.InputSource;

@WebService(endpointInterface = "org.nekoromain.ntr.services.RemboursementAchatOpeImp")
public class RemboursementAchatOpWS implements RemboursementAchatOpeImp {

	
	//test service hello world
	@Override
	public String testHello(String nom) {
		
		return "Hello " + nom;
	}
	
	//Service remboursement : prendre en paramettre l'id du client et le montant du remboursement
	// Va créer une opération crédit puis va l'envoyer sur le service "credit" par REST au serveur Wildfly ServiceBanque
	//Le serveur ServiceBanque va retourner un message (erreur ou non) et un code de statut du résultat de la requête
	@Override
	public String remboursement(int id_client, double amount) {
		String retour = "";
		String uri = "http://localhost:8080/BanqueService-0.0.1-SNAPSHOT/clients/" + id_client +"/credit/" + amount; 
		try {
			Client clientWeb = ClientBuilder.newBuilder().register(Operation.class).build();
			WebTarget webTarget = clientWeb.target(uri);
			Response reponse = webTarget.request().accept("application/xml").post(Entity.xml(new Operation(id_client, 1,amount)));
			if(reponse.getStatusInfo().equals(Response.Status.OK)) {
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				InputSource source = new InputSource(new StringReader(reponse.readEntity(String.class)));
				String status = "Remboursement effectué\n(" + xpath.evaluate("/message", source) + ")";
				retour = status;
			}else {
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				InputSource source = new InputSource(new StringReader(reponse.readEntity(String.class)));
				String status = xpath.evaluate("/erreur", source);
				retour = "Erreur: " + status;
			}
			reponse.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retour;
	}
	
	
	//Service achat : prendre en paramettre l'id du client et le montant de l'achat
	// Va créer une opération debit puis va l'envoyer sur le service "debit" par REST au serveur Wildfly ServiceBanque
	//Le serveur ServiceBanque va retourner un message (erreur ou non) et un code de statut du résultat de la requête si l'achat est possible ou non
	@Override
	public String achat(int id_client, double amount) {
		String retour = "";
    	String uri = "http://localhost:8080/BanqueService-0.0.1-SNAPSHOT/clients/" + id_client +"/debit/" + amount; 
		try {
			javax.ws.rs.client.Client clientWeb = ClientBuilder.newBuilder().register(Operation.class).build();
			WebTarget webTarget = clientWeb.target(uri);
			Response reponse = webTarget.request().accept("application/xml").post(Entity.xml(new Operation(id_client, 0,amount)));
			if(reponse.getStatusInfo().equals(Response.Status.OK)) {
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				InputSource source = new InputSource(new StringReader(reponse.readEntity(String.class)));
				String status = "Achat effectué\n(" + xpath.evaluate("/message", source) + ")";
				retour = status;
			} else  {
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				InputSource source = new InputSource(new StringReader(reponse.readEntity(String.class)));
				String status = xpath.evaluate("/erreur", source);
				retour = "Erreur: " + status;
			}
			reponse.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retour;
	}
}
