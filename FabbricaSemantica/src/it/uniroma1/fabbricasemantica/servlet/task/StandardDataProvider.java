package it.uniroma1.fabbricasemantica.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetConfiguration;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.jlt.Configuration;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.metodologie2019.hw3.*;

/**
 * Questa classe restituisce i dati necessari per il task ricevuto in input.
 * Il metodo {@link #getData(Task)} restituira' una stringa in formato JSON
 * che le pagine in JSweet andranno a formattare. I dati vengono ottenuti dal {@link WordNet}, da {@link BabelNet} e dalle risposte degli utenti già
 * salvate nei file XML del progetto con {@link XMLReader}.
 * N.B. Il proprio {@link DataProvider} dovrebbe evitare di avere stringhe codificate, questo e' solo un esempio.
 *
 *Ha un metodo di istanza statico che permette di trattarlo come singleton
 * 
 * */
public class StandardDataProvider implements DataProvider<String> {
	
	private WordNet wn;
	private BabelNet bn;
	private XMLReader xr;
	private Language taskLanguage = Language.EN;
	private Language userLanguage = Language.IT;
	private int maxNumbQuestions = 10;
	
	/**
	 * Costruttore di StandardDataProvider. <br>
	 * Istanzia un {@link WordNet}, un {@link BabelNet} e un {@link XMLReader}
	 * 
	 * @param path il path del WebContent del progetto, in cui trovare i file per inizializzare BabelNet
	 */
	public StandardDataProvider(String path) {
				
		try {
			this.wn=WordNet.getInstance("3.0");
		} catch (IOException e) { 
			e.printStackTrace();
		} 
		
		//Istanzia BabelNet
		Configuration jltConfiguration = Configuration.getInstance();
		jltConfiguration.setConfigurationFile(new File(path + "/WEB-INF/config/jlt.properties"));

		BabelNetConfiguration bnconf = BabelNetConfiguration.getInstance();
		bnconf.setConfigurationFile(new File(path + "/WEB-INF/config/babelnet.properties"));
		bnconf.setBasePath(path + "/WEB-INF/");
		this.bn =  BabelNet.getInstance();
		
		//istanzia l`XMLReader
		try {
			this.xr = new XMLReader(path);
			xr.setMinPreferences(2);
		} catch (ParserConfigurationException | SAXException | TransformerException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * dato un Task come parametro restituisce una Stringa in formato JSON con dati presi dal {@link WordNet}, da {@link BabelNet}o dal {@link XMLReader},
	 * a seconda dei casi.<br><br>
	 * 
	 *<b>TRANSLATION_ANNOTATION</b>: da un {@link Synset} casuale del WordNet restituisce una parola e una glossa.<br><br>
	 *<b>WORD_ANNOTATION</b>: da un synset casuale restituisce la glossa   <br><br>
	 *<b>DEFINITION_ANNOTATION</b>: da un synset casuale restituisce una parola e un suo iperonimo   <br><br>
	 *<b>SENSE_ANNOTATION</b>: da un synset con contiene esempi restituisce parola, glossa ed esempi (presi anche tra le risposte salvate degli utenti se presenti nel file XML con il metodo {@link #addUserResults})   <br><br>
	 *<b>TRANSLATION_VALIDATION</b>: preso un synset casuale, di cui estrae parola e glossa, ne restituisce le traduzioni in italiano che ricava da BabelNet dalle risposte degli utenti se prensenti nel file XML.  <br><br>
	 * <b>SENSE_VALIDATION</b>: da un synset ricava una parola e glosssa e restituisce esempi che contengono quella parola presi dal WordNet e dalle risposte degli utenti se presenti nel file XML.    <br><br>
	 * <br><br>
	 * Se le riposte ottenute superano un certo valore n (10 di default, settabile con il metodo {@link #setMaxNumbQuestions(int)})
	 * queste vengono scelte casualmente in numero pari a n con il metodo {@link #limitSize(List)}.
	 * 
	 * @see Synset
	 * @see WordNet
	 * @see BabelNet
	 * @see Task
	 * @see #addUserResults(Synset, String, Language, List)
	 * @see #setMaxNumbQuestions(int)
	 * @see #limitSize(List)
	 * @see StandardTask#TRANSLATION_ANNOTATION
	 * @see StandardTask#WORD_ANNOTATION
	 * @see StandardTask#DEFINITION_ANNOTATION
	 * @see StandardTask#SENSE_ANNOTATION
	 * @see StandardTask#TRANSLATION_VALIDATION
	 * @see StandardTask#SENSE_VALIDATION

	 */
	@Override 
	public String getData(Task task) {	
		
		Synset sys = null;
		String word="";
		String gloss="";
		String example="";
		String hyp="";
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		
		if (task == StandardTask.TRANSLATION_ANNOTATION) {
			
			/* ricava una parola e la glossa dal synset ottenuto casualmente
			 * elimina trattini inferiori e virgole dalla parola
			 */
			sys = getSyn();
			word=getWord(sys);
			word=word.replace("_", " ");
			word=word.replace(",", "");
			gloss=sys.getGloss();
			String synset = sys.getID();

			return "{" +
					"\"word\": "+"\""+word+"\"," +
					"\"description\": "+"\""+gloss+"\","+
					"\"synset\": "+"\""+synset+"\""+
					"}";
			
			//qua aggiungere anche la stringa con l'ID del synset
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		
		
		else if (task == StandardTask.WORD_ANNOTATION) {
			
			//ottiene la glossa dal Synset
			sys = getSyn();
			gloss=sys.getGloss();
			String synset = sys.getID();
			
			return "{\"description\": "+"\""+gloss+"\","+
				   "\"synset\": "+"\""+synset+"\""+
				   "}";
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		
		
		else if (task == StandardTask.DEFINITION_ANNOTATION) {
			
			sys = getSyn();
			word="";
			String synset="";
			//ripete il ciclo fino a trovare un Synset che abbia una parola e un Hypernym validi
			while(hyp==null||hyp.equals("")||word==null||word.equals("")) {
				synset = sys.getID();
				word = getWord(sys);
				hyp = getHypernym(sys);
				sys=getSyn();
			}
			//elimina eventuali tratti _ dalle parole
			word=word.replace("_", " ");
			word=word.replace(",", "");
			hyp=hyp.replace("_"," ");
			
		
			return "{" + 
					"\"word\": "+"\""+word+"\"," +
					"\"hypernym\": "+"\""+hyp+"\"," +
					"\"synset\": "+"\""+synset+"\""+
					"}";
			
			//qua aggiungere anche la stringa con l'ID del synset
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		
		
		else if (task == StandardTask.SENSE_ANNOTATION) {
			
			String result="";
			example="";
			String synset ="";
			//ripete il ciclo fino a che non trova un Synset che contiene esempi
			while(example.equals("")) {
				sys=getSyn();
				synset = sys.getID();
				word=getWord(sys);	
				List<String> l = sys.getExamples();
				
				for(String s : l) { 
					if(s.contains(word)) example=s; 
					if(s.length()>0||s!=null)break;
				}
			}
			
			List<Synset> l = wn.getSynsets(word);
			word=word.replace("_", " ");
			word=word.replace(",", "");
			result = "{" +"\"word\": "+"\""+word+"\"," +
					 "\"description\": "+"\""+example+"\","+
					 "\"synset\": "+"\""+synset+"\","+
					 "\"senses\": ";
			
			//crea una lista di glosse dei synset trovati
			List<String> results = l.stream().map(s -> s.getGloss()).collect(Collectors.toList());
			
			//aggiunge alle glosse altre eventualemente già aggiunte dagli utenti
			results = addUserResults(sys, "gloss", taskLanguage, results);
			
			//riduce il numero di glosse se supera il limite consentito
			results = limitSize(results);
			
			//costruisce una stringa in cui vengono separati ogni parola e rispettiva glossa dal carattere $
			//servirà nel front-end per estrarre i dati
			String glosse = results.stream().collect(Collectors.joining("$"));
	
			result+="\""+glosse+"\"" +"}";
			return result;
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		
		
		else if (task == StandardTask.TRANSLATION_VALIDATION) {
			
			/*
			 * istanzia BabelNet per ottenere le traduzioni da validare da un dizionario inglese-italiano
			 */
			

			Set<String> traduzioniBabelNet = null;
			String synset="";
			
			while(traduzioniBabelNet==null || traduzioniBabelNet.size()==0) {
				sys=getSyn();
				word=getWord(sys);		
				synset = sys.getID();
				
				BabelNetQuery query = new BabelNetQuery.Builder(word)
						.from(taskLanguage)
						.to(Arrays.asList(userLanguage))
						.build();
				
				traduzioniBabelNet = bn.getSynsets(query).stream()
						.map(s->s.getLemmas(userLanguage))
						.filter(t->t.size()>0)
						.map(s->s.get(0).toString().replace("_", " "))
						.collect(Collectors.toSet());
				gloss=sys.getGloss();	
			}
			
			String translation="";
			gloss=sys.getGloss();

			//aggiunge eventuali traduzioni insetite dagli utenti
			List<String> traduzioniBabelNetList = traduzioniBabelNet.stream().collect(Collectors.toList());
			traduzioniBabelNetList = addUserResults(sys, "word", userLanguage, traduzioniBabelNetList);
			
			//limita il numero delle risposte
			traduzioniBabelNetList = limitSize(traduzioniBabelNetList);
			
			//crea una stringa in cui ogni traduzione è separata dal carattere "$"
			for(String s:traduzioniBabelNetList) translation+="$"+s;

			word=word.replace("_", " ");
			word=word.replace(",", "");
			
			return "{" + 
					"\"word\": "+"\""+word+"\"," +
					"\"description\": "+"\""+gloss+"\"," +
					"\"synset\": "+"\""+synset+"\","+
					"\"translations\": "+"\""+translation+"\"" +
					"}";	
		}
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	
		
		else if (task == StandardTask.SENSE_VALIDATION) {
			example="";
			String synset="";
			/*
			 * il ciclo cerca un synset che abbia un esempio
			 * da questo synset ricava una parola tra i vari sinonimi, un esempio e una glossa
			 */
			while(example==null||example.equals("")|| gloss.equals("")){
				gloss="";
				sys=getSyn();
				synset=sys.getID();
				word=getWord(sys);
				example=getWordExample(word);
				gloss=sys.getGloss();
			}
			word=word.replace("_", " ");
			word=word.replace(",", "");
			
			//cerca la glossa da validare anche tra le riposte degli utenti						
			List<String> userGlosses = addUserResults(sys, "gloss", taskLanguage, Arrays.asList(gloss));
			
			//se trovate ne seleziona solo una
			if(userGlosses.size()>1) {
				Random r = new Random();
				int n = r.nextInt(userGlosses.size());
				gloss=userGlosses.get(n);
			}
			
			
			return "{" + 
			"\"word\": "+"\""+word+"\"," +
			"\"example\": "+"\""+example+"\"," +
			"\"sense\": "+"\""+gloss+"\"," +
			"\"synset\": "+"\""+synset+"\""+
			"}";
		}
	
	return null; 
}


//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	
	/**
	 * imposta la lingua dei tasks, che poi dovrà essere validata o tradotta nella lingua dall´utente nella propria lingua
	 * @param lang lingua in formato {@link Language}
	 */
	public void setTaskLanguage(Language lang) {
		this.taskLanguage=lang;
	}
	
	/**
	 * imposta la lingua dell`utente, nella quale verranno tradotte le tasks
	 * @param lang lingua in formato {@link Language}
	 */
	public void setUserLanguage(Language lang) {
		this.userLanguage=lang;
	}
	
	/**
	 * Restituisce l´istanza del WordNet
	 * @return istanaza di {@link WordNet}
	 */
	public WordNet getWordNet() {
		return wn;
	}
	
	
	/**
	 * restituisce un {@link Synset} casuale dall´intera lista dei Synset dell´istanza caricata di WordNet,
	 * inclusi nomi, aggettivi, verbi e avverbi
	 * 
	 * @return synset casuale dal {@link WordNet}
	 */

	public Synset getSyn(){	
		
		List<Synset> list = wn.getLista();
		int n = list.size();
		Random r = new Random();
		int i = r.nextInt(n);
		Synset sys = list.get(i);
		return sys;
	}
	
	
	
	
	
	/**
	 * dato un Synset, restituisce casualmente uno dei sinonimi contenuti
	 * @param syn {@link Synset}
	 * @return uno dei sinonimi di un {@link Synset}
	 */
	public String getWord(Synset syn){

		List<String> listSynonyms = syn.getSynonyms();	
		int l = listSynonyms.size();
		
		if(l>0) {
			Random r = new Random();
			int n = r.nextInt(l);
			String word = listSynonyms.get(n);
			return word;
		}
		return null;
	}
	
	
	
	
	
	/**
	 * se presente uno o più esempi all`interno del Synset, 
	 * ne restituisce uno casuale
	 * @param syn {@link Synset}
	 * @return esempio casuale dal {@link Synset}
	 */
	public String getExample(Synset syn) {
		
		List<String> listExamples = syn.getExamples();
		int l = listExamples.size();
		
		if(l>0) {
			
			Random r = new Random();
			int n = r.nextInt(l);
			String example = listExamples.get(n);	
			return example;
		}
		return null;
	}
	
	
	
	
	
	/**
	 *  restituisce una parola casuale da un Synset legato da una relazione semantica
	 *   al Synset passato come argomento
	 *   
	 *   @param syn {@link Synset}
	 *   @return iperonimo
	 */
	public String getHypernym(Synset syn){		

		List<Synset> relatedSynsets = syn.getRelatedSynsets("@");
		
		if(relatedSynsets!=null && relatedSynsets.size()==1) {
			return getWord(relatedSynsets.get(0));}
		
		return null;
	}
	
	

	
	/**
	 * Restituisce dal {@link WordNet} un esempio casuale contenente la parola richiesta
	 * 
	 * @param word parola richiesta
	 * @return String esempio contente la parola
	 */
	public String getWordExample(String word) {
		
		//ottiene l`insieme contenente tutti gli esempi presenti su WordNet
		Set<String> esempi = wn.getSetExamples();
		word=word.replace("_", " ");
		word=word.replace(",", "");
		
		//seleziona un esempio random che contiene la parola richiesta (la causalità è data dal non ordinamento del set)
		for(String s : esempi)  
			if (s.contains(" "+word+" ") || s.contains(" "+word+","))
				return s;
		
		//se nessun esempio del WordNet contiene la parola restituisce null
		return null;		
	}
	
	
	/**
	 * Permette di aggiungere tra le possibili risposte di un task anche quelle inserite in precedenza dagli utenti,
	 * che vengono cercate tramite il {@link XMLReader} nel file XML dove sono salvate tutte le risposte.
	 * <br>
	 * La ricerca tiene anche conto della lingua di inserimento e di una soglia di preferenza {@link XMLReader#findElements(String, Language)}.
	 * 
	 * @param sys  Synset da cui si ottiene l´ID
	 * @param type parola, glossa, esempio
	 * @param lang Language del task
	 * @param result lista delle risposte ottenute fino a quel momento dal WordNet o da BabelNet
	 * @return lista con aggiunti gli eventuali risultati degli utenti
	 */
	
	public List<String> addUserResults(Synset sys, String type, Language lang, List<String> result) {
		//cerca glosse già selezionate dagli utenti relativamente a questo synset
		if(xr.containsSynset(sys.getID())) {
			List<String> lista = xr.findElements(type, lang);		
			for(String s : lista)
				if(!result.contains(s))
					result.add(s);
		}		
		return result;
	}

	/**
	 * Imposta il numero massimo di voci che appariranno come scelte possibili per l´utente nei task.
	 * <br> Il valore di default è 10.
	 * @param n numero 
	 * @return this
	 */
	public StandardDataProvider setMaxNumbQuestions(int n) {
		this.maxNumbQuestions=n;
		return this;
	}
	
	/**
	 * Verifica se le voci da inviare all`utente superano di numero il valore massimo (di default n=10).
	 * <br> In caso affermativo riordina casualmente i risultati e ne prende n, altrimenti restituisce
	 * la lista immutata.
	 * @param l lista di risultati
	 * @return lista con un numero ridotto di risultati
	 */
	public List<String> limitSize(List<String> l) {
		
		if(l.size()>maxNumbQuestions) {					
			Collections.shuffle(l);
			return l.stream().limit(maxNumbQuestions).collect(Collectors.toList());
		}		
		return l;
		
	}

}
