package filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import beans.LoginUserForm;


public class AuthenticationFilter implements Filter {

	private FilterConfig config;
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
			throws IOException, ServletException {
	    
		if (((HttpServletRequest) req).getSession().getAttribute(LoginUserForm.AUTH_KEY) == null) {

			((HttpServletResponse) resp).sendRedirect("../index.html");
	    } 
		else {
	      
			chain.doFilter(req, resp);
	    }
	}

	
	public void init(FilterConfig config) throws ServletException {
	
		this.config = config;
	}

	
	public void destroy() {

		this.config = null;
	}
}