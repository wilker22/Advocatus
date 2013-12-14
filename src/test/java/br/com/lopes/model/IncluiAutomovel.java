package br.com.lopes.model;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.lopes.util.JPAUtil;

public class IncluiAutomovel {

	
	public static void main(String[] args) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		
		Automovel auto = new Automovel();
		
		auto.setAnoFabricacao(2013);
		auto.setModelo("Ferrari");
		auto.setObservacoes("Zerado");
		
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		manager.persist(auto);
		tx.commit();
		
		manager.close();

	}
}
