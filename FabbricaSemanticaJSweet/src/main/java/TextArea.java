
import def.dom.HTMLTextAreaElement;
/**
 * Istanzia la classe TextArea che incapsula un oggetto di tipo HTMLTextAreaElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link HTMLTreeElement#element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 *
 */
public class TextArea extends HTMLTreeElement<HTMLTextAreaElement, TextArea> {

	/**
	 * Costruttore della classe TextArea senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLTextAreaElement nel campo protetto {@link #element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 	<br> Assegna al campo {@link HTMLTreeElement#element} un valore di className = "form-control"
	 * <br> Assegna al campo {@link HTMLTreeElement#element} un valore di placeholder = "Scrivi la traduzione qui..."
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public TextArea() {
		super("TextArea");
		element.className = "form-control";
		element.placeholder = "Scrivi la traduzione qui...";
	}
	/**
	 * Costruttore della classe TextArea con parametri
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDTextAreaElement nel campo protetto {@link HTMLTreeElement#element} della 
	 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi  e innestarvi al suo interno altre classi di tipo HTMLTreeElement
	 * che incapsulano tag HTML che si troveranno all´interno della TextArea.
	 * @param l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
	 */
	// sicuro per le ragioni spiegate nel costruttore di HTMLTreeElement
	public TextArea(HTMLTreeElement<?,?>... l) {
		this();
		appendTags(l);
	}
	/**
	 * imposta il campo placeholder della TextArea
	 * @param placeholder placeholder
	 * @return this per concatenare i metodi
	 */
	public TextArea placeholder(String placeholder) {
		element.placeholder = placeholder;
		return this;
	}
	/**
	 * imposta il campo name della TextArea
	 * @param name nome
	 * @return this per concatenare i metodi
	 */
	public TextArea name(String name) {
		element.name=name;
		return this;
	}
}
