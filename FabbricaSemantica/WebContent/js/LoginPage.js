/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruisce la pagina di Login.
 * @class
 * @extends Page
 */
class LoginPage extends Page {
    constructor() {
        super();
        if (this.form === undefined)
            this.form = null;
        if (this.signin === undefined)
            this.signin = null;
        if (this.password === undefined)
            this.password = null;
        if (this.email === undefined)
            this.email = null;
        if (this.titolo === undefined)
            this.titolo = null;
        if (this.label2 === undefined)
            this.label2 = null;
        if (this.label3 === undefined)
            this.label3 = null;
        if (this.loginError === undefined)
            this.loginError = null;
        this.signin = new Button();
        this.password = new Input().placeholder("insert your password").type("password").name("password").id("password");
        this.email = new Input().placeholder("insert your email").type("email").name("email").id("email");
        this.titolo = new Div();
        this.label2 = new Label();
        this.label3 = new Label();
        this.loginError = new Label();
        this.form = new Form(this.titolo, this.label3, this.email, this.label2, this.password, this.loginError, this.signin);
    }
    /**
     *
     */
    setDefaultAttributes() {
        super.setDefaultAttributes();
        this.label2.textContent("Password");
        this.label3.textContent("Email");
        this.titolo.innerHTML("<h2>Login</h2>");
        this.email.setAttributes("class", "form-control", "display", "inline");
        this.email.element.required = true;
        this.password.setAttributes("class", "form-control");
        this.password.element.required = true;
        this.loginError.textContent("dati di autenticazione errati!");
        this.loginError.className("loginError");
        this.loginError.hidden(true);
        this.signin.setAttributes("value", "sign in");
    }
    /**
     *
     */
    connectToServer() {
        this.form.setAttributes("action", "login.jsp");
        $(this.form.element).on("submit", (e, a) => {
            e.preventDefault();
            this.loginError.hidden(true);
            let s = $(this.form.element).serialize();
            $.post("login.jsp", s, (data, q, xhr) => {
                let r = data;
                if (((o1, o2) => { if (o1 && o1.equals) {
                    return o1.equals(o2);
                }
                else {
                    return o1 === o2;
                } })(r, "false")) {
                    this.loginError.hidden(false);
                }
                else
                    this.form.element.submit();
                return null;
            });
            return null;
        });
        $.get("isLoggedIn.jsp", (result, a, ctx) => {
            let b = result;
            $("#login").hide();
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
            }
            return null;
        });
    }
    /**
     *
     */
    setStyle() {
        $(this.signin.element).css("float", "right");
        $(this.signin.element).css("margin", "10px");
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
LoginPage["__class"] = "LoginPage";
