package br.com.lopes.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"cpf"}))
@PrimaryKeyJoinColumn(name="id_pessoafisica")
public abstract class PessoaFisica extends Pessoa {
	
	protected String cpf;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar dataNascimento;
	 
}
 
