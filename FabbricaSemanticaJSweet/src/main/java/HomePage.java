
import static def.jquery.Globals.$;

/**
 * Pagina iniziale, contiene un pulsante per iniziare un task casuale, attivabile solo se l´utente è loggato.
 *
 *@see Page
 */
public class HomePage extends Page {

	private Form form;
	private Button start;
	private Label pleaseLogin;
	private Label welcome;

	/**
	 * Costruttore della pagina, aggiunge il pulsante di accesso casuale alle task.
	 */
	public HomePage() {

		this.welcome = new Label().className("text-success");
		this.start = new Button();
		this.pleaseLogin = new Label();
		this.form = new Form(start, pleaseLogin, welcome);
	}


	public void setDefaultAttributes() {
		super.setDefaultAttributes();

		start.setAttributes("value", "start");
		start.element.className += " btn-block";
		pleaseLogin.element.innerText = "Please login or sign up to access this page";
		pleaseLogin.element.hidden = true;
		form.element.appendChild(start.element);
	}

	@Override
	public void connectToServer() {
		// setta la servlet a cui inviare i dati del form
		form.setAttributes("action", "redirect.jsp");

		$.get("isLoggedIn.jsp", (result, a, ctx) -> {

			String b = (String) result;

			if (b.equals("false")) {
				$(start.element).click((e) -> {
					e.preventDefault();
					pleaseLogin.hidden(false);
					return null;
					});
			}
			else {
				welcome.hidden(false).textContent("Benvenuto " + b + "! Premi su start per iniziare le task");
			}
			return null;
		});

	}

	@Override
	public void setStyle() {
		pleaseLogin.className("text-danger mx-auto");
		form.className("shadow-lg p-4 mb-4 bg-white rounded-top");
	}

	@Override
	public void build() {
		super.mainForm.appendTag(form);
		$("body").append(super.header.element, super.links.element, super.mainForm.element);

	}

}