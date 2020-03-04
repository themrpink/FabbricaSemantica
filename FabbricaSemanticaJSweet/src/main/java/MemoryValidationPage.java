
import static def.dom.Globals.console;
import static def.dom.Globals.document;
import static def.dom.Globals.window;
import static def.dom.Globals.alert;
import static def.dom.Globals.setTimeout;
import static def.jquery.Globals.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import def.dom.Event;
import def.dom.HTMLOptionElement;
import def.dom.HTMLSelectElement;

import def.js.Math;
import jsweet.util.StringTypes;
/**
 * Pagina del task MemoryValidation. Si tratta di un gioco del memory in cui si cerca di riconoscere
 * le coppie parola-glossa.<br>
 * <br>
 * Si dispone di vari livelli di difficoltà, nei quali cambiano la distanza semantica tra le parole e le dimensioni della
 * tabella di gioco. Si hanno a disposizione anche suggerimenti e soluzioni. Il gioco, in analogia con le altre task, non esegue controlli sulla correttezza delle corrispondenze,
 * si limita a inviare le coppie scelte dall´utente al server a fine partita.<br><br>
 * Le istruzioni di gioco si trovano nel menu Game Menu.
 * @see Page
 *
 */
public class MemoryValidationPage extends Page {

	private Table table;
	private Button endGame;
	private GameMenu gameMenu;
	private Div menu;
	private Div divLevel;
	private Div divMessage;
	private Div divPagina;
	private Input task;
	private Form formResults;
	private Input task2;
	private Map<String, String> combinazioni;
	private Map<String[], String[]> risposteConSynset;
	private List<Card> cardList;
	private Button soluzioni;

	// utilizzate per girare le carte e trovare le coppie. Gli oggetti cell hanno
	// come campo un oggetto di tipo Card
	private Table.Cell one;
	private Table.Cell two;

	private Button convalida;
	private Label erroreConvalida;
	private String previousID = "";
	private Button suggerimento;
	private Div divSuggerimento;
	private Button skip;
	
	private Function<Event, Object> event;

	
	/**
	 * Costruttore, istanzia una pagina {@link MemoryValidationPage}
	 */
	public MemoryValidationPage() {

		/*
		 * la tabella poi dovrà essere creata con il numero di celle specificato
		 */
		this.table = new Table(0, 0);

		// input nascosto per passare il task alla servlet
		this.task = new Input().name("task").value("MEMORY_VALIDATION").hidden(true);

		// istanzia il menu del gioco
		this.gameMenu = new GameMenu();
		this.menu = gameMenu.getMenu();

		this.convalida = new Button()
				.name("convalida")
				.value("convalida coppia")
				.id("convalida")
				.className("btn btn-outline-success");

		this.erroreConvalida = new Label()
				.textContent("Le caselle selezionatea non possono essere convalidate perchè non sono una coppia parola-glossa")
				.className("label label-warning")
				.id("erroreConvalida");

		this.endGame = new Button()
				.name("endGame")
				.value("termina partita")
				.id("endGame")
				.className("btn btn-outline-warning");
		
		this.task2 =  new Input().name("task2").hidden(true);
		this.formResults = new Form(task2);
		
		this.suggerimento = new Button()
				.name("suggerimento")
				.value("suggerimento")
				.id("suggerimento")
				.className("btn btn-outline-info");
		
		this.divSuggerimento = new Div()
				.className("alert alert-info alert-dismissible")
				.id("divSuggerimento");

		this.skip = new Button()
				.value("Skip")
				.name("skip")
				.className("btn btn-outline-primary")
				.id("Skip");
		
		this.soluzioni= new Button()
				.value("mostra soluzioni")
				.name("mostra soluzioni")
				.className("btn btn-outline-info")
				.hidden(true);
		
		// div che contiene il pulsante per aprire il menu e il titolo
		this.divLevel = new Div(suggerimento, convalida, endGame, skip, gameMenu.open).id("divLevel");

		this.divMessage = new Div(divSuggerimento, erroreConvalida);
		// div che contiene la tabella
		this.divPagina = new Div(loader, table, soluzioni, formResults);
		
		
		this.event = (e) -> {
			super.loader.hidden(false);
			return null;
		};
		
	}

