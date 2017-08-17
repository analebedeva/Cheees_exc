package me;

import java.util.ArrayList;

public class ME {
	private ArrayList<Book> bookList = new ArrayList<>();

	public ArrayList<ExecutionReport> addOrder(Order order, int instrumentId) {
//проверка заявки
		if (bookList.size() != 0) {
			for (int i = 0; i < bookList.size(); i++)
				if (instrumentId == bookList.get(i).getInstrumentId()) 
					return bookList.get(i).process(order);
		}
		Book book = new Book(instrumentId);
		bookList.add(book);
		return book.process(order);
	}
}
