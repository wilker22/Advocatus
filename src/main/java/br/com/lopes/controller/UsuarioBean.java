package br.com.lopes.controller;

import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	public List<Usuario> getUsuario(String logon) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		String sql = "select a from Usuario a where logon = :logon";
		Query query = manager.createQuery(sql,Usuario.class);
		query.setParameter("logon", logon);

		@SuppressWarnings("unchecked")
		List<Usuario> results = query.getResultList();
		
		Logger.getLogger(UsuarioBean.class.getName()).info("Consultar USUARIO: [" + logon + "]:" + results);		
		
		return results;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	

	/**
	 * Adicionar um novo usuário na base de dados
	 * @param usuario
	 * @return
	 */
	public String novoUsuario(Usuario usuario){
		
		String msg = null;
		
		// Usuário já existe na base
		if (this.existe(usuario)){
			msg = "Este usuário já está cadastrado no sistema, por favor escolha outro.";
		}
		
		// E-mail não é válido
		if (!EmailUtil.emailValido(usuario.getEmail())){
			msg = "O e-mail utilizado não é válido. ["+usuario.getEmail()+ ']';
		}
		
		
		// Senhas não coincidem		
		if (!usuario.getSenha().equals(usuario.getSenhaConfirmacao())){
			msg = "As senhas digitadas não coincidem";
		}
		
		// Em caso de erro, não permitir a criação do novo usuário
		if (msg != null){
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);  
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        
			return "novo-usuario.xhtml";
		}
		
		// Registrar usuário na base
		usuario.setDataCriacao(Calendar.getInstance());
		usuario.setAtivo(1);
		usuario.setLogon(usuario.getLogon());
		
		Logger.getLogger(AutenticacaoBean.class.getName()).info("Registrar usuário: " + usuario.getLogon());
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(usuario);		
		manager.getTransaction().begin();
		manager.getTransaction().commit();
		manager.close();
		Logger.getLogger(AutenticacaoBean.class.getName()).info("Usuário registrado: [" + usuario.getLogon() + ']');
		
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário "+usuario.getLogon()+" criado", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
		return "login.xhtml?faces-redirect=true";
	}
	
	
	/**
	 * Verifica se o usuário está cadastrado na base
	 * @param usuario
	 * @return
	 */
	public boolean existe(Usuario usuario){

		List<Usuario> results = getUsuario(usuario.getLogon());

		boolean existe = !results.isEmpty();
		
		return existe;
	}

	
	
	public String recuperarSenha(Usuario usuario){
		
		// Usuário inexistente
		if (!existe(usuario)){
			return null;
		}else{
			
			try {
				EmailUtil.enviarEmail(usuario.getEmail());
			} catch (MessagingException e) {
				Logger.getLogger(UsuarioBean.class.getName()).error("Falha ao enviar e-mail para o usuário: ["+ usuario.getLogon() + "] email: ["+ usuario.getEmail()+']');
			}
		}
		return null;
	}
	
}
