package br.com.lopes.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.lopes.model.Usuario;
import br.com.lopes.util.JPAUtil;

public class UsuarioDao {

	
	/**
	 * Busca o usuário na base de acordo com o logon, verificando se o mesmo encontra-se ativo
	 * @param usuario
	 * @return 
	 */
	public boolean ativo(Usuario usuario){
		
		boolean ativo;
		
		EntityManager manager = JPAUtil.getEntityManager();
		String sql = "select a from Usuario a where logon = :logon and ativo = 1";
		List<Usuario> results = manager.createQuery(sql,Usuario.class)
								.setParameter("logon", usuario.getLogon())
								.getResultList();
		ativo = !(results.isEmpty());
		manager.close();
		
		return ativo;
	}

	/**
	 * Validar o usuário de acordo com o Logon e Senha
	 * @param usuario
	 * @return boolean indicando se validou
	 */
	public boolean validarLoginSenha(Usuario usuario){
		
		boolean b;
		
		EntityManager manager = JPAUtil.getEntityManager();
		String sql = "select a from Usuario a where logon = :logon and senha = :senha";
		List<Usuario> results = manager.createQuery(sql,Usuario.class)
								.setParameter("logon", usuario.getLogon())
								.setParameter("senha", usuario.getSenha())
								.getResultList();
		b = !(results.isEmpty());
		manager.close();
		
		Logger.getLogger(UsuarioDao.class.getName()).info("Validar login/senha: [" + usuario.getLogon() + '/' + usuario.getSenha() + "]:" + b);
		
		return b;
	}
	
	/**
	 * Inserir usuário no sistema, cadastrando na base de dados
	 * @param usuario
	 */
	public void insert(Usuario usuario){

		// Registrar usuário na base
		usuario.setDataCriacao(Calendar.getInstance());
		usuario.setAtivo(true);
		usuario.setLogon(usuario.getLogon());
		
		Logger.getLogger(UsuarioDao.class.getName()).info("Registrar usuário: " + usuario.getLogon());
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(usuario);		
		manager.getTransaction().commit();
		manager.close();
		Logger.getLogger(UsuarioDao.class.getName()).info("Usuário registrado: [" + usuario.getLogon() + ']');		
	}
	
	/**
	 * Atualizar os dados do usuário
	 * @param usuario
	 */
	public void update(Usuario usuario){		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(usuario);		
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Deletar usuário do sistema
	 * @param usuario
	 */
	public void delete(Usuario usuario){
		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.remove(usuario);		
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Busca o usuário na base de acordo com o logon 
	 * @param logon
	 * @return usuario
	 */
	public Usuario selecionarUsuarioPorLogon(String logon) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		String sql = "select a from Usuario a where logon = :logon";
		List<Usuario> results = manager.createQuery(sql, Usuario.class)
								.setParameter("logon", logon)
								.getResultList();
		manager.close();
		
		Logger.getLogger(UsuarioDao.class.getName()).info("Selecionar usuario por logon: [" + logon + "]:" + results);		
		
		if (results.isEmpty()) { return null;}		
		return results.get(0);
	}
	
	/**
	 * Busca o usuário na base de acordo com o E-mail 
	 * @param logon
	 * @return usuario
	 */
	public Usuario selecionarUsuarioPorEmail(String email) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		String sql = "select a from Usuario a where email = :email";
		List<Usuario> results = manager.createQuery(sql, Usuario.class)
								.setParameter("email", email)
								.getResultList();
		manager.close();
		
		Logger.getLogger(UsuarioDao.class.getName()).info("Selecionar usuario por e-mail: [" + email + "]:" + results);		
		
		if (results.isEmpty()) { return null;}		
		return results.get(0);
		
	}
	
	/**
	 * Ativar usuário do sistema
	 * @param usuario
	 */
	public void ativarUsuario(Usuario usuario){
		
		usuario.setAtivo(true);
		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(usuario);		
		manager.getTransaction().commit();
		manager.close();
		
	}
	
	/**
	 * Desativar usuário do sistema
	 * @param usuario
	 */
	public void desativarUsuario(Usuario usuario){
		
		usuario.setAtivo(false);
		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(usuario);		
		manager.getTransaction().commit();
		manager.close();
		
	}
	
	/**
	 * Alterar situação de usuário administrador
	 * @param usuario
	 */
	public void setAdministrador(Usuario usuario, boolean administrador){
		usuario.setAdministrador(administrador);
		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(usuario);		
		manager.getTransaction().commit();
		manager.close();
	}
	
	
	public boolean isUsuarioAdministrador(String logon){
		
		Usuario usuario = selecionarUsuarioPorLogon(logon);
						
		return usuario.getAdministrador();
	}
	
	/**
	 * Listagem de todos os usuários do sistema
	 * @return
	 */
	public List<Usuario> listaUsuarios(){
		EntityManager manager = JPAUtil.getEntityManager();
		List<Usuario> usuarios = manager.createQuery("select a from Usuario a",Usuario.class).getResultList();
		manager.close();
		
		return usuarios;
	}
	
}
