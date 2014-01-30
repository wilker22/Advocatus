package br.com.lopes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(uniqueConstraints=@UniqueConstraint(columnNames={"email"}, name="UK_PESSOA_01"))
public class Pessoa {
 
	@SequenceGenerator(name="pessoaGenerator",sequenceName="seq_pessoa")
	@Id @GeneratedValue(generator="pessoaGenerator")
	protected long id;
	

	@NotNull(message="Informe o Nome")
	protected String nome;
	
	@OneToMany(mappedBy="pessoa")
	protected List<Endereco> enderecos;
	
	@OneToOne(mappedBy="pessoa", cascade=CascadeType.ALL)
	protected Usuario usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	


	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		//usuario.setPessoa(this);
	}
	
	
	 
}
 
