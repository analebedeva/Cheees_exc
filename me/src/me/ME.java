package me;

import java.util.ArrayList;

public class ME {
	private ArrayList<Book> bookList = new ArrayList<>();

	 ArrayList<ExecutionReport> addOrder(Order order, int instrumentId) {
		if (checkOrder(order)) {
			if (bookList.size() != 0) {
				for (int i = 0; i < bookList.size(); i++)
					if (instrumentId == bookList.get(i).getInstrumentId())
						return bookList.get(i).process(order);
			}
			Book book = new Book(instrumentId);
			bookList.add(book);
			return book.process(order);
		} else {
			ArrayList<ExecutionReport> rejectReport = new ArrayList<ExecutionReport>();
			rejectReport.add(new ExecutionReport(order, 2));
			return rejectReport;
		}
	}

	private boolean checkOrder(Order order) {
		if (order.getQty()>0 && order.getPrice().signum()==1) 
			return true;
		return false;
	}
}
