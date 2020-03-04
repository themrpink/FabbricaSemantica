/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Link senza parametri. <br>
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLAnchorElement nel campo protetto {@link HTMLTreeElement#element} della superclasse
 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
 * La classe Link � una specifica implementazione del tag Anchor per creare link tra pagine html.
 *
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 *
 * @class
 * @extends HTMLTreeElement
 * @author stefano urani
 */
class Link extends HTMLTreeElement {
    constructor() {
        super("a");
    }
    /**
     * Permette di impostare il campo text del HTMLAnchorElement, cio� il testo del link
     * @param {string} text testo link
     * @return {Link} this per concatenare i metodi
     */
    text(text) {
        this.element.text = text;
        return this;
    }
    /**
     * Permette di impostare il campo href del HTMLAnchorElement, cio� l�indirizzo di destinazione del link
     * @param {string} href collegamento
     * @return {Link} this per concatenare i metodi
     */
    href(href) {
        this.element.href = href;
        return this;
    }
}
Link["__class"] = "Link";
