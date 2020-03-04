package it.uniroma1.fabbricasemantica.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.uniroma1.fabbricasemantica.servlet.BaseServlet;

/**
 * Esegue il logout dell´utente
 * @author themr
 *
 */
@WebServlet(name="LogoutServlet", urlPatterns="/logout.jsp")
public class LogoutServlet extends BaseServlet {
	private static final long serialVersionUID = 8484501789787L;

	/**
	 * Per eseguire il logout setta tutti gli attributi utente della sessione a null.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {
		 
		HttpSession session = request.getSession();
		
		//setta il valore di username a null e fa il redirect verso login.html
		session.setAttribute("username", null);
		session.setAttribute("password", null);
		session.setAttribute("email", null);
		session.setAttribute("id", null);
		session.setAttribute("motherLanguages", null);
		session.setAttribute("lang", null);
		response.sendRedirect("login.html");
	}

}
