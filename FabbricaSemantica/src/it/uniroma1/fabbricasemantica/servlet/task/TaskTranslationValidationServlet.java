package it.uniroma1.fabbricasemantica.servlet.task;

import java.io.File;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import it.uniroma1.fabbricasemantica.data.XMLWriter;
import it.uniroma1.fabbricasemantica.servlet.BaseServlet;
/**
 * Servlet per ricevere i dati relativi al task e  salvarli nei file XML.
 *
 */
@WebServlet(name = "TaskTranslationValidationServlet", urlPatterns = "/translationValidation.jsp")
public class TaskTranslationValidationServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * riceve i dati relativi al task e li salva nei file XML.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO Salvare i dati
		//TODO reinderizzare a un task a caso.
		String word = (String) request.getParameter("hidden1");
		String description = (String) request.getParameter("hidden2");
		String validation = (String) request.getParameter("hidden3");

		String[] checkboxes = request.getParameterValues("answer");
		String synsetID = (String)request.getParameter("hiddenSynsetID");
		
		//ottieni l´id dell´utente
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("UserID");
	
		
		//crea un´unica stringa con tutte le risposte selezionate dall´user
		String answers ="";
		for(String s : checkboxes) answers+=s+"; ";
		answers = answers.substring(0, answers.length()-2);
		//ricava il path del file XML
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/resultsdata.xml";
		File file = new File(xmlFilePath);
		
		XMLWriter writer=null;
		try {
			writer = new XMLWriter(file);

			//inserisce i dati in arrivo dal client nel file XML
			writer.insertParentTag("task")
			  	  .insertTag("task_type", "TRANSLATION_VALIDATION")
				  .insertTag("synsetID", synsetID)
				  .insertTag("question_word", word)
				  .insertTag("question_sense", description)
				  .insertTag("question_translation", validation)
				  .insertTag("answer", answers)
				  .insertTag("userID", userID)
				  .writeFile();
			
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
		

		//il file in cui vengono salvati i dati sui synsets inseriti dall´utente
		File fileSynsetXML= new File(request.getServletContext().getRealPath("/")+"/WEB-INF/synsets.xml");
		writer=null;
		try {
			//viene istanziato un nuovo XMLWriter e vengono inseriti i dati nel file specificato
			writer = new XMLWriter(fileSynsetXML);
			writer.insertSynsetTag(synsetID, "word", "IT", answers);
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}	
		
		
		response.sendRedirect("redirect.jsp");
	}

}
