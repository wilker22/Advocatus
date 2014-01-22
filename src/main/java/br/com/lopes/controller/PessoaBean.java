package br.com.lopes.controller;

import javax.faces.bean.ManagedBean;

import br.com.lopes.model.Pessoa;

@ManagedBean
public class PessoaBean {

	Pessoa pessoa = new Pessoa();

	public Pessoa getPessoa() {
		return pessoa;
	}

		
}
