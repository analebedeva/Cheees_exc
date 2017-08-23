package me_web;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class HelloWorldWS {

	@OnMessage
	public String onMessage(String message, Session session) {
		return message;
	}

}
