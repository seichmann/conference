package com.prodyna.conference.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

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

		logger.log(Level.INFO, "called {" + time + "}: "
				+ ic.getTarget().getClass().getSimpleName() + "."
				+ ic.getMethod().getName() + "() with params: "
				+ ic.getParameters().toString() + " >>> " + proceed.toString());
		return proceed;
	}
}
