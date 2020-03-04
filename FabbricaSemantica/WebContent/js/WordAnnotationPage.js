/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina WordAnnotation.
 * @class
 * @extends AnnotationPage
 */
class WordAnnotationPage extends AnnotationPage {
    constructor() {
        super();
        if (this.sense === undefined)
            this.sense = null;
        if (this.text === undefined)
            this.text = null;
        this.text = new Label();
        this.sense = new Div(this.text);
    }
    /**
     *
     */
    connectToServer() {
        super.connectToServer();
        this.form.setAttributes("action", "wordAnnotation.jsp");
        $.getJSON("jsonData.jsp", "task=WORD_ANNOTATION", (result, a, ctx) => {
            let json = result;
            let sDescription = (json["description"]);
            let synset = (json["synset"]);
            $("#loader").hide();
            $(this.description.element).text(sDescription);
            $(this.hiddenSynsetID.element).val(synset);
            $(this.hidden1.element).val(sDescription);
            return null;
        });
    }
    /**
     *
     */
    setStyle() {
        super.setStyle();
        this.text.textContent("Data la seguente definizione, provare a indovinare il termine definito");
        this.sense.className("mx-auto border border-primary rounded p-1");
        this.taskName.textContent("Word Annotation");
    }
    /**
     *
     */
    build() {
        super.build();
        this.divWord.element.appendChild(this.sense.element);
    }
}
WordAnnotationPage["__class"] = "WordAnnotationPage";
