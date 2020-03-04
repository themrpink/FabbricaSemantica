/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe astratta Page. Istanzia i tag, crea i menu, controlla se l�utente � connesso
 * e in base a questo imposta i link in maniera appropriata.
 * @class
 */
class Page {
    constructor() {
        if (this.header === undefined)
            this.header = null;
        if (this.mainForm === undefined)
            this.mainForm = null;
        if (this.links === undefined)
            this.links = null;
        if (this.menuLaterale === undefined)
            this.menuLaterale = null;
        if (this.loader === undefined)
            this.loader = null;
        if (this.title === undefined)
            this.title = null;
        if (this.taskName === undefined)
            this.taskName = null;
        this.title = new Div().textContent("Fabbrica Semantica").id("title").className("mx-auto");
        this.taskName = new Div().id("taskName");
        this.header = new Div(this.title, this.taskName).id("header");
        this.links = new Page.Navbar(this).getMenu().id("links");
        this.menuLaterale = new Page.Sidebar(this).getMenu();
        this.mainForm = new Div().id("mainForm");
        this.loader = new Div().id("loader").className("mx-auto").hidden(true);
        this.checkUser();
    }
    /**
     * Imposta propriet� e contenuti dei tag HTML necessari alla corretta visualizzazione delle pagine.
     */
    setDefaultAttributes() {
        $("body").append(this.header.element, this.links.element, this.menuLaterale.element, this.mainForm.element);
    }
    checkUser() {
        $.get("isLoggedIn.jsp", (result, a, ctx) => {
            let b = result;
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
                $("#signup").hide();
                $("#account").text(b);
                $("#login").hide();
            }
            return null;
        });
    }
}
Page["__class"] = "Page";
(function (Page) {
    /**
     * Barra di navigazione
     *
     * @class
     */
    class Navbar {
        constructor(__parent) {
            this.__parent = __parent;
            if (this.menu === undefined)
                this.menu = null;
            if (this.home === undefined)
                this.home = null;
            if (this.signup === undefined)
                this.signup = null;
            if (this.login === undefined)
                this.login = null;
            if (this.signout === undefined)
                this.signout = null;
            if (this.account === undefined)
                this.account = null;
            this.home = new Link().text("Home").href("homepage.html").id("home");
            this.signup = new Link().text("Sign up").href("signup.html").id("signup");
            this.login = new Link().text("Login").href("login.html").id("login");
            this.signout = new Link().text("Logout").href("logout.jsp").id("signout");
            this.account = new Link().text(" ").href("user.html").id("account");
            this.menu = new Div(this.home, this.signup, this.login, this.signout, this.account).className("navbar");
        }
        getMenu() {
            return this.menu;
        }
    }
    Page.Navbar = Navbar;
    Navbar["__class"] = "Page.Navbar";
    /**
     * Menu laterale a scomparsa
     * @class
     */
    class Sidebar {
        constructor(__parent) {
            this.__parent = __parent;
            if (this.menu === undefined)
                this.menu = null;
            if (this.wordAnnotation === undefined)
                this.wordAnnotation = null;
            if (this.translationAnnotation === undefined)
                this.translationAnnotation = null;
            if (this.translationValidation === undefined)
                this.translationValidation = null;
            if (this.senseAnnotation === undefined)
                this.senseAnnotation = null;
            if (this.senseValidation === undefined)
                this.senseValidation = null;
            if (this.definitionAnnotation === undefined)
                this.definitionAnnotation = null;
            if (this.memoryValidation === undefined)
                this.memoryValidation = null;
            this.wordAnnotation = new Link().text("Word annotation").href("word_annotation.html").id("wa");
            this.translationAnnotation = new Link().text("Translation annotation").href("translation_annotation.html").id("ta");
            this.translationValidation = new Link().text("Translation validation").href("translation_validation.html").id("tv");
            this.senseAnnotation = new Link().text("Sense annotation").href("sense_annotation.html").id("sa");
            this.senseValidation = new Link().text("Sense validation").href("sense_validation.html").id("sv");
            this.definitionAnnotation = new Link().text("Definition annotation").href("definition_annotation.html").id("da");
            this.memoryValidation = new Link().text("Memory validation").href("memory_validation.html").id("mv");
            this.menu = new Div(this.wordAnnotation, this.translationAnnotation, this.translationValidation, this.senseAnnotation, this.senseValidation, this.definitionAnnotation, this.memoryValidation);
            this.menu.className("sidenav").id("mySidenav");
        }
        getMenu() {
            return this.menu;
        }
    }
    Page.Sidebar = Sidebar;
    Sidebar["__class"] = "Page.Sidebar";
})(Page || (Page = {}));
