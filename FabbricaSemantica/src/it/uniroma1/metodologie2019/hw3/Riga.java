package it.uniroma1.metodologie2019.hw3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * questa classe prende una stringa e la divide secondo 
 * le caratteristiche di un synset con i suoi metodi
 * 
 *String riga: la riga in formato String
 *String[] rigaArray: split di riga
 *int indice Barra: l´indice dove inizia la glossa
 *int inizioEsempi: indice dove iniziano gli esempi
 * @author stefano urani
 *
 */
public class Riga 
{
	private String[] rigaArray;
	private int indiceBarra;
	private String riga;
	private int inizioEsempi;
	
	public Riga(String riga) 
	{
		this.riga=riga;
		this.rigaArray=riga.split(" ");
		indiceBarra=getIndiceBarra();
	}
	
	/**
	 * getter di riga
	 * @return String riga
	 */
	public String getRiga() {return riga;}

	/**
	 * restiuisce l´offset del synset
	 * @return stringa
	 */
	public String getOffset() {return rigaArray[0];}
	
	/**
	 * Estrae e restituisce i sinomini del synset
	 * @return array di stringhe
	 */
	public String[] getSynonyms() 
	{
		
		int conta=0;
		for(int i=4; i<indiceBarra; i+=2)
		{
			
			if(rigaArray[i].charAt(0)=='0') break;
			if(rigaArray[i].charAt(0)=='1') break;
			if(rigaArray[i].charAt(0)=='2') break;
			else conta+=1;
		}
		
		String[] words = new String[conta];
		for(int i=0; i<conta;i++) words[i]=rigaArray[4+(i*2)];
		return words;
	}

	/**
	 * restituisce la posizione della barra verticale
	 * che separa la glossa e gli esempi dal resto del synset
	 * @return intero  posizione della stringa
	 */
	public int getIndiceBarra()
	{
		for(int i=5; i<riga.length(); i++)
		{
			if(riga.charAt(i)=='|') 
			{
				indiceBarra=i;
			}
		}
	
		
		
		return indiceBarra;
	}
	
	/**
	 * questo metodo estrae la glossa. Inoltre cerca 
	 * se sono presenti degli esempi ed eventualmente salva 
	 * nel campo inizioEsempi la posizione di inizio degli esempi nella riga.
	 * 
	 * @return stringa, glossa del synset
	 */
	public String getGloss() 
	{
		for(int i=indiceBarra; i<riga.length(); i++)
		{
			if(riga.charAt(i)=='"') 
			{ 
				inizioEsempi=i; 
				String riga2=riga.substring(indiceBarra+2, i).trim(); 
				int l = riga2.length();
				if(l>3&&!riga2.isEmpty()&&riga2.charAt(l-1)==';') return riga2.substring(0, l-1);
				return riga2;
			}
		}
		
		return riga.substring(indiceBarra+2).trim();}
	
	/**
	 * per estrarre gli esempi utilizza il campo inizioEsempi,
	 * ottenuto con il metodo getGloss().
	 * Se il campo inizioEsempi è uguale a 0, allora
	 * non ci sono esempi e viene restituita una stringa vuota
	 * 
	 * @return String	esempi del synset
	 */
	public ArrayList<String> getEsempi()
	{
		ArrayList<String> listaEsempi = new ArrayList<>();
		if(inizioEsempi!=0)
		{
			String riga2 = riga.replace("\"", "");
			String[] s = riga2.substring(inizioEsempi).split(";");
			for(String ss : s) listaEsempi.add(ss.replace(";", "").trim());
			return listaEsempi;
		}
		return listaEsempi;
	}
	
	/**
	 * Estrae dalla riga l`ID del Synset corrente in formato POS
	 * @return POS  NOUN,VERB,ADJECTIVE,ADVERB
	 * 
	 * se il synset ha come ID "s" oppure "a" restituisce in entrambi i casi ADJECTIVE
	 * Se non fosse presente nessuno dei casi indicati restituisce null
	 */
	public POS getID()
	{
	
		switch(rigaArray[2]) 
			{
			case "n": return POS.NOUN; 
			case "v" : return POS.VERB;
			case "a": case "s": return POS.ADJECTIVE;
			case "r": return POS.ADVERB;
			}
	
		return null;
       
	}

	/**
	 * Estrae dalla riga l`ID del Synset corrente in formato Stringa
	 * @return String  "n" per nome, "v" per verbo, "a" per aggettivo, "r" per avverbio
	 * 
	 * se il synset ha come ID "s" oppure "a" restituisce in entrambi i casi "a"
	 * Se non fosse presente nessuno dei casi indicati restituisce null
	 */
	public String getId()
	{
	
		switch(rigaArray[2]) 
			{
			case "n": return "n"; 
			case "v": return "v";
			case "a": case "s": return "a";
			case "r": return "r";
			}
	
		return null;
       
	}
	
	
	/**
	 * Cerca nella riga tutti i simboli che indicano relazioni con altri synset e ne estrae l`offset dei 
	 * relativi offset.
	 * 
	 * Dal momento che questi simboli cambiano a seconda del tipo di synset, quelli specifici al tipo
	 * di synset corrente si ottengono dal suo POS come mappa che associa a ogni tipo di relazione la sua descrizione.
	 * 
	 * Utilizza come appoggio il metodo findRelation() sempre della classe Riga
	 * @return una mappa 
	 */
	public HashMap<String, String> getReferences() 
	{	
		HashMap<String, String> hm;
		HashMap<String,String> relazioniSynset = new HashMap<>();
		int count=0;
		
		switch(getID()){
			case NOUN: hm=POS.NOUN.getHS(); break;
			case VERB: hm=POS.VERB.getHS(); break;
			case ADJECTIVE: hm=POS.ADJECTIVE.getHS(); break;
			case ADVERB: hm=POS.ADVERB.getHS(); break;
			default: hm=null; break;
		}
		
		for(String s:rigaArray)
		{
			if(s.equals("|")) break;
			
			else if(s.length()<3 && rigaArray[count+1].length()>6 && findRelation(hm, s))
				relazioniSynset.put(rigaArray[count+1]+rigaArray[count+2],s);
			
			count++;
		}		
		return relazioniSynset;
	}
	
	/**
	 * verifica se la Stringa s è presente come simbolo relazionale nella mappa di relazioni
	 * importata da POS per il tipo del Synset corrente.
	 * @param  hm  mappa delle relazioni
	 * @param s  relazione in formato Stringa
	 * @return boolean true se è presente, false altrimenti
	 */
	
	public boolean findRelation(HashMap<String,String> hm, String s) 
	{
	
		boolean b = hm.keySet().stream()
							   .map(s::equals)
							   .filter(st->st==true)
							   .findAny()
							   .orElse(false);
		return b;
	}
}
