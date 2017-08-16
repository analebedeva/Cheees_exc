package me;
import java.math.BigDecimal;

	public class ExecutionReport {
		String executionReport;
		ExecutionReport (Order order, BigDecimal price, int contrUserId){
		executionReport="Trade FullFill";
			
		}
		ExecutionReport (Order order,BigDecimal price, int sales, int contrUserId){

		}
		ExecutionReport(Order order, boolean entry){
			executionReport="New Order";
		}
		public String getExecutionReport() {
			return executionReport;
		}
		public void setExecutionReport(String executionReport) {
			this.executionReport = executionReport;
		}

	}

