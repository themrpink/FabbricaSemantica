
import def.dom.HTMLInputElement;
/**
* Istanzia la classe Button che incapsula un oggetto di tipo HTMLInputElement a cui è possibile accedere 
* tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
* anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
* verranno innestati come children del campo {@link HTMLTreeElement#element}.
 @see HTMLTreeElement#element
 @see HTMLTreeElement
*
*/
public class Button extends HTMLTreeElement<HTMLInputElement, Button> {

	/**
	 * Costruttore della classe Button senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 * <br> Assegna al campo {@link HTMLTreeElement#element} un valore di type = "submit" (pulsante per invio dati)
	 	<br> Assegna al campo {@link HTMLTreeElement#element} un valore di className = "btn btn-outline-primary" (Bootstrap)
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */	
	public Button() {
		super("input");
		element.type = "submit";
		element.className = "btn btn-outline-primary";
	}

	/**
	 * imposta il campo value del pulsante
	 * @param value valore 
	 * @return this per concatenare i metodi
	 */
	public Button value(String value) {
		element.value = value;
		return this;
	}
	
	/**
	 * imposta il campo placeholder del pulsante
	 * @param type tipo 
	 * @return this per concatenare i metodi
	 */
	public Button type(String type) {
		element.type = type;
		return this;
	}
	/**
	 * imposta il campo placeholder del pulsante
	 * @param placeholder placeholder
	 * @return this per concatenare i metodi
	 */
	public Button placeholder(String placeholder) {
		element.placeholder = placeholder;
		return this;
	}
	
	/**
	 * imposta il campo name del pulsante
	 * @param name nome
	 * @return this per concatenare i metodi
	 */
	public Button name(String name) {
		element.name = name;
		return this;
	}

}
