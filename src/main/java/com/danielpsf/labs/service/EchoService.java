package com.danielpsf.labs.service;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoService {

	private final Logger LOG = LoggerFactory.getLogger(EchoService.class);

	public EventOutput subscribe() {
		final EventOutput eventOutput = new EventOutput();

		new Runnable() {

			//FIX-ME: This is just a sample, so if you wanna do it right, remove this thread from here.
			@Override
			public void run() {
				try {

					int count = 0;
					while (count != 100) {
						count++;
						Thread.sleep(1000);
						dispatchEventToSubscriver(eventOutput, "Hello dude!!!");
					}

					eventOutput.close();

				} catch (InterruptedException e) {
					LOG.error("ERROR CLOSING EVENT");
				} catch (IOException e) {
					LOG.error("ERROR DEALING WITH EVENT");
				}
			}

		}.run();
		
		return eventOutput;
	}

	public void dispatchEventToSubscriver(EventOutput eventOutput, String message) throws IOException {

		LOG.debug("Sending message: " + message);
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();

		OutboundEvent event = eventBuilder
									.name("message")
									.mediaType(MediaType.APPLICATION_JSON_TYPE)
									.data(String.class, message)
									.build();

		eventOutput.write(event);
	}
}
