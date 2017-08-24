package me_web;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.*;

@ServerEndpoint(value = "/ws")
public class DataWS {
	@OnMessage
	public void EventMessage(String message, Session session) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request req = mapper.readValue(message, Request.class);
			ME.write(req);
		} catch (IOException e) {}
	}
}