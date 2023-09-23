package com.robot.cryptoRobot.domain.trade;

public interface TradeExchange {

	public String orderTrade(String symbol, String type, String side,String quantity);
	public String averagePrice(String symbol);
	public String getStatusAPI();
	
}
