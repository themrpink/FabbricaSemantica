
import def.dom.HTMLDivElement;

/**
 * Istanzia la classe Div che incapsula un oggetto di tipo HTMLDivElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link HTMLTreeElement#element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 *
 */
public class Div extends HTMLTreeElement<HTMLDivElement, Div> {

	/**
	 * Costruttore della classe Div senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 	<br> Assegna al campo {@link HTMLTreeElement#element} un valore di className = "form-group"
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public Div() {
		super("div");
		element.className = "form-group";
	}
	

	/**
	 * Costruttore della classe Div con parametri. Il varargs è sicuro per le ragioni spiegate a {@link HTMLTreeElement#appendTags(HTMLTreeElement...)}<br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link #element} della 
	 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi  e innestarvi al suo interno altre classi di tipo HTMLTreeElement
	 * che incapsulano tag HTML che si troveranno all´interno del div.
	 * @param l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * @see HTMLTreeElement#appendTags(HTMLTreeElement...l)
	 */

	// sicuro per le ragioni spiegate nel costruttore di HTMLTreeElement
	public Div(HTMLTreeElement<?,?>... l) {
		this();
		appendTags(l);
	}



}
