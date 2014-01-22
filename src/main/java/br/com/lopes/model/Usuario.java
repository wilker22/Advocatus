package br.com.lopes.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"logon"}, name="UK_USUARIO_01"))
public class Usuario implements Serializable{
 
	@SequenceGenerator(name="usuarioGenerator",sequenceName="seq_usuario")
	@Id @GeneratedValue(generator="usuarioGenerator")
	private long id;
	
	@Column(nullable=false)
	@NotNull(message="Informe o Usuário")
	private String logon;
	
	@Column(nullable=false)	
	@NotNull(message="Informe a Senha")
	private String senha;
	
	@Column(nullable=false)
	private int ativo;
	
	@Column(nullable=false, updatable=false)
	private Calendar dataCriacao;

	@ForeignKey(name="FK_USUARIO_PESSOA")
	@OneToOne(cascade=CascadeType.ALL)
	private Pessoa pessoa;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	
	public void setSenha(String senha) {
		this.senha= senha ;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setPessoa(Pessoa pessoa){
		this.pessoa = pessoa;
		pessoa.setUsuario(this);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
}
 
