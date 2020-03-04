package it.uniroma1.fabbricasemantica.data;

/**
 * Tutti i Task devono essere in grado di restituire una stringa con il loro valore
 *
 */
public interface Task {
	
	/**
	 * chiama il rispettivo toString() della classe implementata
	 * @return il valore in formato stringa
	 */
	default String getTaskID() {
		return toString();
	}
	
}
 