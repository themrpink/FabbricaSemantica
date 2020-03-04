package it.uniroma1.metodologie2019.hw3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.*;



/**
 * 
 * La classe permette di istanziare una versione di WordNet,.
 * Ogni versione è istanziabile una sola volta tramite il metodo statico
 * getInstance che prende come parametro la stringa che indica la versione
 * e richiama il costrutture privato.
 * 
 * Ad ogni versione corrisponde un path, se la directory corrispondente alla versione
 * che si vuole istanziare non è presente getInstance restituisce null.
 * 
 * Le varie istanze create vengono raccolte in un HashMap che prende come chiave la stringa della
 * versione e come valore l'istanza. Questo permette di verificare se per una versione è già stato 
 * istanziato un oggetto, in tal caso restituisce l'oggetto già istanziato.
 * 
 * @author stefano urani
 * 

 * 
 *
 */
public final class WordNet implements Iterable<Synset>
{
	public static File file;
	public static Path p;
	private static HashMap<String, WordNet> instances;
	private static HashMap<String, File> pathMap;
	private String versione;
	private int start;
	private Dizionario diz;
	private List<Synset> listaSynset;
	private String path;
	private Set<String> setExamples;

	/**
	 * assegna a path il percorso per accedere ai file del WordNet
	 * assegna a instance l'istanza corrispondente alla versione attuale
	 * prendendolo dall'HashSet instances.
	 * Il valore start permette di iniziare a estrarre i synset da una riga specifica
	 * Puo' essere modificato tramite il metodo pubblico setStart()
	 * Infine istanzia un oggetto di tipo Dizionario e lancia il metodo estraiTesti()
	 * che si occupa di estrarre da tutti i file necessari i synset e salvarli in una 
	 * collezione iterabile 
	 * 
	 * @param versione
	 */
	private WordNet(String versione)
	{
		this.versione=versione;
		this.start=29;
		this.path=file.getPath()+File.separator+"dict"+File.separator;
		p = Paths.get(path);
		this.diz= new Dizionario(this);
		estraiTesti();
		setExamples=getAllExamples();
	}

	/**
	 *  Istanzia l'oggetto statico FilePermette e controlla se il path della versione 
	 *  è corretto, permettendo così di verificare se la versione richiesta è disponibile 
	 *  oppure no.
	 *  
	 * @param s  Stringa che indica la versione del WordNet 
	 * @return True se il path della versione richiesta esiste, 
	 * 			False altrimenti
	 * @throws IOException file non trovato
	 */
	public static boolean checkPath(String s) throws IOException {
	
		File catalinaBase = new File(System.getProperty( "catalina.base" ) ).getAbsoluteFile();
		file=new File(catalinaBase+"/wtpwebapps/fabbricasemantica/WEB-INF/wordnet-releases/releases/WordNet-"+s);
		
		//file=new File("C:\\Users\\themr\\eclipse-workspace\\fabbricasemantica\\WebContent\\WEB-INF\\wordnet-releases\\releases\\WordNet-3.0");
		if(file.exists())return true;
		return false;
	}
	
	
	/**
	 * 
	 * @param s  versione di WordNet da istanziare
	 * @return	una versione di WordNet dall'HashMap instances.
	 * 			Se la versione esiste nella cartella di destinazione 	
	 * 			e non è già prensete in instances ne istanzia una nuova
	 * @throws IOException file non trovato
	 */
	public static WordNet getInstance(String s) throws IOException
	{
		if(!checkPath(s)) return null;
		if(instances==null){ 
			instances=new HashMap<>();
			instances.put(s, new WordNet(s));
			pathMap=new HashMap<>();
			pathMap.put(s, file);
		}
		
		else for(String str: instances.keySet()) if(str.equals(s)) return instances.get(str);
	
		//instances.put(s, new WordNet(s));
		return instances.get(s);
	}
	
	/**
	 *  Il metodo a partire dall'oggetto diz di tipo Dizionario
	 *  istanziato dal costruttore lancia i relativi metodi
	 *  estraiTesto() e createSynsets() per ognuno dei 4 file
	 *  del WordNet passando come parametri il path costruito nel costruttore
	 *  + il nome del file e il valore di start, cioè la riga da cui si inizia a estrarre
	 *  synset.
	 *  
	 *  Infine importa dal dizionario tutti i synset estratti in listaSynset tramite
	 *  il metodo di Dizionario getListaSynset()
	 */
	private void estraiTesti(){
		diz.estraiTesto(path+"data.noun", start);
		diz.createSynsets();
		diz.estraiTesto(path+"data.verb", start);
		diz.createSynsets();
		diz.estraiTesto(path+"data.adj", start);
		diz.createSynsets();
		diz.estraiTesto(path+"data.adv", start);
		diz.createSynsets();
		listaSynset=diz.getListaSynset();
	}

