/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Il valore "unchecked" in questo caso � sicuro: viene infatti eseguito il casting verso un elemento di tipo generico
 * T extends HTMLElement, imposto dalla sottoclasse che chiama il costruttore (ad esempio la classe Div estende {@code HTMLTreeELement<HTMLDivElement>}). Se il parametro tag passato al costruttore dalla sottoclasse � corretto il metodo document.createElement(String tag)
 * istanzier� un oggetto dello stesso tipo del tipo generico imposto dalla sottoclasse. Questo fa s� che {@link #element} sia un sottotipo specifico di HTMLElement, che dispone anche dei metodi specifici della
 * sua classe. Il parametro "tag" viene passato dal costrutture della sottoclasse senza che sia accessibile all�utente, cos� da evitare possibili ClassCastException che verrebbero individuati solo in fase di compilazione.
 * Viceversa, se una sottoclasse venisse sviluppata passando al costruttore di HTMLTreeElement un paremetro "tag" non appropriato, che istanzia cio� un tipo diverso di sottoclasse di HTMLElement da quello scelto per il generico, questo
 * darebbe una ClassCastException in fase di compilazione.
 * <br>
 * Inoltre viene effettuato un cast da HTMLTreeElement a R, classe generica che estende HTMLTreeElement, quindi il cast � sempre possibile.
 * <br>
 * Questo costruttore permette di istanziare un oggetto di tipo HTMLTreeElement e il rispettivo campo {@link #element} di tipo generico
 * T extends HTMLElement, il cui tipo specifico viene specificato dalla sottoclasse che lo ha chiamato.
 * <br> Istanzia inoltre un oggetto di tipo R extends HTMLTreeElement che verr� usanto per concatenare i metodi
 * secondo il tipo della sottoclasse che estende questa classe astratta.
 * <br><br>
 *
 *
 * @param {string} tag stringa che indica il tipo di HTMLElement da istanziare
 *
 * @see #element
 * @see HTMLTreeElement
 *
 * @class
 */
class HTMLTreeElement {
    constructor(tag) {
        if (this.element === undefined)
            this.element = null;
        if (this.self === undefined)
            this.self = null;
        this.element = document.createElement(tag);
        this.self = this;
    }
    /**
     * restituisce la collezione di tutti i figli innestati nell'elemento incapsulato dalla classe
     *
     * @return {HTMLCollection} il campo children di tipo HTMLCollection dell'oggetto di tipo T extends HTMLElement incapsulato dalla classe
     * @see #element
     */
    getList() {
        return this.element.children;
    }
    /**
     * restituisce il campo element di tipo T extends HTMLElement
     *
     * @return {HTMLElement} element campo della classe di tipo generico T
     *
     * @see #element
     */
    getElement() {
        return this.element;
    }
    /**
     *
     *
     * inserisce un numero variabile di elementi HTMLElement come figli del campo {@link #element}
     * <br><br>
     * I Varargs sono sicuri perch� saranno tutti di tipo HTMLTreeElement, quindi avranno tutti un campo {@link #element} e un metodo {@link #getElement()}
     * con il rispettivo metoto appendChild(HTMLElement)
     *
     * @param {Array} l varargs di oggetti di tipo HTMLTreeElement
     */
    appendTags(...l) {
        for (let index121 = 0; index121 < l.length; index121++) {
            let hte = l[index121];
            {
                this.element.appendChild(hte.getElement());
            }
        }
    }
    /**
     * aggiunge un figlio all'elemento di tipo HTMLElement del campo {@link #element}
     *
     * @param {HTMLTreeElement} child figlio del tag HTML
     *
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     */
    appendTag(child) {
        this.element.appendChild(child.getElement());
        return this.self;
    }
    /**
     * inserisce un figlio all'interno del campo {@link #element}, prima di quello
     * indicato nel secondo parametro.
     *
     * @param <R> elemento HTMLElement generico
     * @param <S> elemento HTMLElement generico
     * @param {HTMLTreeElement} newChild tag da inserire
     * @param {HTMLTreeElement} refChild tag dopo il quale inserirlo
     */
    insertTagBefore(newChild, refChild) {
        if (this.element.hasChildNodes())
            this.element.insertBefore(newChild.getElement(), refChild.getElement());
    }
    /**
     * prende un numero variabile di attributi CSS nel formato "key:value" e li imposta al campo {@link #element}
     *
     * @param {Array} keyvalues varargs di stringhe nel formato
     * "key:value"
     */
    $css(...keyvalues) {
        if (keyvalues != null && keyvalues.length > 0) {
            for (let index122 = 0; index122 < keyvalues.length; index122++) {
                let style = keyvalues[index122];
                {
                    if ((style.indexOf(":") != -1)) {
                        let l = style.split(":");
                        let key = l[0];
                        let value = l[1];
                        $(this.element).css(key, value);
                    }
                }
            }
        }
    }
    /**
     * Setta gli attributi del campo element di tipo T extends HTMLElement
     * @param {Array} attr varargs di stringhe
     */
    setAttributes(...attr) {
        if (attr != null && attr.length > 1)
            for (let i = 0; i < attr.length; i += 2) {
                {
                    if (i + 1 < attr.length)
                        this.element.setAttribute(attr[i], attr[i + 1]);
                }
                ;
            }
    }
    /**
     * Questo metodo setta il corrispondente attributo del campo element di tipo
     * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
     * <br><br>
     * Pu� essere usato per concatenare altri metodi dell'oggetto.
     * <br><br>
     * Si possono concatenare i
     * metodi della classe HTMLTreeElement quali id(), textContent(),
     * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
     * come ultimo metodo della concatenazione.
     * <br><br>
     * In caso di istanziazione di un oggetto, i metodi non concatenabili non
     * possono essere utilizzati.
     * <br><br>
     * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
     * essere seguiti da getElement()
     * <br><br>
     * @param {string} id stringa da inserire come id di {@link #element}
     * <br>
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     * * <br><br><br>
     * Metodi concatenabili:
     * @see #id(String)
     * @see #textContent(String)
     * @see #innerHTML(String)
     * @see #className(String)
     * @see #hidden(boolean)
     *
     */
    id(id) {
        this.element.id = id;
        return this.self;
    }
    /**
     * Questo metodo setta il corrispondente attributo del campo element di tipo
     * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
     * <br><br>
     * Pu� essere usato per concatenare altri metodi dell'oggetto.
     * <br><br>
     * Si possono concatenare i
     * metodi della classe HTMLTreeElement quali id(), textContent(),
     * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
     * come ultimo metodo della concatenazione.
     * <br><br>
     * In caso di istanziazione di un oggetto, i metodi non concatenabili non
     * possono essere utilizzati.
     * <br><br>
     * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
     * essere seguiti da getElement()
     * <br><br>
     * @param {string} text stringa da inserire come textContent di {@link #element}
     * <br>
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     * * <br><br><br>
     * Metodi concatenabili:
     * @see #id(String)
     * @see #textContent(String)
     * @see #innerHTML(String)
     * @see #className(String)
     * @see #hidden(boolean)
     */
    textContent(text) {
        this.element.textContent = text;
        return this.self;
    }
    /**
     * Questo metodo setta il corrispondente attributo del campo element di tipo
     * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
     * <br><br>
     * Pu� essere usato per concatenare altri metodi dell'oggetto.
     * <br><br>
     * Si possono concatenare i
     * metodi della classe HTMLTreeElement quali id(), textContent(),
     * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
     * come ultimo metodo della concatenazione.
     * <br><br>
     * In caso di istanziazione di un oggetto, i metodi non concatenabili non
     * possono essere utilizzati.
     * <br><br>
     * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
     * essere seguiti da getElement()
     * <br><br>
     * @param {string} text stringa da inserire come innerHTML di {@link #element}
     * <br>
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     * * <br><br><br>
     * Metodi concatenabili:
     * @see #id(String)
     * @see #textContent(String)
     * @see #innerHTML(String)
     * @see #className(String)
     * @see #hidden(boolean)
     */
    innerHTML(text) {
        this.element.innerHTML += " " + text;
        return this.self;
    }
    /**
     * Questo metodo setta il corrispondente attributo del campo element di tipo
     * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
     * <br><br>
     * Pu� essere usato per concatenare altri metodi dell'oggetto.
     * <br><br>
     * Si possono concatenare i
     * metodi della classe HTMLTreeElement quali id(), textContent(),
     * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
     * come ultimo metodo della concatenazione.
     * <br><br>
     * In caso di istanziazione di un oggetto, i metodi non concatenabili non
     * possono essere utilizzati.
     * <br><br>
     * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
     * essere seguiti da getElement()
     * <br><br>
     * @param {string} className stringa da inserire come className di {@link #element}
     * <br>
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     * * <br><br><br>
     * Metodi concatenabili:
     * @see #id(String)
     * @see #textContent(String)
     * @see #innerHTML(String)
     * @see #className(String)
     * @see #hidden(boolean)
     */
    className(className) {
        this.element.className = className;
        return this.self;
    }
    /**
     * Questo metodo setta il corrispondente attributo del campo element di tipo
     * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
     * <br><br>
     * Pu� essere usato per concatenare altri metodi dell'oggetto.
     * <br><br>
     * Si possono concatenare i
     * metodi della classe HTMLTreeElement quali id(), textContent(),
     * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
     * come ultimo metodo della concatenazione.
     * <br><br>
     * In caso di istanziazione di un oggetto, i metodi non concatenabili non
     * possono essere utilizzati.
     * <br><br>
     * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
     * essere seguiti da getElement()
     * <br><br>
     * @param {boolean} b boolean da inserire come hidden value di {@link #element} per renderlo o meno visibile
     * <br>
     * @return {HTMLTreeElement} oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
     * dover fare il casting.
     *
     * * <br><br><br>
     * Metodi concatenabili:
     * @see #id(String)
     * @see #textContent(String)
     * @see #innerHTML(String)
     * @see #className(String)
     * @see #hidden(boolean)
     */
    hidden(b) {
        this.element.hidden = b;
        return this.self;
    }
}
HTMLTreeElement["__class"] = "HTMLTreeElement";
