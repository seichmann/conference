package com.prodyna.conference.rest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.filter.ExcludeRegExpPaths;
import org.junit.runner.RunWith;

/**
 * Abstract base class for all Tests.
 * 
 * Handle deployment.
 * 
 * @author Stephan Eichmann
 * 
 */
@RunWith(Arquillian.class)
public abstract class AbstractDeployableTest {

	@Deployment(testable = false)
	public static Archive<?> createArchive() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
		war.addPackages(true, new ExcludeRegExpPaths("^.*(\\/mbean\\/).*$"),
				"com.prodyna.conference");
		war.addAsResource("META-INF/beans.xml");
		war.addAsResource("META-INF/test-persistence.xml",
				"META-INF/persistence.xml");
		war.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		return war;
	}
}
