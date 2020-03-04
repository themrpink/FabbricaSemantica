/**
 * Carta del gioco del memory specifica per le parole.
 * @see Card
 * @see MemoryValidationPage
 *
 */
public class WordCard extends Card {
	/**
	 * Costruisce una carta contenente una parola.
	 * @param word testo parola
	 * @param synset Id
	 */
	public WordCard(String word, String synset) {
		super(word, synset);
	}
}