package it.uniroma1.metodologie2019.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * 
 * La classe ha come campi due oggetti di tipo WordNet e una lista 
 * di oggetti di tipo SynsetPairing.
 * 
 * Permette di fare la mappatura tra versioni diverse di WordNet 
 * e attribuire un punteggio di somiglianza tra synset
 * 
 * @author stefano urani
 */
public class WordNetMapping 
{

	private WordNet wn1;
	private WordNet wn2;
	private List<String> paroleEscluse;
	
	public WordNetMapping(WordNet wn1, WordNet wn2)
	{
		this.wn1=wn1;
		this.wn2=wn2;
		this.paroleEscluse=Arrays.asList("the","with","for","who","whom","which", "from", "what", "when", "where");
	}
	
	/**
	 * Aggiunge una parola alla lista paroleEscluse,
	 * che sono le parole che non vengono prese in considerazione
	 * nel confronto tra glosse
	 * @param parola   String, parola da escludere nel confronto tra glosse
	 */
	public void addParolaEsclusa(String parola) {if(!paroleEscluse.contains(parola)) paroleEscluse.add(parola);}
	
	/**
	 * Rimuove una parola dalla lista paroleEscluse,
	 * che sono le parole che non vengono prese in considerazione
	 * nel confronto tra glosse
	 * @param parola   String, parola da escludere nel confronto tra glosse
	 */
	public void removeParolaEsclusa(String parola) {if(paroleEscluse.contains(parola)) paroleEscluse.remove(parola);}
	
	/**
	 * 
	 * @return List paroleEscluse, la lista delle parole che non vengono prese
	 * in consderazione nel confronto tra glosse
	 */
	public List<String> getParoleEscluse(){return paroleEscluse;}
	
	
	/**
	 * Cerca per il Synset passato come parametro 
	 * il corrispettivo oggetto di tipo SynsetPairing 
	 * con il miglior punteggio
	 * 
	 * @param src   Synset
	 * @return oggetto di tipo Optional contenente un SynsetPairing 
	 */
	
	public Optional <SynsetPairing> getMapping(Synset src)

	{		
		if(wn1.getVersione().equals(wn2.getVersione())) {
			Optional<SynsetPairing> sc = Optional.ofNullable(new SynsetPairing(src,src, 1.0));
			return sc;
		}

		Optional<SynsetPairing> sc = Optional.ofNullable(compare(src));
		return sc;
	}
	
	/**
	 * 
	 * confronta i synset di due WordNet diversi che hanno i sinonimi
	 * in comune e gli attribuisce un punteggio tra 0.0 e 1.0 
	 * in base al numero di parole in comune (se di lunghezza superiore a 2
	 * e se non presenti nella lista paroleEscluse
	 * per evitare le function words )dei due Synsets.
	 * 
	 * Il controllo di somiglianza viene effettuato con il metodo checkIdentity()
	 * Il punteggio viene calcolato con il metodo formnula()
	 * 
	 * Il metodo ritorna l'oggetto di tipo
	 * SynsetPairing per ogni coppia di Synsets con punteggio maggiore. 
	 * 
	 * @param source  oggetto Synset
	 * 
	 *@return SynsetPairing  oggetto tipo SynsetPairing con il punteggio massimo per il Synset in input
	 */
	
	public SynsetPairing compare(Synset source) 
	{
		Synset s=null;
		double score=0.0;
		double temp=0.0;
		String id=source.getid();
		
			for(Synset target: wn2.getLista()){					

				if(id.equals(target.getid())&&checkIdentity(source.getSynonyms(), target.getSynonyms())) {
						
					score = formula(source.getGloss(), target.getGloss());
					if (score==1.0)
						return new SynsetPairing(source, target, score);
							
					if(score>0.0&&score>temp) {
						temp=score;
						s=target;
					}								
				}

			}
			
			if(temp>0.0)
				return new SynsetPairing(source, s, temp);
			else return null;
	}

	
	/**
	 * controlla se tutti i sinonimi di un synset sono presenti nell´altro
	 * 
	 * @param synonyms1  ArrayList di sinonimi del source Synset
	 * @param synonyms2	 ArrayList di sinonimi del target Synset
	 * @return boolean   True se i due synset hanno lo stesso numero di sinonimi e i sinomini sono gli stessi
	 */
	public boolean checkIdentity(ArrayList<String>synonyms1, ArrayList<String>synonyms2) 
	{
		if(synonyms1.size()!=synonyms2.size()) return false;
		
	/*	
	 * realizzato con lo stream è più dispendioso:
	 * 
	 * boolean b = synonyms1.parallelStream()
					.map(s->synonyms2.contains(s))
					.filter(s->s==false)
					.findAny()
					.orElse(true);
		return b */
		
		for(String word: synonyms1) if(!synonyms2.contains(word)) return false;
		return true;
	}

	
	/**
     * Controlla quante parole le glosse dei due synsets hanno in comune.
     * Esclude le function words e conta quante parole vengono escluse
     * 
     * Il punteggio di somiglianza é dato da:
     * 
     *               "numero di parole in comune tra glosse"/
	 *	("numero di parole della glossa source" - "numero parole escluse")
     * 
	 * 
	 * @param glossa1 String glossa source Synset	
	 * @param glossa2 String glossa target Synset
	 * @return double
	 */
	private double formula(String glossa1, String glossa2) 
	{
		List<String> glossaSource = Arrays.asList(glossa1.split(" "));
		//List<String> glossaSource2 = List.of(glossa2.split(" ")); //la differenza è che controllavo se una parola era presente nella glossa sotto forma di stringa, 
																  //non prendendo in considerazione le virgole, dal momento che non si prendono neanche in considerazione le functional words e non viene specificato che le virgole 
																  //implicano una minore somiglianza tra glosse. A me sembra un errore dato da un'ambiguità del testo dell'esercizio, che avrei potuto facilemente evitare

		double parziale=0.0;
		double count=0.0;
		
		
		if(glossa1.equals(glossa2)) 
			return 1.0;
		
		for(String s1:glossaSource) 
			if(s1.length()>2&&!paroleEscluse.contains(s1)) 
			{
				if(glossa2.contains(s1))
				//if(glossaSource2.contains(s1))
					parziale+=1.0;
			}
			else count+=1.0;
		
		parziale = parziale/(glossaSource.size()-count);
		return parziale;
	}
	

	
}
