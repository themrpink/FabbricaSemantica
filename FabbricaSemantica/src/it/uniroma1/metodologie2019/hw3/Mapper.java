package it.uniroma1.metodologie2019.hw3;

/**
 * La classe Mapper istanzia e restituisce un oggetto di tipo Synset
 * @author stefano urani
 *
 */
public class Mapper 
{
	/**
	 * 
	 * @param wn1  WordNet source
	 * @param wn2	WordNet tager
	 * @return  WordNetMapping se i due WordNet non sono null
	 * 			altrimenti restiuisce null
	 */
	public static WordNetMapping map(WordNet wn1, WordNet wn2) 
	{
		if(wn1!=null&&wn2!=null)
			return new WordNetMapping(wn1, wn2);
		return null;
	}

}

