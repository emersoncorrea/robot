package com.robot.cryptoRobot.application;

public interface TradeExchangeApplication{

	public String getStatusExchangeAPI();
	public String averagePrice(String symbol);
	public String orderTradeTest(String symbol, String type, String side, String quantity);
}
