package me;
import java.math.BigDecimal;

	public class ExecutionReport {
		String executionReport;
		ExecutionReport (Order order, BigDecimal price, int contrUserId){
		executionReport="Trade FullFill "+order.getUserId();	
		}
		ExecutionReport (Order order,BigDecimal price, int sales, int contrUserId){
			executionReport="Trade Fill "+price.toString();
		}
		ExecutionReport(Order order, int tag){
			switch (tag) {
			case 0:	executionReport="New";
			break;
			case 4: executionReport="Canceled";
			break;
			}
		}
		public String getExecutionReport() {
			return executionReport;
		}
		public void setExecutionReport(String executionReport) {
			this.executionReport = executionReport;
		}

	}

