/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Factory che istanzia tutte le pagine del progetto.<br><br>
 * Ogni pagina HTML passa un valore che viene a sua volta passato come parametro
 * al metodo statico {@link #buildPage(Pages) buildPage} dopo essere stato convertito in un elemento della enum {@link
 * Pages}.
 * @class
 */
class PageFactory {
    /**
     * Metodo statico che permette di istanziare le pagine di Fabbrica Semantica come oggetto di tipo {@link Page}.<br>
     * Tutte le pagine del progetto estendono infatti la classe astratta {@link Page} i cui metodi vengono qui chiamati per costruire la pagina.
     * Prende come parametro un oggetto di tipo {@link Pages} che indica quale pagina verr� istanziata.<br><br>
     *
     * vengono chiamati i metodi:<br>
     * {@link Page#setDefaultAttributes() setDefaultAttributes}<br>
     * {@link Page#connectToServer() Page.connectToServer}<br>
     * {@link Page#setStyle() Page.setStyle}<br>
     * {@link Page#build() Page.build}<br>
     *
     * @param {Pages} p pagina da istanziare
     */
    static buildPage(p) {
        let page = null;
        switch ((p)) {
            case Pages.HOME:
                page = new HomePage();
                break;
            case Pages.TRANSLATION_ANNOTATION:
                page = new TranslationAnnotationPage();
                break;
            case Pages.WORD_ANNOTATION:
                page = new WordAnnotationPage();
                break;
            case Pages.ANNOTATION_PAGE:
                page = new AnnotationPage();
                break;
            case Pages.DEFINITION_ANNOTATION:
                page = new DefinitionAnnotationPage();
                break;
            case Pages.SENSE_ANNOTATION:
                page = new SenseAnnotationPage();
                break;
            case Pages.SENSE_VALIDATION:
                page = new SenseValidationPage();
                break;
            case Pages.TRANSLATION_VALIDATION:
                page = new TranslationValidationPage();
                break;
            case Pages.MEMORY_VALIDATION:
                page = new MemoryValidationPage();
                break;
            case Pages.SIGNUP:
                page = new SignUpPage();
                break;
            case Pages.LOGIN:
                page = new LoginPage();
                break;
            case Pages.USER_PAGE:
                page = new UserPage();
                break;
            case Pages.ERROR_PAGE:
                page = new ErrorPage();
                break;
            default:
                break;
        }
        if (page != null) {
            page.setDefaultAttributes();
            page.connectToServer();
            page.setStyle();
            page.build();
        }
    }
    static main(args) {
        let pageName = document.getElementById("main").getAttribute("data-name");
        console.log("called: " + pageName);
        PageFactory.buildPage(/* Enum.valueOf */ Pages[pageName]);
    }
}
PageFactory["__class"] = "PageFactory";
PageFactory.main(null);
