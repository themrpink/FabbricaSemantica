/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruisce una carta dal gioco del memory
 * @param {string} testo generalmente parola o glossa
 * @param {string} synset id del synset
 * @class
 */
class Card {
    constructor(testo, synset) {
        if (this.selected === undefined)
            this.selected = false;
        if (this.testo === undefined)
            this.testo = null;
        if (this.synset === undefined)
            this.synset = null;
        this.selected = false;
        this.testo = testo;
        this.synset = synset;
    }
    /**
     * Controlla se la carta � stata selezionata
     * @return {boolean} true se selezionata
     */
    isSelected() {
        return this.selected;
    }
    /**
     * Seleziona una carta, corrisponde a scoprirla nel gioco.
     */
    select() {
        this.selected = true;
    }
    /**
     * Deseleziona la carta.
     */
    unselect() {
        this.selected = false;
    }
    /**
     * Restituisce il testo contenuto nella carta.
     * @return {string} testo generalmente parola o glossa
     */
    getText() {
        return this.testo;
    }
    /**
     * Restituisce l�id del synset associato alla carta.
     * @return {string} synset ID
     */
    getSynset() {
        return this.synset;
    }
}
Card["__class"] = "Card";
