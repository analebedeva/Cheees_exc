package me;

import java.math.BigDecimal;
import java.util.Date;

enum TypeOrder {
	BUY, SELL
}
public class Order {
	private BigDecimal price; // цена
	private int qty; // количество
	private int userId; // ИД клиента
	private final int orderId;// ИД заявки
	Date timestamp; // время поступления заявки
	private int squenceNumber;
	private TypeOrder type;
	public Order(BigDecimal price, int qty, int userId, int orderId, int sqNum, String type) {
		this.orderId = orderId;
		this.price=price;
		this.qty=qty;
		this.userId=userId;
		squenceNumber=sqNum;
		timestamp = new Date();
		this.type=TypeOrder.valueOf(type);
	}


	public TypeOrder getType() {
		return type;
	}


	public void setType(TypeOrder type) {
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


	public void Amend(BigDecimal price){
		this.price=price;
		timestamp = new Date();
	}
	public void Amend(int qty){
		this.qty=qty;
		timestamp = new Date();
	}
}

