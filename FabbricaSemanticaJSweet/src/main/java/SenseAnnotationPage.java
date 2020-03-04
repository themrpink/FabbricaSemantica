
import static def.jquery.Globals.$;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import def.js.JSON;

/**
 * Pagina del task Sense Annotation Page
 * @see Page
 *
 */
public class SenseAnnotationPage extends AnnotationPage {

	private Div d;
	private Div sense;
	private Label text;
	private Label checkboxError;
	
	/**
	 * Costruttore, istanzia una pagina SenseAnnotation.
	 */
	public SenseAnnotationPage() {
		
		this.checkboxError = new Label().textContent("Verificare che almeno una casella sia stata selezionata").hidden(true).className("PostError");
		this.d = new Div(checkboxError);
		this.text = new Label();
		this.sense = new Div(text);		
	}

	@Override
	public void connectToServer() {
		super.connectToServer();
		form.setAttributes("action", "senseAnnotation.jsp");
		
		$.getJSON("jsonData.jsp", "task=SENSE_ANNOTATION", (result, a, ctx) -> {
			JSON json = (JSON) result;
			String sWord = json.$get("word");
			String sDescription = json.$get("description");
			String sAnnotations = json.$get("senses");
			String synset = json.$get("synset");
			
			List<String> senses = Arrays.asList(sAnnotations.split("$"));

			word.innerHTML(sWord);
			$(description.getElement()).text(sDescription);
			List<Label> ll = new ArrayList<>();
			
			for (String s : senses) {
				int i = ll.size();
				ll.add(new Label(new Input().type("checkbox").name("answer").value(s)));
				ll.get(i).element.className = "form-check";
				ll.get(i).element.innerHTML += " "+ s;
				d.appendTag(ll.get(i));
			}
			$("#loader").hide();
			
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sWord);
			$(hidden2.element).val(sDescription);
			//questa è la lista delle riposte da scegliere
			$(hidden3.element).val(sAnnotations.substring(1).replace("$", "; "));
			
			form.element.replaceChild(d.element, super.divTranslation.element);
			return null;
		});
		
		//controlla che almeno una casella sia stata inserita
		super.checkPost = (e) -> {

			// ottiene il numero di checkbox selezionati
			double checked = $("input[type=checkbox]:checked").length;

			// se nessuno checkbox selezionato mostra messaggio di errore
			// se almeno uno è stato selezionato invia il form
			if (checked == 0) {
				// blocca il submit della pagina
				e.preventDefault();
				// mostra il messaggio di errore
				checkboxError.hidden(false);
			} 
	
			return null;
		};
		super.submit.element.onclick = checkPost;
		
	}

	@Override
	/**
	 * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
	 * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
	 */
	public void setStyle() {
		super.setStyle();
		d.$css("padding:0.5% 0% 4%");
		text.textContent("Selezionare i corretti significati della parola seguente nell'esempio fornito");
		sense.className("mx-auto border border-primary rounded p-2 m-2");
		super.taskName.textContent("Sense Annotation");
	}

	@Override
	public void build() {
		super.build();
		$(sense.element).insertBefore(divWord.element);
	}
}