	@Override
	public void connectToServer() {
		
		skip.element.onclick=(EVE)->{window.location.href="redirect.jsp";return null;};
			
		$.get("isLoggedIn.jsp", (result, b, ctx) -> {
			String s = (String) result;
			if (s.equals("false")) {
				divLevel.hidden(true);
				erroreConvalida.textContent("Please login or sign up to access this page");
				$(erroreConvalida.element).show();
			}
			return null;
		});
		
		$(gameMenu.createTable.element).click((e) -> {
			e.preventDefault();

			// ottiene la stringa con i valori da passare alla servlet per il livello di
			// difficoltà  scelto
			String rangeLevel = "LEVEL" + gameMenu.level.element.value;

			// inserisci i valori inseriti dall´untente e convertiti in parametri per la
			// servlet nell´input nascosto
			// per essere inviati insieme agli altri dati
			gameMenu.level.value(rangeLevel);
			gameMenu.levelRangeTranslated.value(rangeLevel);

			// dimensione tabella di gioco
			String[] dimTable = (String[]) $("select").val();

			// crea la tabella con le dimensioni date
			table = new Table(Integer.parseInt(dimTable[0]), Integer.parseInt(dimTable[2]));
			$("table").replaceWith(table.element);
			// passa al server il numero delle coppie da generare (numero di caselle / 2)
			gameMenu.tableSize.value("" + (Integer.parseInt(dimTable[0]) * Integer.parseInt(dimTable[2])) / 2);

			// raccoglie tutti i valori degli input in questa stringa
			String dati = $(gameMenu.form1.element).serialize();
			console.log(dati);
			/*
			 * //funzione per nascondere o visualizzare le celle della tabella on un click
			 * differenzia tre casi: se nessuna, uno o due caselle sono già  state
			 * selezionate.
			 *
			 */

			$(table.element.children).click((ev) -> {
				/*
				 * il metodo della tabella gelCellFromID permette di ottenere un oggetto di tipo
				 * Cell a partire da un ID l`ID lo otteniamo dall´oggetto appena cliccato.
				 * Quindi dalla cella appena cliccata estraggo la Card che contiene con
				 * getCard() e di questa controllo se isSelected(). In caso affermativo la
				 * parola è già  stata confermata e quindi non posso più modificarla
				 */

				if (!table.getCellFromID($(ev.target).attr("id")).getCard().isSelected())
					// labelSuggerimento.hidden(true);
					// altrimenti procedi con la funzione
					if (one == null) {
						one = table.getCellFromID($(ev.target).attr("id"));
						$(ev.target.firstChild).show();
						two = null;
					} else if (two == null) {
						if (!one.getID().equals($(ev.target).attr("id"))) {
							two = table.getCellFromID($(ev.target).attr("id"));
							$(ev.target.firstChild).show();
							previousID = $(ev.target).attr("id");
						}
					} else if (one != null && two != null) {
						if(one.getID().equals($(ev.target).attr("id"))||two.getID().equals($(ev.target).attr("id"))){
							$(one.element.firstChild).hide();
							$(two.element.firstChild).hide();
							one=null;
							two=null;
						}
						else if (!one.getID().equals($(ev.target).attr("id"))
								|| !two.getID().equals($(ev.target).attr("id"))) {
							// questa condizione fa sì che le coppie convalidate non scompaiano (isSelected verifica se la coppia è stata convalidata)
							if (!table.getCellFromID(previousID).getCard().isSelected()) {
								$(one.element.firstChild).hide();
								$(two.element.firstChild).hide();
							}
							one = table.getCellFromID($(ev.target).attr("id"));
							$(ev.target.firstChild).show();
							two = null;		
						}
					}

				return null;
			});

			// chiudi il menu
			gameMenu.getMenu().element.style.width = "0%";

			// invia i dati del form alla servelt
			$.post("memoryGame.jsp", dati, (result, r, t) -> {

				// ottiene la stringa con i risultati della query
				String sWord = (String) result;
				console.log(sWord);
				//separa i synset da parole/glosse
				String[] synsets = sWord.split("#");
				console.log(synsets);
				List<String> syns = new ArrayList<>();
				int l=0;
				String res ="";
				for(String str: synsets) {
					if(l%2==0) syns.add(str);
					else if (l==synsets.length-1) res+=str;
					else res+=str+"$";
					l++;}
				//crea un array di parole e corrispettive glosse
				String[] results = res.split("$");
				int n = results.length;
				console.log(results);
				// assegna a ogni elemento il suo valore
				combinazioni = new HashMap<>();
				cardList = new ArrayList<>();
				//risposte = new HashMap<>();
				risposteConSynset = new HashMap<>();
				
				for(int i=0; i<n; i+=2)
					//questo viene usato solo per mappare le soluzioni
					combinazioni.put(results[i], results[i+1]);
				
				int indice_synset=0;
				for (int i = 0; i < n; i++) {
					String str = results[i];
					String synset=syns.get(indice_synset);
					
					//indice_synset+=1;
					// per capire se il numero attribuito ha una o due cifre ed estrarlo dalla
					// stringa
					char check = str.charAt(str.length() - 2);
					String numb = "";
					if (check == '*') {
						numb = str.substring(str.length() - 1);
						// aggiunge alla lista una card
						cardList.add(new WordCard(str.replace("*" + numb, ""), synset));
						indice_synset+=1;
					} 
					else if (check == '&') {
						numb = str.substring(str.length() - 1);
						cardList.add(new GlossCard(str.replace("&" + numb, ""), synset));
					} 
					else {
						numb = str.substring(str.length() - 2);
						if (str.charAt(str.length() - 3) == '*') {
							cardList.add(new WordCard(str.replace("*" + numb, ""), synset));
							indice_synset+=1;}
						else
							cardList.add(new GlossCard(str.replace("&" + numb, ""), synset));
					}
					console.log(numb);

					// elimina il valore dalla stringa e lo sostituisce nell'array
					str = str.replace("*" + numb, "");
					results[i] = str;
				}

				// riordinamento casuale dell`array
				for (int i = 0; i < n; i++) {
					Integer j = ((int) (Math.random() * n));
					// estrae e sostituisce, di nuovo estrae e sostituisce
					Card temp = cardList.get(i);
					Card temp2 = cardList.get(j);
					cardList.remove(temp);
					cardList.add(i, temp2);
					cardList.remove(temp2);
					cardList.add(j, temp);
				}

				int j = 0;
				// inserisce il testo nella tabella
				for (Table.Row row : table.getRows()) {
					for (Table.Cell cell : row.getCells()) {
						cell.insertCard(cardList.get(j++));
						cell.appendTag(new Label()
								// con insertHTML permette di rendere la glossa in corsivo
								.innerHTML(cell.getCard().getText()).className("cella"));
						$(cell.element.firstChild).hide();
					}
				}
				$("#loader").hide();
				soluzioni.hidden(false);
				return null;
			});
			return null;
		});

		/*
		 * questa funzione controlla se le caselle selezionate sono due, se sono una
		 * coppia del tipo parola-glossa, in tal caso colora lo sfondo di verde per
		 * entrambi e le rende non più selezionabili. Inoltre le salva nella mappa delle
		 * coppie parola-glossa convalidate, che poi verrà inviata al server
		 */
		$(convalida.element).click((e) -> {
			e.preventDefault();
			if (one != null && two != null) {
				if (one.getCard() instanceof WordCard && two.getCard() instanceof GlossCard) {
					one.getCard().select();
					two.getCard().select();
					$(one.element).css("background-color", "#cffcba");
					$(two.element).css("background-color", "#cffcba");
					$(one.element).show();
					$(two.element).show();
					String[] risp1 = {one.getCard().getSynset(), one.getCard().getText()};
					String[] risp2 = {two.getCard().getSynset(), two.getCard().getText()};
					risposteConSynset.put(risp1, risp2);
					
				} else if (two.getCard() instanceof WordCard && one.getCard() instanceof GlossCard) {
					one.getCard().select();
					two.getCard().select();
					$(one.element).css("background-color", "#cffcba");
					$(two.element).css("background-color", "#cffcba");
					$(one.element).show();
					$(two.element).show();
					String[] risp1 = {one.getCard().getSynset(), one.getCard().getText()};
					String[] risp2 = {two.getCard().getSynset(), two.getCard().getText()};
					risposteConSynset.put(risp2, risp1);

				} else {
					// lo faccio apparire e poi scomparire dopo 3 secondi
					$("#erroreConvalida").show().delay(3000).fadeOut();
				}
			}
			return null;
		});
		formResults.action("memoryValidation.jsp");

		$(endGame.element).click((e)->{
			e.preventDefault();			
			String results = "";

			for (String[] array : risposteConSynset.keySet()) {
			
				results += "<synset_word>"+array[0]+"</synset_word>" + 
						   "<word>" +array[1] + "</word>" + 
						   "<synset_gloss>"+(risposteConSynset.get(array))[0]+"</synset_gloss>" +
						   "<gloss>"+(risposteConSynset.get(array))[1].replace("<i>", "").replace("</i>","")+"</gloss>";
			}			
			$(task2.element).val(results);
			$(formResults.element).submit();
			return null;
		});

		$(suggerimento.element).click((e) -> {
			e.preventDefault();
			if (one != null && !one.getCard().isSelected() && one.getCard() instanceof WordCard) {
				String word = one.getCard().getText();
				$.post("memoryGame.jsp", "word=" + word, (result, a, xhr) -> {
					String sug = (String) result;
					divSuggerimento.textContent(sug);
					$("#divSuggerimento").show().delay(10000).fadeOut();
				
					console.log(sug);
					return null;
				});
			}
			return null;

		});
		
		$(soluzioni.element).click((e)->{
			e.preventDefault();
			String s="";
			StringBuilder sb = null;
			for (String str : combinazioni.keySet()) {
				sb = new StringBuilder(combinazioni.get(str));
				int i= sb.lastIndexOf("&");
				s+=str + ": "+sb.substring(0, i)+"\n";
			}
			alert(s);
			return null;
		});
	}

		
		
