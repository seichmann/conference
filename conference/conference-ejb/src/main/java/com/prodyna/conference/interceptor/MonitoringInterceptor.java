package com.prodyna.conference.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.prodyna.conference.mbean.PerformanceMXBean;

@Interceptor
@Monitored
public class MonitoringInterceptor {

	@Inject
	private Logger logger;

	@Inject
	private PerformanceMXBean performance;

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

		String service = ic.getTarget().getClass().getSimpleName();
		String method = ic.getMethod().getName();
		logger.info("called [Time: " + time + "]: " + service + "." + method
				+ "() with params: " + parameters + " >>> " + result);

		// JMX Statistics
		performance.report(service, method, time);

		return proceed;
	}

	private String getCommaSeparatedList(Object[] parameters) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < parameters.length; i++) {
			Object param = parameters[i];
			if (param != null) {
				result.append(param.toString());
				if (i < parameters.length - 1) {
					result.append(",");
				}
			}
		}
		return result.toString();
	}
}
