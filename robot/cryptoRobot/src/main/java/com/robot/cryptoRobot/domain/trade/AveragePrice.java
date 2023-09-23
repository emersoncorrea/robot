package com.robot.cryptoRobot.domain.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AveragePrice {
	
	@JsonProperty("mins")
	public Integer mins;
	
	@JsonProperty("price")
	public String price;

	public Integer getMins() {
		return mins;
	}

	public void setMins(Integer mins) {
		this.mins = mins;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
