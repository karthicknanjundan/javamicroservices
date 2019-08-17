package com.brillio.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brillio.microservices.limitsservice.bean.LimitConfiguration;

@RestController
public class LimitConfigController {
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrievLimitConfiguration(){
		return new LimitConfiguration(configuration.getMinimum(),configuration.getMaximum());
		
	}

}
