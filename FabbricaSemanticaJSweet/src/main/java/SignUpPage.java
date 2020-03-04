
import static def.dom.Globals.document;
import static def.jquery.Globals.$;
import static def.dom.Globals.window;
import java.util.function.Function;
import def.dom.Event;
import def.dom.KeyboardEvent;
import def.dom.MouseEvent;
import jsweet.util.StringTypes;
import def.dom.HTMLOptionElement;
import def.dom.HTMLSelectElement;

/**
 * Pagina in cui l´utente può iscriversi al progetto.
 * <br>
 * Vengono richiesti nome utente, email, password, lingue.<br>
 * I dati vengono comunicati al server che provvederà a salvarli.<br>
 * Il controllo della password e 
 * 
 * @see Page
 * @see LoginPage
 *
 */
public class SignUpPage extends Page {

	private Form form;
	private Button signup;
	private Button addLanguages;
	private Input username;
	private Input email;
	private Input password;
	private Input passwordRepeat;
	private Div titolo;
	private Div lingue;
	private Div lingueExtra;
	private Label conteggio;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label checkboxError;
	private Label labelEng;
	private Label labelIta;
	private Label signupError;
	private Function<KeyboardEvent, Object> passRepeatChange;
	private Function<Event, Object> checkPassChange;
	private Function<MouseEvent, Object> addLanguage;
	private Function<MouseEvent, Object> checkboxSelected;

	
	/**
	 * Costruisce la pagina di SignUp. 
	 */
	public SignUpPage() {

		this.username = new Input().placeholder("choose your name").type("text").name("username").id("username");
		this.email = new Input().placeholder("insert your email").type("email").name("email").id("email");
		this.passwordRepeat = new Input().placeholder("confirm the password").type("password").name("passwordRepeat").id("passwordRepeat");
		this.password = new Input().placeholder("choose a password").type("password").name("password").id("password");
		
		this.conteggio = new Label();
		this.label1 = new Label();
		this.label2 = new Label();
		this.label3 = new Label();
		this.label4 = new Label();
		this.labelEng = new Label(new Input().type("checkbox").name("motherLanguages").value("EN"));
		this.labelIta = new Label(new Input().type("checkbox").name("motherLanguages").value("IT"));
		this.checkboxError = new Label();
		this.signupError = new Label();

		this.signup = new Button();
		this.addLanguages = new Button().type("button");

		this.titolo = new Div();
		this.lingue = new Div(label4, labelEng, labelIta, checkboxError);
		this.lingueExtra = new Div(conteggio, addLanguages);

		this.form = new Form(titolo, label1, username, label2, email, signupError, label3, password, passwordRepeat,
				lingue, lingueExtra, signup);

		setFunctions();
	}

	@Override
	public void setDefaultAttributes() {
		super.setDefaultAttributes();

		titolo.innerHTML("<h2>Sign Up</h2>");

		// imposto i label del form
		label1.textContent("Username");
		label2.textContent("Email");
		label3.textContent("Password");
		label4.textContent("Choose your mother language(s)");

		labelEng.element.innerHTML += " English";
		labelEng.element.className = "form-check";
		labelIta.element.innerHTML += " Italian";
		labelIta.element.className = "form-check";

		// imposto i div che segnalano errori
		checkboxError.textContent("please select at least one mother language");
		checkboxError.hidden(true);
		checkboxError.className("loginError");

		signupError.textContent("email already in use");
		signupError.hidden(true);
		signupError.className("loginError");

		// imposto gli input per username e email
		username.setAttributes("class", "form-control", "display", "inline");
		username.element.required = true;
		username.element.setAttribute("minLength", "6");

		email.setAttributes("class", "form-control");
		email.getElement().required = true;

		// imposto gli input per le password
		password.setAttributes("class", "form-control");
		password.element.required = true;
		// attribuisce un evento
		password.element.addEventListener(StringTypes.change, checkPassChange, true);
		password.element.setAttribute("minLength", "8");

		passwordRepeat.setAttributes("class", "form-control");
		passwordRepeat.element.required = true;
		// un altro modo di attribuire un evento
		passwordRepeat.element.onkeyup = passRepeatChange;

		// imposto il pulsante
		signup.setAttributes("value", "sign up");
		signup.element.onclick = checkboxSelected;
		form.element.id = "signup_form";

		// imposto il div delle lingue
		lingue.element.id = "lingue";
		lingueExtra.element.id = "lingueExtra";

		addLanguages.element.onclick = addLanguage;
		addLanguages.setAttributes("value", "add language");
	}

