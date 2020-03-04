/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina TranslationValidation.
 * @class
 * @extends AnnotationPage
 */
class TranslationValidationPage extends AnnotationPage {
    constructor() {
        super();
        if (this.d === undefined)
            this.d = null;
        if (this.sense === undefined)
            this.sense = null;
        if (this.text === undefined)
            this.text = null;
        if (this.checkboxError === undefined)
            this.checkboxError = null;
        this.text = new Label();
        this.checkboxError = new Label().textContent("Verificare che almeno una casella sia stata selezionata oppure di non aver selezionato la casella nessuna insieme ad altre caselle").hidden(true).className("PostError");
        this.sense = new Div(this.text);
        this.d = new Div(this.checkboxError);
    }
    /**
     *
     */
    connectToServer() {
        super.connectToServer();
        this.form.setAttributes("action", "translationValidation.jsp");
        $.getJSON("jsonData.jsp", "task=TRANSLATION_VALIDATION", (result, a, ctx) => {
            let json = result;
            let sWord = (json["word"]);
            let sDescription = (json["description"]);
            let sValidation = (json["translations"]);
            let synset = (json["synset"]);
            let translations = sValidation.split("$").slice(0);
            /* remove */ translations.splice(0, 1)[0];
            this.word.innerHTML(sWord);
            $(this.description.getElement()).text(sDescription);
            for (let index136 = 0; index136 < translations.length; index136++) {
                let s = translations[index136];
                {
                    this.d.appendTag(new Label(new Input().type("checkbox").name("answer").value(s)).className("form-check").innerHTML(" " + s));
                }
            }
            this.d.appendTag(new Label(new Input().type("checkbox").name("answer").value("nessuna").id("nessuna")).className("form-check").innerHTML(" nessuna"));
            $("#loader").hide();
            $(this.hiddenSynsetID.element).val(synset);
            $(this.hidden1.element).val(sWord);
            $(this.hidden2.element).val(sDescription);
            $(this.hidden3.element).val(/* replace */ sValidation.substring(1).split("$").join("; "));
            this.form.element.replaceChild(this.d.element, this.divTranslation.element);
            return null;
        });
        this.checkPost = (e) => {
            let checked = $("input[type=checkbox]:checked").length;
            if (checked === 0) {
                e.preventDefault();
                this.checkboxError.hidden(false);
            }
            else if (checked > 1 && $("#nessuna").is(":checked")) {
                e.preventDefault();
                this.checkboxError.hidden(false);
            }
            return null;
        };
        this.submit.element.onclick = (this.checkPost);
    }
    /**
     *
     */
    setStyle() {
        super.setStyle();
        this.d.$css("padding:0% 0% 4%");
        this.text.textContent("Selezionare le corrette traduzioni della parola seguente e della definizione fornita");
        this.sense.className("mx-auto border border-primary rounded p-2 m-2");
        this.taskName.textContent("Translation Validation");
    }
    /**
     *
     */
    build() {
        super.build();
        $(this.sense.element).insertBefore(this.divWord.element);
    }
}
TranslationValidationPage["__class"] = "TranslationValidationPage";
