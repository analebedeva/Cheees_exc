package me;
import java.math.BigDecimal;

import org.junit.Test;


public class TestJunit {

	@Test
	   public void testOrder () {
		Book book=new Book();
		Order orderSell =new Order(new BigDecimal(5005.21), 100, 0, 0, 0, SideType.SELL);
		for (ExecutionReport s : book.process(orderSell))
			System.out.println(s.getExecutionReport());
		Order orderBuy =new Order(new BigDecimal(5007), 100, 0, 0, 0, SideType.BUY);
		for (ExecutionReport s : book.process(orderBuy))
		System.out.println(s.getExecutionReport());			
	}
}