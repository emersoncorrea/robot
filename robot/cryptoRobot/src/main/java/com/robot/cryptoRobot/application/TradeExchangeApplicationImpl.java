package com.robot.cryptoRobot.application;

import org.springframework.beans.factory.annotation.Autowired;

import com.robot.cryptoRobot.domain.trade.TradeExchange;

public class TradeExchangeApplicationImpl implements TradeExchange {
	
	@Autowired
	private TradeExchange tradeExchange;
	
	@Override
	public String orderTrade(String symbol, String type, String side, String quantity) {
		return tradeExchange.orderTrade(symbol, type, side, quantity);
	}

	@Override
	public String averagePrice(String symbol) {
		return tradeExchange.averagePrice(symbol);
	}

	@Override
	public String getStatusAPI() {
		return tradeExchange.getStatusAPI();
	}

}
