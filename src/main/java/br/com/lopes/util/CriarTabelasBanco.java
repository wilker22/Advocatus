package br.com.lopes.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriarTabelasBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");  
        factory.close();  
	}

}
