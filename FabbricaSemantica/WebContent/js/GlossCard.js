/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruisce una carta contenente una glossa.
 * @param {string} gloss testo glossa
 * @param {string} synset Id
 * @class
 * @extends Card
 */
class GlossCard extends Card {
    constructor(gloss, synset) {
        super("<i>" + gloss + "</i>", synset);
    }
    /**
     * Restituisce la glossa in un tag per il corsivo.
     * @return {string} s glossa in corsivo
     */
    getPlainText() {
        let s = super.getText().split("<i>").join("");
        /* replace */ s.split("</i>").join("");
        return s;
    }
}
GlossCard["__class"] = "GlossCard";
