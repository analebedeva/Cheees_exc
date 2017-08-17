package me;
import java.math.BigDecimal;

import org.junit.Test;


public class TestJunit {

	@Test
	   public void testOrder () {
		Book book=new Book(1);
		Order orderBuy =new Order(new BigDecimal(5007), 1000, 2, 0, 3, SideType.BUY);
		for (ExecutionReport s : book.process(orderBuy))
		System.out.println(s.getExecutionReport());		

		Order orderSell =new Order(new BigDecimal(5005.21), 200, 1, 0, 2, SideType.SELL);
		for (ExecutionReport s : book.process(orderSell))
			System.out.println(s.getExecutionReport());
	}
	
}