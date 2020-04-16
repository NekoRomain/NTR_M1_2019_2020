package org.nekoromain.ntr.test;

import static org.junit.Assert.assertEquals;


import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.nekoromain.ntr.services.ClientService;



public class ClientRessourceIntegrationTest extends JerseyTest{
	
	@Override
	protected Application configure() {
		return new ResourceConfig(ClientService.class);
	}
	
	
	@Test
	public void givenGetClient_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("clients/39/").request().get();
		assertEquals("Http response shoulde be 200", Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void givenGetClientOperation_whenCorrectRequest_thenResponseIsOk() {
		Response response = target("clients/39/operations").request().get();
		assertEquals("Http response shoulde be 200", Status.OK.getStatusCode(), response.getStatus());
	}
	

}
