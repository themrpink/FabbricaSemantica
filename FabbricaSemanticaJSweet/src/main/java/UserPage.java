import static def.jquery.Globals.$;
import def.js.JSON;

/**
 * Pagina dell´utente. Mostra, oltre ai dati dell´utente la tabella con tutti i task svolti, con
 * nome task, id del synset richiesto, testo della domanda e testo della risposta.
 * 
 *@see Page
 */
public class UserPage extends AnnotationPage {

	
	private Label nome;
	private Label email;
	private Label titolo;
	private Table table;
	private Div div;
	
	/**
	 * Costruttore della pagina dell´utente.
	 */
	public UserPage() {
		
		this.nome = new Label();
		this.email = new Label();
		this.titolo = (Label) new Label().innerHTML("<h2>Task completati</h2>");
		titolo.$css("margin:5%, 5%");
		this.table=new Table(1, 2);
		this.div = new Div(titolo, table);
		//$("#loader").hide();	
	}

	
	@Override
	public void connectToServer() {
		$.getJSON("user.jsp", (r, a, ctx) -> {
			
			JSON json = (JSON) r;
			String name = json.$get("username");
			String mail = json.$get("email");
			String result = json.$get("results");
			nome.textContent("Nome utente: "+name);			
			email.textContent("Email: "+mail);	
			String[] results = result.split("<end_task>");

			buildUserTable(results);
			return null;
		});		
	}
/**
 * costruisce la tabella con i task completati dall'utente
 */
	private void buildUserTable(String[] results) {

		int rowCount=0;
		for(String s : results) {
			String[] task = s.split("<br>");			
			for(String t : task) {	
				String[] line = t.split(": ");
				if(line[0].contains("userID")) continue;
				else if(line[0].equals("")) table.addRow();
				else {		
					if(line[0].equals("task_type")) { 
						line[0] = "<b>"+line[0]+"</b>";
						line[1] = "<b>"+line[1]+"</b>";
					}
					table.getCellFromID("r"+rowCount+"c"+0).innerHTML(line[0]);
					table.getCellFromID("r"+rowCount+"c"+1).innerHTML(line[1]);
					table.addRow();
				}
				
				rowCount++;
			}
		}		
		div.appendTag(table);		
	}

	@Override
	public void build() {
		mainForm.appendTag(nome);
		mainForm.appendTag(email);
		mainForm.appendTag(div);
		$("body").append(super.header.element, super.links.element, super.mainForm.element);
	}

}
