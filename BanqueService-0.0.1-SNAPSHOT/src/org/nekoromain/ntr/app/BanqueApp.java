package org.nekoromain.ntr.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.nekoromain.ntr.services.ClientService;

public class BanqueApp extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(ClientService.class);
		return s;
	}
}