	@Override
	public void setStyle() {
		$(divLevel.element).width("100%");
		$(divMessage.element).width("100%");
		super.taskName.textContent("Memory Validation");
	}

	@Override
	public void build() {
		super.mainForm.appendTag(divPagina);
		$("body").append(super.header.element, super.links.element, divLevel.element, divMessage.element, menu.element,
				super.mainForm.element, formResults.element);
	}
	
	/**
	 * Questa classe permette di istanziare un oggetto menu specifico per il gioco.
	 * Si tratta di un menu a scorrimento che riempie l´intero schermo.
	 * Al suo interno ci sono le istruzioni del gioco e i form e pulsanti delle impostazioni.
	 * Con questi si potrà scegliere il livello di difficoltà del gioco e costruire la tabella.
	 *
	 *L´elemento principale è il div nav, che contiene l´intero menu, e viene innestato nella pagina.
	 *L´unico elemento separato dal nav è il label open, che viene inserito separatamente nella pagina e 
	 *permette di aprire il menu.
	 *
	 *
	 */
	private class GameMenu {

		private Div nav;
		private Div menuContent;
		private Link close;
		private Button open;
		private Label text;
		private Label range;
		private Input level;
		private Input levelRangeTranslated;
		private Input tableSize;
		private Button createTable;
		private Form form1;

