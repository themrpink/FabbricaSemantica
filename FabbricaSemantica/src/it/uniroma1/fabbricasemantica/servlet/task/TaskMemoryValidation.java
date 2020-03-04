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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.uniroma1.fabbricasemantica.data.XMLWriter;
import it.uniroma1.fabbricasemantica.servlet.BaseServlet;

/**
 * Servlet per ricevere i dati relativi al task e  salvarli nei file XML.
 *
 */
@WebServlet(name = "TaskMemoryValidation Servlet", urlPatterns = "/memoryValidation.jsp")
public class TaskMemoryValidation  extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * riceve i dati relativi al task e li salva nei file XML.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ottieni l´id dell´utente e le risposte
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("UserID");
		String answers = (String) request.getParameter("task2");
		System.out.println(answers);
		
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/resultsdata.xml";
		File file = new File(xmlFilePath);	
		XMLWriter writer=null;	
		File fileSynsetXML= new File(request.getServletContext().getRealPath("/")+"/WEB-INF/synsets.xml");
		
		try {			
			String answer = "<answers>"+answers+"</answers>";
			//viene istanziato un nuovo XMLWriter e vengono inseriti i dati nel file specificato
			writer = new XMLWriter(file);
			NodeList nodeList = writer.parseStringToXML(answer);
			
			//crea un XMLWriter per il file synsets.xml 
			XMLWriter r = new XMLWriter(fileSynsetXML);
			String synsID = "";
			String word ="";
			String gloss ="";
			
			for (int i=0; i<nodeList.getLength(); i++) {			
				Node child = nodeList.item(i);
				String name = child.getNodeName();
				switch(name) {
				case "synset_word": synsID = child.getTextContent(); 
									//resetta i valori di word e gloss
									word=""; 
									gloss=""; 
									break;
				case "word": word=child.getTextContent();  break;
				case "gloss" : gloss=child.getTextContent(); 
							   //salva la risposta attuale in synsets.xml
							   r.insertSynsetTag(synsID, "gloss", "EN", gloss);
							   //salva la risposta attuale in resultsdata.xml
							   writer.insertParentTag("task")
							   		 .insertTag("task_type", "MEMORY_VALIDATION")
							   		 .insertTag("synsetID", synsID)
							   		 .insertTag("question_word", word)				   
							   		 .insertTag("answer", gloss)
							   		 .insertTag("userID", userID)
							   		 .writeFile();
							   break;
				default: break;
				} 
				
			}	
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}	
				
		response.sendRedirect("redirect.jsp");
	}
}
