package it.uniroma1.fabbricasemantica.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import it.uniroma1.metodologie2019.hw3.Relations;
import it.uniroma1.metodologie2019.hw3.Synset;
import it.uniroma1.metodologie2019.hw3.WordNet;
/**
 * Permette di estrarre i dati necessari al task MemoryValidation e di restituirli in formato stringa.<br>
 * I dati vengono estratti tenendo conto del livello di difficoltà scelto dall´utente e della dimensione della tabella
 * di gioco con i metodi {@link #getLevel1}, {@link #getLevel2}, {@link #getLevel3}.<br>
 * <br>Dispone di un builder {@link GameBuilder} che permette di istanziare un MemoryGameProvider o di modificarne l´istanza a seconda 
 * delle richieste dell´utente. Si tratta inoltre di un singleton che viene istanziato una sola volta dal server. Poichè ogni livello di difficoltà è composto da una percentuale di parole fornite dai metodi  {@link #getLevel1(int)}, {@link #getLevel2(int, int)}, {@link #getLevel3(int, int)},
 * ogni volta che viene lanciata una partita è necessario lanciare tutti e tre questi metodi, perciò è utile che siano concatenabili.
 * <br><br>
 * Le percentuali vengono imposte dal {@link RangeLevel}, al momento della costruzione del gioco nella servlet
 *  e a seconda del livello scelto dall´utente.
<br><br>
 * Estende {@link StandardDataProvider} e disponde quindi dell´accesso a tutti i suoi metodi.
 * 
 * <br><br>
 * <b>In cosa consiste il gioco:</b> una tabella contiene in ogni sua cella una parola o una glossa. A ogni parola corrisponde sempre una glossa appropriata e viceversa, 
 * disposte però in ordine casuale. 
 * Intoltre, come nel gioco classico del memory, si possono selezionare e scoprire
 * solo due caselle alla volta, mentre le altre restano coperte. Se queste contengono una parola e una glossa che l´utente ritiene essere corrispondenti possono essere convalidate come coppia.
 * Non viene fornita una risposta sulla correttezza della coppia, ma ogni selezione a partita terminata verrà inviata al server e salvata insieme ai risultati degli altri task.
 * È possibile comunque avere dei suggerimenti con il metodo {@link #getSuggerimento(String)} e di vedere i risultati corretti dell´interaa partita.
 *  *
 */
public class MemoryGameProvider extends StandardDataProvider {
	
	private Map<String, String> mappaSuggerimenti;
	private Map<String, String[]> mappa;
	private WordNet wn;
	
	/**
	 * costruttore privato, istanziato dal {@link GameBuilder}
	 * @param path il percorso dell´applicazione corrente
	 */
	private MemoryGameProvider(String path) {
		super(path);
		this.mappa=new HashMap<>();
		this.mappaSuggerimenti = new HashMap<>();
		this.wn = super.getWordNet();
	}

