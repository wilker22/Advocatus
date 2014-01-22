package br.com.lopes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ManyToAny;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"numeroOAB"}))
public class Advogado extends PessoaFisica implements Advogar {
 
	private String numeroOAB;
	
	@ManyToMany(mappedBy="advogados")
	private List<ProcessoJuridico> processosJuridicos;
	

	@ManyToOne
	private EscritorioAdvocacia escritorio;
	
	
	public void produzProcesso() {
	}
	 
}
 
