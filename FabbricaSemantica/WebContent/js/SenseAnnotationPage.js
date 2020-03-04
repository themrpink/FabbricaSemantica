/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina SenseAnnotation.
 * @class
 * @extends AnnotationPage
 */
class SenseAnnotationPage extends AnnotationPage {
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
        this.checkboxError = new Label().textContent("Verificare che almeno una casella sia stata selezionata").hidden(true).className("PostError");
        this.d = new Div(this.checkboxError);
        this.text = new Label();
        this.sense = new Div(this.text);
    }
    /**
     *
     */
    connectToServer() {
        super.connectToServer();
        this.form.setAttributes("action", "senseAnnotation.jsp");
        $.getJSON("jsonData.jsp", "task=SENSE_ANNOTATION", (result, a, ctx) => {
            let json = result;
            let sWord = (json["word"]);
            let sDescription = (json["description"]);
            let sAnnotations = (json["senses"]);
            let synset = (json["synset"]);
            let senses = sAnnotations.split("$").slice(0);
           // let n = senses.length;
           // if (n > 1)
            //    senses.splice(n - 1, 1)[0];
            this.word.innerHTML(sWord);
            $(this.description.getElement()).text(sDescription);
            let ll = ([]);
            for (let index132 = 0; index132 < senses.length; index132++) {
                let s = senses[index132];
                {
                    let i = ll.length;
                    /* add */ (ll.push(new Label(new Input().type("checkbox").name("answer").value(s))) > 0);
                    /* get */ ll[i].element.className = "form-check";
                    /* get */ ll[i].element.innerHTML += " " + s;
                    this.d.appendTag(/* get */ ll[i]);
                }
            }
            $("#loader").hide();
            $(this.hiddenSynsetID.element).val(synset);
            $(this.hidden1.element).val(sWord);
            $(this.hidden2.element).val(sDescription);
            $(this.hidden3.element).val(/* replace */ sAnnotations.substring(1).split("$").join("; "));
            this.form.element.replaceChild(this.d.element, this.divTranslation.element);
            return null;
        });
        this.checkPost = (e) => {
            let checked = $("input[type=checkbox]:checked").length;
            if (checked === 0) {
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
        this.d.$css("padding:0.5% 0% 4%");
        this.text.textContent("Selezionare i corretti significati della parola seguente nell\'esempio fornito");
        this.sense.className("mx-auto border border-primary rounded p-2 m-2");
        this.taskName.textContent("Sense Annotation");
    }
    /**
     *
     */
    build() {
        super.build();
        $(this.sense.element).insertBefore(this.divWord.element);
    }
}
SenseAnnotationPage["__class"] = "SenseAnnotationPage";
