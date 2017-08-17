package me;

import java.math.BigDecimal;
import java.util.Date;


public class Order {
	private BigDecimal price; // цена
	private int qty; // количество
	private int userId; // ИД клиента
	private final int orderId;// ИД заявки
	private Date timestamp; // время поступления заявки
	private int squenceNumber;
	private SideType type;
	public Order(BigDecimal price, int qty, int userId, int orderId, int sqNum, SideType type) {
		this.orderId = orderId;
		this.price=price.setScale(1, BigDecimal.ROUND_HALF_UP);
		this.qty=qty;
		this.userId=userId;
		squenceNumber=sqNum;
		timestamp = new Date();
		this.type=type;
	}


	public SideType getType() {
		return type;
	}


	public void setType(SideType type) {
		this.type = type;
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


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public Integer getSquenceNumber() {
		return squenceNumber;
	}


	public void setSquenceNumber(int squenceNumber) {
		this.squenceNumber = squenceNumber;
	}


	public int getOrderId() {
		return orderId;
	}

	
	@Override
	public String toString() {
		return "Order [price=" + price + ", qty=" + qty + ", userId=" + userId + ", orderId=" + orderId + ", temestamp="
				+ timestamp + ", squenceNumber=" + squenceNumber + "]";
	}
}

