package br.com.lopes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Cidade {
 
	@SequenceGenerator(name="cidadeGenerator", sequenceName="seq_cidade")
	@Id @GeneratedValue(generator="cidadeGenerator")
	private long id;
	 
	private String nome;
	
	private String estado;
	
	@OneToMany(mappedBy="cidade")
	private List<Endereco> endereco;
	
}
 
