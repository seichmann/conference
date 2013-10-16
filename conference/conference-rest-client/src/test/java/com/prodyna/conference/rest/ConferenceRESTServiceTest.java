package com.prodyna.conference.rest;

import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.util.DateUtil;

public class ConferenceRESTServiceTest extends AbstractDeployableTest {

	private static final String REST_PATH = "services";

	@Test
	@InSequence(value = 1)
	@RunAsClient
	public void saveConference(@ArquillianResource URL url) throws Exception {
		ClientRequest request = new ClientRequest(new URL(url + REST_PATH
				+ "/conferences/").toExternalForm());
		request.header("Accept", MediaType.APPLICATION_JSON);

		Conference conference = new Conference();
		conference.setName("JAX");
		conference.setDescription("ENTER");
		conference.setStart(DateUtil.parse("01.05.2013"));
		conference.setEnd(DateUtil.parse("01.05.2013"));

		request.body(MediaType.APPLICATION_JSON, conference);

		ClientResponse<String> responseObj = request.post(String.class);

		Assert.assertEquals(200, responseObj.getStatus());
		Assert.assertEquals(
				responseObj.getEntity(),
				"{\"id\":1,\"name\":\"JAX\",\"description\":\"ENTER\",\"start\":1367359200000,\"end\":1367359200000,\"talks\":null}");
	}

	@Test
	@InSequence(value = 2)
	@RunAsClient
	public void getConferences(@ArquillianResource URL url) throws Exception {
		ClientRequest request = new ClientRequest(new URL(url + REST_PATH
				+ "/conferences/").toExternalForm());
		request.header("Accept", MediaType.APPLICATION_JSON);

		ClientResponse<String> responseObj = request.get(String.class);

		Assert.assertEquals(200, responseObj.getStatus());
		Assert.assertEquals(
				responseObj.getEntity(),
				"[{\"id\":1,\"name\":\"JAX\",\"description\":\"ENTER\",\"start\":1367359200000,\"end\":1367359200000,\"talks\":[]}]");
	}
}
