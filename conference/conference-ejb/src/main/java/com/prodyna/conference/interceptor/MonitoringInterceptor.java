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

		String parameters = ic.getParameters().length > 0 ? getCommaSeparatedList(ic
				.getParameters()) : "";
		String result = proceed != null ? proceed.toString() : "";

		logger.info("called [Time: " + time + "]: "
				+ ic.getTarget().getClass().getSimpleName() + "."
				+ ic.getMethod().getName() + "() with params: " + parameters
				+ " >>> " + result);
		return proceed;
	}

	private String getCommaSeparatedList(Object[] parameters) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < parameters.length; i++) {
			result.append(parameters[i].toString());
			if (i < parameters.length - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}
}
