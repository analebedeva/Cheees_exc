package me;

import java.math.BigDecimal;

public class ReferenceData {
	private int instrumentId;
	private BigDecimal priceMin = new BigDecimal(0.0);
	private int qtyMin = 0;
	private BigDecimal priceTick=null;

	public BigDecimal getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(BigDecimal priceMin) {
		this.priceMin = priceMin;
	}

	public int getQtyMin() {
		return qtyMin;
	}

	public void setQtyMin(int qtyMin) {
		this.qtyMin = qtyMin;
	}

	public BigDecimal getPriceTick() {
		return priceTick;
	}

	public void setPriceTick(BigDecimal priceTick) {
		this.priceTick = priceTick;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}
}
