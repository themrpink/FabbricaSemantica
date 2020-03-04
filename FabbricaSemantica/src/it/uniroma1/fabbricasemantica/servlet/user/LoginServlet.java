package it.uniroma1.fabbricasemantica.servlet.user;

import java.io.File;
import java.io.FileNotFoundException;
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
 * Servlet per il login dell´utente. Verifica che i dati sono corretti e connette l´utente o restituisce un messaggio
 * d´errore
 *
 */
@WebServlet(name="LoginServlet", urlPatterns="/login.jsp")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 8484501789787L;

	/**
	 * Esegue il login dell´utente. Verifica che i dati siano corretti e connette l´utente o restituisce un messaggio
	 * d´errore
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		// ridirige l'utente alla pagina home.html
		boolean b = true; 
		String email = request.getParameter("email").toLowerCase();
		String password = request.getParameter("password");		

		request.setAttribute("email", email);
		request.setAttribute("password", password);
		
		String url = "/homepage.html";
		response.setContentType("text/html");
		
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/userdata.xml";
		File file = new File(xmlFilePath);
		
		////////////////////////////XML/////////////////////////////////////
		try {
			 
            XMLWriter xmlWriter = new XMLWriter(file);
            NodeList nl = xmlWriter.getElementsByTagName("user");
            int i = nl.getLength();
            b=false;
        
            for(int j=0; j<i;j++) {
            	Element nodoAttuale = (Element) nl.item(j);
            	
            	//Ottiene l'Element che contiene l'email
            	Element emailNodeText = (Element) nodoAttuale.getElementsByTagName("email").item(0);
       
            	if(emailNodeText.getTextContent().equals(email)) {
            	
            		//ottiene l'Element che contiene la password 
            		Element passwordNode = (Element) nodoAttuale.getElementsByTagName("password").item(0);
            		
            		//se la password salvata coincide con la password inserita effettua il login
            		if(passwordNode.getTextContent().equals(password)) {
            			
            			//salva i dati dell´user connesso nella sessione
            			HttpSession session = request.getSession();
            			session.setAttribute("email",email);
            			session.setAttribute("password", password);
            			
            			//ottieni username
            			Element usernameNode = (Element) nodoAttuale.getElementsByTagName("username").item(0);
            			session.setAttribute("username", usernameNode.getTextContent());
            			
            			//ottieni UserID
            			String UserID = nodoAttuale.getAttribute("id");
            			session.setAttribute("UserID", UserID);
           
            			//lingue madre dell'utente
            			Element motherLanguages = (Element) nodoAttuale.getElementsByTagName("motherLanguages").item(0);
            			session.setAttribute("motherLanguages", motherLanguages.getTextContent());  
            			
            			//altre lingue con rispettivo livello
            			Element lang = (Element) nodoAttuale.getElementsByTagName("lang").item(0);
            			session.setAttribute("lang", lang.getTextContent());
            			
            			b=true;
            			getServletContext().getRequestDispatcher(url).forward(request, response);
            			break;
            		}
            	}           
            }
            if(!b)
            	//se l'utente non è presente nel file invia un messaggio di errore
            	response.getWriter().write("false");
   
            
        } catch (ParserConfigurationException | SAXException | TransformerException | FileNotFoundException  e) {
        	response.sendRedirect(request.getContextPath() + "/error_page.html");	
            e.printStackTrace();
        } 
		
		
		/////////////////////fine XML/////////////////////////////////

	}


}
