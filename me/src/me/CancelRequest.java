package me;

import java.util.ArrayList;

public class CancelRequest implements Request {

	private int userId;
	private int orderId;
	private int instrumentId;

	public CancelRequest() {
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

	@Override
	public ArrayList<ExecutionReport> process(Book book) {
		ArrayList<ExecutionReport> listReports = new ArrayList<>();
		listReports.add(book.cancel(getOrderId()));
		return listReports;
	}
}
