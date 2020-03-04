/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Div con parametri. Il varargs � sicuro per le ragioni spiegate a {@link HTMLTreeElement#appendTags(HTMLTreeElement...)}<br>
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link #element} della
 * superclasse {@link HTMLTreeElement}, accedere ai suoi campi e metodi  e innestarvi al suo interno altre classi di tipo HTMLTreeElement
 * che incapsulano tag HTML che si troveranno all�interno del div.
 * @param {Array} l varargs di oggetti che estendono HTMLElement, i quali vengono innestati come children del campo {@link HTMLTreeElement#element}, e HTMLTreeElement.
 * @see HTMLTreeElement
 * @see HTMLTreeElement#element
 * @see HTMLTreeElement#appendTags(HTMLTreeElement...l)
 * @class
 * @extends HTMLTreeElement
 */
class Div extends HTMLTreeElement {
    constructor(...l) {
        if (((l != null && l instanceof Array && (l.length == 0 || l[0] == null || (l[0] != null && l[0] instanceof HTMLTreeElement))) || l === null)) {
            let __args = arguments;
            {
                let __args = arguments;
                super("div");
                (() => {
                    this.element.className = "form-group";
                })();
            }
            (() => {
                this.appendTags.apply(this, l);
            })();
        }
        else if (l === undefined) {
            let __args = arguments;
            super("div");
            (() => {
                this.element.className = "form-group";
            })();
        }
        else
            throw new Error('invalid overload');
    }
}
Div["__class"] = "Div";
