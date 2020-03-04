package it.uniroma1.metodologie2019.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * L´oggetto Synset contiene tutte le informazioni di un synset:
 * 
 * 	offset
 * 	id in formato String
 * 	ID in formato POS
 * 	glossa in formato String
 *  esempi se presenti in un ArrayList
 *  i sinonimi in un Array di stringhe
 *  le relazioni tra synset mappati
 *  l`oggetto WordNet a cui il synset appartiene
 *
 *@author stefano urani
 */
public class Synset
{
	private String offset;
	private String id;
	private POS iD;
	private String gloss;
	private ArrayList<String> esempi;
	private String[] synonyms;
	private HashMap<String, String> relazioniSynset;
	private WordNet wn;
	
/**
 * costruttore 
 * @param offset   String
 * @param id       String
 * @param iD		POS
 * @param synonyms  String[]
 * @param gloss		String
 * @param esempi  ArrayList
 * @param hm	HashMap
 * @param wn   WordNet
 */
	public Synset(String offset, String id,POS iD, String[] synonyms, String gloss, ArrayList<String> esempi, HashMap<String,String> hm, WordNet wn)
	{
		this.offset=offset;
		this.iD=iD;
		this.id=id;
		this.esempi=esempi;
		this.synonyms=synonyms;
		this.gloss=gloss;
		this.relazioniSynset=hm;
		this.wn=wn;
	}
	
/**
 * 
 * @return offset String
 */
	public String getOffset()  {return offset;}

/**
 * 
 * @return String offset+id
 */
	public String getID()      {return offset+id;}
	
/**
 * 
 * @return  String id
 */
	public String getid()	   {return id;}
	
/**
 * 
 * @return String glossa
 */
	public String getGloss()  {return gloss;}
	
/**
 * 
 * @return ArrayList esempi
 * se non ci sono esempi restituisce una lista vuota
 */
	public ArrayList<String> getExamples() {return esempi;}
	
/**
 * 
 * @return ArrayList lista dei sinonimi del synset
 */
	public ArrayList<String> getSynonyms() { return new ArrayList<String>(Arrays.asList(synonyms));}
	
/**
 * 
 * @return POS il valore ID del synset di tipo POS
 */
	public POS getPOS() {return iD;}
	
	
/**
 * 
 * @param s parola String
 * @return True se una parola è presente tra i sinonimi del synset
 */
	public boolean contains(String s) 
	{

		List<String> l = Arrays.asList(synonyms);
	
		return l.stream()
		  .filter(ss->ss.equals(s.toLowerCase()))
		  .anyMatch(s::equals);

	}
	
	
	/**
	 * 
	 * @return HashMap    la mappa delle relazioni de synset
	 * 									con key=offset, value=relazione
	 */
	public HashMap<String, String> getRelazioniSynset(){return relazioniSynset;}

	
	/**
	 * Override del metodo toString()
	 * 
	 * stampa nel formato:
	 * offeset + id + sinomimi + relazioni + glossa + esempi
	 * 
	 */
	@Override
	public String toString() 
	{		 
		String s = offset+" "+"("+id+")"; 
		for(int i=0; i<synonyms.length;i++) { s+=" "; s+=synonyms[i]; if(i!=synonyms.length-1)s+=",";}
		for(String st : relazioniSynset.keySet()) s+=" "+ relazioniSynset.get(st)+st+" ";
		s+=" ("+gloss+")"+" "+esempi;
		
		return s;
	}
	
	/**
	 * crea una stringa con il contenuto del synset, escludendo gli ID delle relazioni con altri synset
	 * @return stringa synset
	 */
	public String printSynset() {
		String s = "";
		for(int i=0; i<synonyms.length;i++) { s+=" "; s+=synonyms[i]; if(i!=synonyms.length-1)s+=",";}
		s+=" ("+gloss+")";
		if(esempi.size()>0) s+=" "+esempi;
		return s;
	}
	
	/**
	 * Trova i Synset in relazione con il synset chiamante
	 * Il tipo di relazione viene dato come parametro
	 * 
	 * @param   rel   Valore che indica il tipo di relazione
	 * @return  Una lista di Synset, vuota se la ricerca non dà risultati
	 */
	public List<Synset> getRelatedSynsets(String rel) {

		List<Synset> relatedSynsets = new ArrayList<>();

		for(String str : relazioniSynset.keySet()) 
			if(relazioniSynset.get(str).equals(rel)) 	
				//for(String word:wn.getSynsetFromID(ss).getSynonyms()) relatedWords.add(word);
				relatedSynsets.add(wn.getSynsetFromID(str));
		
		return relatedSynsets;
	}
	
	/**
	 * confronta due synset
	 * @param sys Oggetto di tipo Synset
	 * @return	True se sono Synset uguali (stesso offset, stesso ID)
	 */
	public boolean equal(Synset sys) 
	{
		if(sys.getOffset().equals(offset)&&sys.getid().equals(id)) return true;
		return false;}
	

	
}