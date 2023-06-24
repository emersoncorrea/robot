package com.robot.cryptoRobot.controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.robot.cryptoRobot.dto.Status;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/robot")
public class RobotController {

	private static final String HMAC_SHA256 = "HmacSHA256";
    private RestTemplate restTemplate;

    public static final String API_SECRET="jZqf942HMUy6qUzGmWmKTdXOCrE0Qv3xBibLq0tFU5tWl3x1JO55CE415hl6fG7b";
    private final long recvWindow = 50000; //

    
    public RobotController() {
        this.restTemplate = new RestTemplateBuilder().build();
    }
	
    @Operation(summary = "Get a book by its id")
	@GetMapping("/{id}")
    public String findById(@PathVariable long id) {
    	
    	String param1 = "timestamp="+System.currentTimeMillis();
    	//String param2 = String.valueOf(recvWindow);
        String apiUrl = "https://api.binance.com/sapi/v1/account/status?"+param1
        		+"&recvWindow="+recvWindow
        		+"&signature="+getSignature(param1+"&recvWindow="+recvWindow);
        
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Content-Type", "application/json");
        headers.set("X-MBX-APIKEY", "RKwUHAoPFoMi3NKSS7wBylSUQKV6ECHbPpheIJd7Eb98MqQwWSWUZibtabYlwsVB");
        
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        
        try {
        	ResponseEntity<Status> responseEntity = restTemplate.exchange(
        
                apiUrl,
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
    
    public String getSignature(String data) {
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
