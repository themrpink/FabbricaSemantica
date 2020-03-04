/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Form con parametri
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLFormElement nel campo protetto {@link HTMLTreeElement#element} della
 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
 * che incapsulano tag HTML che si troveranno all�interno del form.
 * @param {Array} l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
 * @class
 * @extends HTMLTreeElement
 */
class Form extends HTMLTreeElement {
    constructor(...l) {
        if (((l != null && l instanceof Array && (l.length == 0 || l[0] == null || (l[0] != null && l[0] instanceof HTMLTreeElement))) || l === null)) {
            let __args = arguments;
            {
                let __args = arguments;
                super("form");
                (() => {
                    this.element.method = "POST";
                })();
            }
            (() => {
                this.appendTags.apply(this, l);
            })();
        }
        else if (l === undefined) {
            let __args = arguments;
            super("form");
            (() => {
                this.element.method = "POST";
            })();
        }
        else
            throw new Error('invalid overload');
    }
    /**
     * permette di attribuire un valore al campo action di {@link #element}, che setta l�URL
     * al quale il contenuto del form viene inviato per l�elaborazione dei dati.
     * @param {string} action stringa con la URL da assegnare
     * @return {Form} this la classe stessa per poter concatenare i metodi
     */
    action(action) {
        this.element.action = action;
        return this;
    }
    /**
     * permette di attribuire un valore al campo method di {@link HTMLTreeElement#element},
     * che indica come mandare i dati dal form al server (POST, GET)
     *
     * @param {string} method stringa con la action da assegnare
     * @return {Form} this la classe stessa per poter concatenare i metodi
     *
     */
    method(method) {
        this.element.method = method;
        return this;
    }
}
Form["__class"] = "Form";
