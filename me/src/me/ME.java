package me;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class ME implements Runnable {
	Map<Integer, Book> books = new HashMap<>();
	public static Queue<ExecutionReport> reports = new PriorityBlockingQueue<>();
	public static Queue<Request> requests = new PriorityBlockingQueue<>();
	boolean stop = false;

	public void process(Request request) {
		if (books.containsKey(request.getInstrumentId())) {
			reports.addAll(request.process(books.get(request.getInstrumentId())));

		} else
			reports.offer(new ExecutionReport(ExecutionReportType.REJECTED));
	}

	synchronized void read() throws InterruptedException {
		while (!stop) {
			while (requests.size() > 0) {
				process(requests.poll());
			}
			wait();
		}
	}

	synchronized public void write(Request request) {
		requests.offer(request);
		notify();
	}

	@Override
	public void run() {
		stop = false;
		try {
			read();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
