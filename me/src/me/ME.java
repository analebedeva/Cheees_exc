package me;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ME {
	Map<Integer, Book> hashmap = new HashMap<>();
	private int sequenceNumber = 0;

	public void createOrder(BigDecimal price, int qty, int userId, int orderId, SideType type, int instrumentId) {
		if (qty > 0 && price.signum() > 0) {
			addOrder(new Order(price, qty, userId, orderId, sequenceNumber, type), instrumentId);
		}
		sequenceNumber++;
	}

	private ArrayList<ExecutionReport> addOrder(Order order, int instrumentId) {
		if (!hashmap.containsKey(instrumentId))
			hashmap.put(instrumentId, new Book(instrumentId));
		return hashmap.get(instrumentId).process(order);
	}
}
