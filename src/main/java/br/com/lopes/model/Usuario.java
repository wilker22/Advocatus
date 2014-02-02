package br.com.lopes.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;


@Entity
@Table(uniqueConstraints={
			@UniqueConstraint(columnNames={"logon"}, name="UK_USUARIO_01"),
			@UniqueConstraint(columnNames={"email"}, name="UK_USUARIO_02")})							
public class Usuario{
 
	@SequenceGenerator(name="usuarioGenerator",sequenceName="seq_usuario")
	@Id @GeneratedValue(generator="usuarioGenerator")
	private long id;
	
	@Column(nullable=false)
	@NotNull(message="Informe o Usuário")
	private String logon;
	
	@Column(nullable=false)
	@NotNull(message="Informe o E-mail")
	private String email;
	
	@Column(nullable=false)	
	private String senha;
	
	@Column(nullable=false)
	private boolean ativo;
	
	@Column(nullable=false)
	private boolean administrador;
	
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
		this.logon = logon.trim().toUpperCase();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha= senha ;
	}	
	
	
	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public void setPessoa(Pessoa pessoa){
		this.pessoa = pessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
}
 
