
import static def.jquery.Globals.$;
import java.util.Arrays;
import java.util.List;
import def.js.JSON;
/**
 * Pagina del task Translation Validation.
 * @see Page
 *
 */
public class TranslationValidationPage extends AnnotationPage {

	private Div d;
	private Div sense;
	private Label text;
	private Label checkboxError;
	
	
	/**
	 * Costruttore, istanzia una pagina TranslationValidation.
	 */
	public TranslationValidationPage() {
		this.text = new Label();
		this.checkboxError = new Label().textContent("Verificare che almeno una casella sia stata selezionata oppure di non aver selezionato la casella nessuna insieme ad altre caselle").hidden(true).className("PostError");
		this.sense = new Div(text);
		this.d = new Div(checkboxError);
		
	}

	@Override
	public void connectToServer() {
		super.connectToServer();
		form.setAttributes("action", "translationValidation.jsp");
		$.getJSON("jsonData.jsp", "task=TRANSLATION_VALIDATION", (result, a, ctx) -> {

			JSON json = (JSON) result;
			String sWord = json.$get("word");
			String sDescription = json.$get("description");
			String sValidation = json.$get("translations");
			String synset = json.$get("synset");
			
			List<String> translations = Arrays.asList(sValidation.split("$"));
			translations.remove(0);
			word.innerHTML(sWord);
			$(description.getElement()).text(sDescription);
			//List<Label> ll = new ArrayList<>();

			for (String s : translations) {
				d.appendTag((Label) new Label(new Input().type("checkbox")
												.name("answer")
												.value(s))
									.className("form-check")
									.innerHTML(" " + s));
			}

			d.appendTag(new Label(new Input().type("checkbox").name("answer").value("nessuna").id("nessuna")).className("form-check")
					.innerHTML(" nessuna"));
			$("#loader").hide();
			
			//inserisce i dati della domanda nei campi nascosti per inviarli al server
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sWord);
			$(hidden2.element).val(sDescription);
			$(hidden3.element).val(sValidation.substring(1).replace("$", "; "));
			
			form.element.replaceChild(d.element, super.divTranslation.element);
			return null;
		});
		
		//controlla che almeno una checkbox sia stata selezionata e che la casella nessuna, se selezionata, sia l´unica
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
			else if(checked>1 && $("#nessuna").is(":checked")) {
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
		d.$css("padding:0% 0% 4%");
		text.textContent("Selezionare le corrette traduzioni della parola seguente e della definizione fornita");
		sense.className("mx-auto border border-primary rounded p-2 m-2");
		super.taskName.textContent("Translation Validation");
	}

	@Override
	public void build() {
		super.build();
		$(sense.element).insertBefore(divWord.element);
	}
}