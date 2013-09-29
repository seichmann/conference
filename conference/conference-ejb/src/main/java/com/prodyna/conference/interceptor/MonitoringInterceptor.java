package com.prodyna.conference.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

@Interceptor
@Monitored
public class MonitoringInterceptor {

	@Inject
	private Logger logger;

	public MonitoringInterceptor() {
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		long start = System.currentTimeMillis();

		Object proceed = ic.proceed();

		long time = System.currentTimeMillis() - start;

		logger.info("called {" + time + "}: "
				+ ic.getTarget().getClass().getSimpleName() + "."
				+ ic.getMethod().getName() + "() with params: "
				+ ic.getParameters().toString() + " >>> " + proceed.toString());
		return proceed;
	}
}
