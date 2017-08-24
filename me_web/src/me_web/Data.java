package me_web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.*;

public class Data implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println("STOP");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ME r= new ME();
		Thread myThread = new Thread(r);
		myThread.start();
	}
}
