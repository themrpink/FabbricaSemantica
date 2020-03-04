
import static def.jquery.Globals.$;
import def.js.JSON;
/**
 * Pagina del task Translation Annotation.
 * @see Page
 *
 */
public class TranslationAnnotationPage extends AnnotationPage {

	private Div sense;
	private Label text;
	
	/**
	 * Costruttore, istanzia una pagina TranslationAnnotation.
	 */
	public TranslationAnnotationPage() {
		this.text = new Label();
		this.sense = new Div(text);
	}

	@Override
	public void connectToServer() {
		super.connectToServer();
		form.setAttributes("action", "translationAnnotation.jsp");
		$.getJSON("jsonData.jsp", "task=TRANSLATION_ANNOTATION", (result, a, ctx) -> {
			JSON json = (JSON) result;
			String sWord = json.$get("word");
			String sDescription = json.$get("description");
			String synset = json.$get("synset");
			
			$("#loader").hide();
			$(word.getElement()).text(sWord);
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sWord);
			$(hidden2.element).val(sDescription);
			
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
		text.textContent("Data la seguente parola e definizione, fornire una definizione nella tua lingua madre");
		sense.className("mx-auto border border-primary rounded p-2 m-2");
		super.taskName.textContent("Translation Annotation");
	}

	@Override
	public void build() {
		super.build();// $("body").append(form.getElement());
		$(sense.element).insertBefore(divWord.element);
		// sense.element.insertBefore(super.divWord.element);
	}
}
