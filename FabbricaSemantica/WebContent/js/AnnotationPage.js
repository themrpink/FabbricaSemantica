/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Istanzia la pagina
 * @class
 * @extends Page
 */
class AnnotationPage extends Page {
    constructor() {
        super();
        if (this.description === undefined)
            this.description = null;
        if (this.word === undefined)
            this.word = null;
        if (this.form === undefined)
            this.form = null;
        if (this.divDescription === undefined)
            this.divDescription = null;
        if (this.divWord === undefined)
            this.divWord = null;
        if (this.divTranslation === undefined)
            this.divTranslation = null;
        if (this.divButtons === undefined)
            this.divButtons = null;
        if (this.notLoggedIn === undefined)
            this.notLoggedIn = null;
        if (this.submit === undefined)
            this.submit = null;
        if (this.skip === undefined)
            this.skip = null;
        if (this.translation === undefined)
            this.translation = null;
        if (this.postError === undefined)
            this.postError = null;
        if (this.hiddenSynsetID === undefined)
            this.hiddenSynsetID = null;
        if (this.hidden1 === undefined)
            this.hidden1 = null;
        if (this.hidden2 === undefined)
            this.hidden2 = null;
        if (this.hidden3 === undefined)
            this.hidden3 = null;
        if (this.checkPost === undefined)
            this.checkPost = null;
        this.description = new Label();
        this.word = new Label();
        this.submit = new Button();
        this.skip = new Button();
        this.translation = new TextArea().name("answer");
        this.postError = new Label().textContent("Verificare di aver inserito una risposta").hidden(true).className("PostError");
        this.divDescription = new Div(this.description, this.postError);
        this.divWord = new Div(this.word);
        this.divButtons = new Div(this.submit, this.skip);
        this.divTranslation = new Div(this.translation);
        this.hiddenSynsetID = new Input().name("hiddenSynsetID").hidden(true);
        this.hidden1 = new Input().name("hidden1").hidden(true);
        this.hidden2 = new Input().name("hidden2").hidden(true);
        this.hidden3 = new Input().name("hidden3").hidden(true);
        this.form = new Form(this.divWord, this.divDescription, this.loader, this.divTranslation, this.divButtons, this.hiddenSynsetID, this.hidden1, this.hidden2, this.hidden3);
        this.notLoggedIn = new Div();
    }
    /**
     *
     */
    setDefaultAttributes() {
        super.setDefaultAttributes();
        this.submit.setAttributes("name", "submit_button", "value", "Avanti");
        this.skip.setAttributes("name", "skip_button", "value", "Skip");
        this.word.id("word");
        this.notLoggedIn.textContent("Please login or sign up to access this page");
        let ev = (e) => {
            this.loader.hidden(false);
            return null;
        };
        setTimeout(ev, 300);
    }
    /**
     *
     */
    connectToServer() {
        this.skip.element.formAction = "redirect.jsp";
        $.get("isLoggedIn.jsp", (result, b, ctx) => {
            let s = result;
            if (((o1, o2) => { if (o1 && o1.equals) {
                return o1.equals(o2);
            }
            else {
                return o1 === o2;
            } })(s, "false")) {
                this.mainForm.element.replaceChild(this.notLoggedIn.element, this.form.element);
            }
            return null;
        });
        this.checkPost = (e) => {
            if ($("textarea").val() === "") {
                e.preventDefault();
                this.postError.hidden(false);
            }
            return null;
        };
        this.submit.element.onclick = (this.checkPost);
    }
    /**
     * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
     * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
     */
    setStyle() {
        this.description.$css("margin:2% 5% 0% 5%", "font-style:italic", "text-overflow: ellipsis", "padding:0% 8% 3% 0%");
        this.word.$css("font-weight:bold", "font-size:2.2em", "font-style: normal");
        this.skip.$css("float:right");
        this.form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
        this.notLoggedIn.className("shadow-lg p-4 mb-4 bg-white rounded-top text-danger w-50 mx-auto");
    }
    /**
     * inserisce i nodi principali all'interno del body
     */
    build() {
        this.mainForm.appendTag(this.form);
        $("body").append(this.header.element, this.links.element, this.mainForm.element);
    }
}
AnnotationPage["__class"] = "AnnotationPage";
