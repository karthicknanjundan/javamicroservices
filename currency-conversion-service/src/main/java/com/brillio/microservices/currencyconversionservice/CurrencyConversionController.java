package com.brillio.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversionBean retrieveCurrencyConversion(
			@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity){
		
		
		//instead of writing lot of code below to access other application
		//we can use Feign
			Map<String, String> uriVariable = new HashMap<>();
			uriVariable.put("from", from);
			uriVariable.put("to", to);
		
		    ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,uriVariable);
		    CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,quantity.multiply(response.getConversionMultiple()),response.getPort()) ;
		
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversionBean retrieveCurrencyConversionFeign(
			@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity){
		
		
		//instead of writing lot of code below to access other application
		//we can use Feign
		//It makes it easy to call the rest fully Web services when we use this template to call the service.
		CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		logger.info("Response :{} ", response);
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,quantity.multiply(response.getConversionMultiple()),response.getPort()) ;
		
	}

}
