package br.com.lopes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_pessoajuridica")
public class EscritorioAdvocacia extends PessoaJuridica implements Advogar {
 
	@OneToMany(mappedBy="escritorio")
	private List<Advogado> advogados;
	 
	public void produzProcesso() {
	}
	 
}
 
