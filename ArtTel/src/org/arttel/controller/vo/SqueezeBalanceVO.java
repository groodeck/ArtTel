package org.arttel.controller.vo;

public class SqueezeBalanceVO {
	
	private final int squeezeCount;
	private final int meters;
	private final String amount;
	
	public SqueezeBalanceVO(final int squeezeCount, final int meters, final String amount){
		this.squeezeCount = squeezeCount;
		this.meters = meters;
		this.amount = amount;
	}

	public int getSqueezeCount() {
		return squeezeCount;
	}

	public int getMeters() {
		return meters;
	}

	public String getAmount() {
		return amount;
	}
}