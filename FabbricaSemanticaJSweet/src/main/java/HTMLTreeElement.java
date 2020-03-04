
import def.dom.HTMLCollection;
import def.dom.HTMLElement;
import static def.dom.Globals.document;
import static def.jquery.Globals.$;

/**
 * Questa classe astratta istanzia e incapsula un elemento generico che estende
 * HTMLElement. Il tipo viene specificato dalle classi che la estendono. Queste inoltre specificano 
 * anche il tipo che estende HTMLTreeElement, rendolo così accessibile per fare casting.<br><br>
 
 * Reimplementa alcuni dei metodi più comuni di HTMLElement e permette l'accesso
 * a tutti i metodi e campi di questa tramite l'unico campo (protected) element o il metodo getElement().
 * 
 * @see #element
 * @see #getElement()
 * @see #HTMLTreeElement(String tag)
 * 
 * 
 */
public abstract class HTMLTreeElement<T extends HTMLElement, R extends HTMLTreeElement<T,R>>{

	/**
	 * Campo di tipo generico che estende HTMLElement. Il tipo viene assegnato dalla classe
	 * che estende HTMLTreeELement. <br><br>
	 * Protected per essere accessibile nell'intero package.<br><br>
	 * Final per evitare assegnazioni multiple di uno stesso oggetto a più elementi.
	 * 
	 * @see HTMLTreeElement
	 */
	protected final T element;
	
	/**
	 * Campo di tipo R che estende HTMLTreeElement stesso. Viene usato nei metodi per restituire un
	 * oggetto del tipo della sottoclasse che estende questa classe astratta in modo da renderlo concatenabile.
	 */
	private R self;

	/**
	 * Il valore "unchecked" in questo caso è sicuro: viene infatti eseguito il casting verso un elemento di tipo generico 
	 * T extends HTMLElement, imposto dalla sottoclasse che chiama il costruttore (ad esempio la classe Div estende {@code HTMLTreeELement<HTMLDivElement>}). Se il parametro tag passato al costruttore dalla sottoclasse è corretto il metodo document.createElement(String tag)
	 * istanzierà un oggetto dello stesso tipo del tipo generico imposto dalla sottoclasse. Questo fa sì che {@link #element} sia un sottotipo specifico di HTMLElement, che dispone anche dei metodi specifici della
	 * sua classe. Il parametro "tag" viene passato dal costrutture della sottoclasse senza che sia accessibile all´utente, così da evitare possibili ClassCastException che verrebbero individuati solo in fase di compilazione.
	 * Viceversa, se una sottoclasse venisse sviluppata passando al costruttore di HTMLTreeElement un paremetro "tag" non appropriato, che istanzia cioè un tipo diverso di sottoclasse di HTMLElement da quello scelto per il generico, questo
	 * darebbe una ClassCastException in fase di compilazione.
	 * <br>
	 * Inoltre viene effettuato un cast da HTMLTreeElement a R, classe generica che estende HTMLTreeElement, quindi il cast è sempre possibile.
	 * <br>
	 * Questo costruttore permette di istanziare un oggetto di tipo HTMLTreeElement e il rispettivo campo {@link #element} di tipo generico
	 * T extends HTMLElement, il cui tipo specifico viene specificato dalla sottoclasse che lo ha chiamato.
	 * <br> Istanzia inoltre un oggetto di tipo R extends HTMLTreeElement che verrà usanto per concatenare i metodi 
	 * secondo il tipo della sottoclasse che estende questa classe astratta.
	 * <br><br>

	 
	 * @param tag stringa che indica il tipo di HTMLElement da istanziare
	 * 
	 * @see #element
	 * @see HTMLTreeElement
	 * 
	 */
@SuppressWarnings("unchecked")
	// sicuro perchè il parametro T, estendendo HTMLElement sarà sempre
	// compatibile con il tipo restituito da document.createElement(), mentre R estendendo HTMLTreeElement stesso potrà
	//sempre farne un cast
	public HTMLTreeElement(String tag) {
		this.element = (T) document.createElement(tag);
		this.self = (R) this; 
	}


	/**
	 * restituisce la collezione di tutti i figli innestati nell'elemento incapsulato dalla classe
	 * 
	 * @return il campo children di tipo HTMLCollection dell'oggetto di tipo T extends HTMLElement incapsulato dalla classe
		@see #element
	 */
	public HTMLCollection getList() {
		return element.children;
	}