	/**
	 * istanzia le funzioni della pagina, viene chiamata direttamente dal
	 * costruttore
	 */
	private void setFunctions() {

		/*
		 * queste funzioni controllano che le password siano uguali
		 */
		this.passRepeatChange = (a) -> {
			if (password.element.value != passwordRepeat.element.value)
				passwordRepeat.element.setCustomValidity("Le password non coincidono");
			else
				passwordRepeat.element.setCustomValidity("");
			return a;
		};

		this.checkPassChange = (a) -> {
			if (!password.element.value.equals(passwordRepeat.element.value))
				passwordRepeat.element.setCustomValidity("Le password non coincidono");
			else
				passwordRepeat.element.setCustomValidity("");
			return a;
		};

		/*
		 * questa funzione permette di aggiungere una lingua ogni volta che viene
		 * chiamata dal pulsante "addLanguages"
		 */
		this.addLanguage = (a) -> {

			// crea un menu a discesa con i livelli della lingua
			HTMLOptionElement level = document.createElement(StringTypes.option);
			HTMLOptionElement a1 = document.createElement(StringTypes.option);
			HTMLOptionElement a2 = document.createElement(StringTypes.option);
			HTMLOptionElement b1 = document.createElement(StringTypes.option);
			HTMLOptionElement b2 = document.createElement(StringTypes.option);
			HTMLOptionElement c1 = document.createElement(StringTypes.option);
			HTMLOptionElement c2 = document.createElement(StringTypes.option);
			HTMLSelectElement hse = (HTMLSelectElement) document.createElement(StringTypes.select);

			// inserisce gli elementi nel select
			hse.className = "form-control";
			hse.name = "select";
			hse.appendChild(level);
			hse.appendChild(a1);
			hse.appendChild(a2);
			hse.appendChild(b1);
			hse.appendChild(b2);
			hse.appendChild(c1);
			hse.appendChild(c2);

			// imposta gli attributi dei livelli
			level.textContent = "level";
			a1.value = "A1";
			a1.textContent = "A1";
			a2.value = "A2";
			a2.textContent = "A2";
			b1.value = "B1";
			b1.textContent = "B1";
			b2.value = "B2";
			b2.textContent = "B2";
			c1.value = "C1";
			c1.textContent = "C1";
			c2.value = "C2";
			c2.textContent = "C2";

			// crea l`input di testo per la lingua
			Input input = new Input().placeholder("your language").type("text").name("lang").id("lang").className("form-control");

			// inserisce l`input e il select in un div
			Div main = new Div().className("input-group mb-3");
			main.element.appendChild(input.element);
			main.element.appendChild(hse);

			$("#lingueExtra").append(main.element);
			return null;
		};

		/*
		 * questa funzione permette di controllare, al momento del click su signup, se
		 * almeno un checkbox ï¿½ stato selezionato. In caso negativo, appare una scritta
		 * di errore e l`invio dei dati viene interrotto.
		 */
		this.checkboxSelected = (e) -> {

			// ottiene il numero di checkbox selezionati
			double checked = $("input[type=checkbox]:checked").length;

			// se nessuno checkbox selezionato mostra messaggio di errore
			// se almeno uno è stato selezionato invia il form
			if (checked == 0) {
				// blocca il submit della pagina
				e.preventDefault();
				// mostra il messaggio di errore
				checkboxError.hidden(false);
			} else {
				$(form.element).on("submit", (ev, a) -> {
					// blocca il submit del form
					ev.preventDefault();
					signupError.hidden(true);
					// prepara i dati del form da inviare al server
					String s = $(form.element).serialize();
					// la funzione invia i dati del form al server e riceve una risposta
					$.post("signup.jsp", s, (data, q, xhr) -> {
						String r = (String) data;
						// se la risposta è "false"
						if (r.equals("false")) {

							// mostra il messaggio di errore
							signupError.hidden(false);
						}
						/*
						 * else if(r.equals("char")){ signupError.element.hidden=false;
						 * signupError.element.
						 * textContent="sono stati utilizzati dei caratteri non ammessi"; }
						 */
						else
							window.location.href = "login.html";
						return null;
					});
					return null;
				});
			}
			return null;
		};

		/*
		 * questo comando JQuery e la funzione fanno scomparire il messaggio di errore
		 * delle checkbox una volta selezionate .change(handler) è uno shortcut per
		 * .on("change", handler)
		 */
		$(lingue.element).change((e) -> {
			checkboxError.hidden(true);
			return null;
		});

	}

	@Override
	public void connectToServer() {

		form.action("signup.jsp");
		// form.element.submit();

		/*
		 * questa funzione chiede al server se l'utente è connesso e in caso affermativo
		 * ne ottiene l´username e lo inserisce nel menu quando la pagina viene caricata
		 */
		$.get("isLoggedIn.jsp", (result, a, ctx) -> {
			String b = (String) result;

			$("#signup").hide();

			if (b.equals("false")) {
				$("#account").hide();
				$("#signout").hide();
			} else {
				$("#account").text(b);
				$("#login").hide();
				$("#signup").hide();
			}

			return null;
		});
	}

	@Override
	public void setStyle() {
		$(signup.element).css("float", "right");
		$(signup.element).css("margin", "10px");

		// Bootstrap
		form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
		addLanguages.className("btn btn-outline-secondary btn-sm");

		lingue.$css("padding:0% 0% 4%");
	}

	@Override
	public void build() {
		super.mainForm.appendTag(form);
		$("body").append(super.header.element, super.links.element, super.mainForm.element);

	}

}
