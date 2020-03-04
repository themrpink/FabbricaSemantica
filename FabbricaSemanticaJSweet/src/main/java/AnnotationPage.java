
import static def.jquery.Globals.$;

import java.util.function.Function;

import def.dom.Event;
import def.dom.MouseEvent;

import static def.dom.Globals.setTimeout;;

/**
 * Questa pagina prepara le basi di tutte le pagine di annotazione e validazione
 * riprendendo a sua volta la struttura di base di {@link Page}. <br>La pagina è istanziabile e mostra la 
 * struttura completa senza però caricare alcun task.
 * 
 *@see Page
 *@see DefinitionAnnotationPage
 *@see SenseAnnotationPage
 *@see SenseValidationPage
 *@see TranslationAnnotationPage
 *@see TranslationValidationPage
 *@see MemoryValidationPage
 *
 */
public class AnnotationPage extends Page {

	/**
	 * {@link Label}
	 * qua viengono indicate le istruzioni della task
	 */
	protected final Label description;
	/**
	 * {@link Label}
	 * qui viene mostrata la parola
	 */
	protected final Label word;
	/**
	 * {@link Form} per inviare il task
	 */
	protected final Form form;
	/**
	 * {@link Div} Descrizione
	 */
	protected final Div divDescription;
	/**
	 * {@link Div} Testo del task
	 */
	protected final Div divWord;
	/**
	 * {@link Div} in cui viene inserita la traduzione
	 */
	protected final Div divTranslation;
	/**
	 * {@link Div} qua vengono messi i pulsanti
	 */
	protected final Div divButtons;
	/**
	 * {@link Div} qua viene mostrato il messaggio di errore se l´utente non si è autenticato.
	 */
	protected final Div notLoggedIn;
	/**
	 * {@link Button} pulsante di invio form. Fino a che l´utente non inserisce dati non è possibile inviare il form.
	 * @see #postError
	 */
	protected final Button submit;
	/**
	 *  {@link Button}  pulsante per saltare a una task random senza inviare il contenuto
	 */
	protected final Button skip;
	/**
	 *   {@link TextArea} qua vengono inserite le definizioni
	 */
	protected final TextArea translation;
	/**
	 *  {@link Label} 
	 * messaggio di errore in caso di invio del form senza aver completato il task 
	 */
	protected final Label postError;
	/**
	 *  
	 *{@link Input}  nascosto per inviare l´id del synset al server
	 */
	protected final Input hiddenSynsetID;
	/**
	 * {@link Input}  nascosto
	 */
	protected final Input hidden1;
	/**
	 * {@link Input}  nascosto
	 */
	protected final Input hidden2;
	/**
	 * {@link Input}  nascosto
	 */
	protected final Input hidden3;
	/**
	 * funzione che controlla la correttezza dell´invio del form, cioè che i task siano
	 * stati completati correttamente, altrimenti blocca l´invio del form e mostra un messaggio
	 * di errore.
	 */
	protected Function<MouseEvent, Object> checkPost;
	
	/**
	 * Istanzia la pagina
	 */
	public AnnotationPage() {

		this.description = new Label();
		this.word = new Label();
		this.submit = new Button();
		this.skip = new Button();
		this.translation = new TextArea().name("answer");

		this.postError = new Label().textContent("Verificare di aver inserito una risposta")
										    .hidden(true)
										    .className("PostError");

		this.divDescription = new Div(description, postError);
		this.divWord = new Div(word);
		this.divButtons = new Div(submit, skip);
		this.divTranslation = new Div(translation);

		// inserisci 4 campi nascosti per inviare dati al server
		this.hiddenSynsetID = new Input().name("hiddenSynsetID").hidden(true);
		this.hidden1 = new Input().name("hidden1").hidden(true);
		this.hidden2 = new Input().name("hidden2").hidden(true);
		this.hidden3 = new Input().name("hidden3").hidden(true);
		
		this.form = new Form(divWord, divDescription, loader, divTranslation, divButtons, hiddenSynsetID, hidden1, hidden2, hidden3);
		this.notLoggedIn = new Div();
	}

	@Override
	public void setDefaultAttributes() {
		super.setDefaultAttributes();

		submit.setAttributes("name", "submit_button", "value", "Avanti");
		skip.setAttributes("name", "skip_button", "value", "Skip");
		word.id("word");

		// messaggio di errore in caso di utente disconnesso
		notLoggedIn.textContent("Please login or sign up to access this page");

		// imposta un leggero ritardo sul loader, in modo che compaia solo in caso di un
		// significativo
		// tempo di caricamento della pagina
		Function<Event, Object> ev = (e) -> {
			loader.hidden(false);
			return null;
		};
		setTimeout(ev, 300);

	}

	@Override
	public void connectToServer() {

		skip.element.formAction = "redirect.jsp";

		// la funzione interroga il server e se l'utente non risulta connesso
		// sostituisce il form della
		// pagina con un messaggio che invita a fare il login
		$.get("isLoggedIn.jsp", (result, b, ctx) -> {
			String s = (String) result;
			if (s.equals("false")) {
				super.mainForm.element.replaceChild(notLoggedIn.element, form.element);
			}
			return null;
		});
		
		this.checkPost = (e) -> {

			
			// ottiene il numero di checkbox selezionati
			if($("textarea").val()=="") {
				e.preventDefault();
				// mostra il messaggio di errore
				postError.hidden(false);
			} 
			
			return null;
		};
		submit.element.onclick = checkPost;

	}

	/**
	 * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
	 * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
	 */
	@Override
	public void setStyle() {

		description.$css("margin:2% 5% 0% 5%", "font-style:italic", "text-overflow: ellipsis", "padding:0% 8% 3% 0%");
		word.$css("font-weight:bold", "font-size:2.2em", "font-style: normal");
		skip.$css("float:right");

		// Bootstrap
		form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
		notLoggedIn.className("shadow-lg p-4 mb-4 bg-white rounded-top text-danger w-50 mx-auto");
	}

	/**
	 * inserisce i nodi principali all'interno del body
	 */
	@Override
	public void build() {
		super.mainForm.appendTag(form);
		$("body").append(super.header.element, super.links.element, super.mainForm.element);
	}
}