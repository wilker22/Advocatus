package br.com.lopes.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.com.lopes.dao.UsuarioDao;
import br.com.lopes.model.Pessoa;
import br.com.lopes.model.Usuario;
import br.com.lopes.util.EmailUtil;
import br.com.lopes.util.JPAUtil;


@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {
	
	private Usuario usuario;
	private Pessoa pessoa;
	private UsuarioDao usuarioDao;
	private List<Usuario> usuarios;

	public UsuarioBean() {
		usuario = new Usuario();
		pessoa = new Pessoa();
		usuario.setPessoa(pessoa);		
		usuarioDao = new UsuarioDao();
	}

	public Usuario getUsuario(){
		return usuario;
	}
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Usuario selecionarUsuarioPorLogon(String logon){
		if (usuario != null && usuario.getLogon() != null && usuario.getLogon().equals(logon)){
			return usuario;
		}else{
			usuario = usuarioDao.selecionarUsuarioPorLogon(logon);	
		}		
		
		return usuario;
	}
	
	/**
	 * Adicionar um novo usuário na base de dados
	 * @param usuario
	 * @return
	 */
	public String novoUsuario(Usuario usuario){
		
		List<String> mensagens = new ArrayList<String>();
		
		// Usuário já existe na base
		if (usuarioDao.selecionarUsuarioPorLogon(usuario.getLogon()) != null){
			mensagens.add("Este usuário já está cadastrado no sistema!");
		}
		
		// E-mail já existe na base
		if (usuarioDao.selecionarUsuarioPorEmail(usuario.getEmail()) != null){
			mensagens.add("Este e-mail já está cadastrado no sistema!");
		}
		
		// E-mail não é válido
		if (!EmailUtil.emailValido(usuario.getEmail())){
			mensagens.add("O e-mail utilizado não é válido. ["+usuario.getEmail()+ "]!");
		}
		
		// Senhas não coincidem		
		if (!usuario.getSenha().equals(usuario.getSenhaConfirmacao())){
			mensagens.add("As senhas digitadas não coincidem!");
		}
		
		
		// Em caso de erro, não permitir a criação do novo usuário
		if (!mensagens.isEmpty()){
			for (String msg : mensagens) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);  
		        FacesContext.getCurrentInstance().addMessage(null, facesMessage);	
			}
			return "novo-usuario.xhtml";
		}
		
		usuarioDao.insert(usuario);

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário "+usuario.getLogon()+" criado com sucesso!", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
		return "login.xhtml?faces-redirect=true";
	}
	
	public String salvar(Usuario usuario){		
		Logger.getLogger(UsuarioBean.class.getName()).info("Salvar alterações usuário:" + usuario.getLogon() + " Nome:"+ usuario.getPessoa().getNome());				
		usuarioDao.update(usuario);
		
		return "meus-dados.xhtml";
	}
	
	/**
	 * Recuperar o usuário e senha através do e-mail cadastrado no sistema
	 * @param usuario
	 * @return
	 */
	public String recuperarSenha(Usuario usuario){

		// Usuário inexistente
		if (usuarioDao.selecionarUsuarioPorEmail(usuario.getEmail()) == null){
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "E-mail ["+usuario.getEmail()+"] não cadastrado no sistema", null);
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);	        
	        return "recuperar-usuario-senha.xhtml";	        
		}
		
		
		try {
			EmailUtil.enviarEmail(usuario.getEmail());
		} catch (MessagingException e) {
			String msg = "Falha ao enviar e-mail para: ["+ usuario.getEmail()+"]. Tente novamente!"; 
			Logger.getLogger(UsuarioBean.class.getName()).warn(msg + " Erro:"+ e.getMessage());
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        return "recuperar-usuario-senha.xhtml";
		}

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário/senha enviados para o e-mail ["+usuario.getEmail()+"]", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return "login.xhtml-?faces-redirect=true";
	}
	
	
	public List<Usuario> listaUsuarios(){
		if (usuarios == null){
			usuarios = usuarioDao.listaUsuarios();
		}
		return usuarios;
	}
	
	public void ativarDesativarUsuario(Usuario usuario, boolean ativo){
		Logger.getLogger(UsuarioBean.class.getName()).info("Ativar/Desativar usuário " + usuario.getLogon() + ' '+ ativo);
		
		if (ativo) {
			usuarioDao.desativarUsuario(usuario);
		}
		else{
			usuarioDao.ativarUsuario(usuario);
		}
			
	}
}
