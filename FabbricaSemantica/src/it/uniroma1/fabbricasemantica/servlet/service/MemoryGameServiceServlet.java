package it.uniroma1.fabbricasemantica.servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma1.fabbricasemantica.data.MemoryGameProvider;
import it.uniroma1.fabbricasemantica.data.MemoryGameProvider.GameBuilder;
import it.uniroma1.fabbricasemantica.data.RangeLevel;
import it.uniroma1.fabbricasemantica.data.Task;

/**
 * Estende la {@link DataProviderServiceServlet} e si occupare nello specifico dell`elaborazione dei dati
 * per il gioco. È comunque in grado di rispondere alle stesse query della superclasse.<br><br>
 * 
 * I dati del gioco vengono costruiti a seconda delle impostazioni scelte dall´utente secondo
 * vari criteri di difficoltà. 
 * 
 *
 */
@WebServlet(name="MemoryGameServlet", urlPatterns="/memoryGame.jsp")
public class MemoryGameServiceServlet extends DataProviderServiceServlet{


	private static final long serialVersionUID = -142725510069685791L;


	/**
	 * Costruisce un gioco MemoryValidation secondo le impostazioni passate dall´utente e restituisce le coppie in formato JSON.
	 * Se è stata inviata una parola, questa viene interprepata come una richiesta di suggerimento, e cerca per poi restituire
	 * il suggerimento relativo alla parola inviata.
	 * 
	 * @param request richiesta del browser
	 * @param response risposta del server
	 * @throws IOException generico errore di input
	 */
	@Override
	protected void doSomething(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {
		
		
		
		String word =  (String) request.getParameter("word");
		String path = request.getServletContext().getRealPath("/");
		MemoryGameProvider mgp = GameBuilder.getInstance(path).buildGame();
		
		if(word!=null) {
			//se viene richiesto un suggerimento il parametro word non è null, e viene restituito il suggerimento creato con il metodo getSuggerimento()
			String suggerimento = mgp.getSuggerimento(word);
			response.getWriter().write(suggerimento);
		}
		
		//se non è stato richiesto un suggerimento allora viene istanziata una nuova partita, prendendo gli altri parametri di gioco
		else {
			String sTask = (String) request.getParameter("task");
			String rangeLevel = (String) request.getParameter("rangeLevel");
			String tableSize = (String) request.getParameter("tableSize");
	
			//if(rangeLevel!=null)
			//prende i valori di ogni livello salvati in RangeLevel e li utilizza per costruire il gioco al livello chiesto
			String[] inputLevelGame = RangeLevel.valueOf(rangeLevel).getLevelValues().split(",");
			int numberOfCouples =  Integer.parseInt(tableSize);
			
	
			// a partire dalle parcentuali di parole per ogni livello di difficoltà ottenute dal RangeLevel
			//calcola quante parole generare per ogni livello
			int level1 = Integer.parseInt(inputLevelGame[0])*numberOfCouples/100;
			int level2 = Integer.parseInt(inputLevelGame[1])*numberOfCouples/100;
			int level3 = Integer.parseInt(inputLevelGame[2])*numberOfCouples/100;
			int setSize = Integer.parseInt(inputLevelGame[3]);
			
			//se il totale delle parole calcolate è inferiore al numero delle parole richieste aggiunge la differenza in parole di secondo livello
			if(level1+level2+level3<numberOfCouples) {
				level2+=numberOfCouples-(level1+level2+level3);
			}
	
			
			//costruisce il gioco
			mgp = GameBuilder.getInstance(path)
												 .resetGame()
												 .buildLevel1(level1)
												 .buildLevel2(level2, setSize)
												 .buildLevel3(level3, setSize)
												 .buildGame();
	
			/*
			 * ottiene la risposta relativa al task richiesto e la invia al client
			 */				
			Task task = tasks.get(sTask);
			String json = mgp.getData(task);	
			response.getWriter().write(json);}
		}

}
