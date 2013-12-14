package br.com.lopes.model;

public class Advogado extends PessoaFisica implements Advogar {
 
	private String nrOAB;
	 
	private ProcessoJuridico processoJuridico;
	 
	/**
	 *@see Advogar#produzProcesso()
	 */
	public void produzProcesso() {
	}
	 
}
 
