package com.robot.cryptoRobot.infra.repository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.cryptoRobot.domain.trade.AveragePrice;
import com.robot.cryptoRobot.domain.trade.NewOrder;
import com.robot.cryptoRobot.domain.trade.Status;
import com.robot.cryptoRobot.domain.trade.TradeExchange;

@Component
public class TradeExchangeRepository implements TradeExchange {

	private static final String HMAC_SHA256 = "HmacSHA256";
    private RestTemplate restTemplate;

    public static final String API_SECRET="82fFXBzheUE5ajpzSysi0fhgOzNJ1SmgclkiXKaXhU7vanAUyCn8OFSMDCT6cmcl";
    private final long recvWindow = 50000;

    private String recvWindowParam;
    
    public TradeExchangeRepository() {
    	recvWindowParam = "&recvWindow="+recvWindow;
        this.restTemplate = new RestTemplateBuilder().build();
    }
	
	@Override
	public String orderTrade(String symbol, String type, String side, String quantity) {
		
		StringBuffer apiUrl = new StringBuffer();
    	String timestamp = "timestamp="+System.currentTimeMillis();
    	apiUrl.append("https://api.binance.com/api/v3/order/test?")
    		.append(timestamp)
    		.append(recvWindowParam)
    		.append("&signature=").append(getSignature(timestamp+recvWindowParam));
		
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", "GUtCK3Cu5SvMYIv7qMJsgSUuenKhft7DpKq31uJE73sRY3QuaoGBhSwemUTBEN8f");
        headers.set("Content-Type", "application/json");
        
        HttpEntity<String> requestEntity = new HttpEntity<>(getJsonTrade(symbol, type, side,quantity), headers);
        
        try {
        	ResponseEntity<Status> responseEntity = restTemplate.exchange(
        
                apiUrl.toString(),
                HttpMethod.GET,
                requestEntity,
                Status.class
        	);
        	if (responseEntity.getStatusCode().is2xxSuccessful()) {
        		return responseEntity.getBody().getData();
        	} else {
        		throw new RuntimeException("Failed to call API. Status code: 500");
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
        return "";
	}

	@Override
	public String averagePrice(String symbol) {
		String apiUrl = "https://api.binance.com/api/v3/avgPrice?symbol="+symbol;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", "GUtCK3Cu5SvMYIv7qMJsgSUuenKhft7DpKq31uJE73sRY3QuaoGBhSwemUTBEN8f");
        headers.set("Content-Type", "application/json");
        
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        
        try {
        	ResponseEntity<AveragePrice> responseEntity = restTemplate.exchange(
        
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                AveragePrice.class
        	);
        	if (responseEntity.getStatusCode().is2xxSuccessful()) {
        		return responseEntity.getBody().getPrice();
        	} else {
        		throw new RuntimeException("Failed to call API. Status code: 500");
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
        return "";
	}
	
	@Override
	public String getStatusAPI() {
    	
    	StringBuffer apiUrl = new StringBuffer();
    	String timestamp = "timestamp="+System.currentTimeMillis();
    	apiUrl.append("https://api.binance.com/sapi/v1/account/status?")
    		.append(timestamp)
    		.append(recvWindowParam)
    		.append("&signature=").append(getSignature(timestamp+recvWindowParam));
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", "GUtCK3Cu5SvMYIv7qMJsgSUuenKhft7DpKq31uJE73sRY3QuaoGBhSwemUTBEN8f");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        
        try {
        	ResponseEntity<Status> responseEntity = 
        		restTemplate.exchange(apiUrl.toString(), HttpMethod.GET, requestEntity, Status.class);
        	
        	if (responseEntity.getStatusCode().is2xxSuccessful()) {
        		return responseEntity.getBody().getData();
        	} else {
        		throw new RuntimeException("Failed to call API. Status code: 500");
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	private String getJsonTrade(String symbol, String type, String side, String quantity) {
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
        	/*NewOrder newOrder = new NewOrder();
        	newOrder.setSymbol("BTCUSDT");
        	newOrder.setType("MARKET");
        	newOrder.setSide("SELL");
        	newOrder.setQuantity("0.00001000");*/
        	NewOrder newOrder = new NewOrder();
        	newOrder.setSymbol(symbol);
        	newOrder.setType(type);
        	newOrder.setSide(side);
        	newOrder.setQuantity(quantity);
        	
            String json = objectMapper.writeValueAsString(newOrder);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	private String getSignature(String data) {
        byte[] hmacSha256;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(API_SECRET.getBytes(), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return Hex.encodeHexString(hmacSha256).toString();
    }
	
}
