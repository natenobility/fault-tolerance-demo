package com.nano.demoware.hystrix.service;

import com.nano.demoware.hystrix.controller.MyController.TestCase;


/**
 * Implementations of this interface locate and validate folders on disk by
 * name.
 */
public interface MyService {

    /** Simulates doing some work, with the behavior driven by the given test case. */
    String simulateDoingWork(TestCase testCase);
}
