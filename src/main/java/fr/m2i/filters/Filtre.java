package fr.m2i.filters;

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

@WebFilter({"/api/news/*", "/api/user/*", "/admin"})
public class Filtre implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		if(httpServletRequest.getSession().getAttribute("access") != null && (boolean) httpServletRequest.getSession().getAttribute("access") == true) {
			
			System.out.println("Filtre ok");
			chain.doFilter(request, response);
		}
		else {
			System.out.println("Filtre non ok redirect");
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//			httpServletResponse.sendRedirect("/Base/accueil");
//			httpServletResponse.setStatus(403);
			httpServletResponse.sendError(403);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
