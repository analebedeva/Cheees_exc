package me;

import java.math.BigDecimal;
import java.util.*;

public class Book {
	BuyComparator bcomp = new BuyComparator();
	SellComparator scomp = new SellComparator();
	private ArrayList<Order> bid = new ArrayList<>();
	private ArrayList<Order> ask = new ArrayList<>();
	Report rp;
	int remain;

	public void process(Order order) {
		if (order.getType() == TypeOrder.BUY) {
			int index = Collections.binarySearch(bid, order, bcomp);
			index = Math.abs(index) - 1;
			bid.add(index, order);
			if (ask.size() != 0) {
				for (int i = 0; i <= index; i++) {
					runAskList(bid.get(i), i);
				}
			}
		} else {
			int index = Collections.binarySearch(ask, order, scomp);
			ask.add(Math.abs(index) - 1, order);
		}
	}
//покупка
	void runAskList(Order order, int index) {
		for (int i = 0; i < ask.size(); i++) {
			if (order.getPrice().compareTo(ask.get(i).getPrice()) >= 0) {
				remain = order.getQty() - ask.get(i).getQty();
				if (remain < 0) {
					remain = Math.abs(remain);
					rp = new Report(order, ask.get(i).getPrice());
					rp = new Report(ask.get(i), ask.get(i).getPrice(), order.getQty());
					ask.get(i).setQty(remain);
					bid.remove(index);
					break;
				} else if (remain == 0) {
					rp = new Report(order, ask.get(i).getPrice());
					rp = new Report(ask.get(i), ask.get(i).getPrice());
					ask.remove(i);
					bid.remove(index);
					break;
				} else {
					rp = new Report(order, ask.get(i).getPrice(), ask.get(i).getQty());
					rp = new Report(ask.get(i), ask.get(i).getPrice());
					ask.remove(i);
					bid.get(index).setQty(remain);
					i--;
				}
			}
		}
	}
}

class BuyComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
		if (o1.getPrice() == o2.getPrice())
			if (o1.getTimestamp() == o2.getTimestamp())
				return o1.getSquenceNumber().compareTo(o2.getSquenceNumber());
			else
				return o1.getTimestamp().compareTo(o2.getTimestamp());
		else
			return o2.getPrice().compareTo(o1.getPrice());
	}
}

class SellComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
		if (o1.getPrice() == o2.getPrice())
			if (o1.getTimestamp() == o2.getTimestamp())
				return o1.getSquenceNumber().compareTo(o2.getSquenceNumber());
			else
				return o1.getTimestamp().compareTo(o2.getTimestamp());
		else
			return o1.getPrice().compareTo(o2.getPrice());
	}
}