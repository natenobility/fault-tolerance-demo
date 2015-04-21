package com.nano.demoware.hystrix.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nano.demoware.hystrix.controller.MyController.TestCase;
import com.nano.demoware.hystrix.faulttolerance.FaultTolerant;

@Service
public class MyServiceImpl implements MyService {

	@Value("${long.time.millis}")
	private int longDelayMillis;

	@Override
	@FaultTolerant
//	@FaultTolerant(group = ServiceGroup.GROUP_A, command = "specialWork")
	public String simulateDoingWork(TestCase testCase) {
		switch (testCase) {
		case fast:
			return simulateFastExecution();
		case slow:
			return simulateSlowExecution();
		case oops:
			return simulateFastFailure();
		default:
			break;
		}
		return null;
	}

	private String simulateFastFailure() {
		throw new RuntimeException("oops, I failed");
	}

	private String simulateSlowExecution() {
		try {
			Thread.sleep(longDelayMillis);
			return "I timed out";
		} catch (InterruptedException e) {}
		return null;
	}

	private String simulateFastExecution() {
		return "I did work quickly";
	}
}
