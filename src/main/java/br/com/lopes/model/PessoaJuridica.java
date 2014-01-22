package br.com.lopes.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"cnpj"}))
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id_pessoajuridica")
public abstract class PessoaJuridica extends Pessoa {
 
	protected String cnpj;
	 
}
 
