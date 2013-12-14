package br.com.lopes.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ConsultaAutomovel {

	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("select a from Automovel a",Automovel.class);
		
		List<Automovel> autos = query.getResultList();
		
		for (Automovel automovel : autos) {
			System.out.println(automovel.getModelo());
		}

	}

}
