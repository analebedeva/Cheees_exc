package me;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "type")  
@JsonSubTypes({  
	    @Type(value = NewRequest.class, name = "new"),  
	    @Type(value = AmendRequest.class, name = "amend"),
	    @Type(value = CancelRequest.class, name = "cancel")   })

public interface Request {
	void setInstrumentId(int instrumentId);

	void setOrderId(int orderId);

	void setUserId(int userId);

	int getInstrumentId();

	int getOrderId();

	int getUserId();

	ArrayList<ExecutionReport> process(Book book);
}



