
/**
 * Carta del gioco del memory specifica per le glosse.
 * @see Card
 * @see MemoryValidationPage
 *
 */
public class GlossCard extends Card {

	/**
	 * Costruisce una carta contenente una glossa.
	 * @param gloss testo glossa
	 * @param synset Id
	 */
	public GlossCard(String gloss, String synset) {
		// aggiunge il codice html per il corsivo
		super("<i>" + gloss + "</i>", synset);
	}
	
	/**
	 * Restituisce la glossa in un tag per il corsivo.
	 * @return s glossa in corsivo
	 */
	public String getPlainText() {
		String s = super.getText().replace("<i>", "");
		s.replace("</i>", "");
		return s;
	}

}
