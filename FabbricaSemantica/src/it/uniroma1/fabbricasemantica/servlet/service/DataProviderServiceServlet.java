package it.uniroma1.fabbricasemantica.servlet.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import it.uniroma1.fabbricasemantica.data.DataProvider;
import it.uniroma1.fabbricasemantica.data.StandardDataProvider;
import it.uniroma1.fabbricasemantica.data.StandardTask;
import it.uniroma1.fabbricasemantica.data.Task;
import it.uniroma1.fabbricasemantica.servlet.BaseServlet;

/**
 * Servlet che ottiene e invia i dati per la task richiesta dall´utente.
 *
 */
@WebServlet(name="DataProviderServlet", urlPatterns="/jsonData.jsp")
public class DataProviderServiceServlet extends BaseServlet {
	private static final long serialVersionUID = 8783416660707020469L;
	
	/**
	 * Mappa in cui a ogni {@link Task} viene assegnato il corrispondente ID.
	 */
	protected Map<String, Task> tasks;
	private DataProvider<String> dataProvider ;

	/**
	 * Inizializza la servlet istanziando come campo un dataProvider, in modo che non debba essere istanziato ogni 
	 * volta che viene chiamata la servlet.
	 */
	@Override 
	public void init() throws ServletException {
		super.init();
		String path = getServletContext().getRealPath("/");
		dataProvider = new StandardDataProvider(path).setMaxNumbQuestions(10);
		tasks = Arrays.stream(StandardTask.values()).collect(Collectors.toMap(Task::getTaskID, s -> s));		
	}

	/**
	 * interrora il {@link DataProvider} e ne ottiene i dati da inviare alla pagina del task.
	 * @param request richiesta del browser
	 * @param response risposta del server
	 * @throws IOException generico errore di input
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {

		
		/*
		 * I dati sulle lingue dell´utente non vengono utilizzati perchè in questa versione del progetto
		 * le lingue sono impostate di default nello StandardDataProvider, dal momento che il WordNet 
		 * utilizzato fornisce dati solo in inglese. Utilizzando BabelNet nello standardDataProvider al posto del WordNet
		 * è invece possibile utilizzare per i task tutte le lingue che questo supporta.
		 *
			HttpSession session = request.getSession();
			String languages = (String) session.getAttribute("motherLanguages");
			String [] motherLanguages = languages.replace("[", "").replace("]", "").split(",");
			String lang = (String) session.getAttribute("lang");
		 */
		 
		
		String sTask = (String) request.getParameter("task");
		Task task = tasks.get(sTask);			
		String json = dataProvider.getData(task);	
		
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}
	

	}

}