	/**
	 * restituisce il campo element di tipo T extends HTMLElement
	 * 
	 * @return element campo della classe di tipo generico T
	 * 
	 * @see #element
	 */
	public T getElement() {
		return element;
	}

	/**

	 * 
	 * inserisce un numero variabile di elementi HTMLElement come figli del campo {@link #element}
	 * <br><br>
	 * I Varargs sono sicuri perchè saranno tutti di tipo HTMLTreeElement, quindi avranno tutti un campo {@link #element} e un metodo {@link #getElement()}
	 * con il rispettivo metoto appendChild(HTMLElement)
	 * 
	 * @param l varargs di oggetti di tipo HTMLTreeElement
	 */

	public void appendTags ( HTMLTreeElement<?,?>... l) {
		//  sicuro perchè HTMLTreeElement accetta ogni e solo classi che estendono HTMLElement e HTMLTreeElement
		for (HTMLTreeElement<?,?> hte : l) {
			element.appendChild(hte.getElement());
		}
	}

	/**
	 * aggiunge un figlio all'elemento di tipo HTMLElement del campo {@link #element}
	 * 
	 * @param child figlio del tag HTML
	 * 
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	 * 
	 */
	public R appendTag(HTMLTreeElement<?,?> child) {
		element.appendChild(child.getElement());
		return self;
	}

 	/**
	 * inserisce un figlio all'interno del campo {@link #element}, prima di quello
	 * indicato nel secondo parametro.
	
	 * @param <U> elemento HTMLElement generico
	 * @param <S> elemento HTMLElement generico
	 * @param newChild tag da inserire
	 * @param refChild tag dopo il quale inserirlo
	 */
	public <U extends HTMLElement, S extends HTMLElement> void insertTagBefore(HTMLTreeElement<U,?> newChild,
			HTMLTreeElement<S,?> refChild) {
		if (element.hasChildNodes())
			element.insertBefore(newChild.getElement(), refChild.getElement());
	}

	/**
	 * prende un numero variabile di attributi CSS nel formato "key:value" e li imposta al campo {@link #element}
	 * 
	 * @param keyvalues varargs di stringhe nel formato
	 * "key:value"
	 */
	public void $css(String... keyvalues) {
		if (keyvalues != null && keyvalues.length > 0) {
			for (String style : keyvalues) {
				if (style.contains(":")) {
					String[] l = style.split(":");
					String key = l[0];
					String value = l[1];
					$(element).css(key, value);
				}
			}
		}
	}
	/**
	 * Setta gli attributi del campo element di tipo T extends HTMLElement
	 * @param attr varargs di stringhe
	 */
	public void setAttributes(String... attr) {
		if (attr != null && attr.length > 1)
			for (int i = 0; i < attr.length; i += 2) {
				if (i + 1 < attr.length)
					element.setAttribute(attr[i], attr[i + 1]);
			}
	}

	/**
	 * Questo metodo setta il corrispondente attributo del campo element di tipo
	 * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
	 * <br><br>
	 * Può essere usato per concatenare altri metodi dell'oggetto.
	 * <br><br>
	 * Si possono concatenare i
	 * metodi della classe HTMLTreeElement quali id(), textContent(),
	 * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
	 * come ultimo metodo della concatenazione.
	 * <br><br>
	 * In caso di istanziazione di un oggetto, i metodi non concatenabili non
	 * possono essere utilizzati.
	 * <br><br>
	 * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
	 * essere seguiti da getElement()
	 * <br><br>
	 * @param id stringa da inserire come id di {@link #element}
	 * <br>
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	
	 *	 * <br><br><br>
	 * Metodi concatenabili:
	 * @see #id(String)
	 * @see #textContent(String)
	 * @see #innerHTML(String)
	 * @see #className(String)
	 * @see #hidden(boolean)
	 * 
	 */
	public R id(String id) {
		element.id = id;
		return self;
	}