	/**
	 * 
	 * @return la versione del WordNet corrente
	 */
	public String getVersione() {return versione;}
	
	/**
	 * 
	 * @param s  lemma richiesto
	 * @return	una lista di tutti i synset che contengono
	 * 			il lemma richiesto
	 */
	public  List<Synset>  getSynsets(String s) {		
		
		List<Synset> listaSynsetTrovati = diz.getListaSynset().stream()
				.parallel()
				.filter(syn->syn.getSynonyms().stream()
											  .anyMatch(st->st.toLowerCase().equals(s.toLowerCase()))
											   )
				.collect(Collectors.toList());			

		return listaSynsetTrovati;
	}

	/**
	 * Overload
	 * @param s  lemma richiesto in formato String
	 * @param e  tipo richiesto in formato Enum POS 
				(NOUN, VERB, ADJECTIVE, ADVERB)
	 * @return List una lista di tutti i synset che contengono
	 * 				il lemma richiesto di tipo corrispondente a
	 * 				quello richiesto
	 */
	public  List<Synset>  getSynsets(String s, POS e) {				
		
		List<Synset> listaSynsetTrovati = diz.getListaSynset().stream()
					.parallel()
					.filter(syn->syn.getPOS().equals(e))
					.filter(syn->syn.getSynonyms().stream()
												  .anyMatch(st->st.toLowerCase().equals(s.toLowerCase()))
												  )
					.collect(Collectors.toList());			
		
		return listaSynsetTrovati;
	}
	
	/**
	 * permette di settare il valore di start
	 * @param n intero
	 */
	public void setStart(int n) {start=n;}
	
	
	/**
	 * 
	 * @return la lista completa dei Synset del WordNet corrente
	 */
	public List<Synset> getLista() {return listaSynset;}
	
	/**
	 * 
	 * @return lo stream della lista di Synset
	 */
	public Stream<Synset> stream(){ return listaSynset.stream();}
	
	/**
	 * dato in input l’ID sotto forma di stringa (offset+pos, es. 00001740n), 
	 * restituisce il synset corrispondente, null se non presente.
	 * 
	 * @param rel  ID richiesto
	 * @return	 oggetto di tipo Synset
	 */
	public Synset getSynsetFromID(String rel){
		
	/*	Synset s = diz.getListaSynset().stream().parallel()
					.filter(syn->syn.getID().equals(rel))			
					.findAny()
					.orElse(null);	*/
		for (Synset s : diz.getListaSynset()) {if (s.getID().equals(rel)) return s;}
		return null;
		//return s;
	}
	
	
	/**
	 * 
	 * @return  restituisce la versione dell'istanza corrente di WordNet
	 */
	public String getVersion() {return versione;}
	
	
	/**
	 * Dato un Synset e una relazione sotto forma di Stringa
	 * restituisce tutti i Synset che sono legati a quello indicato
	 * secondo la relazione richiesta inserendoli in un ArrayList
	 * 
	 * @param sys   oggetto tipo Synset
	 * @param relazione  String, indica la relazione
	 * @return List	una lista di Synset
	 */
	public List<Synset> getRelatedSynsets(Synset sys, String relazione){
	
		List<Synset> relatedSynsets = sys.getRelazioniSynset().keySet().stream()
								.parallel()
								.filter(s->sys.getRelazioniSynset().get(s).equals(relazione))
								.map(s->getSynsetFromID(s))
								.collect(Collectors.toList());				
		return relatedSynsets;
	}
	
	/**
	 * Overload
	 * 
	 * Dato un Synset e una relazione sotto forma di WordNetRelation
	 * restituisce tutti i Synset che sono legati a quello indicato
	 * secondo la relazione richiesta
	 * @param sys   oggetto tipo Synset
	 * @param e     relazione  di tipo WordNetRelation, indica la relazione
	 * @return ArrayList	una lista di Synset
	 */
	public List<Synset> getRelatedSynsets(Synset sys, WordNetRelation e){
			
		List<Synset> relatedSynsets = sys.getRelazioniSynset().keySet().stream()
				.parallel()
				.filter(s->sys.getRelazioniSynset().get(s).equals(e.getID()))
				.map(s->getSynsetFromID(s))
				.collect(Collectors.toList());	
		
		return relatedSynsets;
	}

	@Override
	public Iterator<Synset> iterator(){ return new Iterator<Synset>() {
		
		private int n;
		@Override
		public boolean hasNext() {return n<listaSynset.size();}

		@Override
		public Synset next() {return listaSynset.get(n++);}
	};
	}


	
	
	private Set<String> getAllExamples() {
		
		Set<String> setEsempi = new HashSet<>();
		for(Synset syn : listaSynset)  
			for(String str : syn.getExamples())
				setEsempi.add(str);		
		return setEsempi;
	}
	

	public Set<String> getSetExamples(){
		return setExamples;
	}


}
