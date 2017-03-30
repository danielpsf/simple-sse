package com.danielpsf.labs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

import com.danielpsf.labs.service.EchoService;

@Path("/echo")
public class EchoController {

	private EchoService service = null;
	
	public EchoController() {
		service = new EchoService();
	}
	
	public EchoController(EchoService service) {
		this.service = service;
	}

	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {
		return service.subscribe();
	}
	
}
