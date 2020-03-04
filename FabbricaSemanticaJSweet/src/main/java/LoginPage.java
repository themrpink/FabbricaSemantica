
import static def.jquery.Globals.$;

/**
 * Pagina di login. I dati di autenticazione vengono verificati dal server e se non sono corretti
 * viene mostrato un messaggio di errore.
 *
 *
 */
public class LoginPage extends Page {

	private Form form;
	private Button signin;
	private Input password;
	private Input email;
	private Div titolo;
	private Label label2;
	private Label label3;
	private Label loginError;

	/**
	 * Costruisce la pagina di Login.
	 */
	public LoginPage() {

		this.signin = new Button();
		this.password = new Input().placeholder("insert your password").type("password").name("password").id("password");
		this.email = new Input().placeholder("insert your email").type("email").name("email").id("email");
		this.titolo = new Div();

		this.label2 = new Label();
		this.label3 = new Label();
		this.loginError = new Label();
		this.form = new Form(titolo, label3, email, label2, password, loginError, signin);

	}

	@Override
	public void setDefaultAttributes() {

		super.setDefaultAttributes();

		label2.textContent("Password");
		label3.textContent("Email");

		titolo.innerHTML("<h2>Login</h2>");

		email.setAttributes("class", "form-control", "display", "inline");
		email.element.required = true;

		password.setAttributes("class", "form-control");
		password.element.required = true;

		loginError.textContent("dati di autenticazione errati!");
		loginError.className("loginError");
		loginError.hidden(true);

		signin.setAttributes("value", "sign in");

	}

	@Override
	public void connectToServer() {
		form.setAttributes("action", "login.jsp");
		// la funzione fa verificare i dati di autenticazione dal server senza uscire
		// dalla pagina
		$(form.element).on("submit", (e, a) -> {
			// blocca il submit del form
			e.preventDefault();
			loginError.hidden(true);
			// prepara i dati del form da inviare al server
			String s = $(form.element).serialize();

			// la funzione invia i dati del form al server e riceve una risposta
			$.post("login.jsp", s, (data, q, xhr) -> {
				String r = (String) data;
				// se la risposta è "false"
				if (r.equals("false")) {
					// mostra il messaggio di errore
					loginError.hidden(false);
				}
				// altrimenti invia il form e esce dalla pagina
				else
					form.element.submit();
				return null;
			});
			return null;
		});

		
		$.get("isLoggedIn.jsp", (result, a, ctx) -> {
			String b = (String) result;
			$("#login").hide();
			if (b.equals("false")) {
				$("#account").hide();
				$("#signout").hide();
			} else {
				$("#account").text(b);
			}
			return null;
		});
	}

	@Override
	public void setStyle() {
		$(signin.element).css("float", "right");
		$(signin.element).css("margin", "10px");
		// bootstrap
		form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
	}

	@Override
	public void build() {
		super.mainForm.appendTag(form);
		$("body").append(super.header.element, super.links.element, super.mainForm.element);

	}

}
