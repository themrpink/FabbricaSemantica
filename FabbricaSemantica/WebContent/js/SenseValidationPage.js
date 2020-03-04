/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina SenseValidation.
 * @class
 * @extends AnnotationPage
 */
class SenseValidationPage extends AnnotationPage {
    constructor() {
        super();
        if (this.d === undefined)
            this.d = null;
        if (this.instruction === undefined)
            this.instruction = null;
        if (this.sense === undefined)
            this.sense = null;
        if (this.radio === undefined)
            this.radio = null;
        if (this.radioYes === undefined)
            this.radioYes = null;
        if (this.radioNo === undefined)
            this.radioNo = null;
        if (this.text === undefined)
            this.text = null;
        if (this.yes === undefined)
            this.yes = null;
        if (this.no === undefined)
            this.no = null;
        if (this.checkPost === undefined)
            this.checkPost = null;
        this.text = new Label();
        this.yes = new Label();
        this.no = new Label();
        this.instruction = new Div(this.text);
        this.sense = new Div();
        this.radioYes = new Input().type("radio").value("yes");
        this.radioNo = new Input().type("radio").value("no");
        this.radio = new Div(this.radioYes, this.yes, this.radioNo, this.no);
        this.d = new Div(this.instruction, this.sense, this.radio);
    }
    /**
     *
     */
    setDefaultAttributes() {
        super.setDefaultAttributes();
        this.radio.className("form-check-inline");
        this.text.textContent("\u00c8 il seguente senso appropriato?");
        this.yes.textContent("Yes");
        this.no.textContent("No");
        this.radioYes.element.name = "sense";
        this.radioNo.element.name = "sense";
    }
    /**
     *
     */
    connectToServer() {
        super.connectToServer();
        this.form.setAttributes("action", "senseValidation.jsp");
        $.getJSON("jsonData.jsp", "task=SENSE_VALIDATION", (result, a, ctx) => {
            let json = result;
            let sWord = (json["word"]);
            let sExample = (json["example"]);
            let sSense = (json["sense"]);
            let synset = (json["synset"]);
            $("#loader").hide();
            $(this.hiddenSynsetID.element).val(synset);
            $(this.hidden1.element).val(sWord);
            $(this.hidden2.element).val(sExample);
            $(this.hidden3.element).val(sSense);
            $(this.word.element).text(sWord);
            $(this.description.element).text(sExample);
            $(this.sense.element).text("\"" + sSense + "\"");
            return null;
        });
        this.checkPost = (e) => {
            if (!$("input[type=radio]").is(":checked")) {
                e.preventDefault();
                this.postError.hidden(false);
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
        this.instruction.className("mx-auto border border-primary rounded p-1");
        this.sense.$css("margin:4% 5% 4% 5%", "font-style:italic");
        this.yes.className("pl-2 pr-1");
        this.no.className("pl-2 pr-1");
        this.taskName.textContent("Sense Validation");
    }
    /**
     *
     */
    build() {
        super.build();
        this.form.element.replaceChild(this.d.element, this.divTranslation.element);
    }
}
SenseValidationPage["__class"] = "SenseValidationPage";
