package me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class ME {
	Map<Integer, Book> books = new HashMap<>();
	Queue<ExecutionReport> reports = new PriorityBlockingQueue<>();

	public void process(Request request) {
		if (books.containsKey(request.getInstrumentId())) {
		reports.addAll(request.process(books.get(request.getInstrumentId())));
			
		} else
			reports.offer(new ExecutionReport(ExecutionReportType.REJECTED));
	}
}
