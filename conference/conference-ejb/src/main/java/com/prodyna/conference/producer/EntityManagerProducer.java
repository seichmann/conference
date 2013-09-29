package com.prodyna.conference.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

	@PersistenceContext
	private EntityManager em;

	@Produces
	public EntityManager produceEntityManager() {
		return em;
	}

}
