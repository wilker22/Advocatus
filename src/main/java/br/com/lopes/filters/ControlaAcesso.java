package br.com.lopes.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.lopes.controller.AutenticacaoBean;

@WebFilter(servletNames={"Faces Servlet"})
public class ControlaAcesso implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		if (session.getAttribute("usuario") != null
				|| req.getRequestURI().endsWith("pages/login.xhtml")
				|| req.getRequestURI().endsWith("pages/novo-usuario.xhtml")
				|| req.getRequestURI().endsWith("pages/recuperar-usuario-senha.xhtml")
				|| req.getRequestURI().endsWith("template.xhtml")
				|| req.getRequestURI().contains("logo_48x48.png")
				|| req.getRequestURI().contains("user.png")
				|| req.getRequestURI().contains("primefaces.css")
				|| req.getRequestURI().contains("theme.css")
				|| req.getRequestURI().contains("jquery.js")
				|| req.getRequestURI().contains("jquery-plugins.js")
				|| req.getRequestURI().contains("primefaces.js")
				|| req.getRequestURI().contains("style.css")
				) {
			//Logger.getLogger(ControlaAcesso.class.getName()).info("URL:" + req.getRequestURL());			
			//Logger.getLogger(ControlaAcesso.class.getName()).info(true);
			chain.doFilter(request, response);
		} else {
			//Logger.getLogger(ControlaAcesso.class.getName()).info("URL:" + req.getRequestURL());
			//Logger.getLogger(ControlaAcesso.class.getName()).info(false);			
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("login.xhtml");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}	

}
