package it.uniroma1.fabbricasemantica.data;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Crea, legge e modifica file XML con metodi generici che permettono di soddisfare le operazioni richieste dal server del progetto, snellendone il codice.<br>
 * <br>I file del progetto sono:
 * <br><br>
 * <b>Synsets.XML</b> è un file in cui vengono salvati tutti i synsets che sono apparsi nei tasks completati dagli utenti e le relative risposte
 * divise per tipo (parola, glossa, esempio), specificando per ciascuna  la lingua e il numero di occorrenze (cioè quante 
 * volte gli utenti hanno scelto quella risposta per quel synset). Questo file permette di recuperare queste 
 * informazioni e riutilizzarle nei  task. Questo succede quando un synset risulta già presente nel file 
 * (quindi sono già presenti risposte di utenti relative a quel synset e nella lingua corrispondente).
 * <br><br>
 * <b>ResultsData.XML</b> contiene invece un nodo per ogni task completato da ciascun utente, indicandone:
 * 	<br>
 * tipo, synset ID, la domanda, tutte le riposte dell´utente, il codice identificativo utente.<br>
 * Permette quindi di tenere traccia dei dati di tutti i tasks completati relativamente a ogni utente.<br><br>
 *  <b>Userdata.XML</b> contiene i dati di ogni utente registrato.<br><br>
 * <br><br>
 * XMLWriter permette di aprirli, crearli, modificarli, cercare e inserire all´interno tag e testo.
 * <br>
 * 
 */
public class XMLWriter {
	
	private Element root;
	private File file;
	private Document document;
	//private String userID;
	
	/**

	 * Costruttore di {@link XMLWriter}: se il file richiesto esiste lo apre e ne ricava il parent node, se non esiste lo crea inserendo nel {@link Document} il parent node "data".
	 * @param file file da aprire
	 * @param rootNode nome del nodo principale del file

	 * @throws ParserConfigurationException errore di configurazione del parser
	 * @throws SAXException errore nella concersione in XML
	 * @throws TransformerException errore durante la trasformazione
	 */
	public XMLWriter(File file, String rootNode) throws SAXException, ParserConfigurationException, TransformerException {

		this.file=file;		
		
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        
        try {
			this.document = documentBuilder.parse(file.getPath());
			this.root = (Element) document.getFirstChild();
		} catch (IOException f) {
			this.document=documentBuilder.newDocument();
            this.root = document.createElement(rootNode);
            document.appendChild(root);
            writeFile();
		}

        document.getDocumentElement().normalize();
	}
	
	
	/**
	 * Secondo costruttore di {@link XMLWriter}, prende come argomento il File da aprire o creare, e gli assegna di default un nodo principale chiamato
	 * "data".
	 * @param file il File da aprire	
	 * @throws ParserConfigurationException errore di configurazione del parser
	 * @throws SAXException errore nella concersione in XML
	 * @throws TransformerException errore durante la trasformazione
	 */
	public XMLWriter(File file) throws SAXException, ParserConfigurationException, TransformerException {
		this(file, "data");
	}
	
	
	/**
	 * Restituisce il {@link Document} su cui viene costruito il file XML.
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}
	
	
	/**
	 * Restituisce il {@link Element} principale del file XML.
	 * @return document
	 */
	public Element getRoot() {
		return root;
	}

	
	/**
	 * Inserisce un nuovo tag nel file XML, in ultima posizione e assegnandogli un valori di ID incrementato di 1 
	 * rispetto al tag che lo precede.
	 * @param tag il tag da aggiungere
	 * 
	 * @return this
	 */
	public XMLWriter insertParentTag(String tag) {
		Element e = (Element)root.getLastChild();
        //se ci sono già altri record, recupera l´ultimo id e convertilo in intero
        int n=0;
        if(e!=null) { 
        	String id = e.getAttribute("id");
        	n = Integer.parseInt(id);
        }
        
        Element newData = document.createElement(tag);
        // setta un ID all´user
        Attr attr = document.createAttribute("id");
        attr.setValue(""+(n+1));
        newData.setAttributeNode(attr);
        
        // inserisci il tag nel documento
        root.appendChild(newData);
	
        return this;
	}
	
	
	/**
	 * Cerca a partire da tag con uno stesso nome, il primo in ordine di inserimento 
	 * che contenga esattamente il testo richiesto.
	 * @param tagName nome del tag
	 * @param tagTextContent contenuto del tag da trovare
	 * @return vero se è presente
	*/
	public boolean findTagTextContent(String tagName, String tagTextContent) {
		
		NodeList nList = document.getElementsByTagName(tagName);
        for(int i=0; i<nList.getLength();i++)
        	if(((Element)nList.item(i)).getTextContent().equalsIgnoreCase(tagTextContent)) 
        		return false;      
		return true;
	} 
	
	
	/**
	 * Dato il nome di un tag, restituisce una {@link NodeList} di tutti i tag xml con quel nome.
	 * @param tag nome del tag 
	 * @return lista di tag con quel nome
	 */
	public NodeList getElementsByTagName(String tag) {
		return document.getElementsByTagName(tag);
		
	}

