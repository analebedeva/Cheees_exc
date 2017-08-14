package me;

import java.util.Date;

public class Order {
	private double price; // цена
	private int qty; // количество
	private int userId; // ИД клиента
	private final int orderId;// ИД заявки
	Date temestamp; // время поступления заявки
	private int squenceNumber;

	public Order(double price, int gty, int userId, int orderId) {
		this.orderId = orderId;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
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


	public Date getTemestamp() {
		return temestamp;
	}


	public void setTemestamp(Date temestamp) {
		this.temestamp = temestamp;
	}


	public int getSquenceNumber() {
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
		return "Order [price=" + price + ", qty=" + qty + ", userId=" + userId + ", orderId=" + orderId
				+ ", temestamp=" + temestamp + "]";
	}

	public void Amend(double price, int gty){
		try{
			setQty(Qty);
		setPrice(price);
		}
		
	 }
	
	class IncorrectDataException extends Exception {
	
	}

}