	/**
	 * Stampa i suggerimenti della partita attualmente istanziata
	 */
	public void stampaSuggerimenti() {
		for(String s : mappaSuggerimenti.keySet()) {
			System.out.println(s+":"+mappaSuggerimenti.get(s));
		}
	}
	/**
	 * crea una stringa contenente tutte le coppie parola-glossa del gioco.
	 * Ogni elemento viene saparato dal carattere "$" che potrà essere utilizzato per fare lo split.
	 * Inoltre ogni parola o glossa termina con un * e un numero. Il numero serve ad accoppiare ogni
	 * parola con la glossa che ha lo stesso numero, l`* serve a identificare la posizione e il numero
	 * di cifre del numero nella stringa.
	 * 
	 * Se il task richieso non è MEMORY_VALIDATION, lancia il getTask(Task) implementato dalla superclasse {@link StandardDataProvider}.
	 * 
	 * @param task il task di cui si vogliono ottenere i dati
	 * 
	 * @return le coppie parola glossa in formato Strign
	 */
	@Override 
	public String getData(Task task) {
		
		if (task == StandardTask.MEMORY_VALIDATION) {
			
			int i = 1;
			int l = mappa.keySet().size();
			String s="";	
			
			for(String key : mappa.keySet()) {
				if(i==l) s+=mappa.get(key)[1]+"#"+key+"*"+i+"$"+mappa.get(key)[0]+"&"+i;
				else s+=mappa.get(key)[1]+"#"+key+"*"+i+"$"+mappa.get(key)[0]+"&"+i+"#";	
				i+=1;
			}
			
			s=s.replace("_", " ");			
			return s;
		}

		else super.getData(task);
		return null; 
	}
	/**
	 * Builder modificato in versione singleton che permette di costruire il gioco i suoi metodi 
	 * del builder, e evita che il server lo istanzi ogni volta che viene richiesto dall´utente.
	 * <br><br>
	 * Questa class permette di accedere a un oggetto di tipo {@link MemoryGameProvider} nel seguente modo:<br>
	 * -istanziarlo con il builder statico GameBuilder<br>
	 * -dall`istanza del GameBuilder costruire il gioco (il gioco viene quindi costruito ogni volta senza 
	 * 	bisogno di istanziare di nuovo l´oggetto, ma semplicemente resettando i valori della partira precedente)<br>
	 * <br><br>
	 * Il gioco viene costruito con le coppie parola-glossa fornite da 3 metodi-livelli. Ogni livello di difficoltà
	 * scelto dall´utente è composto da proporzioni diverse di parole fornite da questi 3 metodi 
	 * {@link #getLevel1(int)}, {@link #getLevel2(int, int)}, {@link #getLevel3(int, int)}
	  * <br><br>
	  * 
	  * Le proporzioni vengono dettate dal {@link RangeLevel} nella servlet al momento della costruzione del gioco
	  *  a seconda del livello scelto dall´utente.
	 * 
	 *
	 */	
    public static class GameBuilder {
    	
    	/**
    	 * campo statico con l´istanza dell´oggetto builder stesso
    	 * usato per poter tornare il builder nel metodo statico getInstance()
    	 * e istanziato nel costruttore del builder
    	 */
    	private static GameBuilder builderInstance;
    	
    	/**
    	 * istanza del gioco che stiamo costruendo. Viene istanziato dal costruttore 
    	 * del builder
    	 */
    	private MemoryGameProvider instance;
    	
    	/**
    	 * costruttore privato del GameBuilder
    	 * @param path il path dell´applicazione corrente
    	 */
    	private GameBuilder(String path) {	
    		builderInstance=this;
    		instance=new MemoryGameProvider(path);
    	}
    	
    	/**
    	 * istanzia un GameBuilder e il suo MemoryGameProvider o restituisce il GameBuilder se era già
    	 * stato istanziato precedentemente (singleton pattern).
    	 * 
    	 * @param path stringa indicante il path dell´applicazione corrente
    	 * @return istanza del builder
    	 */
    	public static GameBuilder getInstance(String path) {
    		if(builderInstance==null)
    			new GameBuilder(path); 	
    		return builderInstance;
    	}
    	
    	/**
    	 * Azzera lo stato del gioco.
    	 * @return this
    	 */
    	public GameBuilder resetGame() {
    		instance.mappaSuggerimenti = new HashMap<>();
    		instance.mappa = new HashMap<>();
    		return this;
    	}
    	
    	/**
    	 * Ottiene coppie parola-glossa secondo il metodo {@link MemoryGameProvider#getLevel1(int)}
    	 * @param numeroParoleFinale parole richieste dal livello scelto
    	 * @return this
    	 */
        public GameBuilder buildLevel1(int numeroParoleFinale) {
            instance.getLevel1(numeroParoleFinale);
            return this;
        }
        
    	/**
    	 * Ottiene coppie parola-glossa secondo il metodo {@link MemoryGameProvider#getLevel2(int, int)}
    	 * @param numeroParoleFinale parole richieste dal livello scelto
    	 * @param level numero minimo di sinonimi per ogni synset scelto
    	 * @return this
    	 */
        public GameBuilder buildLevel2(int numeroParoleFinale, int level) {
            instance.getLevel2(numeroParoleFinale, level);
            return this;
        }
        
    	/**
    	 * Ottiene coppie parola-glossa secondo il metodo {@link MemoryGameProvider#getLevel3(int, int)}
    	 * @param numeroParoleFinale parole richieste dal livello scelto
    	 * @param level numero minimo di sinonimi per ogni synset scelto
    	 * @return this
    	 */
        public GameBuilder buildLevel3(int numeroParoleFinale, int level) {
            instance.getLevel3(numeroParoleFinale, level); 
            return this;
        }
      
