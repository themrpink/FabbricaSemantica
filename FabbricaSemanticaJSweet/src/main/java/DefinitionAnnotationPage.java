
import static def.jquery.Globals.$;
import def.js.JSON;

/**
 * Pagina del task Definition Annotation
 *
 */
public class DefinitionAnnotationPage extends AnnotationPage {

	private Div sense;
	private Label text;

	/**
	 * Costruttore, istanzia una pagina Definition Annotation.
	 */
	public DefinitionAnnotationPage() {
		this.text = new Label();
		this.sense = new Div(text);
	}

	@Override
	public void connectToServer() {
		super.connectToServer();

		// setta la servlet chiamata dal form
		form.setAttributes("action", "definitionAnnotation.jsp");

		// ottiene dal server i dati richiesti e li inserisce nella pagina
		$.getJSON("jsonData.jsp", "task=DEFINITION_ANNOTATION", (result, a, ctx) -> {
			JSON json = (JSON) result;
			String sWord = json.$get("word");
			String sDescription = json.$get("hypernym");
			String synset = json.$get("synset");
			
			$("#loader").hide();
			
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sWord);
			$(hidden2.element).val(sDescription);
			
			$(word.getElement()).text(sWord);
			$(description.getElement()).text(sDescription);
			return null;
		});
	}

	/**
	 * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
	 * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
	 */
	@Override
	public void setStyle() {
		super.setStyle();
		text.textContent("Data la seguente parola e un suo iperonimo, fornire una traduzione nella tua lingua madre");
		sense.className("mx-auto border border-primary rounded p-2 m-2");
		sense.$css("margin:0% 0% 6% 0%");
		super.taskName.textContent("Definition Annotation");
	}


	@Override
	public void build() {
		super.build();
		// inserisce il div sense all'interno del form
		$(sense.element).insertBefore(divWord.element);
	}
}
