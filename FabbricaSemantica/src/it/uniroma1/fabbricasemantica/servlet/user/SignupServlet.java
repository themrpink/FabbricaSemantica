package it.uniroma1.fabbricasemantica.servlet.user;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import it.uniroma1.fabbricasemantica.data.XMLWriter;
import it.uniroma1.fabbricasemantica.servlet.BaseServlet;

/**
 * Servlet per l´iscrizione di un utente
 */
@WebServlet(name="SignupServlet", urlPatterns="/signup.jsp")
public class SignupServlet extends BaseServlet {
	private static final long serialVersionUID = 8484501789787L;

	/**
	 * Salva i dati utente in un file userdata.xml
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO implementare la logica per la registrazione
	
		//recupera i dati per l'iscrizione inviati dal form 

		String username = (String) request.getParameter("username");
		String email = request.getParameter("email").toLowerCase();
		String password = (String) request.getParameter("password");
		String passwordRepeat = (String) request.getParameter("passwordRepeat");
		String[] checkboxes = request.getParameterValues("motherLanguages");
		String[] lang = request.getParameterValues("lang");
		String[] level = request.getParameterValues("select");
		
		if(lang==null) {
			lang = new String[0];
			level = new String[0];
		}

		//controllo lato server che le password coincidano
		if(!password.equals(passwordRepeat)) {
			request.setAttribute("errorMessage", "Passwords dont match");
			getServletContext().getRequestDispatcher("/login.html").forward(request, response);
		}
		
		//se coincidono prosegue con il signup
		else request.setAttribute("errorMessage", "");

		//ricava il path del file XML
		String xmlFilePath = request.getServletContext().getRealPath("/")+"/WEB-INF/userdata.xml";
		File file = new File(xmlFilePath);
		
		try {
			
				XMLWriter writer = new XMLWriter(file);
				boolean check = writer.findTagTextContent("email", email);
				
				if(check) {
					
					writer.insertParentTag("user")
							.insertTag("email", email)
							.insertTag("username", username)
							.insertTag("password", password)
							.insertTag("motherLanguages", Arrays.asList(checkboxes).toString());
					
				
					for(int i=0; i<lang.length;i++)
						if(lang[i].equals("")) lang[i]="null";
					writer.insertTag("lang", Arrays.asList(lang).toString(), Arrays.asList(level).toString());
					
				
				writer.writeFile();
				}
			
				else 
					response.getWriter().write("false");
				
		} catch (SAXException | ParserConfigurationException | TransformerException e) {
			response.sendRedirect(request.getContextPath() + "/error_page.html");
			e.printStackTrace();
			}	
	}
}
