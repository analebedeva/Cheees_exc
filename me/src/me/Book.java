package me;

import java.math.BigDecimal;
import java.util.*;

public class Book {

	private ArrayList<Order> bid = new ArrayList<>();
	private ArrayList<Order> ask = new ArrayList<>();
	private ArrayList<ExecutionReport> reportList;
	private final int instrumentId;
	private ReferenceData referenceData=new ReferenceData();

	public Book(int instrumentId, ReferenceData referenceData) {
		this.instrumentId = instrumentId;
		this.referenceData=referenceData;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public ArrayList<ExecutionReport> amend(int orderId, BigDecimal price, int sequenceNumber) {
		ArrayList<ExecutionReport> listReports=new ArrayList<>();
		Order order = searchOrder(orderId);
		if (order.getQty() != 0 && sequenceNumber>order.getSequenceNumber()) {
			order.setPrice(price);
			order.setTimestamp(new Date());
			order.setSequenceNumber(sequenceNumber);
			listReports.add(new ExecutionReport(order, ExecutionReportType.AMENDED));
		}
		listReports.add(new ExecutionReport(ExecutionReportType.REJECTED));
		return listReports;
	}

	public ArrayList<ExecutionReport> amend(int orderId, int qty, int sequenceNumber) {
		ArrayList<ExecutionReport> listReports=new ArrayList<>();
		Order order = searchOrder(orderId);
		if (order.getQty() != 0) {
			if (order.getQty() < qty  && sequenceNumber>order.getSequenceNumber()) {
				order.setTimestamp(new Date());
				order.setSequenceNumber(sequenceNumber);
				}
			order.setQty(qty);
			listReports.add(new ExecutionReport(order, ExecutionReportType.AMENDED));
		}
		listReports.add(new ExecutionReport(ExecutionReportType.REJECTED));
		return listReports;
	}

	public ExecutionReport cancel(int orderId) {

		Order order = searchOrder(orderId);
		if (order.getQty() != 0) {
			if (order.getType() == SideType.BUY)
				bid.remove(bid.indexOf(order));
			else
				ask.remove(ask.indexOf(order));
			return new ExecutionReport(order, ExecutionReportType.CANCELED);
		}
		else return new ExecutionReport(ExecutionReportType.REJECTED);
	}

	public ArrayList<ExecutionReport> process(Order order) {
		reportList = new ArrayList<>();
		int index;
		Comparator<Order> bcomp = new BuyPriceComparator().thenComparing(new SequenceNumberComparator());
		Comparator<Order> scomp = new SellPriceComparator().thenComparing(new SequenceNumberComparator());
		if (order.getType() == SideType.BUY) {
			index = Collections.binarySearch(bid, order, bcomp);
			index = Math.abs(index) - 1;
			bid.add(index, order);
			reportList.add(new ExecutionReport(order, ExecutionReportType.NEW));
			if (ask.size() != 0) {
				for (int i = 0; i <= index; i++) {
					work(bid.get(i), ask);
				}
			}
		} else {
			index = Collections.binarySearch(ask, order, scomp);
			index = Math.abs(index) - 1;
			ask.add(index, order);
			reportList.add(new ExecutionReport(order, ExecutionReportType.NEW));
			if (bid.size() != 0) {
				for (int i = 0; i <= index; i++) {
					work(ask.get(i), bid);
				}
			}
		}
		return reportList;
	}

	 Order searchOrder(int orderId) {
		if (ask.size() != 0) {
			for (int i = 0; i < ask.size(); i++)
				if (ask.get(i).getOrderId() == orderId)
					return ask.get(i);
		}
		if (bid.size() != 0) {
			for (int i = 0; i < bid.size(); i++)
				if (bid.get(i).getOrderId() == orderId)
					return bid.get(i);
		}
		return new Order(null, 0, 0, 0, 0, null);
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
						reportList.add(new ExecutionReport(buyer, seller.getPrice(), buyer.getQty(), seller.getUserId(),
								ExecutionReportType.TRADE_FULL_FILL));
						reportList.add(new ExecutionReport(seller, seller.getPrice(), buyer.getQty(), buyer.getUserId(),
								ExecutionReportType.TRADE_FILL));
						seller.setQty(remain);
						cancel(buyer.getOrderId());
						break;
					} else if (remain == 0) {
						reportList.add(new ExecutionReport(buyer, seller.getPrice(), buyer.getQty(), seller.getUserId(),
								ExecutionReportType.TRADE_FULL_FILL));
						reportList.add(new ExecutionReport(seller, seller.getPrice(), seller.getQty(),
								buyer.getUserId(), ExecutionReportType.TRADE_FULL_FILL));
						cancel(seller.getOrderId());
						cancel(buyer.getOrderId());
						break;
					} else {
						reportList.add(new ExecutionReport(buyer, seller.getPrice(), seller.getQty(),
								seller.getUserId(), ExecutionReportType.TRADE_FILL));
						reportList.add(new ExecutionReport(seller, seller.getPrice(), seller.getQty(),
								buyer.getUserId(), ExecutionReportType.TRADE_FULL_FILL));
						cancel(seller.getOrderId());
						buyer.setQty(remain);
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

class SequenceNumberComparator implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
		return o1.getSequenceNumber().compareTo(o2.getSequenceNumber());
	}
}
