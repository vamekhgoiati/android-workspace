package com.vamekh.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static final EntityManagerFactory entityManager;

	static {
		entityManager = Persistence.createEntityManagerFactory("persistenceUnit");
	}

	public static EntityManagerFactory getEntityManager() {
		return entityManager;
	}

}
