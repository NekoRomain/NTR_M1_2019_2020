package org.nekoromain.ntr.services;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.nekoromain.ntr.entities.Client;
import org.nekoromain.ntr.entities.Operation;


@Path("/clients")
public class ClientService {
	
	//formatteur pour la date
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
	/*
	//Obtenir les infos du client id
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Client getClient(@PathParam("id") int id) {
		Configuration config = new Configuration();
		config.configure();
	    config.addClass(Client.class);
	    SessionFactory sessionFactory = config.buildSessionFactory();
	    Session session = sessionFactory.openSession();
		Client c = (Client) session.load(Client.class, new Integer(id));
		Client client = new Client(c.getId(), c.getName(), c.getSolde());
		session.close();
		sessionFactory.close();
		return client;
	}*/
	
	
	//Obtenir les infos du client id
		@GET
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getClient(@PathParam("id") int id) {
			Configuration config = new Configuration();
			config.configure();
		    config.addClass(Client.class);
		    //TODO DEBUG
		    System.out.println("TEST TEST TEST");
		    Response reponse = null;
		    try {
		    	SessionFactory sessionFactory = config.buildSessionFactory();
			    Session session = sessionFactory.openSession();
				Client c = (Client) session.load(Client.class, new Integer(id));
				Client client = new Client(c.getId(), c.getName(), c.getSolde());
				session.close();
				sessionFactory.close();
				reponse = Response.status(Response.Status.OK).entity(client).build();
		    } catch (HibernateException e) {
		    	reponse = Response.status(Response.Status.NOT_FOUND).entity("{\"Erreur\": Client inconnu}").build();
		    }
		    
			return reponse;
		}
	
	
	// obtenir le solde pour le client id
	/*
	@GET
	@Path("/{id}/solde")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClientSolde(@PathParam("id") int id) {
		Configuration config = new Configuration();
		config.configure();
	    config.addClass(Client.class);
	    SessionFactory sessionFactory = config.buildSessionFactory();
	    Session session = sessionFactory.openSession();
		Client c = (Client) session.load(Client.class, new Integer(id));
		double solde = c.getSolde();
		session.close();
		sessionFactory.close();
		return "{"
				+ "	\"solde\": " + solde
				+ "}";
	}*/
	
	@GET
	@Path("/{id}/solde")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientSolde(@PathParam("id") int id) {
		Response reponse = null;
		Configuration config = new Configuration();
		config.configure();
	    config.addClass(Client.class);
	    try {
	    	SessionFactory sessionFactory = config.buildSessionFactory();
		    Session session = sessionFactory.openSession();
			Client c = (Client) session.load(Client.class, new Integer(id));
			double solde = c.getSolde();
			session.close();
			sessionFactory.close();
			String s =  "{"
					+ "	\"solde\": " + solde
					+ "}";
			reponse = Response.status(Response.Status.OK).entity(s).build();
	    } catch (HibernateException hE) {
	    	reponse = Response.status(Response.Status.NOT_FOUND).entity("{\"Erreur\": Client inconnu}").build();
		}
	    
		return reponse;
	}
	
	
	// obtenir les operation pour le client id
	/*
		@GET
		@Path("/{id}/operations")
		@Produces(MediaType.APPLICATION_JSON)
		public List<Operation> getClientOperation(@PathParam("id") int id) {
			Configuration config = new Configuration();
			config.configure();
		    config.addClass(Client.class);
		    config.addClass(Operation.class);
		    SessionFactory sessionFactory = config.buildSessionFactory();
		    Session session = sessionFactory.openSession();
			//On récupère les opération associé au client
		    TypedQuery<Operation> query = session.createQuery("Select o from Operation o, Client c " 
		    + "where c.id='"+ id +"' and c.id = o.idClient", Operation.class);
		    List<Operation> result = query.getResultList();
		    session.close();
			sessionFactory.close();
			return result;
		} */
	
	@GET
	@Path("/{id}/operations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientOperation(@PathParam("id") int id) {
		Configuration config = new Configuration();
		config.configure();
	    config.addClass(Client.class);
	    config.addClass(Operation.class);
	    Response reponse = null;
	    try {
	    	SessionFactory sessionFactory = config.buildSessionFactory();
		    Session session = sessionFactory.openSession();
			//On récupère les opération associé au client
		    TypedQuery<Operation> query = session.createQuery("Select o from Operation o, Client c " 
		    + "where c.id='"+ id +"' and c.id = o.idClient", Operation.class);
		    List<Operation> result = query.getResultList();
		    session.close();
			sessionFactory.close();
			if(result.isEmpty()) {
				reponse = Response.status(Response.Status.NO_CONTENT).build();
			} else {
				reponse = Response.status(Response.Status.OK).entity(result).build();
			}
	    } catch (HibernateException e) {
	    	reponse = Response.status(Response.Status.NOT_FOUND).entity("{\"Erreur\": Client inconnu}").build();
		}
	    
		return reponse;
	}
	
	

