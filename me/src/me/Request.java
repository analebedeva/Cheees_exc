package me;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Request {
	void setInstrumentId(int instrumentId);

	void setOrderId(int orderId);

	void setUserId(int userId);

	int getInstrumentId();

	int getOrderId();

	int getUserId();

	ArrayList<ExecutionReport> process(Book book);
}

class NewRequest implements Request {
	private BigDecimal price;
	private int qty;
	private int userId;
	private int orderId;
	private int sequenceNumber;
	private SideType type;
	private int instrumentId;

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

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public SideType getType() {
		return type;
	}

	public void setType(SideType type) {
		this.type = type;
	}

	@Override
	public ArrayList<ExecutionReport> process(Book book) {
		if (qty > 0 && price.signum() == 1) {
			Order order = new Order(price, qty, userId, orderId, sequenceNumber, type);
			return book.process(order);
		} else {
			ArrayList<ExecutionReport> listReports = new ArrayList<>();
			listReports.add(new ExecutionReport(ExecutionReportType.REJECTED));
			return listReports;
		}
	}

}

class CancelRequest implements Request {
	private int userId;
	private int orderId;
	private int instrumentId;

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

	@Override
	public ArrayList<ExecutionReport> process(Book book) {
		ArrayList<ExecutionReport> listReports = new ArrayList<>();
		listReports.add(book.cancel(getOrderId()));
		return listReports;
	}
}

class AmendRequest implements Request {
	private BigDecimal price;
	private int qty;
	private int userId;
	private int orderId;
	private int instrumentId;
	private int sequenceNumber;

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