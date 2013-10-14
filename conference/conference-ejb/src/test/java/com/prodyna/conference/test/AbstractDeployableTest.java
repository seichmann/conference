package com.prodyna.conference.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.filter.ExcludeRegExpPaths;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class AbstractDeployableTest {

	@Deployment
	public static Archive<?> createArchive() {
//		String pom = AbstractDeployableTest.class.getResource("/arquillian-deps.xml").getFile();
//		war.addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class)
//                .includeDependenciesFromPom(pom).resolveAs(JavaArchive.class));
//		war.addAsLibraries(resolver.artifact("com.prodyna.conference:conference-ejb-client").resolveAsFiles());
//		war.addAsLibraries(resolver.artifact("com.prodyna.conference:conference-model").resolveAsFiles());
		
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
