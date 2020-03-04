/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina Definition Annotation.
 * @class
 * @extends AnnotationPage
 */
class DefinitionAnnotationPage extends AnnotationPage {
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
        this.form.setAttributes("action", "definitionAnnotation.jsp");
        $.getJSON("jsonData.jsp", "task=DEFINITION_ANNOTATION", (result, a, ctx) => {
            let json = result;
            let sWord = (json["word"]);
            let sDescription = (json["hypernym"]);
            let synset = (json["synset"]);
            $("#loader").hide();
            $(this.hiddenSynsetID.element).val(synset);
            $(this.hidden1.element).val(sWord);
            $(this.hidden2.element).val(sDescription);
            $(this.word.getElement()).text(sWord);
            $(this.description.getElement()).text(sDescription);
            return null;
        });
    }
    /**
     * imposta gli attributi specifi della pagina, sostituendoli ad alcuni
     * specificati nel file CSS e in alcuni casi utilizzando Bootstrap
     */
    setStyle() {
        super.setStyle();
        this.text.textContent("Data la seguente parola e un suo iperonimo, fornire una traduzione nella tua lingua madre");
        this.sense.className("mx-auto border border-primary rounded p-2 m-2");
        this.sense.$css("margin:0% 0% 6% 0%");
        this.taskName.textContent("Definition Annotation");
    }
    /**
     *
     */
    build() {
        super.build();
        $(this.sense.element).insertBefore(this.divWord.element);
    }
}
DefinitionAnnotationPage["__class"] = "DefinitionAnnotationPage";
