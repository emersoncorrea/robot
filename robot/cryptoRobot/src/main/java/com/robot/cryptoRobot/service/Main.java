package com.robot.cryptoRobot.service;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class Main {
  public static void main(String[] args) {
      try {
    	  
    	  String secret = "jZqf942HMUy6qUzGmWmKTdXOCrE0Qv3xBibLq0tFU5tWl3x1JO55CE415hl6fG7b";
    	     String message = "RKwUHAoPFoMi3NKSS7wBylSUQKV6ECHbPpheIJd7Eb98MqQwWSWUZibtabYlwsVB";
          // Mensagem a ser assinada
          String mensagem = "GET\n/sapi/v1/capital/config/getall\ntimestamp=1623379467000";
          
          // Chave secreta
          String chaveSecreta = "jZqf942HMUy6qUzGmWmKTdXOCrE0Qv3xBibLq0tFU5tWl3x1JO55CE415hl6fG7b";
          
          // Configurando o objeto Mac com o algoritmo HMAC-SHA256
          Mac mac = Mac.getInstance("HmacSHA256");
          SecretKeySpec secretKeySpec = new SecretKeySpec(chaveSecreta.getBytes(), "HmacSHA256");
          mac.init(secretKeySpec);
          
          // Calculando o HMAC
          byte[] hmacBytes = mac.doFinal(mensagem.getBytes());
          
          // Codificando o HMAC em Base64
          String hmacBase64 = Base64.getEncoder().encodeToString(hmacBytes);
          
          System.out.println("HMAC-SHA256: " + hmacBase64);
      } catch (NoSuchAlgorithmException | InvalidKeyException e) {
          e.printStackTrace();
      }
  }
}