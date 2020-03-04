/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Button senza parametri. <br>
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link HTMLTreeElement#element} della superclasse
 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.
 * <br> Assegna al campo {@link HTMLTreeElement#element} un valore di type = "submit" (pulsante per invio dati)
 * <br> Assegna al campo {@link HTMLTreeElement#element} un valore di className = "btn btn-outline-primary" (Bootstrap)
 *
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 *
 * @class
 * @extends HTMLTreeElement
 */
class Button extends HTMLTreeElement {
    constructor() {
        super("input");
        this.element.type = "submit";
        this.element.className = "btn btn-outline-primary";
    }
    /**
     * imposta il campo value del pulsante
     * @param {string} value valore
     * @return {Button} this per concatenare i metodi
     */
    value(value) {
        this.element.value = value;
        return this;
    }
    /**
     * imposta il campo placeholder del pulsante
     * @param {string} type tipo
     * @return {Button} this per concatenare i metodi
     */
    type(type) {
        this.element.type = type;
        return this;
    }
    /**
     * imposta il campo placeholder del pulsante
     * @param {string} placeholder placeholder
     * @return {Button} this per concatenare i metodi
     */
    placeholder(placeholder) {
        this.element.placeholder = placeholder;
        return this;
    }
    /**
     * imposta il campo name del pulsante
     * @param {string} name nome
     * @return {Button} this per concatenare i metodi
     */
    name(name) {
        this.element.name = name;
        return this;
    }
}
Button["__class"] = "Button";
