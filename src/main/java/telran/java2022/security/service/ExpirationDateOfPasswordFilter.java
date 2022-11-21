package telran.java2022.security.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java2022.accounting.dao.UserAccountRepository;
import telran.java2022.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
//@Order(10)
public class ExpirationDateOfPasswordFilter implements Filter {

	final UserAccountRepository repository;
	//private static final int EXPIRATION_DATE = 60;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			UserAccount user = repository.findById(request.getUserPrincipal().getName()).orElse(null);
			if (user.getPasswordExpDate().isBefore(LocalDate.now())) {
				response.sendError(403, "Password duration expired.");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return !("POST".equalsIgnoreCase(method) && servletPath.matches("/account/register/?")
				|| (servletPath.matches("/forum/posts(/\\w+)+/?")) 
				|| (servletPath.matches("/account/password")));
	}

}
