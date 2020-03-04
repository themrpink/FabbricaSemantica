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
@WebServlet(name = "TaskSenseValidationServlet", urlPatterns = "/senseValidation.jsp")
public class TaskSenseValidationServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * riceve i dati relativi al task e li salva nei file XML.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ottieni l´id dell´utente
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("UserID");
		
		String word = (String) request.getParameter("hidden1");
		String example = (String) request.getParameter("hidden2");
		String sense = (String) request.getParameter("hidden3");
		String synsetID = (String)request.getParameter("hiddenSynsetID");
		
		String answer = (String) request.getParameter("sense");
		
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/resultsdata.xml";
		File file = new File(xmlFilePath);		
		XMLWriter writer=null;
		
		try {
			writer = new XMLWriter(file);

			//inserisce i dati in arrivo dal client nel file XML
			
			writer.insertParentTag("task")
				  .insertTag("task_type", "SENSE_VALIDATION")
				  .insertTag("synsetID", synsetID)
				  .insertTag("question_word", word)
				  .insertTag("question_example", example)
				  .insertTag("question_sense", sense)
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
			writer.insertSynsetTag(synsetID, "answer", "EN", answer);
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}	
		response.sendRedirect("redirect.jsp");
	}

}
