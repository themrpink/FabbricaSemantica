/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della classe Table con 2 parametri. Crea una tabella con il numero di righe e colonne specificate come parametri.<br>
 * Ogni cella viene identificata con un id = "r"+numero riga + "c" + numero colonna<br>
 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link #element} della superclasse
 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.<br><br>
 *
 * <br><br>inserisce le righe (oggetti di tipo Row) in una lista {@link #rowList}
 *
 * @param {number} rows numero di righe
 * @param {number} col  numero di colonne
 *
 * @see Row
 * @see Cell
 * @class
 * @extends HTMLTreeElement
 */
class Table extends HTMLTreeElement {
    constructor(rows, col) {
        super("table");
        if (this.rowList === undefined)
            this.rowList = null;
        if (this.rows === undefined)
            this.rows = 0;
        if (this.cols === undefined)
            this.cols = 0;
        this.rowList = ([]);
        this.rows = rows;
        this.cols = col;
        for (let i = 0; i < this.rows; i++) {
            {
                let row = new Table.Row(this);
                this.element.appendChild(row.element);
                /* add */ (this.rowList.push(row) > 0);
                for (let j = 0; j < col; j++) {
                    {
                        let cell = new Table.Cell(this);
                        cell.element.id = "r" + i + "c" + j;
                        row.addCell(cell);
                    }
                    ;
                }
            }
            ;
        }
    }
    /**
     * Inserisce una nuova riga nella tabella in ultima posizione e vi inserisce tante celle quante sono le colonne della
     * tabella come indicato nel costruttore. A ogni cella viene attribuito un id = "r"+numero riga + "c" + numero colonna<br>
     * <br>
     * @see Table
     * @see Row
     * @see Cell
     *
     */
    addRow() {
        let row = new Table.Row(this);
        this.element.appendChild(row.element);
        /* add */ (this.rowList.push(row) > 0);
        let i = this.rowList.length - 1;
        for (let j = 0; j < this.cols; j++) {
            {
                let cell = new Table.Cell(this);
                cell.element.id = "r" + i + "c" + j;
                row.addCell(cell);
            }
            ;
        }
    }
    /**
     * Restituisce la lista di righe della tabella
     * @return {Table.Row[]} rowList
     *
     * @see #rowList
     * @see Row
     */
    getRows() {
        return this.rowList;
    }
    /**
     * Restituisce una cella della tabella a partire dal suo id.<br>
     * Il formato dell`id � "r"+numero riga + "c" + numero colonna.
     * @param {string} ID "r"+numero riga + "c" + numero colonna
     * @return {Table.Cell} la cella richiesta se presente, altrimenti null
     */
    getCellFromID(ID) {
        for (let index133 = 0; index133 < this.rowList.length; index133++) {
            let r = this.rowList[index133];
            {
                let array135 = r.getCells();
                for (let index134 = 0; index134 < array135.length; index134++) {
                    let c = array135[index134];
                    if (((o1, o2) => { if (o1 && o1.equals) {
                        return o1.equals(o2);
                    }
                    else {
                        return o1 === o2;
                    } })(c.getID(), ID)) {
                        return c;
                    }
                }
            }
        }
        return null;
    }
}
Table["__class"] = "Table";
(function (Table) {
    /**
     * Costruttore della classe {@link Table.Row}
     * @class
     * @extends HTMLTreeElement
     */
    class Row extends HTMLTreeElement {
        constructor(__parent) {
            super("tr");
            this.__parent = __parent;
            if (this.listaCelle === undefined)
                this.listaCelle = null;
            this.listaCelle = ([]);
        }
        addCell(c) {
            if (this.element.childElementCount <= this.__parent.cols) {
                /* add */ (this.listaCelle.push(c) > 0);
                this.element.appendChild(c.element);
            }
        }
        /**
         * Restituisce la lista delle celle contenute nella riga
         * @return {Table.Cell[]} listaCelle
         *
         * @see Cell
         */
        getCells() {
            return this.listaCelle;
        }
    }
    Table.Row = Row;
    Row["__class"] = "Table.Row";
    /**
     * Costruttore con una parametro, card, che oltre a creare una cella incapsula al suo interno un oggetto di tipo {@link Card} necessario per il task {@link MemoryValidationPage}
     * @param {Card} card la carta del gioco
     * @class
     * @extends HTMLTreeElement
     */
    class Cell extends HTMLTreeElement {
        constructor(__parent, card) {
            if (((card != null && card instanceof Card) || card === null)) {
                let __args = Array.prototype.slice.call(arguments, [1]);
                super("td");
                if (this.card === undefined)
                    this.card = null;
                this.__parent = __parent;
                if (this.card === undefined)
                    this.card = null;
                (() => {
                    this.card = card;
                })();
            }
            else if (card === undefined) {
                let __args = Array.prototype.slice.call(arguments, [1]);
                super("td");
                if (this.card === undefined)
                    this.card = null;
                this.__parent = __parent;
                if (this.card === undefined)
                    this.card = null;
            }
            else
                throw new Error('invalid overload');
        }
        /**
         * Inserisce una {@link Card}
         * @param {Card} card la carta del gioco
         */
        insertCard(card) {
            this.card = card;
        }
        /**
         * Restituisce la {@link Card} incapsulata nella cella, null se non � presente
         * @return {Card} card
         */
        getCard() {
            return this.card;
        }
        /**
         * Restituisce l�id della cella
         * @return {string} element.id
         */
        getID() {
            return this.element.id;
        }
    }
    Table.Cell = Cell;
    Cell["__class"] = "Table.Cell";
})(Table || (Table = {}));
