package br.com.lopes.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.lopes.util.JPAUtil;

public class ExcluiAutomovel {

	public static void main(String[] args) {
		EntityManager manager = JPAUtil.getEntityManager();
		
		EntityTransaction tx = manager.getTransaction();
		
		Automovel auto = manager.getReference(Automovel.class, Long.valueOf(1) );
		
		tx.begin();
		manager.remove(auto);
		tx.commit();
	}

}
