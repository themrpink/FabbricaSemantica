package it.uniroma1.metodologie2019.hw3;


import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * Il dizionario ha informazioni sul testo, la lista di synset e il WordNet corrente
 * 
 * @author stefano urani
 *
 */


class Dizionario 
{

	private ArrayList<Synset> listaSynset;
	private Testo testo;
	private WordNet wn;
	
	/**
	 * costruttore
	 * @param wn istanza di WordNet
	 */
	public Dizionario(WordNet wn)
	{		
		listaSynset = new ArrayList<Synset>();
		this.wn=wn;
	}

	/**
	 * istanzia un oggetto Testo
	 * @param nomeFile File nome del file
	 * @param start inizio
	 */
	public void estraiTesto(String nomeFile, int start) 
	{
		testo = new Testo(nomeFile, start);
	}
	
	
	/**
	 * per ogni oggetto Riga del testo istanzia il relativo Synset
	 */
	public void createSynsets() 
	{
		for(Riga r: testo.getTesto()) 
		{
			String offset =  r.getOffset();
			String id     =  r.getId();
			String[] synonyms=  r.getSynonyms();
			String gloss =  r.getGloss();
			ArrayList<String> esempi =  r.getEsempi();
			POS iD = r.getID();
			HashMap<String, String> hm = r.getReferences();
			
			listaSynset.add(new Synset(offset, id, iD, synonyms, gloss, esempi, hm, wn));
		}
	}

	/**
	 * restituisce la lista di Synset del Dizionario
	 * @return lista si synset
	 */
	public ArrayList<Synset> getListaSynset() {return listaSynset;}
}
