import static def.dom.Globals.console;
import static def.dom.Globals.document;

/**
 * Factory che istanzia tutte le pagine del progetto.<br><br>
 * Ogni pagina HTML passa un valore che viene a sua volta passato come parametro
 * al metodo statico {@link #buildPage(Pages) buildPage} dopo essere stato convertito in un elemento della enum {@link 
 * Pages}.
 *
 */
public class PageFactory {

	/**
	 * Metodo statico che permette di istanziare le pagine di Fabbrica Semantica come oggetto di tipo {@link Page}.<br>
	 * Tutte le pagine del progetto estendono infatti la classe astratta {@link Page} i cui metodi vengono qui chiamati per costruire la pagina.
	 * Prende come parametro un oggetto di tipo {@link Pages} che indica quale pagina verrà istanziata.<br><br>
	 * 
	 * vengono chiamati i metodi:<br>
	 * {@link Page#setDefaultAttributes() setDefaultAttributes}<br>
	 * {@link Page#connectToServer() Page.connectToServer}<br>
	 * {@link Page#setStyle() Page.setStyle}<br>
	 * {@link Page#build() Page.build}<br>
	 * 
	 * @param p pagina da istanziare
	 */
	public static void buildPage(Pages p) {

		Page page = null;

		switch (p) {
		
		case HOME:
			page = new HomePage();
			break;
		case TRANSLATION_ANNOTATION:
			page = new TranslationAnnotationPage();
			break;
		case WORD_ANNOTATION:
			page = new WordAnnotationPage();
			break;
		case ANNOTATION_PAGE:
			page = new AnnotationPage();
			break;
		case DEFINITION_ANNOTATION:
			page = new DefinitionAnnotationPage();
			break;
		case SENSE_ANNOTATION:
			page = new SenseAnnotationPage();
			break;
		case SENSE_VALIDATION:
			page = new SenseValidationPage();
			break;
		case TRANSLATION_VALIDATION:
			page = new TranslationValidationPage();
			break;
		case MEMORY_VALIDATION:
			page = new MemoryValidationPage();
			break;
		case SIGNUP:
			page = new SignUpPage();
			break;
		case LOGIN:
			page = new LoginPage();
			break;
		case USER_PAGE:
			page = new UserPage();
			break;
		case ERROR_PAGE:
			page = new ErrorPage();
			break;
		default:
			break;
		}

		// metodi della classe Page e ereditati da tutte le pagine per la loro costruzione
		if(page!=null) {
			page.setDefaultAttributes();
			page.connectToServer();
			page.setStyle();
			page.build();
		}

	}

	public static void main(String[] args) {

		// ottiene l'attributo specificato nello script della pagina HTML
		String pageName = document.getElementById("main").getAttribute("data-name");
		console.log("called: " + pageName);
		// lancia il PageFactory
		PageFactory.buildPage(Pages.valueOf(pageName));
	}

}
