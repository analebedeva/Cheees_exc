package me;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ME implements Runnable {
	Map<Integer, Book> books = new HashMap<>();
	public static BlockingQueue<ExecutionReport> reports = new PriorityBlockingQueue<>();
	public static BlockingQueue<Request> requests = new PriorityBlockingQueue<>();
	boolean stop = false;

	public void process(Request request) {
		if (books.containsKey(request.getInstrumentId())) {
			reports.addAll(request.process(books.get(request.getInstrumentId())));

		} else
			reports.offer(new ExecutionReport(ExecutionReportType.REJECTED));
	}

	void read() throws InterruptedException  {
		while (!stop) {
			Request req = requests.poll(10,TimeUnit.MILLISECONDS);
			if(req!=null)
			{
				process(req);
			}
		}
	}

	public static void write(Request request) {
		requests.offer(request);
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
