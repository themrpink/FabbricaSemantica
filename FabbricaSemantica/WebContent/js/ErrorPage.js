/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
class ErrorPage extends Page {
    constructor() {
        super();
        if (this.divError === undefined)
            this.divError = null;
        if (this.label === undefined)
            this.label = null;
        this.label = new Label();
        this.divError = new Div(this.label);
    }
    /**
     *
     */
    setDefaultAttributes() {
        super.setDefaultAttributes();
    }
    /**
     *
     */
    connectToServer() {
    }
    /**
     *
     */
    setStyle() {
        this.label.textContent("ops, \u00e8 stato riscontrato un errore nel server.");
        this.divError.className("shadow-lg p-4 mb-4 bg-white rounded-top text-danger w-50 mx-auto");
    }
    /**
     *
     */
    build() {
        this.mainForm.appendTag(this.divError);
    }
}
ErrorPage["__class"] = "ErrorPage";
