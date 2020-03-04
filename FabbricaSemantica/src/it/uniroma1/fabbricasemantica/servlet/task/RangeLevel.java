package it.uniroma1.fabbricasemantica.data;

/**
 * Enum dei possibili livelli del gioco MemoryValidation con i rispettivi valori, secondo l`ordine:
 * %liv1, %liv2, %liv3 e cardinalità dell´insieme delle parole vicine. Serve appunto per impostare la difficoltà del gioco.<br>
 * <br>
 * La percentuale indica in quale proporzione sono presenti parole scelte secondo i criteri del primo, del secondo e
 * del terzo criterio di selezione. A ogni criterio corrisponde un diverso algoritmo di ricerca e abbinamento delle parole.<br>
 * L´insieme delle parole vicine impone invece il numero minimo di sinonimi che una parola deve avere per essere selezionata.
 * 
 *@see MemoryGameProvider
 */
public enum RangeLevel implements Task{

	LEVEL1("100,0,0,0"),
	LEVEL2("80,10,10,2"),
	LEVEL3("70,20,10,2"),
	LEVEL4("60,20,20,3"),
	LEVEL5("50,30,20,3"),
	LEVEL6("40,30,30,4"),
	LEVEL7("30,40,30,4"),
	LEVEL8("20,40,40,5"),
	LEVEL9("10,50,40,5"),
	LEVEL10("0,50,50,6");
	
	/**
	 * valori associati a ogni enum, servono per impostare il livello di difficoltà
	 */
	String levelValues;
	
	RangeLevel(String values){
		this.levelValues=values;
	}
	
	/**
	 * restituisce i valori associati a ciascun elemento dell´enum
	 * @return impostazioni livello
	 */
	public String getLevelValues() {
		return levelValues;
	}
}
