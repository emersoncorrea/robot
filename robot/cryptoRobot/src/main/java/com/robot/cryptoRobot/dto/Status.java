package com.robot.cryptoRobot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
	
	@JsonProperty("data")
	public String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
