
import static def.jquery.Globals.$;
import def.js.JSON;
/**
 * Pagina del task Word Annotation.
 * @see Page
 *
 */
class WordAnnotationPage extends AnnotationPage {

	Div sense;
	private Label text;
	
	/**
	 * Costruttore, istanzia una pagina WordAnnotation.
	 */
	public WordAnnotationPage() {
		this.text = new Label();
		this.sense = new Div(text);
	}

	@Override
	public void connectToServer() {
		super.connectToServer();
		form.setAttributes("action", "wordAnnotation.jsp");
		$.getJSON("jsonData.jsp", "task=WORD_ANNOTATION", (result, a, ctx) -> {
			JSON json = (JSON) result;
			String sDescription = json.$get("description");
			String synset = json.$get("synset");
			$("#loader").hide();
			$(description.element).text(sDescription);
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sDescription);
			return null;
		});
	}

	@Override
	/**
	 * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
	 * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
	 */
	public void setStyle() {
		super.setStyle();
		text.textContent("Data la seguente definizione, provare a indovinare il termine definito");
		sense.className("mx-auto border border-primary rounded p-1");
		super.taskName.textContent("Word Annotation");
	}

	@Override
	public void build() {
		super.build();
		super.divWord.element.appendChild(sense.element);
	}
}