        /**
         * Restituisce l´istanza del gioco allo stato attuale
         * @return instance di {@link MemoryGameProvider}
         */
        public MemoryGameProvider buildGame() {
        	return instance;
        }
        
        
        
        
    }
    
    /**
     * restituisce il suggerimento corrispondente alla parola cercata
     * @param word parola
     * @return suggerimento
     */
    public String getSuggerimento(String word) {
		return "Suggerimento per la parola \""+word+"\": "+mappaSuggerimenti.get(word).replace("_", " ");  	
    }
    
    
	/**
	 * ALGORITMO I:
	 * ottiene i synset per il I livello di difficoltà, in cui i synset sono semplicemente presi in maniera casuale dal WordNet
	 * e quindi probabilmente distanti semanticamente tra loro.
	 * 
	 * @param numeroParoleFinale quante parole devono essere trovate
	 */
	public void getLevel1(int numeroParoleFinale) {
		
		int n=0;
		while(n<numeroParoleFinale) {
			Synset sys = getSyn();
			String word = getWord(sys);
			if(!mappa.containsKey(word)) {
				String[] lista = {sys.getGloss(), sys.getID()};
				mappa.put(word, lista);
				mappaSuggerimenti.put(word.replace("_"," "), getSuggestions(sys));
				n++;}
		}
	}
		
	
		/**
		 * 		 * ALGORITMO II:
		 * Prende un synset a caso, ne estrae le parole, per ogni parola cerca i synset che la contengono.
		 * Ne sceglie uno per ogni parola (che non sia già stato scelto per un`altra).
		 * La parola ce l`ha già, estrae la glossa dal synset scelto e inserisce la coppia parola-glossa
		 * nella mappa delle parole da restituire.
		 * 
		 * @param numeroParoleFinale quante parole devono essere trovate
		 * @param level numero minimo di sinonimi che un synset deve contenere
		 */		
		public void getLevel2(int numeroParoleFinale, int level) {
			
			int n=0;
			loop:
			while(n<numeroParoleFinale) {
				Synset s = getSyn();
				List<String> listaParole = s.getSynonyms();		
				List<Synset> listaSynsetScelti = new ArrayList<>();
				//il numero di sinonimi che deve contenere il synset lo decide il parametro level
				if(listaParole.size()>=level) 				
					for(String parola : listaParole) {
						//se la parola non è già presente nella mappa
						if(!mappa.containsKey(parola)) {
							//per ogni parola ottiene una lista di synset che la contengono
							List<Synset> daScegliere = wn.getSynsets(parola);
							
							//ordina la lista di Synset per numero di sinomini contenuti
								daScegliere.sort((p,t)->p.getSynonyms().size() - t.getSynonyms().size());
								
								//di questi Synset ne scelgo uno a caso che non sia già stato scelto
								for(int j=0; j<daScegliere.size(); j++) {
									int i = new Random().nextInt(daScegliere.size());
									Synset syn = daScegliere.get(i);
									if(!listaSynsetScelti.contains(syn)){
											listaSynsetScelti.add(syn);
											//aggiungo nella mappa parola e glossa
											String[] lista = {syn.getGloss(), syn.getID()};
											mappa.put(parola, lista);
											//inserice parola e suggerimento nella mappa dei suggerimenti
											mappaSuggerimenti.put(parola.replace("_"," "), getSuggestions(syn));
											n+=1;
											if(n>=numeroParoleFinale) break loop;
											break;}
								}
							}
						}						
			}			
		}
		
		
		 /**
		 * 		 * ALGORITMO III:
		 * Prende una parola a caso, per quella parola ottiene tutti i synset che la contengono.
		 * Per ognuno di quei synset ne estrae una parola che non sia già stata scelta
		 * (ovvero crea una lista di parole, ne estraa parole a caso, via via che queste
		 * parole vengono scartate le toglie dalla lista. Se la lista diventa vuota interrompe
		 * il ciclo, così se trova la parola).
		 * Il synset, quindi la glossa corrispondete, ce l`ha già: gli associa la parola e salva la coppia nella
		 * mappa delle patole da restituire
		 * 
		 * @param numeroParoleFinale quante parole devono essere trovate
		 * @param level numero minimo di sinonimi che un synset deve contenere
		 */
		public void getLevel3(int numeroParoleFinale, int level) {
			
			int numeroParoleTrovate = 0;
			List<String> paroleTrovate = new ArrayList<>();
			
			loop:
			while(numeroParoleTrovate<numeroParoleFinale) {
				
				//ottieni un synset a caso e estraine una parola a caso
				Synset syn = getSyn();
				String word = getWord(syn);
				
				//ottieni la lista dei synset che contengono la parola
				List<Synset> listaSynset = wn.getSynsets(word);
				
				//ordina la lista di Synset per numero di sinomini contenuti
				listaSynset.sort((s,t)->s.getSynonyms().size() - t.getSynonyms().size());
				
				//il numero minimo di sensi di una parola è dato come parametro relativo alla difficoltà del gioco 
				//(andrà quindi impostato nei campi della classe con il costruttore)
				if(listaSynset.size()>=level) 
					for(Synset synset : listaSynset) 
						if(synset.getGloss()!=null){
						int len = paroleTrovate.size();
						//lancia la ricorsione per trovare una parola
						paroleTrovate=ricorsioneLiv3(paroleTrovate, synset.getSynonyms());
						
						//se una parola è stata trovata
						if(paroleTrovate.size()>len) {
							//inserisce parola e glossa nella mappa
							String[] lista = {synset.getGloss(), synset.getID()};
							mappa.put(paroleTrovate.get(len), lista);
							//inserice parola e suggerimento nella mappa dei suggerimenti
							mappaSuggerimenti.put(paroleTrovate.get(len).replace("_"," "), getSuggestions(synset));
							numeroParoleTrovate+=1;
							if(numeroParoleTrovate>=numeroParoleFinale) break loop;
						}
					}			
			}
		}
		//funzione interna ricorsiva
		private List<String> ricorsioneLiv3(List<String> paroleTrovate, List<String> paroleDaScegliere){
			Random r = new Random();
			int n= paroleDaScegliere.size();
			if(n==0) return paroleTrovate;		
			int i = r.nextInt(n);
			String parola = paroleDaScegliere.get(i);
			if(!paroleTrovate.contains(parola) && !mappa.containsKey(parola)) {
				paroleTrovate.add(parola);
				return paroleTrovate;}
			paroleDaScegliere.remove(parola);
			paroleTrovate=ricorsioneLiv3(paroleTrovate, paroleDaScegliere);
			return paroleTrovate;
		}
		
