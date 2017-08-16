package me;

import java.math.BigDecimal;
import java.util.*;

public class Book {
	
	protected ArrayList<Order> bid = new ArrayList<>();
	private ArrayList<Order> ask = new ArrayList<>();
	private ArrayList <ExecutionReport> reportList;

public void amend (Order order, BigDecimal price) {
	order.setPrice(price);
	order.setTimestamp(new Date());
}
public void amend (Order order, int qty) {
	if (order.getQty()<qty) order.setTimestamp(new Date());
	order.setQty(qty);
}
public void cancel (Order order) {
	if (order.getType()==SideType.BUY) 	bid.remove(bid.indexOf(order));
	else ask.remove(ask.indexOf(order));
}
	public ArrayList<ExecutionReport> process(Order order) {
		reportList =new ArrayList<>();
		Comparator<Order> bcomp = new BuyPriceComparator().thenComparing(new SquenceNumberComparator());
		Comparator<Order> scomp = new SellPriceComparator().thenComparing(new SquenceNumberComparator());
		if (order.getType() == SideType.BUY) {
			int index = Collections.binarySearch(bid, order, bcomp);
			index = Math.abs(index) - 1;
			bid.add(index, order);
			reportList.add(new ExecutionReport (order, true));
			if (ask.size() != 0) {
				for (int i = 0; i <= index; i++) {
					runAskList(bid.get(i), i);
				}
			}
		} else {
			int index = Collections.binarySearch(ask, order, scomp);
			ask.add(Math.abs(index) - 1, order);
			reportList.add(new ExecutionReport (order, true));
		}
		return reportList;
		
	}
//покупка
	private void runAskList(Order order, int index) {
		int remain;
		for (int i = 0; i <= ask.size(); i++) {
			if (order.getPrice().compareTo(ask.get(i).getPrice()) >= 0) {
				remain = order.getQty() - ask.get(i).getQty();
				if (remain < 0) {
					remain = Math.abs(remain);
					reportList.add(new ExecutionReport(order, ask.get(i).getPrice(),ask.get(i).getUserId()));
					reportList.add(new ExecutionReport(ask.get(i), ask.get(i).getPrice(), order.getQty(),order.getUserId()));
					ask.get(i).setQty(remain);
					bid.remove(index);
					break;
				} else if (remain == 0) {
					reportList.add(new ExecutionReport(order, ask.get(i).getPrice(),ask.get(i).getUserId()));
					reportList.add(new ExecutionReport(ask.get(i), ask.get(i).getPrice(),order.getUserId()));
					ask.remove(i);
					bid.remove(index);
					break;
				} else {
					reportList.add(new ExecutionReport(order, ask.get(i).getPrice(), ask.get(i).getQty(),ask.get(i).getUserId()));
					reportList.add(new ExecutionReport(ask.get(i), ask.get(i).getPrice(),order.getUserId()));
					ask.remove(i);
					bid.get(index).setQty(remain);
					i--;
				}
			}
		}
	}
}

class SellPriceComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
			return o1.getPrice().compareTo(o2.getPrice());
	}
}

class BuyPriceComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {		
			return o2.getPrice().compareTo(o1.getPrice());
	}
}
class SquenceNumberComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
				return o1.getSquenceNumber().compareTo(o2.getSquenceNumber());
	}
}
