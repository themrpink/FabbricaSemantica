/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Label con parametri.
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLLabelElement nel campo protetto {@link HTMLTreeElement#element} della
 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi e innestarvi al suo interno altre classi di tipo HTMLTreeElement
 * che incapsulano tag HTMLElement che si troveranno all�interno del Label.
 * @param {Array} l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 * @see HTMLTreeElement#appendTags(HTMLTreeElement...)
 * @class
 * @extends HTMLTreeElement
 */
class Label extends HTMLTreeElement {
    constructor(...l) {
        if (((l != null && l instanceof Array && (l.length == 0 || l[0] == null || (l[0] != null && l[0] instanceof HTMLTreeElement))) || l === null)) {
            let __args = arguments;
            {
                let __args = arguments;
                super("label");
                (() => {
                    this.element.className = "form-control-plaintext";
                })();
            }
            (() => {
                this.appendTags.apply(this, l);
            })();
        }
        else if (l === undefined) {
            let __args = arguments;
            super("label");
            (() => {
                this.element.className = "form-control-plaintext";
            })();
        }
        else
            throw new Error('invalid overload');
    }
}
Label["__class"] = "Label";
