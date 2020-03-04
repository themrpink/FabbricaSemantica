package it.uniroma1.fabbricasemantica.data;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.uniroma1.lcl.jlt.util.Language;

/**
 * Lettore pensato per il file XML "synsets.xml" o comunque per un file avente la stessa struttura.
 *<br>
 * Permette di cercare il synset di una task presente nel file, quindi una task che almeno un utente abbia già completato, e di questo synset cercare
 * tutte le glosse, parole ed esempi che gli utenti gli hanno attribuito, tenendo conto della lingua di inserimento.
 *
 */
public class XMLReader extends XMLWriter{

	private static final String XMLFileName = "synsets.xml";
	private Document document;
	private int minPreferences = 2;
	private Element actualElement;
	
	
	/**
	 * Costruttore, apre un file (o lo crea se non esiste) nella posizione specificata dal parametro path e con nome indicato
	 * dal campo private finale XMLFileName.
	 * @param path percorso del file da aprire
	 * 
	 * @throws ParserConfigurationException errore di configurazione del parser
	 * @throws SAXException errore nella concersione in XML
	 * @throws TransformerException errore durante la trasformazione
	 */
	public XMLReader(String path) throws ParserConfigurationException, SAXException, TransformerException {
		super(new File(path+XMLFileName));
		
		this.document = super.getDocument();		
		this.actualElement = super.getRoot();
	}

	

	
	/**
	 * setta il numero minimo di preferenze: queste indicano il numero di volte che una specifica risposta per uno specifico synset
	 * è stata scelta dagli utenti. <br>
	 * Per esempio se per a un dato synset vari utenti hanno attribuito 3 volte la parola "casa", a questa parola corrisponderanno
	 * i valori "3" i "IT" per la lingua italiana.<br><br>
	 * Queste preferenze forniscono una soglia secondo la quale selezionare o scartare una rispota dal XMLReader.<br>
	 * Questo metodo permette di impostare la soglia di preferenze.
	 * <br><br>
	 * @param n intero, numero minimo di preferenze
	 */
	public void setMinPreferences(int n) {
		this.minPreferences=n;
	}
	
	/**
	 * Controlla che il synset richiesto sia presente nel file XML.
	 * <br>
	 * Se è presente assegna al campo actualElement il nodo che contiene il synset cercato.
	 * 
	 * @param synsetID ID del synset richiesto
	 * @return true se il synset è presente, false altrimenti
	 */
	public boolean containsSynset(String synsetID) {
		
		Element root = (Element) document.getFirstChild();
		NodeList nl = root.getChildNodes();
		
		for(int i=0; i<nl.getLength(); i++) 
			if(nl.item(i)
				 .getAttributes()
				 .getNamedItem("id")
				 .getTextContent()
				 .equals(synsetID)) {
				actualElement = (Element) nl.item(i);
				return true;		
			}
		return false;		
	}
	
	/**
	 * Cerca nei children del nodo del campo actualElement, assegnato dal metodo containsSynset
	 * e contenente il synset a cui ci stiamo riferendo per la ricerca, le risposte degli utenti secondo il tipo richiesto 
	 * (parola, glossa o esempio) e la lingua richiesta.
	 * @param type parola, glossa o esempio
	 * @param lang lingua di tipo Language
	 * @return lista di riposte degli utenti se presenti, vuota altrimenti
	 */
	public List<String> findElements(String type, Language lang){		
		List<String> lista = new ArrayList<>();
		NodeList nl = actualElement.getChildNodes();
		for(int i=0; i<nl.getLength(); i++) { 
			int numb = Integer.parseInt(nl.item(i).getAttributes().getNamedItem("preferences").getTextContent());
			if(nl.item(i).getNodeName().equals(type)&&
			   numb>=minPreferences&&
			   nl.item(i)
			     .getAttributes()
			     .getNamedItem("lang")
			     .getTextContent()
			     .equals(lang.toString()))
					
				lista.add(nl.item(i).getTextContent());
		}
		return lista;
	}
	
	
}

