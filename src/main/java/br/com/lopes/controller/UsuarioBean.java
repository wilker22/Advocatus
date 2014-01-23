package br.com.lopes.controller;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.lopes.model.Pessoa;
import br.com.lopes.model.Usuario;
import br.com.lopes.util.EmailUtil;
import br.com.lopes.util.JPAUtil;


@ManagedBean
@SessionScoped
public class UsuarioBean {
	
	private Usuario usuario;
	private Pessoa pessoa;
	
	public UsuarioBean() {
		usuario = new Usuario();
		pessoa = new Pessoa();
		usuario.setPessoa(pessoa);
	}

	public Usuario getUsuario(){
		return usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	

	/**
	 * Adicionar um novo usu�rio na base de dados
	 * @param usuario
	 * @return
	 */
	public String novoUsuario(Usuario usuario){
		
		// Usu�rio j� existe na base
		if (this.existe(usuario)){
			return "novo-usuario.xhtml?faces-redirect=true&amp;erro=1";
		}
		
		// E-mail n�o � v�lido
		if (!EmailUtil.emailValido(usuario.getLogon())){
			return "novo-usuario.xhtml?faces-redirect=true&amp;erro=2";
		}
		
		// Registrar usu�rio na base
		usuario.setDataCriacao(Calendar.getInstance());
		usuario.setAtivo(1);
		
		Logger.getLogger(AutenticacaoBean.class.getName()).info("Registrar usu�rio: " + usuario.getLogon());
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(usuario);		
		manager.getTransaction().begin();
		manager.getTransaction().commit();
		manager.close();
		Logger.getLogger(AutenticacaoBean.class.getName()).info("Usu�rio: " + usuario + usuario.getLogon() + " registrado");
		
		return "login.xhtml?faces-redirect=true";
	}
	
	
	/**
	 * Verifica se o usu�rio est� cadastrado na base
	 * @param usuario
	 * @return
	 */
	public boolean existe(Usuario usuario){

		EntityManager manager = JPAUtil.getEntityManager();

		Logger.getLogger(AutenticacaoBean.class.getName()).info("Buscar o usu�rio: " + usuario.getLogon() + " no DB");
		String sql = "select a from Usuario a where logon = :logon";
		Query query = manager.createQuery(sql,Usuario.class);
		query.setParameter("logon", usuario.getLogon().trim().toUpperCase());
		
		@SuppressWarnings("unchecked")
		List<Usuario> results = query.getResultList();
		
		return !results.isEmpty();
	}
	
	public String recuperarSenha(Usuario usuario){
		
		// Usu�rio inexistente
		if (!existe(usuario)){
			return null;
		}else{
			
			try {
				EmailUtil.enviarEmail(usuario.getLogon());
			} catch (MessagingException e) {
				Logger.getLogger(UsuarioBean.class.getName()).error("Falha ao enviar e-mail para o usu�rio"+ usuario.getLogon());
			}
		}
		return null;
	}
	
}
