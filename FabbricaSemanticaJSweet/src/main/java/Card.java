
/**
 *Classe astratta che rapprensenta una carta del gioco {@link MemoryValidationPage}.<br>
 *Può essere selezionata, contenere del testo e l´id del synset corrispondente.
 */
public abstract class Card {

	private boolean selected;
	private String testo;
	private String synset;
	/**
	 * Costruisce una carta dal gioco del memory
	 * @param testo generalmente parola o glossa 
	 * @param synset id del synset 
	 */
	public Card(String testo, String synset) {
		this.selected = false;
		this.testo = testo;
		this.synset=synset;
	}

	/**
	 * Controlla se la carta è stata selezionata
	 * @return true se selezionata
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Seleziona una carta, corrisponde a scoprirla nel gioco.
	 */
	public void select() {
		selected = true;
	}

	/**
	 * Deseleziona la carta.
	 */
	public void unselect() {
		selected = false;
	}

	/**
	 * Restituisce il testo contenuto nella carta.
	 * @return testo generalmente parola o glossa
	 */
	public String getText() {
		return testo;
	}
	
	/**
	 * Restituisce l´id del synset associato alla carta.
	 * @return synset ID
	 */
	public String getSynset() {
		return synset;
	}

}
