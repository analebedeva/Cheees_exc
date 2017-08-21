package me;

import java.math.BigDecimal;

public class ExecutionReport {
	private int orderId;
	private int userId;
	private int contrUserId;
	private int tradeQty;
	private BigDecimal tradePrice;
	private ExecutionReportType execType;

	ExecutionReport(Order order, BigDecimal tradePrice, int tradeQty, int contrUserId, ExecutionReportType execType) {
		orderId = order.getOrderId();
		userId = order.getUserId();
		this.tradePrice = tradePrice;
		this.tradeQty = tradeQty;
		this.execType = execType;
	}

	ExecutionReport(Order order, ExecutionReportType execType) {
		orderId = order.getOrderId();
		userId = order.getUserId();
		this.execType = execType;
	}
	public ExecutionReport(ExecutionReportType execType) {
		this.execType = execType;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getContrUserId() {
		return contrUserId;
	}

	public void setContrUserId(int contrUserId) {
		this.contrUserId = contrUserId;
	}

	public int getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(int tradeQty) {
		this.tradeQty = tradeQty;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public ExecutionReportType getExecType() {
		return execType;
	}

	public void setExecType(ExecutionReportType execType) {
		this.execType = execType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
