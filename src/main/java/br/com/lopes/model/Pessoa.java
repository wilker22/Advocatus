package br.com.lopes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Pessoa {
 
	@SequenceGenerator(name="pessoaGenerator",sequenceName="seq_pessoa")
	@Id @GeneratedValue(generator="pessoaGenerator")
	protected long id;
	 
	protected String nome;
	
	@OneToMany(mappedBy="pessoa")
	protected List<Endereco> enderecos;
	
	/*@OneToMany
	protected List<Email> emails;*/
	
	 
}
 
