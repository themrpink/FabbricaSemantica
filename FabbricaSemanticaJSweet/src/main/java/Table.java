
import java.util.ArrayList;
import java.util.List;

import def.dom.HTMLTableCellElement;
import def.dom.HTMLTableElement;
import def.dom.HTMLTableRowElement;
/**
 * La classe Table estende HTMLTreeElement e incapsula un oggetto di tipo HTMLTableElement a cui è possibile accedere 
 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement}.
 * 
 * 
 * <br><br>
 * La classe contiene due classi interne, Row e Cell, rispettivamente le righe e le celle della tabella, che possono venire 
 * create quando si istanzia l´oggetto e con il metodo {@link #addRow()}.
 * <br> Si accede alle righe con il metodo {@link #getRows()} <br>
 * <br> Ogni cella ha un suo id identificativo del tipo (r+#riga+c+#colonna).
 * <br> Infine il metodo {@link #getCellFromID(String)} permette di accedere a una specifica cella indicandone l´id
 * 
  @see HTMLTreeElement#element
  @see HTMLTreeElement
  @see Row
  @see Cell
 *
 */
public class Table extends HTMLTreeElement<HTMLTableElement, Table> {
	/**
	 * Lista di elementi di tipo {@link Row} , in cui vengono inserite le righe della tabella.
	 */
	protected List<Row> rowList;
	private int rows;
	private int cols;
	

	/**
	 * Costruttore della classe Table con 2 parametri. Crea una tabella con il numero di righe e colonne specificate come parametri.<br>
	 * Ogni cella viene identificata con un id = "r"+numero riga + "c" + numero colonna<br>
	 * Permette di istanziare e incapsulare un oggetto di tipo HTMLDivElement nel campo protetto {@link #element} della superclasse 
	 * {@link HTMLTreeElement} e accedere ai suoi campi e metodi.<br><br>
	 * 
	 * <br><br>inserisce le righe (oggetti di tipo Row) in una lista {@link #rowList}
	 * 
	 * @param rows numero di righe
	 * @param col  numero di colonne
	 * 
	 * @see Row
	 * @see Cell
	 */
	public Table(int rows, int col) {
		super("table");
		this.rowList = new ArrayList<>();
		this.rows=rows;
		this.cols=col;
		
		for (int i = 0; i < this.rows; i++) {
			Row row = new Row();
			element.appendChild(row.element);
			rowList.add(row);
			for (int j = 0; j < col; j++) {
				Cell cell = new Cell();
				cell.element.id = "r" + i + "c" + j;
				row.addCell(cell);
			}
		}
	}

	/**
	 * Inserisce una nuova riga nella tabella in ultima posizione e vi inserisce tante celle quante sono le colonne della
	 * tabella come indicato nel costruttore. A ogni cella viene attribuito un id = "r"+numero riga + "c" + numero colonna<br>
	 * <br>
	 * @see Table
	 * @see Row
	 * @see Cell
	 *   
	 */
	public void addRow() {
		Row row = new Row();
		element.appendChild(row.element);
		rowList.add(row);
		int i = rowList.size()-1;
		for (int j = 0; j < cols; j++) {
			Cell cell = new Cell();
			cell.element.id = "r" + i + "c" + j;
			row.addCell(cell);
		}
	}
	
	/**
	 * Restituisce la lista di righe della tabella
	 * @return rowList
	 * 
	 * @see #rowList
	 * @see Row
	 */
	public List<Row> getRows() {
		return rowList;
	}

	
	/**
	 * Restituisce una cella della tabella a partire dal suo id.<br>
	 * Il formato dell`id è "r"+numero riga + "c" + numero colonna.
	 * @param ID "r"+numero riga + "c" + numero colonna
	 * @return la cella richiesta se presente, altrimenti null
	 */
	public Cell getCellFromID(String ID) {
		for (Row r : rowList)
			for (Cell c : r.getCells())
				if (c.getID().equals(ID)) {
					return c;
				}
		return null;
	}

	/**
	 * La classe Row, classe interna di {@link Table}, estende HTMLTreeElement e incapsula un oggetto di tipo HTMLTableRowElement a cui è possibile accedere 
	 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement} e contiene una lista ordinata di tutte le celle al suo interno.
	 * 
	 * @see Cell
	 *
	 */
	public class Row extends HTMLTreeElement<HTMLTableRowElement, Row> {

		private List<Cell> listaCelle;
		
		/**
		 * Costruttore della classe {@link Table.Row}
		 */
		public Row() {
			super("tr");
			this.listaCelle = new ArrayList<>();
		}


		private void addCell(Cell c) {
			
			if(element.childElementCount<=cols) {
				listaCelle.add(c);
				element.appendChild(c.element);}
		}

		/**
		 * Restituisce la lista delle celle contenute nella riga
		 * @return listaCelle
		 * 
		 * @see Cell
		 */
		public List<Cell> getCells() {
			return listaCelle;
		}

	}
	
	/**
	 * La classe Cell, classe interna di {@link Table}, estende HTMLTreeElement e incapsula un oggetto di tipo HTMLTableCellElement a cui è possibile accedere 
	 * tramite il campo {@link HTMLTreeElement#element} della superclasse {@link HTMLTreeElement} e contiene una lista ordinata di tutte le celle al suo interno.
	 * <br><br>
	 * Incapsula inoltre un oggetto di tipo {@link Card} che viene utilizzato nel task MemoryValidation. Nel gioco ogni casella di una
	 * tabella contiena una carta. La classe ha anche metodi che inseriscono una carta nella cella e che la restituiscono.
	 * 
	 * @see Cell
	 * @see Row
	 * @see Card
	 * 
	 */
	public class Cell extends HTMLTreeElement<HTMLTableCellElement, Cell> {

		private Card card;
		
		/**
		 * Costruttore senza parametri, permette di usare la classe come una normale cella di una tabella.
		 */
		public Cell() {
			super("td");
		}

		/**
		 * Costruttore con una parametro, card, che oltre a creare una cella incapsula al suo interno un oggetto di tipo {@link Card} necessario per il task {@link MemoryValidationPage}
		 * @param card la carta del gioco
		 */
		public Cell(Card card) {
			super("td");
			this.card = card;
		}

		/**
		 * Inserisce una {@link Card} 
		 * @param card la carta del gioco
		 */
		public void insertCard(Card card) {
			this.card = card;
		}

		/**
		 * Restituisce la {@link Card} incapsulata nella cella, null se non è presente
		 * @return card
		 */
		public Card getCard() {
			return card;
		}

		/**
		 * Restituisce l´id della cella
		 * @return element.id
		 */
		public String getID() {
			return element.id;
		}
	}

}
