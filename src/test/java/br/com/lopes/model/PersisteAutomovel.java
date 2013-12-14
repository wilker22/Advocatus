package br.com.lopes.model;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersisteAutomovel {

	
	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
		EntityManager manager = factory.createEntityManager();
		
		Automovel auto = new Automovel();
		
		auto.setAnoFabricacao(2013);
		auto.setModelo("Ferrari");
		auto.setObservacoes("Zerado");
		
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		manager.persist(auto);
		tx.commit();
		
		manager.close();
		factory.close();

	}
}
