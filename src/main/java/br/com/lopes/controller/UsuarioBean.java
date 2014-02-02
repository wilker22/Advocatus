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

	private boolean visualizaCampoSenha = false;
	
	public UsuarioBean() {
		usuario = new Usuario();
		pessoa = new Pessoa();
		usuario.setPessoa(pessoa);
		pessoa.setUsuario(usuario);
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
	
	public String novoUsuario(){
		this.usuario = new Usuario();
		this.pessoa = new Pessoa();
		usuario.setPessoa(pessoa);
		pessoa.setUsuario(usuario);
		
		return "novo-usuario.xhtml?faces-redirect=true";
	}
	
	/**
	 * Registra um novo usu�rio na base de dados
	 * @param usuario
	 * @return
	 */
	public String gravarNovoUsuario(Usuario usuario){
		
		List<String> mensagens = new ArrayList<String>();
		
		// Usu�rio j� existe na base
		if (usuarioDao.selecionarUsuarioPorLogon(usuario.getLogon()) != null){
			mensagens.add("Este usu�rio j� est� cadastrado no sistema!");
		}
		
		// E-mail j� existe na base
		if (usuarioDao.selecionarUsuarioPorEmail(usuario.getEmail()) != null){
			mensagens.add("Este e-mail j� est� cadastrado no sistema!");
		}
		
		// E-mail n�o � v�lido
		if (!EmailUtil.emailValido(usuario.getEmail())){
			mensagens.add("O e-mail utilizado n�o � v�lido. ["+usuario.getEmail()+ "]!");
		}
		
		
		// Em caso de erro, n�o permitir a cria��o do novo usu�rio
		if (!mensagens.isEmpty()){
			for (String msg : mensagens) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);  
		        FacesContext.getCurrentInstance().addMessage(null, facesMessage);	
			}
			return "novo-usuario.xhtml";
		}
		
		usuarioDao.insert(usuario);

		usuarios = null;
		
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usu�rio "+usuario.getLogon()+" criado com sucesso!", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
		return "lista-usuarios.xhtml?faces-redirect=true";
	}
	
	public String salvar(Usuario usuario){		
		Logger.getLogger(UsuarioBean.class.getName()).info("Salvar altera��es usu�rio:" + usuario.getLogon() + " Nome:"+ usuario.getPessoa().getNome());
		
		String msg = null;
		
		if (visualizaCampoSenha && usuario.getSenha().isEmpty()){
			msg="Informe a senha";
		}
		
		usuarioDao.update(usuario);
		
		visualizaCampoSenha = false;
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Altera��es realizadas com sucesso!", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    	
		return "meus-dados.xhtml";
	}
	
	/**
	 * Recuperar o usu�rio e senha atrav�s do e-mail cadastrado no sistema
	 * @param usuario
	 * @return
	 */
	public String recuperarSenha(Usuario usuario){

		// Usu�rio inexistente
		if (usuarioDao.selecionarUsuarioPorEmail(usuario.getEmail()) == null){
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "E-mail ["+usuario.getEmail()+"] n�o cadastrado no sistema", null);
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

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usu�rio/senha enviados para o e-mail ["+usuario.getEmail()+"]", null);
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
		Logger.getLogger(UsuarioBean.class.getName()).info("Ativar/Desativar usu�rio " + usuario.getLogon() + ' '+ ativo);
		
		if (ativo) {
			usuarioDao.desativarUsuario(usuario);
		}
		else{
			usuarioDao.ativarUsuario(usuario);
		}
			
	}

	public void setAdministrador(Usuario usuario, boolean administrador){
		Logger.getLogger(UsuarioBean.class.getName()).info("Alterar situa��o usu�rio "+ usuario.getLogon() + " administrador =  " + !administrador);
		
		usuarioDao.setAdministrador(usuario, !administrador);
			
	}
	
	public boolean isUsuarioAdministrador(String logon){
		return usuarioDao.isUsuarioAdministrador(logon);
	}
	
	public boolean visualizaCampoSenha() {
		return visualizaCampoSenha;
	}

	public void setVisualizaCampoSenha(boolean visualizaCampoSenha) {
		this.visualizaCampoSenha = visualizaCampoSenha;
	}
}
