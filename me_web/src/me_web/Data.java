package me_web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.Book;

public class HelloWord implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println("STOP");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.err.println("START");
	}
}
