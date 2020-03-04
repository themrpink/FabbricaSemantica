
import def.dom.HTMLFormElement;

/**
 * Istanzia la classe Form che incapsula un oggetto di tipo HTMLFormElement a cui è possibile accedere 
 * tramite il campo {@link #element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link #element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 *
 */
public class Form extends HTMLTreeElement<HTMLFormElement, Form> {
	/**
	 * Costruttore della classe Form senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLFormElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 	<br> Assegna di default al campo {@link #element} un valore di method = "POST"
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public Form() {
		super("form");
		element.method = "POST";
	}

	/**
	 * Costruttore della classe Form con parametri
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLFormElement nel campo protetto {@link HTMLTreeElement#element} della 
	 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
	 * che incapsulano tag HTML che si troveranno all´interno del form.
	 * @param l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
	 */	
	// sicuro per le ragioni spiegate nel costruttore di HTMLTreeElement
	public Form(HTMLTreeElement<?,?>... l) {
		this();
		appendTags(l);
	}

	/**
	 * permette di attribuire un valore al campo action di {@link #element}, che setta l´URL
	 * al quale il contenuto del form viene inviato per l´elaborazione dei dati.
	 * @param action stringa con la URL da assegnare
	 * @return this la classe stessa per poter concatenare i metodi
	 */
	public Form action(String action) {
		element.action = action;
		return this;
	}
	
	/**
	 * permette di attribuire un valore al campo method di {@link HTMLTreeElement#element},
	 * che indica come mandare i dati dal form al server (POST, GET)
	 * 
	 * @param method stringa con la action da assegnare
	 * @return this la classe stessa per poter concatenare i metodi
	 * 
	 */
	public Form method(String method) {
		element.method = method;
		return this;
	}
}
