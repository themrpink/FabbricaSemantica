package it.uniroma1.fabbricasemantica.servlet.user;


import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.uniroma1.fabbricasemantica.data.XMLWriter;
import it.uniroma1.fabbricasemantica.servlet.BaseServlet;

/**
 * Servlet dell´utente, recupera e restituisce i dati sull´utente.
 *
 */
@WebServlet(name="UserAccountServlet", urlPatterns="/user.jsp")
public class User  extends BaseServlet{

	private static final long serialVersionUID = -13242584409950557L;

	/**
	 * Recupera i dati utente e tutte le task che ha già completato e le invia alla pagina HTML in formato JSON.
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException{
		
		HttpSession session = request.getSession();
		String username= (String) session.getAttribute("username");
		String email = (String) session.getAttribute("email");
		String ID = (String) session.getAttribute("UserID");
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/resultsdata.xml";
		File file = new File(xmlFilePath);

        try {
        	
        	XMLWriter writer = new XMLWriter(file);        
	        NodeList nodeList = writer.getElementsByTagName("task");
	        int l = nodeList.getLength();
	        String results = "";	        
	        
	        for(int i=0; i<l; i++) {
	        	Element e = (Element) nodeList.item(i);	   
	        	if(e.getElementsByTagName("userID").item(0).getTextContent().equals(ID)) {
	        		for(int j=0; j<e.getChildNodes().getLength(); j++) 
	        			results+=e.getChildNodes().item(j).getNodeName()+": "+e.getChildNodes().item(j).getTextContent()+"<br>";	        			        	
	        		if(i<l-1) results+="<end_task>";
	        	}
	        }
		String json = "{\"username\":"+"\""+username+"\""+",\"email\":"+"\""+email+"\""+",\"results\":"+"\""+results+"\""+"}";		
		response.getWriter().write(json);
		} 
        
		catch (ParserConfigurationException | SAXException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
		}
	}
}