		/**
		 * Se il synset richiesto ha delle relazioni semantiche con altri synset, interrogando il {@link WordNet} il metodo
		 * ne prende una a caso e la assegna nella mappa dei risultati alla coppia parola-glossa corrispondente.<br>
		 * Potrà così essere inserita nel gioco come suggerimento per una parola. Se richiesto, il suggerimento mostrerà infatti 
		 * un lemma e rispettiva glossa in una qualche relazione semantica con la parola del gioco, aiutanto così a interpretarne
		 * il senso.<br>
		 * Se la parola non avesse relazioni, viene trasmesso il messaggio "Purtroppo non ci sono suggerimenti disponibili per questa parola"
		 * 
		 * @param syn synset cui trovare una relazione semantica
		 * 
		 * @return stringa con il suggerimento
		 * 
		 */
		public String getSuggestions(Synset syn){
			
			//a partire dal synset ottiene la mappa delle relazioni con altri synset
			Map<String, String> m = syn.getRelazioniSynset();
			
			if(m==null || m.size()==0)
				return "Purtroppo non ci sono suggerimenti disponibili per questa parola";
			
			//dalla mappa ottiene la lista degli ID dei synset 
			Set<String> set = m.keySet();
			
			//estrae un ID a caso di uno dei synset
			int n = set.size();
			if(n>=0) {
				String relatedSynset ="";
				Random r = new Random();
				n = r.nextInt(n);
				int i=0;
				
				for(String str : set) {
					if(i==n) { relatedSynset=str; break;}
					i++;
				}
				
				//ottiene dall`id il simbolo della relazione
				String relazione = m.get(relatedSynset);
				
				//dal simbolo ricava il nome della relazione
				Relations rel = Relations.valueOfLabel(relazione);
				
				//ottiene il synset dall`ID
				Synset temp = wn.getSynsetFromID(relatedSynset);
				
				//crea una string con il nome della relazione e il contenuto del synset
				String suggerimento = rel+": "+temp.printSynset();
				return suggerimento;
			}
		return null;	
		}

}
