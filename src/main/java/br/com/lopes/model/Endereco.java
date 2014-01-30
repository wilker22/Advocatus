package br.com.lopes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

@Entity
public class Endereco {
 
	@SequenceGenerator(name="enderecoGenerator",sequenceName="seq_endereco")
	@Id @GeneratedValue(generator="enderecoGenerator")
	private long id;	
	
	@Column(nullable=false)
	private String endereco;
	
	private String cep;
	
	private int numero;
	
	@Index(name="AK_ENDERECO_COMPLEMENTO")
	private String complemento;
	
	@ForeignKey(name="FK_ENDERECO_PESSOA")
	@ManyToOne
	private Pessoa pessoa;
	
	@ForeignKey(name="FK_ENDERECO_CIDADE")
	@ManyToOne
	private Cidade cidade;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
}
 
