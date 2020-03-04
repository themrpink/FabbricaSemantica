package it.uniroma1.fabbricasemantica.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet di base per il progetto, presenta due metodi {@link #doGet(HttpServletRequest, HttpServletResponse)} e {@link #doPost(HttpServletRequest, HttpServletResponse)}
 */
public abstract class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6784574842574L;
	
	
	/**
	 * Gestisce l´eccezione ServletException reindirizzando a una pagina di errore
	 * @throws ServletException 
	 */       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException, ServletException {

			doSomething(request, response);

	}
	/**
	 * Gestisce l´eccezione ServletException reindirizzando a una pagina di errore
	 * @throws ServletException 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
			doSomething(request, response);	

}
/**

 * Metodo che implementa la logica richiesta nelle specifiche del backend.

	 * @param request richiesta del browser
	 * @param response risposta del server
	 * @throws IOException generico errore di IO
	 * @throws ServletException generico errore della servlet

 */
	protected abstract void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
}
