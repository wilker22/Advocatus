package br.com.lopes.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.lopes.model.Usuario;
import br.com.lopes.util.JPAUtil;

@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable{

	private Usuario usuario = new Usuario();
	
	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public String efetuarLogon(Usuario usuario){	
		
		boolean autenticacao;
		String msg = null;
		
		usuario.setLogon(usuario.getLogon());
		
		// Verificar se usuário existe
		UsuarioBean usuarioBean = new UsuarioBean();
		if (!(autenticacao = usuarioBean.existe(usuario))){
			msg = "Usuário não cadastrado!";
		}
		else{
			
			//Autenticar usuário
			EntityManager manager = JPAUtil.getEntityManager();
			String sql = "select a from Usuario a where logon = :logon and senha = :senha and ativo = 1";
			Query query = manager.createQuery(sql,Usuario.class);
			query.setParameter("logon", usuario.getLogon());
			query.setParameter("senha", usuario.getSenha());

			@SuppressWarnings("unchecked")
			List<Usuario> results = query.getResultList();

			autenticacao = !(results.isEmpty());
			manager.close();
			Logger.getLogger(AutenticacaoBean.class.getName()).info("Autenticação usuário [" + usuario.getLogon() + '/' + usuario.getSenha() + "]:" + autenticacao);
			msg = "Usuário/senha incorretos!";
		}
		
		
		if (!autenticacao) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);  
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        
			return "login?faces-redirect=true";
		}else{
			
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = ( HttpSession )ec.getSession(false);
			session.setAttribute("usuario", usuario.getLogon());
			return "menu?faces-redirect=true";
		}
	}
	
	public String logout(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession)ec.getSession(false);
		
		Logger.getLogger(AutenticacaoBean.class.getName()).info("Logout: "+ session.getAttribute("usuario"));		
		session.removeAttribute("usuario");

		return "login?faces-redirect=true";
	}

	
}
