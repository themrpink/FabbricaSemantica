/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore, istanzia una pagina {@link MemoryValidationPage}
 * @class
 * @extends Page
 */
class MemoryValidationPage extends Page {
    constructor() {
        super();
        /*private*/ this.previousID = "";
        if (this.table === undefined)
            this.table = null;
        if (this.endGame === undefined)
            this.endGame = null;
        if (this.gameMenu === undefined)
            this.gameMenu = null;
        if (this.menu === undefined)
            this.menu = null;
        if (this.divLevel === undefined)
            this.divLevel = null;
        if (this.divMessage === undefined)
            this.divMessage = null;
        if (this.divPagina === undefined)
            this.divPagina = null;
        if (this.task === undefined)
            this.task = null;
        if (this.formResults === undefined)
            this.formResults = null;
        if (this.task2 === undefined)
            this.task2 = null;
        if (this.combinazioni === undefined)
            this.combinazioni = null;
        if (this.risposteConSynset === undefined)
            this.risposteConSynset = null;
        if (this.cardList === undefined)
            this.cardList = null;
        if (this.soluzioni === undefined)
            this.soluzioni = null;
        if (this.one === undefined)
            this.one = null;
        if (this.two === undefined)
            this.two = null;
        if (this.convalida === undefined)
            this.convalida = null;
        if (this.erroreConvalida === undefined)
            this.erroreConvalida = null;
        if (this.suggerimento === undefined)
            this.suggerimento = null;
        if (this.divSuggerimento === undefined)
            this.divSuggerimento = null;
        if (this.skip === undefined)
            this.skip = null;
        if (this.event === undefined)
            this.event = null;
        this.table = new Table(0, 0);
        this.task = new Input().name("task").value("MEMORY_VALIDATION").hidden(true);
        this.gameMenu = new MemoryValidationPage.GameMenu(this);
        this.menu = this.gameMenu.getMenu();
        this.convalida = new Button().name("convalida").value("convalida coppia").id("convalida").className("btn btn-outline-success");
        this.erroreConvalida = new Label().textContent("Le caselle selezionatea non possono essere convalidate perch\u00e8 non sono una coppia parola-glossa").className("label label-warning").id("erroreConvalida");
        this.endGame = new Button().name("endGame").value("termina partita").id("endGame").className("btn btn-outline-warning");
        this.task2 = new Input().name("task2").hidden(true);
        this.formResults = new Form(this.task2);
        this.suggerimento = new Button().name("suggerimento").value("suggerimento").id("suggerimento").className("btn btn-outline-info");
        this.divSuggerimento = new Div().className("alert alert-info alert-dismissible").id("divSuggerimento");
        this.skip = new Button().value("Skip").name("skip").className("btn btn-outline-primary").id("Skip");
        this.soluzioni = new Button().value("mostra soluzioni").name("mostra soluzioni").className("btn btn-outline-info").hidden(true);
        this.divLevel = new Div(this.suggerimento, this.convalida, this.endGame, this.skip, this.gameMenu.open).id("divLevel");
        this.divMessage = new Div(this.divSuggerimento, this.erroreConvalida);
        this.divPagina = new Div(this.loader, this.table, this.soluzioni, this.formResults);
        this.event = (e) => {
            this.loader.hidden(false);
            return null;
        };
    }
    /**
     *
     */
    connectToServer() {
        this.skip.element.onclick = (EVE) => {
            window.location.href = "redirect.jsp";
            return null;
        };
        $.get("isLoggedIn.jsp", (result, b, ctx) => {
            let s = result;
            if (((o1, o2) => { if (o1 && o1.equals) {
                return o1.equals(o2);
            }
            else {
                return o1 === o2;
            } })(s, "false")) {
                this.divLevel.hidden(true);
                this.erroreConvalida.textContent("Please login or sign up to access this page");
                $(this.erroreConvalida.element).show();
            }
            return null;
        });
        $(this.gameMenu.createTable.element).click((e) => {
            e.preventDefault();
            let rangeLevel = "LEVEL" + this.gameMenu.level.element.value;
            this.gameMenu.level.value(rangeLevel);
            this.gameMenu.levelRangeTranslated.value(rangeLevel);
            let dimTable = $("select").val();
            this.table = new Table(/* parseInt */ parseInt(dimTable[0]), /* parseInt */ parseInt(dimTable[2]));
            $("table").replaceWith(this.table.element);
            this.gameMenu.tableSize.value("" + ((parseInt(dimTable[0]) * parseInt(dimTable[2])) / 2 | 0));
            let dati = $(this.gameMenu.form1.element).serialize();
            console.log(dati);
            $(this.table.element.children).click((ev) => {
                if (!this.table.getCellFromID($(ev.target).attr("id")).getCard().isSelected())
                    if (this.one == null) {
                        this.one = this.table.getCellFromID($(ev.target).attr("id"));
                        $(ev.target.firstChild).show();
                        this.two = null;
                    }
                    else if (this.two == null) {
                        if (!((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.one.getID(), $(ev.target).attr("id"))) {
                            this.two = this.table.getCellFromID($(ev.target).attr("id"));
                            $(ev.target.firstChild).show();
                            this.previousID = $(ev.target).attr("id");
                        }
                    }
                    else if (this.one != null && this.two != null) {
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.one.getID(), $(ev.target).attr("id")) || ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.two.getID(), $(ev.target).attr("id"))) {
                            $(this.one.element.firstChild).hide();
                            $(this.two.element.firstChild).hide();
                            this.one = null;
                            this.two = null;
                        }
                        else if (!((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.one.getID(), $(ev.target).attr("id")) || !((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.two.getID(), $(ev.target).attr("id"))) {
                            if (!this.table.getCellFromID(this.previousID).getCard().isSelected()) {
                                $(this.one.element.firstChild).hide();
                                $(this.two.element.firstChild).hide();
                            }
                            this.one = this.table.getCellFromID($(ev.target).attr("id"));
                            $(ev.target.firstChild).show();
                            this.two = null;
                        }
                    }
                return null;
            });
            this.gameMenu.getMenu().element.style.width = "0%";
            $.post("memoryGame.jsp", dati, (result, r, t) => {
                let sWord = result;
                console.log(sWord);
                let synsets = sWord.split("#");
                console.log(synsets);
                let syns = ([]);
                let l = 0;
                let res = "";
                for (let index123 = 0; index123 < synsets.length; index123++) {
                    let str = synsets[index123];
                    {
                        if (l % 2 === 0)
                            (syns.push(str) > 0);
                        else if (l === synsets.length - 1)
                            res += str;
                        else
                            res += str + "$";
                        l++;
                    }
                }
                let results = res.split("$");
                let n = results.length;
                console.log(results);
                this.combinazioni = ({});
                this.cardList = ([]);
                this.risposteConSynset = ({});
                for (let i = 0; i < n; i += 2) {
                    (this.combinazioni[results[i]] = results[i + 1]);
                }
                let indice_synset = 0;
                for (let i = 0; i < n; i++) {
                    {
                        let str = results[i];
                        let synset = syns[indice_synset];
                        let check = str.charAt(str.length - 2);
                        let numb = "";
                        if ((c => c.charCodeAt == null ? c : c.charCodeAt(0))(check) == '*'.charCodeAt(0)) {
                            numb = str.substring(str.length - 1);
                            /* add */ (this.cardList.push(new WordCard(/* replace */ str.split("*" + numb).join(""), synset)) > 0);
                            indice_synset += 1;
                        }
                        else if ((c => c.charCodeAt == null ? c : c.charCodeAt(0))(check) == '&'.charCodeAt(0)) {
                            numb = str.substring(str.length - 1);
                            /* add */ (this.cardList.push(new GlossCard(/* replace */ str.split("&" + numb).join(""), synset)) > 0);
                        }
                        else {
                            numb = str.substring(str.length - 2);
                            if ((c => c.charCodeAt == null ? c : c.charCodeAt(0))(str.charAt(str.length - 3)) == '*'.charCodeAt(0)) {
                                /* add */ (this.cardList.push(new WordCard(/* replace */ str.split("*" + numb).join(""), synset)) > 0);
                                indice_synset += 1;
                            }
                            else
                                (this.cardList.push(new GlossCard(/* replace */ str.split("&" + numb).join(""), synset)) > 0);
                        }
                        console.log(numb);
                        str = str.split("*" + numb).join("");
                        results[i] = str;
                    }
                    ;
                }
                for (let i = 0; i < n; i++) {
                    {
                        let j = (((Math.random() * n) | 0));
                        let temp = this.cardList[i];
                        let temp2 = this.cardList[j];
                        /* remove */ (a => { let index = a.indexOf(temp); if (index >= 0) {
                            a.splice(index, 1);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.cardList);
                        /* add */ this.cardList.splice(i, 0, temp2);
                        /* remove */ (a => { let index = a.indexOf(temp2); if (index >= 0) {
                            a.splice(index, 1);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.cardList);
                        /* add */ this.cardList.splice(j, 0, temp);
                    }
                    ;
                }
                let j = 0;
                {
                    let array125 = this.table.getRows();
                    for (let index124 = 0; index124 < array125.length; index124++) {
                        let row = array125[index124];
                        {
                            {
                                let array127 = row.getCells();
                                for (let index126 = 0; index126 < array127.length; index126++) {
                                    let cell = array127[index126];
                                    {
                                        cell.insertCard(/* get */ this.cardList[j++]);
                                        cell.appendTag(new Label().innerHTML(cell.getCard().getText()).className("cella"));
                                        $(cell.element.firstChild).hide();
                                    }
                                }
                            }
                        }
                    }
                }
                $("#loader").hide();
                this.soluzioni.hidden(false);
                return null;
            });
            return null;
        });
        $(this.convalida.element).click((e) => {
            e.preventDefault();
            if (this.one != null && this.two != null) {
                if ((this.one.getCard() != null && this.one.getCard() instanceof WordCard) && (this.two.getCard() != null && this.two.getCard() instanceof GlossCard)) {
                    this.one.getCard().select();
                    this.two.getCard().select();
                    $(this.one.element).css("background-color", "#cffcba");
                    $(this.two.element).css("background-color", "#cffcba");
                    $(this.one.element).show();
                    $(this.two.element).show();
                    let risp1 = [this.one.getCard().getSynset(), this.one.getCard().getText()];
                    let risp2 = [this.two.getCard().getSynset(), this.two.getCard().getText()];
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.risposteConSynset, risp1, risp2);
                }
                else if ((this.two.getCard() != null && this.two.getCard() instanceof WordCard) && (this.one.getCard() != null && this.one.getCard() instanceof GlossCard)) {
                    this.one.getCard().select();
                    this.two.getCard().select();
                    $(this.one.element).css("background-color", "#cffcba");
                    $(this.two.element).css("background-color", "#cffcba");
                    $(this.one.element).show();
                    $(this.two.element).show();
                    let risp1 = [this.one.getCard().getSynset(), this.one.getCard().getText()];
                    let risp2 = [this.two.getCard().getSynset(), this.two.getCard().getText()];
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.risposteConSynset, risp2, risp1);
                }
                else {
                    $("#erroreConvalida").show().delay(3000).fadeOut();
                }
            }
            return null;
        });
        this.formResults.action("memoryValidation.jsp");
        $(this.endGame.element).click((e) => {
            e.preventDefault();
            let results = "";
            {
                let array129 = ((m) => { let r = []; if (m.entries == null)
                    m.entries = []; for (let i = 0; i < m.entries.length; i++)
                    r.push(m.entries[i].key); return r; })(this.risposteConSynset);
                for (let index128 = 0; index128 < array129.length; index128++) {
                    let array = array129[index128];
                    {
                        results += "<synset_word>" + array[0] + "</synset_word><word>" + array[1] + "</word><synset_gloss>" + (((m, k) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                return m.entries[i].value;
                            } return null; })(this.risposteConSynset, array))[0] + "</synset_gloss><gloss>" + (((m, k) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                return m.entries[i].value;
                            } return null; })(this.risposteConSynset, array))[1].split("<i>").join("").split("</i>").join("") + "</gloss>";
                    }
                }
            }
            $(this.task2.element).val(results);
            $(this.formResults.element).submit();
            return null;
        });
        $(this.suggerimento.element).click((e) => {
            e.preventDefault();
            if (this.one != null && !this.one.getCard().isSelected() && (this.one.getCard() != null && this.one.getCard() instanceof WordCard)) {
                let word = this.one.getCard().getText();
                $.post("memoryGame.jsp", "word=" + word, (result, a, xhr) => {
                    let sug = result;
                    this.divSuggerimento.textContent(sug);
                    $("#divSuggerimento").show().delay(10000).fadeOut();
                    console.log(sug);
                    return null;
                });
            }
            return null;
        });
        $(this.soluzioni.element).click((e) => {
            e.preventDefault();
            let s = "";
            let sb = null;
            {
                let array131 = Object.keys(this.combinazioni);
                for (let index130 = 0; index130 < array131.length; index130++) {
                    let str = array131[index130];
                    {
                        sb = { str: /* get */ ((m, k) => m[k] === undefined ? null : m[k])(this.combinazioni, str), toString: function () { return this.str; } };
                        let i = sb.str.lastIndexOf("&");
                        s += str + ": " + sb.str.substring(0, i) + "\n";
                    }
                }
            }
            alert(s);
            return null;
        });
    }
    /**
     *
     */
    setStyle() {
        $(this.divLevel.element).width("100%");
        $(this.divMessage.element).width("100%");
        this.taskName.textContent("Memory Validation");
    }
    /**
     *
     */
    build() {
        this.mainForm.appendTag(this.divPagina);
        $("body").append(this.header.element, this.links.element, this.divLevel.element, this.divMessage.element, this.menu.element, this.mainForm.element, this.formResults.element);
    }
}
MemoryValidationPage["__class"] = "MemoryValidationPage";
(function (MemoryValidationPage) {
    /**
     * Questa classe permette di istanziare un oggetto menu specifico per il gioco.
     * Si tratta di un menu a scorrimento che riempie l�intero schermo.
     * Al suo interno ci sono le istruzioni del gioco e i form e pulsanti delle impostazioni.
     * Con questi si potr� scegliere il livello di difficolt� del gioco e costruire la tabella.
     *
     * L�elemento principale � il div nav, che contiene l�intero menu, e viene innestato nella pagina.
     * L�unico elemento separato dal nav � il label open, che viene inserito separatamente nella pagina e
     * permette di aprire il menu.
     * @class
     */
    class GameMenu {
        constructor(__parent) {
            this.__parent = __parent;
            if (this.nav === undefined)
                this.nav = null;
            if (this.menuContent === undefined)
                this.menuContent = null;
            if (this.close === undefined)
                this.close = null;
            if (this.open === undefined)
                this.open = null;
            if (this.text === undefined)
                this.text = null;
            if (this.range === undefined)
                this.range = null;
            if (this.level === undefined)
                this.level = null;
            if (this.levelRangeTranslated === undefined)
                this.levelRangeTranslated = null;
            if (this.tableSize === undefined)
                this.tableSize = null;
            if (this.createTable === undefined)
                this.createTable = null;
            if (this.form1 === undefined)
                this.form1 = null;
            this.open = new Button().value(" Game Menu").id("open").className("btn btn-outline-secondary btn-lg");
            let testo = "<h2><mark>Memory Validation - Istruzioni del gioco</mark></h2><br>Questo gioco \u00e8 un riadattamento del memory in versione semantica.<br>Ogni carta del gioco contiene una parola o una definizione. Come nel gioco tradizionale si possono scoprire solo due carte alla volta. <br>A ogni parola corrisponde una sola definizione corretta. Le coppie vanno cercate dall\'utente e confermate ogni volta che si ritiene di aver trovato la giusta corrispondenza parola-definizione. <br>Una coppia convalidata non pu\u00f2 pi\u00f9 essere deselezionata. Se due carte contengono entrambe una parola o una definizione non possono essere convalidate.<br> Una volta terminata la partita i dati verranno inviati al server come per le altre task.<br><br>La difficolt\u00e0 del gioco pu\u00f2 essere impostata tramite il livello di vicinanza semantica e il numero di carte del gioco.<br>Il livello 1 contiene solo parole scelte casualmente dall\'intero WordNet, quindi probabilmente non in relazione tra loro e distanti semanticamente.<br>Aumentando il livello di difficolt\u00e0 le parole saranno sempre pi\u00f9 vicine semanticamente, secondo questi due criteri:<br>1) a partire dalle parole che condividono un senso cercane altri sensi<br>2) a partire da una parola cercane altri sensi e per ciascuno di essi utilizzane i sinonimi<br><br>Con l\'aumentare della difficolt\u00e0 del gioco inoltre si ingrandiscono gli insiemi di parole semanticamente vicine.<br><br>E\' inoltre possibile utilizzare i suggerimenti. Ogni suggerimento fornir\u00e0 un synset completo di sinonimi, <br>glosse ed esempi (se disponibili) di un termine in qualche modo relazionato alla parola selezionata.<br><br>";
            this.text = new Label().innerHTML(testo);
            this.close = new Link().className("closebtn").innerHTML("&times;").id("close");
            this.createTable = new Button().value("Inizia la partita").className("btn btn-lg btn-success").id("iniziaPartita");
            this.range = new Label().innerHTML("<b>Imposta il livello incrementando la vicinanza semantica tra le carte e le dimensioni della tabella del gioco:</b>");
            this.level = new Input().placeholder("inserisci il livello ").type("range").name("level").required(true).className("form-control").id("slider");
            this.level.element.min = "1";
            this.level.element.max = "10";
            this.level.element.step = "1";
            this.levelRangeTranslated = new Input().name("rangeLevel").hidden(true);
            this.tableSize = new Input().name("tableSize").hidden(true);
            let table4x3 = document.createElement("option");
            let table6x4 = document.createElement("option");
            let table8x4 = document.createElement("option");
            let table8x6 = document.createElement("option");
            let hse = document.createElement("select");
            hse.appendChild(table4x3);
            hse.appendChild(table6x4);
            hse.appendChild(table8x4);
            hse.appendChild(table8x6);
            hse.required = true;
            hse.className = "form-control";
            hse.name = "select";
            table4x3.value = "4x3";
            table4x3.textContent = "4x3";
            table6x4.value = "6x4";
            table6x4.textContent = "6x4";
            table8x4.value = "8x4";
            table8x4.textContent = "8x4";
            table8x6.value = "8x6";
            table8x6.textContent = "8x6";
            this.form1 = new Form(__parent.task, this.level, this.levelRangeTranslated, this.tableSize);
            this.form1.element.appendChild(hse);
            this.form1.appendTag(this.createTable);
            this.menuContent = new Div(this.text, this.range, this.form1).className("overlay-content");
            this.nav = new Div(this.close, this.menuContent).className("overlay2").id("myNav");
            this.setFunctions();
        }
        /**
         * Questo metodo imposta le funzioni del menu. Nel caso specifico le due
         * funzioni assegnate a un evento tramite JQuery. Una serve ad apire il menu,
         * l�altra a chiuderlo.
         * @private
         */
        setFunctions() {
            $(this.close.element).click((ev) => {
                this.nav.element.style.width = "0%";
                $("#loader").hide();
                return null;
            });
            $(this.open.element).click((ev) => {
                console.log("premuto");
                ev.preventDefault();
                setTimeout(this.__parent.event, 300);
                this.nav.element.style.width = "100%";
                return null;
            });
        }
        /**
         * restituisce il div che dovr� essere inserito nella pagina, il quale contiene
         * l�intero menu
         *
         * @return {Div} nav
         * @private
         */
        getMenu() {
            return this.nav;
        }
    }
    MemoryValidationPage.GameMenu = GameMenu;
    GameMenu["__class"] = "MemoryValidationPage.GameMenu";
})(MemoryValidationPage || (MemoryValidationPage = {}));
