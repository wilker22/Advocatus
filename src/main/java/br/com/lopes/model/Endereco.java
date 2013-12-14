package br.com.lopes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Endereco {
 
	@SequenceGenerator(name="enderecoGenerator",sequenceName="seq_endereco")
	@Id @GeneratedValue(generator="enderecoGenerator")
	private long id;	
	
	//private Cidade cidade;
	
	private String endereco;
	 
	private int numero;
	
	private String complemento;
	
	@ManyToOne
	private Pessoa pessoa;	 
}
 
