
/**
 * Enum di tutte le pagine del progetto, ognuna associata a una stringa con lo stesso nome.
 * 
 *
 */
public enum Pages {
	HOME("HOME"), 
	LOGIN("LOGIN"), 
	SIGNUP("SIGNUP"), 
	TRANSLATION_ANNOTATION("TRANSLATION_ANNOTATION"), 
	WORD_ANNOTATION("WORD_ANNOTATION"),
	ANNOTATION_PAGE("ANNOTATION_PAGE"), 
	DEFINITION_ANNOTATION("DEFINITION_ANNOTATION"),
	SENSE_ANNOTATION("SENSE_ANNOTATION"), 
	SENSE_VALIDATION("SENSE_VALIDATION"),
	TRANSLATION_VALIDATION("TRANSLATION_VALIDATION"), 
	MEMORY_VALIDATION("MEMORY_VALIDATION"),
	USER_PAGE("USER_PAGE"),
	ERROR_PAGE("ERROR");

	String s;

	Pages(String s) {
		this.s = s;
	}

}
