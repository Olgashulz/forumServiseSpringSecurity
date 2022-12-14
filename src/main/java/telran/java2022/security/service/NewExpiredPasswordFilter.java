package telran.java2022.security.service;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

@Service
public class NewExpiredPasswordFilter extends GenericFilterBean {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		
		System.out.println("principal: " + principal);
		if (principal != null && checkEndPoint(request.getMethod(), request.getServletPath())) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						if(!user.isCredentialsNonExpired()) {
							response.sendError(403, "password expired");
							return;
						}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return !("PUT".equalsIgnoreCase(method) && path.matches("/account/password/?"));
				
	}

}
