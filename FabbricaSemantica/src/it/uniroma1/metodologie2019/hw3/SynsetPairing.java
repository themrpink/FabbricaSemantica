package it.uniroma1.metodologie2019.hw3;


/**
 * 
 * Oggetto che contiene due Synset o il punteggio di
 * somiglianza tra i due, basato sul numero di sinonimi
 * e di parola della glossa in comune, con valore
 * compreso tra 0.0 e 1.0
 *
 *@author stefano urani
 */
public class SynsetPairing
{
	private Synset synsetSource;
	private Synset synsetTarget;
	private double score;
	
	public SynsetPairing(Synset source, Synset target, double score)
	{
		this.synsetSource=source;
		this.synsetTarget=target;
		this.score=score;
	}
	/**
	 * 
	 * @return Synset restituisce il
	 */
	public Synset getSource() {return synsetSource;}
	public Synset getTarget(){ return synsetTarget;}
	public double getScore(){return score;}		
	
}
