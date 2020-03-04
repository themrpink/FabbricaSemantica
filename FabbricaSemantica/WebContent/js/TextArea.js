/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe TextArea con parametri
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDTextAreaElement nel campo protetto {@link HTMLTreeElement#element} della
 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi  e innestarvi al suo interno altre classi di tipo HTMLTreeElement
 * che incapsulano tag HTML che si troveranno all�interno della TextArea.
 * @param {Array} l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
 * @class
 * @extends HTMLTreeElement
 */
class TextArea extends HTMLTreeElement {
    constructor(...l) {
        if (((l != null && l instanceof Array && (l.length == 0 || l[0] == null || (l[0] != null && l[0] instanceof HTMLTreeElement))) || l === null)) {
            let __args = arguments;
            {
                let __args = arguments;
                super("TextArea");
                (() => {
                    this.element.className = "form-control";
                    this.element.placeholder = "Scrivi la traduzione qui...";
                })();
            }
            (() => {
                this.appendTags.apply(this, l);
            })();
        }
        else if (l === undefined) {
            let __args = arguments;
            super("TextArea");
            (() => {
                this.element.className = "form-control";
                this.element.placeholder = "Scrivi la traduzione qui...";
            })();
        }
        else
            throw new Error('invalid overload');
    }
    /**
     * imposta il campo placeholder della TextArea
     * @param {string} placeholder placeholder
     * @return {TextArea} this per concatenare i metodi
     */
    placeholder(placeholder) {
        this.element.placeholder = placeholder;
        return this;
    }
    /**
     * imposta il campo name della TextArea
     * @param {string} name nome
     * @return {TextArea} this per concatenare i metodi
     */
    name(name) {
        this.element.name = name;
        return this;
    }
}
TextArea["__class"] = "TextArea";
