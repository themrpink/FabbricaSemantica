

import def.dom.HTMLAnchorElement;

/**
 * Istanzia la classe Link che incapsula un oggetto di tipo HTMLAnchorElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}. E' possibile
 * anche istanziare la classe con un numero variabile di oggetti di tipo HTMLTreeElement, i campi dei quali
 * verranno innestati come children del campo {@link HTMLTreeElement#element}.
  @see HTMLTreeElement#element
  @see HTMLTreeElement
 * @author stefano urani
 *
 */
public class Link extends HTMLTreeElement<HTMLAnchorElement, Link> {
	/**
	 * Costruttore della classe Link senza parametri. <br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLAnchorElement nel campo protetto {@link HTMLTreeElement#element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
	 * La classe Link è una specifica implementazione del tag Anchor per creare link tra pagine html.
	 * 
	 * @see HTMLTreeElement
	 * @see HTMLTreeElement#element
	 * 
	 */
	public Link() {
		super("a");
	}


/**
 * Permette di impostare il campo text del HTMLAnchorElement, cioè il testo del link
 * @param text testo link
 * @return this per concatenare i metodi
 */
	public Link text(String text) {
		element.text = text;
		return this;
	}
	
	/**
	 * Permette di impostare il campo href del HTMLAnchorElement, cioè l´indirizzo di destinazione del link
	 * @param href collegamento
	 * @return this per concatenare i metodi
	 */
	public Link href(String href) {
		element.href = href;
		return this;
	}

}
