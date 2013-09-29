package com.prodyna.conference.mbean;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Session Bean implementation class PerformanceEnabler
 */
@Singleton
@Startup
public class PerformanceEnabler {

	@Inject
	private Performance bean;

	/**
	 * Default constructor.
	 */
	public PerformanceEnabler() {
	}

	@PostConstruct
	public void startup() {
		try {
			MBeanServer ms = ManagementFactory.getPlatformMBeanServer();
			ObjectName on = new ObjectName("com.prodyna:type=Performance");

			ms.registerMBean(bean, on);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@PreDestroy
	public void cleanup() {
		try {
			MBeanServer ms = ManagementFactory.getPlatformMBeanServer();
			ObjectName on = new ObjectName("com.prodyna:type=Performance");

			ms.unregisterMBean(on);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
