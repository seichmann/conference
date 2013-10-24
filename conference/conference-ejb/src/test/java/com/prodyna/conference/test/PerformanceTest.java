package com.prodyna.conference.test;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

import junit.framework.Assert;

import org.junit.Test;

import com.prodyna.conference.mbean.Performance;

/**
 * JMX-Management Bean Test.
 * 
 * @author Stephan Eichmann
 * 
 */
public class PerformanceTest {

	@Test
	public void test() throws MalformedObjectNameException,
			InstanceAlreadyExistsException, NotCompliantMBeanException,
			InstanceNotFoundException, ReflectionException, MBeanException,
			AttributeNotFoundException {
		MBeanServer ms = ManagementFactory.getPlatformMBeanServer();
		ObjectName on = new ObjectName("com.prodyna:type=Performance");
		Performance bean = new Performance();

		ms.registerMBean(bean, on);

		int countBefore = (Integer) ms.getAttribute(on, "Count");

		ms.invoke(on, "report", new Object[] { "a", "x", 10 },
				new String[] { String.class.getName(), String.class.getName(),
						long.class.getName() });

		int countAfter = (Integer) ms.getAttribute(on, "Count");
		Assert.assertEquals(1, countAfter - countBefore);

		CompositeData[] all = (CompositeData[]) ms.getAttribute(on, "All");
		for (CompositeData compositeData : all) {
			if (compositeData.get("service").equals("a")) {
				Assert.assertEquals(1L, compositeData.get("count"));
			}
		}
		ms.unregisterMBean(on);
	}
}
