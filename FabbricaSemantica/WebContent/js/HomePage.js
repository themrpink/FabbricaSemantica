/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della pagina, aggiunge il pulsante di accesso casuale alle task.
 * @class
 * @extends Page
 */
class HomePage extends Page {
    constructor() {
        super();
        if (this.form === undefined)
            this.form = null;
        if (this.start === undefined)
            this.start = null;
        if (this.pleaseLogin === undefined)
            this.pleaseLogin = null;
        if (this.welcome === undefined)
            this.welcome = null;
        this.welcome = new Label().className("text-success");
        this.start = new Button();
        this.pleaseLogin = new Label();
        this.form = new Form(this.start, this.pleaseLogin, this.welcome);
    }
    setDefaultAttributes() {
        super.setDefaultAttributes();
        this.start.setAttributes("value", "start");
        this.start.element.className += " btn-block";
        this.pleaseLogin.element.innerText = "Please login or sign up to access this page";
        this.pleaseLogin.element.hidden = true;
        this.form.element.appendChild(this.start.element);
    }
    /**
     *
     */
    connectToServer() {
        this.form.setAttributes("action", "redirect.jsp");
        $.get("isLoggedIn.jsp", (result, a, ctx) => {
            let b = result;
            if (((o1, o2) => { if (o1 && o1.equals) {
                return o1.equals(o2);
            }
            else {
                return o1 === o2;
            } })(b, "false")) {
                $(this.start.element).click((e) => {
                    e.preventDefault();
                    this.pleaseLogin.hidden(false);
                    return null;
                });
            }
            else {
                this.welcome.hidden(false).textContent("Benvenuto " + b + "! Premi su start per iniziare le task");
            }
            return null;
        });
    }
    /**
     *
     */
    setStyle() {
        this.pleaseLogin.className("text-danger mx-auto");
        this.form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
    }
    /**
     *
     */
    build() {
        this.mainForm.appendTag(this.form);
        $("body").append(this.header.element, this.links.element, this.mainForm.element);
    }
}
HomePage["__class"] = "HomePage";
