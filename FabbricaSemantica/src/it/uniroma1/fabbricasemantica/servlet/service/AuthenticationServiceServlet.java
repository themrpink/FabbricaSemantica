package it.uniroma1.fabbricasemantica.servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.uniroma1.fabbricasemantica.servlet.BaseServlet;
/**
 * Servlet per controllare se l´utente é connesso.
 *
 */
@WebServlet(name="AuthenticationServiceServlet", urlPatterns="/isLoggedIn.jsp")
public class AuthenticationServiceServlet extends BaseServlet {
	private static final long serialVersionUID = 8484501789787L;

	/**
	 * Se l´utente è connesso invia alla pagina il nome, altrimenti invia un messaggio di errore.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {

		HttpSession session = request.getSession();
		boolean isLoggedIn = session.getAttribute("username") != null;
		if(isLoggedIn) {
			String username =(String) session.getAttribute("username");
			response.getWriter().write(username);
		}
		else
		response.getWriter().write(isLoggedIn + "");
	}

}