	/*
	//Faire une opération credit sur le client id d'un montant amount
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{id}/credit/{amount}")
	public Client credit(@PathParam("id") int id, @PathParam("amount") double amount, Operation op) {
		Configuration config = new Configuration();
		config.configure();
	    config.addClass(Client.class);
	    config.addClass(Operation.class);
	    SessionFactory sessionFactory = config.buildSessionFactory();
	    Session session = sessionFactory.openSession();
	    Client client = null;
	    Transaction tx = null;
	 	try {
	 		tx = session.beginTransaction();
	 		//On récupère le client
		    Client c = (Client) session.load(Client.class, new Integer(id));
		    client = new Client(c.getId(), c.getName(), c.getSolde());
		    client.setSolde(client.getSolde() + amount);
	 		session.save(op);
	 		session.merge(client);
	 		session.flush();
	 		tx.commit();
	 	} catch (Exception e) { 
	 		 if(tx != null) {
	 			tx.rollback(); 
	 		}
	 		throw e;
	 	} finally {
	 		session.close();
	 	}
	 	sessionFactory.close();
	    return client;
	}*/
	

	//Faire une opération credit sur le client id d'un montant amount
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{id}/credit/{amount}")
	public Response credit(@PathParam("id") int id, @PathParam("amount") double amount, Operation op) {
		Configuration config = new Configuration();
		Response reponse = null;
		config.configure();
	    config.addClass(Client.class);
	    config.addClass(Operation.class);
	    SessionFactory sessionFactory = config.buildSessionFactory();
	    Session session = sessionFactory.openSession();
	    Client client = null;
	    Transaction tx = null;
	 	try {
	 		if(amount > 0) {
	 			tx = session.beginTransaction();
		 		//On récupère le client
			    Client c = (Client) session.load(Client.class, new Integer(id));
			    client = new Client(c.getId(), c.getName(), c.getSolde());
			    client.setSolde(client.getSolde() + amount);
			    if(op == null) {
			    	Operation opr = new Operation(id, 1, amount);
			    	session.save(opr);
			    } else {
			    	session.save(op);
			    }
		 		
		 		session.merge(client);
		 		session.flush();
		 		tx.commit();
		 		reponse = Response.status(Response.Status.OK).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
		 				+"<message>Montant crédité sur le compte</message>").build();
	 		} else {
	 			reponse = Response.status(Response.Status.NOT_ACCEPTABLE).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
		 				+"<erreur>La somme doit être positif</erreur>").build();
	 		}
	 		
	 	} catch(HibernateException e) {
	 		reponse = Response.status(Response.Status.NOT_FOUND).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
	 				+"<erreur>Client inconnu</erreur>").build();
	 	} catch (Exception e) { 
	 		 if(tx != null) {
	 			tx.rollback(); 
	 			reponse = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
		 				+"<erreur>Erreur transaction</erreur>").build();
	 		}
	 		throw e;
	 	} finally {
	 		session.close();
	 	}
	 	sessionFactory.close();
	    return reponse;
	}
	
	//Faire une opération debit sur le client id d'un montant amount
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{id}/debit/{amount}")
	public Response debit(@PathParam("id") int id, @PathParam("amount") double amount, Operation op) {
		Configuration config = new Configuration();
		Response response = null;
		config.configure();
	    config.addClass(Client.class);
	    config.addClass(Operation.class);
	    SessionFactory sessionFactory = config.buildSessionFactory();
	    Session session = sessionFactory.openSession();
	    Client client = null;
	    Transaction tx = null;
	 	try {	
	 		tx = session.beginTransaction();
	 		//On récupère le client
		    Client c = (Client) session.load(Client.class, new Integer(id));
		    client = new Client(c.getId(), c.getName(), c.getSolde());
		    if(client.getSolde() >= amount) {
				client.setSolde(c.getSolde()-amount);
				if(op == null) {
			    	Operation opr = new Operation(id, 0, amount);
			    	session.save(opr);
			    } else {
			    	session.save(op);
			    }
		 		session.merge(client);
		 		session.flush();
		 		tx.commit();
				response = Response.status(Response.Status.OK).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
		 				+"<message>Montant débité du compte</message>").build();
			} else {
				response = Response.status(Response.Status.FORBIDDEN).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
			+"<erreur>Solde insuffisant</erreur>").build();
			}
	 	} catch (HibernateException e) {
	 		response = Response.status(Response.Status.FORBIDDEN).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
	 				+"<erreur>Client inconnu</erreur>").build(); 
	 	}catch (Exception e) { 
	 		 if(tx != null) {
	 			tx.rollback(); 
	 			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
		 				+"<erreur>Erreur transaction</erreur>").build();
	 		}
	 		throw e;
	 	} finally {
	 		session.close();
	 	}
	 	sessionFactory.close();
	 	return response;
	}
	
	
}
