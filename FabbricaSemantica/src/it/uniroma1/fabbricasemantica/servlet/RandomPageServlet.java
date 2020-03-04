package it.uniroma1.fabbricasemantica.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma1.fabbricasemantica.data.StandardTask;
import it.uniroma1.fabbricasemantica.data.Task;


/**
 * Servlet per il reindirizzamento a una pagina casuale
 *
 */
@WebServlet(name="RandomPageServlet", urlPatterns="/redirect.jsp")
public class RandomPageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Reindirizza l´utente a una pagina casuale del progetto
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {
 	
		Task[] tskArray= StandardTask.values();
		
		int i=tskArray.length;
		Random r = new Random();
		int n = r.nextInt(i);
		
		String page = tskArray[n].getTaskID().toLowerCase();
		
		//reindirizza a una task casuale
		response.sendRedirect(request.getContextPath() + "/"+page+".html");

	}

}
