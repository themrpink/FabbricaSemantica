

import def.dom.HTMLInputElement;
/**
 * Istanzia la classe Input che incapsula un oggetto di tipo HTMLInputElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link HTMLTreeElement#element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 *
 */
public class Input extends HTMLTreeElement<HTMLInputElement, Input> {
	/**
	 * Costruttore della classe Input senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLFormElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public Input() {
		super("input");

	}

	/**
	 * Costruttore della classe Input con parametri.<br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLInputElement nel campo protetto {@link HTMLTreeElement#element} della 
	 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
	 * che incapsulano tag HTML che si troveranno all´interno del tag input.
	 * @param l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
	 */	
	// sicuro per le ragioni spiegate nel costruttore di HTMLTreeElement
	public Input(HTMLTreeElement<?,?>... l) {
		this();
		appendTags(l);
	}

	/**
	 * Metodo concatenabile. Setta il corrispettivo attributo dell´HTMLElement
	 * 
	 * @param type tipo
	 * @return Input la classe stessa
	 */
	public Input type(String type) {
		element.type = type;
		return this;
	}

	/**
	 * metodo concatenabile. Setta il corrispettivo attributo dell´HTMLInputElement
	 * 
	 * @param value valore
	 * @return Input la classe stessa
	 */
	public Input value(String value) {
		element.value = value;
		return this;
	}

	/**
	 * metodo concatenabile. Setta il corrispettivo attributo dell´HTMLInputElement
	 * 
	 * @param placeholder placeholder
	 * @return Input la classe stessa
	 */
	public Input placeholder(String placeholder) {
		element.placeholder = placeholder;
		return this;
	}

	/**
	 * metodo concatenabile. Setta il corrispettivo attributo dell´HTMLInputElement
	 * 
	 * @param name nome
	 * @return Input  la classe stessa
	 */
	public Input name(String name) {
		element.name = name;
		return this;
	}

	/**
	 * metodo concatenabile. Permette di impostare HTMLInputElement come required o meno.
	 * 
	 * @param b valore booleano
	 * @return Input  la classe stessa
	 */
	public Input required(boolean b) {
		element.required = b;
		return this;
	}

}
