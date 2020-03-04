/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruisce la pagina di SignUp.
 * @class
 * @extends Page
 */
class SignUpPage extends Page {
    constructor() {
        super();
        if (this.form === undefined)
            this.form = null;
        if (this.signup === undefined)
            this.signup = null;
        if (this.addLanguages === undefined)
            this.addLanguages = null;
        if (this.username === undefined)
            this.username = null;
        if (this.email === undefined)
            this.email = null;
        if (this.password === undefined)
            this.password = null;
        if (this.passwordRepeat === undefined)
            this.passwordRepeat = null;
        if (this.titolo === undefined)
            this.titolo = null;
        if (this.lingue === undefined)
            this.lingue = null;
        if (this.lingueExtra === undefined)
            this.lingueExtra = null;
        if (this.conteggio === undefined)
            this.conteggio = null;
        if (this.label1 === undefined)
            this.label1 = null;
        if (this.label2 === undefined)
            this.label2 = null;
        if (this.label3 === undefined)
            this.label3 = null;
        if (this.label4 === undefined)
            this.label4 = null;
        if (this.checkboxError === undefined)
            this.checkboxError = null;
        if (this.labelEng === undefined)
            this.labelEng = null;
        if (this.labelIta === undefined)
            this.labelIta = null;
        if (this.signupError === undefined)
            this.signupError = null;
        if (this.passRepeatChange === undefined)
            this.passRepeatChange = null;
        if (this.checkPassChange === undefined)
            this.checkPassChange = null;
        if (this.addLanguage === undefined)
            this.addLanguage = null;
        if (this.checkboxSelected === undefined)
            this.checkboxSelected = null;
        this.username = new Input().placeholder("choose your name").type("text").name("username").id("username");
        this.email = new Input().placeholder("insert your email").type("email").name("email").id("email");
        this.passwordRepeat = new Input().placeholder("confirm the password").type("password").name("passwordRepeat").id("passwordRepeat");
        this.password = new Input().placeholder("choose a password").type("password").name("password").id("password");
        this.conteggio = new Label();
        this.label1 = new Label();
        this.label2 = new Label();
        this.label3 = new Label();
        this.label4 = new Label();
        this.labelEng = new Label(new Input().type("checkbox").name("motherLanguages").value("EN"));
        this.labelIta = new Label(new Input().type("checkbox").name("motherLanguages").value("IT"));
        this.checkboxError = new Label();
        this.signupError = new Label();
        this.signup = new Button();
        this.addLanguages = new Button().type("button");
        this.titolo = new Div();
        this.lingue = new Div(this.label4, this.labelEng, this.labelIta, this.checkboxError);
        this.lingueExtra = new Div(this.conteggio, this.addLanguages);
        this.form = new Form(this.titolo, this.label1, this.username, this.label2, this.email, this.signupError, this.label3, this.password, this.passwordRepeat, this.lingue, this.lingueExtra, this.signup);
        this.setFunctions();
    }
    /**
     *
     */
    setDefaultAttributes() {
        super.setDefaultAttributes();
        this.titolo.innerHTML("<h2>Sign Up</h2>");
        this.label1.textContent("Username");
        this.label2.textContent("Email");
        this.label3.textContent("Password");
        this.label4.textContent("Choose your mother language(s)");
        this.labelEng.element.innerHTML += " English";
        this.labelEng.element.className = "form-check";
        this.labelIta.element.innerHTML += " Italian";
        this.labelIta.element.className = "form-check";
        this.checkboxError.textContent("please select at least one mother language");
        this.checkboxError.hidden(true);
        this.checkboxError.className("loginError");
        this.signupError.textContent("email already in use");
        this.signupError.hidden(true);
        this.signupError.className("loginError");
        this.username.setAttributes("class", "form-control", "display", "inline");
        this.username.element.required = true;
        this.username.element.setAttribute("minLength", "6");
        this.email.setAttributes("class", "form-control");
        this.email.getElement().required = true;
        this.password.setAttributes("class", "form-control");
        this.password.element.required = true;
        this.password.element.addEventListener("change", (this.checkPassChange), true);
        this.password.element.setAttribute("minLength", "8");
        this.passwordRepeat.setAttributes("class", "form-control");
        this.passwordRepeat.element.required = true;
        this.passwordRepeat.element.onkeyup = (this.passRepeatChange);
        this.signup.setAttributes("value", "sign up");
        this.signup.element.onclick = (this.checkboxSelected);
        this.form.element.id = "signup_form";
        this.lingue.element.id = "lingue";
        this.lingueExtra.element.id = "lingueExtra";
        this.addLanguages.element.onclick = (this.addLanguage);
        this.addLanguages.setAttributes("value", "add language");
    }
    /**
     * istanzia le funzioni della pagina, viene chiamata direttamente dal
     * costruttore
     * @private
     */
    /*private*/ setFunctions() {
        this.passRepeatChange = (a) => {
            if (this.password.element.value !== this.passwordRepeat.element.value)
                this.passwordRepeat.element.setCustomValidity("Le password non coincidono");
            else
                this.passwordRepeat.element.setCustomValidity("");
            return a;
        };
        this.checkPassChange = (a) => {
            if (!((o1, o2) => { if (o1 && o1.equals) {
                return o1.equals(o2);
            }
            else {
                return o1 === o2;
            } })(this.password.element.value, this.passwordRepeat.element.value))
                this.passwordRepeat.element.setCustomValidity("Le password non coincidono");
            else
                this.passwordRepeat.element.setCustomValidity("");
            return a;
        };
        this.addLanguage = (a) => {
            let level = document.createElement("option");
            let a1 = document.createElement("option");
            let a2 = document.createElement("option");
            let b1 = document.createElement("option");
            let b2 = document.createElement("option");
            let c1 = document.createElement("option");
            let c2 = document.createElement("option");
            let hse = document.createElement("select");
            hse.className = "form-control";
            hse.name = "select";
            hse.appendChild(level);
            hse.appendChild(a1);
            hse.appendChild(a2);
            hse.appendChild(b1);
            hse.appendChild(b2);
            hse.appendChild(c1);
            hse.appendChild(c2);
            level.textContent = "level";
            a1.value = "A1";
            a1.textContent = "A1";
            a2.value = "A2";
            a2.textContent = "A2";
            b1.value = "B1";
            b1.textContent = "B1";
            b2.value = "B2";
            b2.textContent = "B2";
            c1.value = "C1";
            c1.textContent = "C1";
            c2.value = "C2";
            c2.textContent = "C2";
            let input = new Input().placeholder("your language").type("text").name("lang").id("lang").className("form-control");
            let main = new Div().className("input-group mb-3");
            main.element.appendChild(input.element);
            main.element.appendChild(hse);
            $("#lingueExtra").append(main.element);
            return null;
        };
        this.checkboxSelected = (e) => {
            let checked = $("input[type=checkbox]:checked").length;
            if (checked === 0) {
                e.preventDefault();
                this.checkboxError.hidden(false);
            }
            else {
                $(this.form.element).on("submit", (ev, a) => {
                    ev.preventDefault();
                    this.signupError.hidden(true);
                    let s = $(this.form.element).serialize();
                    $.post("signup.jsp", s, (data, q, xhr) => {
                        let r = data;
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(r, "false")) {
                            this.signupError.hidden(false);
                        }
                        else
                            window.location.href = "login.html";
                        return null;
                    });
                    return null;
                });
            }
            return null;
        };
        $(this.lingue.element).change((e) => {
            this.checkboxError.hidden(true);
            return null;
        });
    }
    /**
     *
     */
    connectToServer() {
        this.form.action("signup.jsp");
        $.get("isLoggedIn.jsp", (result, a, ctx) => {
            let b = result;
            $("#signup").hide();
            if (((o1, o2) => { if (o1 && o1.equals) {
                return o1.equals(o2);
            }
            else {
                return o1 === o2;
            } })(b, "false")) {
                $("#account").hide();
                $("#signout").hide();
            }
            else {
                $("#account").text(b);
                $("#login").hide();
                $("#signup").hide();
            }
            return null;
        });
    }
    /**
     *
     */
    setStyle() {
        $(this.signup.element).css("float", "right");
        $(this.signup.element).css("margin", "10px");
        this.form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
        this.addLanguages.className("btn btn-outline-secondary btn-sm");
        this.lingue.$css("padding:0% 0% 4%");
    }
    /**
     *
     */
    build() {
        this.mainForm.appendTag(this.form);
        $("body").append(this.header.element, this.links.element, this.mainForm.element);
    }
}
SignUpPage["__class"] = "SignUpPage";
