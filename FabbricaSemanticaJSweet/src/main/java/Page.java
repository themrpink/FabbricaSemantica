
import static def.jquery.Globals.$;

/**
 * Su questa classe astratta si basa la struttura di tutte le altre pagine del progetto. Tutte le pagine infatti ereditano da questa.
 *<br>
 *L´intestazione, i menu, il controllo se l´utente è connesso, la suddivisione principale della struttura HTML vengono 
 *tutti impostati in Page.<br><br>
 * I menu si ottengono da classi private interne che restituiscono la barra di navigazione e il menu laterale
 * a scomparsa già inseriti ciascuno in un {@link Div}.  
 *
 */
public abstract class Page {
	
	/**
	 * {@link Div} con l'header della pagina. Contiene il titolo ({@link #title} e il nome del task {@link #taskName}
	 */
	protected Div header;
	/**
	 * {@link Div} contenente il form che costituisce il nucleo centrale di ogni pagina del progetto
	 */
	protected Div mainForm;
	/**
	 * barra di navigazione
	 */
	protected Div links;
	/**
	 * {@link Div} che contiene il menu laterale a scomparsa
	 */
	protected Div menuLaterale;
	/**
	 * {@link Div} che contiene la rotella di caricamento pagina
	 */
	protected Div loader;
	/**
	 *{@link Div} che contiene il titolo e l´immagine
	 */
	protected Div title;
	/**
	 * {@link Div} che contiene il nome della task aperta
	 */
	protected Div taskName;
	
	/**
	 * Costruttore della classe astratta Page. Istanzia i tag, crea i menu, controlla se l´utente è connesso
	 * e in base a questo imposta i link in maniera appropriata.
	 */
	public Page() {
		this.title 	= 	new Div().textContent("Fabbrica Semantica").id("title").className("mx-auto");
		this.taskName = new Div().id("taskName");
		this.header = 	new Div(title, taskName).id("header");

		// Istanzia i due menu
		this.links 		= 	new Navbar().getMenu().id("links");
		this.menuLaterale = new Sidebar().getMenu();
		this.mainForm 	= 	new Div().id("mainForm");
		this.loader 	= 	new Div().id("loader").className("mx-auto").hidden(true);
		
		checkUser();		
	
	}

	
	/**
	 * Imposta proprietà e contenuti dei tag HTML necessari alla corretta visualizzazione delle pagine.
	 */
	public void setDefaultAttributes() {
		$("body").append(header.element, links.element, menuLaterale.element, mainForm.element);
	}


	private void checkUser() {
		$.get("isLoggedIn.jsp", (result, a, ctx) -> {
			String b = (String) result;
			if (b.equals("false")) {
				$("#account").hide();
				$("#signout").hide();
			} else {
				$("#signup").hide();
				$("#account").text(b);
				$("#login").hide();
			}
			return null;
		});
	}

	/**
	 * Raccoglie ed esegue le funzioni che comunicano con il server.
	 */
	public abstract void connectToServer();

	/**
	 * Setta dettagli di stile minori e specifici oltre a quelli settati nel CSS, come per esempio
	 * i parametri di Bootstrap.
	 */
	public abstract void setStyle();

	/**
	 * Costruisce le pagine inserendo i tag HTML nel body.
	 */
	public abstract void build();

	///////////////////////////////////////////////////////////////////////////////


		/**
		 * Barra di navigazione
		 * 
		 */
		private class Navbar{
			
			private Div menu;
			private Link home;
			private Link signup;
			private Link login;
			private Link signout;
			private Link account;

			private Navbar() {
				this.home = 	new Link().text("Home").href("homepage.html").id("home");
				this.signup = 	new Link().text("Sign up").href("signup.html").id("signup");
				this.login = 	new Link().text("Login").href("login.html").id("login");
				this.signout = 	new Link().text("Logout").href("logout.jsp").id("signout");
				this.account = 	new Link().text(" ").href("user.html").id("account");
				this.menu = 	new Div(home, signup, login, signout, account).className("navbar");
			}

	
			private Div getMenu() {
				return menu;
			}
		}

		/**
		 * Menu laterale a scomparsa
		 *
		 */
		private class Sidebar{
			private Div menu;
			private Link wordAnnotation;
			private Link translationAnnotation;
			private Link translationValidation;
			private Link senseAnnotation;
			private Link senseValidation;
			private Link definitionAnnotation;
			private Link memoryValidation;

			private Sidebar() {
				this.wordAnnotation = 		 new Link().text("Word annotation").href("word_annotation.html").id("wa");
				this.translationAnnotation = new Link().text("Translation annotation").href("translation_annotation.html").id("ta");
				this.translationValidation = new Link().text("Translation validation").href("translation_validation.html").id("tv");
				this.senseAnnotation = 		 new Link().text("Sense annotation").href("sense_annotation.html").id("sa");
				this.senseValidation = 		 new Link().text("Sense validation").href("sense_validation.html").id("sv");
				this.definitionAnnotation =  new Link().text("Definition annotation").href("definition_annotation.html").id("da");
				this.memoryValidation = 	 new Link().text("Memory validation").href("memory_validation.html").id("mv");
				this.menu = new Div(wordAnnotation, translationAnnotation, translationValidation, senseAnnotation,
									senseValidation, definitionAnnotation, memoryValidation);

				menu.className("sidenav").id("mySidenav");
			}

			private Div getMenu() {
				return menu;
			}
		}
}

