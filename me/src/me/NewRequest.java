package me;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("")
public class NewRequest  implements Request {
		private BigDecimal price;
		private int qty;
		private int userId;
		private int orderId;
		private int sequenceNumber;
		private SideType type;
		private int instrumentId;

		public NewRequest() {}
			
		
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
