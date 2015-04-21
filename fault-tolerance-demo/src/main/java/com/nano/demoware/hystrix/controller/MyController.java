package com.nano.demoware.hystrix.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nano.demoware.hystrix.service.MyService;

@Controller
public class MyController {

//	private static Logger log = LoggerFactory.getLogger(MyController.class);

	@Resource
	private MyService service;
	
    public static enum TestCase {
		slow, fast, oops
	}

	@ResponseBody
    @RequestMapping("{testCase}")
    public String handleRequest(@PathVariable TestCase testCase) {
    	return service.simulateDoingWork(testCase);
    }
	
//	@PostConstruct
//	public void logInstructions() {
//		log.info("");
//		log.info("To demonstrate a call that works normally, navigate to the following url:");
//		log.info("http://localhost:8080/fast");
//		log.info("To demonstrate a call that times out, navigate to the following url");
//		log.info("http://localhost:8080/slow");
//		log.info("To demonstrate a call that fails, navigate to the following url");
//		log.info("http://localhost:8080/oops");
//		log.info("");
//	}
}
