
import def.dom.HTMLLabelElement;
/**
 * Istanzia la classe Label che incapsula un oggetto di tipo HTMLInputElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link HTMLTreeElement#element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 *
 */
public class Label extends HTMLTreeElement<HTMLLabelElement, Label> {

	/**
	 * Costruttore della classe Label senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLLabelElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 * <br> Assegna di default al campo {@link HTMLTreeElement#element} un valore di className = "form-control-plaintext", che è un parametro di Bootstrap
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public Label() {
		super("label");
		element.className = "form-control-plaintext";
	}

	/**
	 * Costruttore della classe Label con parametri.
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLLabelElement nel campo protetto {@link HTMLTreeElement#element} della 
	 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
	 * che incapsulano tag HTMLElement che si troveranno all´interno del Label.
	 * @param l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
	 */	

	// sicuro per le ragioni spiegate nel costruttore di HTMLTreeElement
	public Label(HTMLTreeElement<?,?>... l) {
		this();
		appendTags(l);
	}

}
