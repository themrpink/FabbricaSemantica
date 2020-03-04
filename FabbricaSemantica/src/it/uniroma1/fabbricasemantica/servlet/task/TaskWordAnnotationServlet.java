package it.uniroma1.fabbricasemantica.servlet.task;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
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
@WebServlet(name = "TaskWordAnnotationServlet", urlPatterns = "/wordAnnotation.jsp")
public class TaskWordAnnotationServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * riceve i dati relativi al task e li salva nei file XML.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Salvare i dati
		//ottieni l´id dell´utente
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("UserID");
		
		String description = (String) request.getParameter("hidden1");
		String answer = (String) request.getParameter("answer");
		String synsetID = (String)request.getParameter("hiddenSynsetID");
		
		//ricava il path del file XML
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/resultsdata.xml";
		File file = new File(xmlFilePath);
		
		XMLWriter writer=null;
		try {
			writer = new XMLWriter(file);
			writer.insertParentTag("task")
			  	  .insertTag("task_type", "WORD_ANNOTATION")
				  .insertTag("synsetID", synsetID)
				  .insertTag("question_sense", description)
				  .insertTag("answer", answer)
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
			writer.insertSynsetTag(synsetID, "word", "EN", answer);
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}	
		
		response.sendRedirect("redirect.jsp");
		
		
	}

}
