package br.com.lopes.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.lopes.model.Automovel;
import br.com.lopes.util.JPAUtil;

@ManagedBean
public class AutomovelBean {
	/* Teste GIT */
	Automovel automovel = new Automovel();
	private List<Automovel> automoveis;

	public Automovel getAutomovel() {
		return this.automovel;
	}

	public void setAutomovel(Automovel automovel) {
		this.automovel = automovel;
	}

	public String salva(Automovel automovel) {
		EntityManager manager = JPAUtil.getEntityManager();

		manager.getTransaction().begin();
		manager.persist(automovel);
		manager.getTransaction().commit();
		manager.close();
		return null;
	}

	public List<Automovel> getAutomoveis() {

		if (this.automoveis == null) {

			EntityManager manager = JPAUtil.getEntityManager();

			Query query = manager.createQuery("select a from Automovel a",
					Automovel.class);

			this.automoveis = query.getResultList();
			manager.close();
		}
		
		return automoveis;
	}
}