		private GameMenu() {
	
			this.open = new Button().value(" Game Menu").id("open")
					.className("btn btn-outline-secondary btn-lg");

			String testo = "<h2><mark>Memory Validation - Istruzioni del gioco</mark></h2><br>Questo gioco è un riadattamento del memory in versione semantica.<br>"
					+"Ogni carta del gioco contiene una parola o una definizione. Come nel gioco tradizionale si possono scoprire solo"
					+ " due carte alla volta. <br>A ogni parola corrisponde una sola definizione corretta. Le coppie vanno cercate"
					+ " dall'utente e confermate ogni volta che si ritiene di aver trovato la giusta corrispondenza parola-definizione."
					+ " <br>Una coppia convalidata non può più essere deselezionata. Se due carte contengono entrambe una parola o una definizione"
					+ " non possono essere convalidate.<br> Una volta terminata la partita i dati verranno inviati al server come per le altre task."
					+ "<br><br>La difficoltà del gioco può essere impostata tramite il livello di vicinanza semantica e il numero di carte del gioco."
					+ "<br>Il livello 1 contiene solo parole scelte casualmente dall'intero WordNet, quindi"
					+ " probabilmente non in relazione tra loro e distanti semanticamente."
					+ "<br>Aumentando il livello di difficoltà le parole saranno sempre più vicine semanticamente, secondo questi due criteri:<br>"
					+ "1) a partire dalle parole che condividono un senso cercane altri sensi<br>"
					+ "2) a partire da una parola cercane altri sensi e per ciascuno di essi utilizzane i sinonimi<br>"
					+ "<br>Con l'aumentare della difficoltà del gioco inoltre si ingrandiscono gli insiemi di parole semanticamente vicine.<br><br>"
					+ "E' inoltre possibile utilizzare i suggerimenti. "
					+ "Ogni suggerimento fornirà un synset completo di sinonimi, <br>glosse ed esempi (se disponibili) di un termine in qualche modo relazionato alla parola"
					+ " selezionata."
					+ "<br><br>";

			// label contenente il testo
			this.text = new Label().innerHTML(testo);

			// bottone per chiudere il menu
			this.close = new Link().className("closebtn").innerHTML("&times;").id("close");

			// bottone per creare la tabella e iniziare il gioco
			this.createTable = new Button().value("Inizia la partita").className("btn btn-lg btn-success")
					.id("iniziaPartita");

			this.range = new Label().innerHTML(
					"<b>Imposta il livello incrementando la vicinanza semantica tra le carte e le dimensioni della tabella del gioco:</b>");

			this.level = new Input().placeholder("inserisci il livello ")
											.type("range")
											.name("level")
											.required(true)
											.className("form-control")
											.id("slider");

			level.element.min = "1";
			level.element.max = "10";
			level.element.step = "1";

			// input nascosto per inviare il valore del livelli e dimensione tabella al
			// server
			this.levelRangeTranslated = new Input().name("rangeLevel").hidden(true);

			this.tableSize = new Input().name("tableSize").hidden(true);

			// crea un menu a discesa con le dimensioni della tabella
			// HTMLOptionElement tableSize = document.createElement(StringTypes.option);
			HTMLOptionElement table4x3 = document.createElement(StringTypes.option);
			HTMLOptionElement table6x4 = document.createElement(StringTypes.option);
			HTMLOptionElement table8x4 = document.createElement(StringTypes.option);
			HTMLOptionElement table8x6 = document.createElement(StringTypes.option);
			HTMLSelectElement hse = (HTMLSelectElement) document.createElement(StringTypes.select);
			// hse.appendChild(tableSize);
			hse.appendChild(table4x3);
			hse.appendChild(table6x4);
			hse.appendChild(table8x4);
			hse.appendChild(table8x6);
			hse.required = true;
			hse.className = "form-control";
			hse.name = "select";

			// imposta gli attributi
			// tableSize.textContent="table size";
			table4x3.value = "4x3";
			table4x3.textContent = "4x3";
			table6x4.value = "6x4";
			table6x4.textContent = "6x4";
			table8x4.value = "8x4";
			table8x4.textContent = "8x4";
			table8x6.value = "8x6";
			table8x6.textContent = "8x6";

			// istanzia il form e vi inserisce i tag
			this.form1 = new Form(task, level, levelRangeTranslated, tableSize);
			form1.element.appendChild(hse);
			form1.appendTag(createTable);

			// il div con i contenuti del menu
			this.menuContent = new Div(text, range, form1).className("overlay-content");

			// il div del menu
			this.nav = new Div(close, menuContent).className("overlay2").id("myNav");

			// carica le funzioni
			setFunctions();
		}

		/**
		 * Questo metodo imposta le funzioni del menu. Nel caso specifico le due
		 * funzioni assegnate a un evento tramite JQuery. Una serve ad apire il menu,
		 * l´altra a chiuderlo.
		 */
		private void setFunctions() {

			$(close.element).click((ev) -> {
				nav.element.style.width = "0%";
				$("#loader").hide();
				return null;
			});

			$(open.element).click((ev) -> {
				console.log("premuto");
				ev.preventDefault();
				setTimeout(event, 300);
				nav.element.style.width = "100%";
				return null;
			});
			
		}

		/**
		 * restituisce il div che dovrà  essere inserito nella pagina, il quale contiene
		 * l´intero menu
		 * 
		 * @return nav
		 */
		private Div getMenu() {
			return nav;
		}
	}
}
