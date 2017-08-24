package me;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AmendRequest implements Request {
	private BigDecimal price;
	private int qty;
	private int userId;
	private int orderId;
	private int instrumentId;
	private int sequenceNumber;

	public AmendRequest() {

	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	@Override
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int getInstrumentId() {
		return instrumentId;
	}

	@Override
	public int getOrderId() {
		return orderId;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public ArrayList<ExecutionReport> process(Book book) {
		ArrayList<ExecutionReport> listReports = new ArrayList<>();
		Order order = book.searchOrder(getOrderId());
		if (qty > 0 && price.signum() == 1) {
			if (order.getPrice().compareTo(getPrice()) != 0)
				listReports.addAll(book.amend(getOrderId(), getPrice(), getSequenceNumber()));
			if (order.getQty() != getQty())
				listReports.addAll(book.amend(getOrderId(), getQty(), getSequenceNumber()));
		} else
			listReports.add(new ExecutionReport(ExecutionReportType.REJECTED));
		return listReports;

	}

}