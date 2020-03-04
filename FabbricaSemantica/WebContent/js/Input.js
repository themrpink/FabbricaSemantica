/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Input con parametri.<br>
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLInputElement nel campo protetto {@link HTMLTreeElement#element} della
 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
 * che incapsulano tag HTML che si troveranno all�interno del tag input.
 * @param {Array} l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
 * @class
 * @extends HTMLTreeElement
 */
class Input extends HTMLTreeElement {
    constructor(...l) {
        if (((l != null && l instanceof Array && (l.length == 0 || l[0] == null || (l[0] != null && l[0] instanceof HTMLTreeElement))) || l === null)) {
            let __args = arguments;
            {
                let __args = arguments;
                super("input");
            }
            (() => {
                this.appendTags.apply(this, l);
            })();
        }
        else if (l === undefined) {
            let __args = arguments;
            super("input");
        }
        else
            throw new Error('invalid overload');
    }
    /**
     * Metodo concatenabile. Setta il corrispettivo attributo dell�HTMLElement
     *
     * @param {string} type tipo
     * @return {Input} Input la classe stessa
     */
    type(type) {
        this.element.type = type;
        return this;
    }
    /**
     * metodo concatenabile. Setta il corrispettivo attributo dell�HTMLInputElement
     *
     * @param {string} value valore
     * @return {Input} Input la classe stessa
     */
    value(value) {
        this.element.value = value;
        return this;
    }
    /**
     * metodo concatenabile. Setta il corrispettivo attributo dell�HTMLInputElement
     *
     * @param {string} placeholder placeholder
     * @return {Input} Input la classe stessa
     */
    placeholder(placeholder) {
        this.element.placeholder = placeholder;
        return this;
    }
    /**
     * metodo concatenabile. Setta il corrispettivo attributo dell�HTMLInputElement
     *
     * @param {string} name nome
     * @return {Input} Input  la classe stessa
     */
    name(name) {
        this.element.name = name;
        return this;
    }
    /**
     * metodo concatenabile. Permette di impostare HTMLInputElement come required o meno.
     *
     * @param {boolean} b valore booleano
     * @return {Input} Input  la classe stessa
     */
    required(b) {
        this.element.required = b;
        return this;
    }
}
Input["__class"] = "Input";
