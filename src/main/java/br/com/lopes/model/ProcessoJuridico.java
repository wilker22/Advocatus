package br.com.lopes.model;

import java.util.Calendar;
import java.util.List;

public class ProcessoJuridico {
 
	private long id;
	 
	private String nrProcesso;
	 
	private Calendar data;
	 
	private TipoProcesso tipoProcesso;
	 
	private List<Pessoa> requerentes;
	 
	private List<Pessoa> requeridos;
	
	private List<Advogado> advogados;
	
	private List<DocumentoProcessoJuridico> documentos;

	private List<SituacaoProcessoJuridico> situacoes;	
	
	
	public SituacaoProcessoJuridico situacaoProcesso() {
		return null;
	}
	 
}
 
