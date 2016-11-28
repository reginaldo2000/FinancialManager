package br.com.cary.jpa.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("manegerPU");
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
