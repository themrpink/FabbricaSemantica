/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * questa � la pagina iniziale, legge l'attributo specificato nello script della
 * pagina HTML e lancia il PageFactory che costruir� la pagina richiesta
 *
 * @author themr
 * @class
 */
class HTMLPage {
    static main(args) {
        let pageName = document.getElementById("main").getAttribute("data-name");
        console.log("called: " + pageName);
        PageFactory.buildPage(/* Enum.valueOf */ Pages[pageName]);
    }
}
HTMLPage["__class"] = "HTMLPage";
HTMLPage.main(null);
