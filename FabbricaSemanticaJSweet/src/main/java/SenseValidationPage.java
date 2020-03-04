
import static def.jquery.Globals.$;

import java.util.function.Function;

import def.dom.MouseEvent;
import def.js.JSON;

/**
 * Pagina del task Sense Validation.
 * 
 * @see Page
 *
 */
public class SenseValidationPage extends AnnotationPage {

	private Div d;
	private Div instruction;
	private Div sense;
	private Div radio;
	private Input radioYes;
	private Input radioNo;
	private Label text;
	private Label yes;
	private Label no;
	private Function<MouseEvent, Object> checkPost;
	
	/**
	 * Costruttore, istanzia una pagina SenseValidation.
	 */
	public SenseValidationPage() {

		this.text = new Label();
		this.yes = new Label();
		this.no = new Label();
		this.instruction = new Div(text);
		this.sense = new Div();
		this.radioYes = new Input().type("radio").value("yes");
		this.radioNo = new Input().type("radio").value("no");
		this.radio = new Div(radioYes, yes, radioNo, no);
		this.d = new Div(instruction, sense, radio);

	}

	@Override
	public void setDefaultAttributes() {
		super.setDefaultAttributes();

		radio.className("form-check-inline");

		text.textContent("È il seguente senso appropriato?");
		yes.textContent("Yes");
		no.textContent("No");

		radioYes.element.name = "sense";
		radioNo.element.name = "sense";

	}

	@Override
	public void connectToServer() {
		super.connectToServer();

		form.setAttributes("action", "senseValidation.jsp");
		$.getJSON("jsonData.jsp", "task=SENSE_VALIDATION", (result, a, ctx) -> {

			JSON json = (JSON) result;
			String sWord = json.$get("word");
			String sExample = json.$get("example");
			String sSense = json.$get("sense");
			String synset = json.$get("synset");
			
			$("#loader").hide();
			
			$(hiddenSynsetID.element).val(synset);
			$(hidden1.element).val(sWord);
			$(hidden2.element).val(sExample);
			$(hidden3.element).val(sSense);
			
			$(word.element).text(sWord);
			$(description.element).text(sExample);
			$(sense.element).text("\"" + sSense + "\"");
			return null;
		});
		
		this.checkPost = (e) -> {
			// verifica se i radiobutton sono selezionati
			if(!$("input[type=radio]").is(":checked")) {
				e.preventDefault();
				// mostra il messaggio di errore
				postError.hidden(false);
			} 			
			return null;
		};
		submit.element.onclick = checkPost;	
	}

	@Override
	/**
	 * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
	 * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
	 */
	public void setStyle() {
		super.setStyle();
		instruction.className("mx-auto border border-primary rounded p-1");
		sense.$css("margin:4% 5% 4% 5%", "font-style:italic");
		yes.className("pl-2 pr-1");
		no.className("pl-2 pr-1");
		super.taskName.textContent("Sense Validation");
	}

	@Override
	public void build() {
		super.build();
		form.element.replaceChild(d.element, super.divTranslation.element);
	}
}