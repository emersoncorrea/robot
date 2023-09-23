package com.robot.cryptoRobot.infra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robot.cryptoRobot.application.TradeExchangeApplication;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/robot")
public class TradeExchangeRest {


	@Autowired
	private TradeExchangeApplication application;
	
    @Operation(summary = "Get Status API")
	@GetMapping()
    public String getStatusExchangeAPI() {
        return application.getStatusExchangeAPI();
    }

    @Operation(summary = "Get avarege price")
	@GetMapping("/order/avg/{symbol}")
    public String averagePrice(@PathVariable String symbol) {
        return application.averagePrice(symbol);
    }
    
    @Operation(summary = "Order trade test")
	@GetMapping("/order/test")
    public String orderTradeTest(@RequestParam String symbol, @RequestParam String type, @RequestParam String side,@RequestParam String quantity) {
    	
        return application.orderTradeTest(symbol, type, side, quantity);
    }
    
}