	/**
	 * Questo metodo setta il corrispondente attributo del campo element di tipo
	 * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
	 * <br><br>
	 * Può essere usato per concatenare altri metodi dell'oggetto.
	 * <br><br>
	 * Si possono concatenare i
	 * metodi della classe HTMLTreeElement quali id(), textContent(),
	 * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
	 * come ultimo metodo della concatenazione.
	 * <br><br>
	 * In caso di istanziazione di un oggetto, i metodi non concatenabili non
	 * possono essere utilizzati.
	 * <br><br>
	 * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
	 * essere seguiti da getElement()
	 * <br><br>
	 * @param text stringa da inserire come textContent di {@link #element}
	 * <br>
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	
	 *	 * <br><br><br>
	 * Metodi concatenabili:
	 * @see #id(String)
	 * @see #textContent(String)
	 * @see #innerHTML(String)
	 * @see #className(String)
	 * @see #hidden(boolean)
	 */
	public R textContent(String text) {
		element.textContent = text;
		return self;
	}
	/**
	 * Questo metodo setta il corrispondente attributo del campo element di tipo
	 * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
	 * <br><br>
	 * Può essere usato per concatenare altri metodi dell'oggetto.
	 * <br><br>
	 * Si possono concatenare i
	 * metodi della classe HTMLTreeElement quali id(), textContent(),
	 * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
	 * come ultimo metodo della concatenazione.
	 * <br><br>
	 * In caso di istanziazione di un oggetto, i metodi non concatenabili non
	 * possono essere utilizzati.
	 * <br><br>
	 * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
	 * essere seguiti da getElement()
	 * <br><br>
	 * @param text stringa da inserire come innerHTML di {@link #element}
	 * <br>
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	
	 *	 * <br><br><br>
	 * Metodi concatenabili:
	 * @see #id(String)
	 * @see #textContent(String)
	 * @see #innerHTML(String)
	 * @see #className(String)
	 * @see #hidden(boolean)
	 */
	public R innerHTML(String text) {
		element.innerHTML += " " + text;
		return self;
	}

	/**
	 * Questo metodo setta il corrispondente attributo del campo element di tipo
	 * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
	 * <br><br>
	 * Può essere usato per concatenare altri metodi dell'oggetto.
	 * <br><br>
	 * Si possono concatenare i
	 * metodi della classe HTMLTreeElement quali id(), textContent(),
	 * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
	 * come ultimo metodo della concatenazione.
	 * <br><br>
	 * In caso di istanziazione di un oggetto, i metodi non concatenabili non
	 * possono essere utilizzati.
	 * <br><br>
	 * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
	 * essere seguiti da getElement()
	 * <br><br>
	 * @param className stringa da inserire come className di {@link #element}
	 * <br>
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	
	 *	 * <br><br><br>
	 * Metodi concatenabili:
	 * @see #id(String)
	 * @see #textContent(String)
	 * @see #innerHTML(String)
	 * @see #className(String)
	 * @see #hidden(boolean)
	 */
	public R className(String className) {
		element.className = className;
		return self;
	}
	/**
	 * Questo metodo setta il corrispondente attributo del campo element di tipo
	 * T e restituisce un'istanza dell'oggetto di tipo HTMLTreeElement T.
	 * <br><br>
	 * Può essere usato per concatenare altri metodi dell'oggetto.
	 * <br><br>
	 * Si possono concatenare i
	 * metodi della classe HTMLTreeElement quali id(), textContent(),
	 * innerHTML(), className(), hidden(), oppure uno dei metodi non concatenabili
	 * come ultimo metodo della concatenazione.
	 * <br><br>
	 * In caso di istanziazione di un oggetto, i metodi non concatenabili non
	 * possono essere utilizzati.
	 * <br><br>
	 * Per istanziare un oggetto di tipo HTMLElement i metodi concatenabili dovranno
	 * essere seguiti da getElement()
	 * <br><br>
	 * @param b boolean da inserire come hidden value di {@link #element} per renderlo o meno visibile
	 * <br>
	 * @return oggetto del tipo della sottoclasse che estende questa classe astratta. Permette di concatenare i metodi senza
	 * dover fare il casting.
	
	 *	 * <br><br><br>
	 * Metodi concatenabili:
	 * @see #id(String)
	 * @see #textContent(String)
	 * @see #innerHTML(String)
	 * @see #className(String)
	 * @see #hidden(boolean)
	 */
	
	public R hidden(boolean b) {
		element.hidden = b;
		return self;
	}
}
