package br.com.lopes.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;

@Entity
public class ProcessoJuridico {
 
	@SequenceGenerator(name="processoGenerator",sequenceName="seq_processo")
	@Id @GeneratedValue(generator="processoGenerator")
	protected long id;
	 
	private String numeroProcesso;
	
	
	private Calendar data;
	
	@ManyToMany
	@JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={"processosJuridicos_id","advogados_id_pessoafisica"}))
    private List<Advogado> advogados;
	
/*	private TipoProcesso tipoProcesso;
	 
	private List<Pessoa> requerentes;
	 
	private List<Pessoa> requeridos;
	
	
	
	private List<DocumentoProcessoJuridico> documentos;

	private List<SituacaoProcessoJuridico> situacoes;	
	
	
	public SituacaoProcessoJuridico situacaoProcesso() {
		return null;
	}*/
	 
}
 