	/**
	 * Crea e inserisce un tag nel file XML in ultima posizione all´interno del nodo principale e gli assegna un testo.
	 * @param tag nome del tag da aggiungere
	 * @param text testo da inserire nel tag
	 * @return this
	 */
	public XMLWriter insertTag(String tag, String text) {
		if(tag!=null) {
			Element e = (Element) root.getLastChild();
	        // crea un user element e inseriscilo nel file
	        Element newTag = document.createElement(tag);
	        newTag.appendChild(document.createTextNode(text));
	        e.appendChild(newTag);
			}
		return this;
	}
	
	
	/**
	 * Crea e inserisce un tag nel file XML  in ultima posizione all´interno del nodo principale e gli assegna un testo.
	 * @param tag nome del tag da aggiungere
	 * @param text varargs di testo da inserire nel tag
	 * @return this
	 */
	public XMLWriter insertTag(String tag, String... text) {
		if(tag!=null) {
			Element e = (Element) root.getLastChild();
	        // crea un user element e inseriscilo nel file
	        Element newTag = document.createElement(tag);
	        for(String s : text) 
	        	newTag.appendChild(document.createTextNode(s));
	        e.appendChild(newTag);
			}
		return this;
	}
	
	
	/**
	 * Se è presente un {@link Node} nullo lo elimina
	 * @param n parent node in cui cercare un Node nullo
	 */
	private void deleteNullNode(Node n)
    {
        NodeList nl = n.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++)
        {
            if (nl.item(i).getNodeType() == Node.TEXT_NODE && nl.item(i).getNodeValue() == null)
                nl.item(i).getParentNode().removeChild(nl.item(i));
            else
                deleteNullNode(nl.item(i));
        }
    }
	
	
	/**
	 * Converte una stringa scritta in formato XML in un {@link Node} mantenendone la struttura e ne restituisce una {@link NodeList}.
	 * Per fare questo istanzia prima un nuovo {@link DocumentBuilder} e un nuovo {@link Document}, per questo vengono dichiarate le varie eccezioni.
	 * @param str tag XML anche innestati su più livelli in formato stringa
	 * @return lo NodeList dei tag trasformati da stringa in nodi
	 */
	public NodeList parseStringToXML(String str) {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();
			//crea un DOM Xml a partire dalla stringa delle risposte
	        Document doc = documentBuilder.parse(new InputSource(new StringReader(str)));
			//ottieni il primo child <answer>
			Node node = doc.getFirstChild();
			//ottiene tutti i tag interni e li itera
			NodeList nodeList = node.getChildNodes();
			//String glossID="";
			return nodeList;
			//return this;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * Inserisce oppure aggiorna i synset nel file. Il file synsets.xml contiene tutti i synset delle task a cui
	 * gli utenti hanno risposto. A ciascuno di essi vengono innestati tag di parola, glossa o esempio, a seconda del tipo 
	 * di riposta fornita da ciascun tag. Per ognuno di questi vengono salvati come parametri anche il numero di inserimenti
	 * e la lingua. Per esempio se a più utenti è capitato (anche in task diversi) lo stesso synset e hanno dato risposte uguali 
	 * ciascuna avrà un attributo che indica il numero di volte che è stata data. Nella ricerca di risposte degli utenti come risorsa delle
	 * task si tiene conto di questo valore per fare una selezione: per garantire una certa consistenza si prendono in considerazione solo
	 * riposte che hanno un numero minimo di inserimenti.
	 *  
	 * @param synset id del synset del task
	 * @param field se si tratta di parola, glossa o esempio
	 * @param lang in che lingua è stata data la risposta
	 * @param value la riposta 
	 * @throws TransformerException errore nel processo di trasformazione
	 */
	public void insertSynsetTag(String synset, String field, String lang, String value) throws TransformerException {
		//converte in lower case e fa il trim in modo da diminuire le differenze di battitura con parole già inserite 
		field = field.toLowerCase().trim();
		//vengono recuperati tutti i nodi del file
		NodeList nl = root.getChildNodes();
		//check in caso il synset sia già presente nel file		

		//divide in una lista la stringa in caso di risposte multiple dell`utente
		List<String> listaRisultati = new LinkedList<>(Arrays.asList(value.split(";")));
		Element synsetElement = null;
		
		//cerca il tag del synset richiesto
		for(int i=0; i<nl.getLength(); i++) {
			NamedNodeMap nnm = nl.item(i).getAttributes();
			String synsetNumber = nnm.getNamedItem("id").getTextContent();
			//se nel file è presente un tag "synset" con id corrispondente a quello cercato, inserisci in questo
			//i tag con le risposte dell´utente
			if (synset.equals(synsetNumber)) {
				synsetElement = (Element) nl.item(i);
				break;
			}
		}
		
		if(synsetElement!=null) {
				
			NodeList fields = synsetElement.getElementsByTagName(field);
			List<Element> lista = new ArrayList<>();
			
			for(int i=0; i<fields.getLength();  i++) 
				if(fields.item(i).getAttributes().getNamedItem("lang").getTextContent().equals(lang))
					lista.add((Element)fields.item(i));
			
			for(Element e : lista) {
				String risposta = e.getTextContent();
				if(listaRisultati.contains(risposta)) {
					Integer numb = Integer.parseInt(e.getAttributes().getNamedItem("preferences").getTextContent());
					numb+=1;
					e.setAttribute("preferences", numb.toString());
					listaRisultati.remove(risposta);
				}
			}
			
			for(String s : listaRisultati) {
		        Element newTag = document.createElement(field);		
		        newTag.setAttribute("preferences", "1");
		        newTag.setAttribute("lang", lang);
		        newTag.appendChild(document.createTextNode(s));
		        synsetElement.appendChild(newTag);				
			}
		}
		//se nel file il synset non é presente lo inserisce e gli aggiunge le risposte dell´utente
		else {
			Element newSynsetTag = document.createElement("synset");
			newSynsetTag.setAttribute("id", synset);
			System.out.println(listaRisultati);
			for(String str : listaRisultati) {	
		        Element newTag = document.createElement(field);
		        newTag.setAttribute("preferences", "1");
		        newTag.setAttribute("lang", lang);
		        newTag.appendChild(document.createTextNode(str));
		        newSynsetTag.appendChild(newTag);
	        }
			root.appendChild(newSynsetTag);
		}	
		writeFile();
	}
	
	
	/**
	 * Converte il {@link Document} in file
	 * @throws TransformerException errore nel processo di trasformazione
	 */
	public void writeFile() throws TransformerException {
        // crea il file xml, transformando il DOM Object in un file xml
		deleteNullNode(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file.getPath());
        transformer.transform(domSource, streamResult);
	}	
	
}
