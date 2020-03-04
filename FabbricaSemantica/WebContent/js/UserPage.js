/* Generated from Java with JSweet 2.3.0-SNAPSHOT - http://www.jsweet.org */
/**
 * Costruttore della pagina dell�utente.
 * @class
 * @extends AnnotationPage
 */
class UserPage extends AnnotationPage {
    constructor() {
        super();
        if (this.nome === undefined)
            this.nome = null;
        if (this.email === undefined)
            this.email = null;
        if (this.titolo === undefined)
            this.titolo = null;
        if (this.table === undefined)
            this.table = null;
        if (this.div === undefined)
            this.div = null;
        this.nome = new Label();
        this.email = new Label();
        this.titolo = new Label().innerHTML("<h2>Task completati</h2>");
        this.titolo.$css("margin:5%, 5%");
        this.table = new Table(1, 2);
        this.div = new Div(this.titolo, this.table);
    }
    /**
     *
     */
    connectToServer() {
        $.getJSON("user.jsp", (r, a, ctx) => {
            let json = r;
            let name = (json["username"]);
            let mail = (json["email"]);
            let result = (json["results"]);
            this.nome.textContent("Nome utente: " + name);
            this.email.textContent("Email: " + mail);
            let results = result.split("<end_task>");
            this.buildUserTable(results);
            return null;
        });
    }
    /**
     * costruisce la tabella con i task completati dall'utente
     * @param {Array} results
     * @private
     */
    /*private*/ buildUserTable(results) {
        let rowCount = 0;
        for (let index137 = 0; index137 < results.length; index137++) {
            let s = results[index137];
            {
                let task = s.split("<br>");
                for (let index138 = 0; index138 < task.length; index138++) {
                    let t = task[index138];
                    {
                        let line = t.split(": ");
                        if ((line[0].indexOf("userID") != -1))
                            continue;
                        else if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(line[0], ""))
                            this.table.addRow();
                        else {
                            if (((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(line[0], "task_type")) {
                                line[0] = "<b>" + line[0] + "</b>";
                                line[1] = "<b>" + line[1] + "</b>";
                            }
                            this.table.getCellFromID("r" + rowCount + "c" + 0).innerHTML(line[0]);
                            this.table.getCellFromID("r" + rowCount + "c" + 1).innerHTML(line[1]);
                            this.table.addRow();
                        }
                        rowCount++;
                    }
                }
            }
        }
        this.div.appendTag(this.table);
    }
    /**
     *
     */
    build() {
        this.mainForm.appendTag(this.nome);
        this.mainForm.appendTag(this.email);
        this.mainForm.appendTag(this.div);
        $("body").append(this.header.element, this.links.element, this.mainForm.element);
    }
}
UserPage["__class"] = "UserPage";
