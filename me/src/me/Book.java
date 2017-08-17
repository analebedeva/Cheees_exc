package me;

import java.math.BigDecimal;
import java.util.*;

public class Book {

	private ArrayList<Order> bid = new ArrayList<>();
	private ArrayList<Order> ask = new ArrayList<>();
	private ArrayList<ExecutionReport> reportList;
	private int instrumentId;

	Book (int instrumentId){
		this.instrumentId=instrumentId;
	}
	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public void amend(Order order, BigDecimal price) {
		order.setPrice(price);
		order.setTimestamp(new Date());
	}

	public void amend(Order order, int qty) {
		if (order.getQty() < qty)
			order.setTimestamp(new Date());
		order.setQty(qty);
	}

	public void cancel(Order order) {
		if (order.getType() == SideType.BUY)
			bid.remove(bid.indexOf(order));
		else
			ask.remove(ask.indexOf(order));
		reportList.add(new ExecutionReport(order, 1));
	}

	public ArrayList<ExecutionReport> process(Order order) {
		reportList = new ArrayList<>();
		int index;
		Comparator<Order> bcomp = new BuyPriceComparator().thenComparing(new SquenceNumberComparator());
		Comparator<Order> scomp = new SellPriceComparator().thenComparing(new SquenceNumberComparator());
		if (order.getType() == SideType.BUY) {
			index = Collections.binarySearch(bid, order, bcomp);
			index = Math.abs(index) - 1;
			bid.add(index, order);
			reportList.add(new ExecutionReport(order, 0));
			if (ask.size() != 0) {
				for (int i = 0; i <= index; i++) {
					work(bid.get(i),ask);
				}
			}
		} else {
			index = Collections.binarySearch(ask, order, scomp);
			index = Math.abs(index) - 1;
			ask.add(index, order);
			reportList.add(new ExecutionReport(order, 0));
			if (bid.size() != 0) {
				for (int i = 0; i <= index; i++) {
					work(ask.get(i), bid);
				}
			}
		}
		return reportList;
	}

	private void work(Order order, ArrayList<Order> list) {
		int remain;
		Order seller;
		Order buyer;
		for (int i = 0; i < list.size(); i++) {
			if (list.size() != 0) {
				if (order.getType() == SideType.BUY) {
					buyer = order;
					seller = list.get(i);
				} else {
					buyer = list.get(i);
					seller = order;
				}
				if (buyer.getPrice().compareTo(seller.getPrice()) >= 0) {
					remain = buyer.getQty() - seller.getQty();
					if (remain < 0) {
						remain = Math.abs(remain);
						reportList.add(new ExecutionReport(buyer, seller.getPrice(), seller.getUserId()));
						reportList
								.add(new ExecutionReport(seller, seller.getPrice(), buyer.getQty(), buyer.getUserId()));
						seller.setQty(remain);
						cancel(buyer);
						break;
					} else if (remain == 0) {
						reportList.add(new ExecutionReport(buyer, seller.getPrice(), seller.getUserId()));
						reportList.add(new ExecutionReport(seller, seller.getPrice(), buyer.getUserId()));
						cancel(seller);
						cancel(buyer);
						break;
					} else {
						reportList.add(
								new ExecutionReport(buyer, seller.getPrice(), seller.getQty(), seller.getUserId()));
						reportList.add(new ExecutionReport(seller, seller.getPrice(), buyer.getUserId()));
						cancel(seller);
						amend(buyer, remain);
						i--;
						break;
					}
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
