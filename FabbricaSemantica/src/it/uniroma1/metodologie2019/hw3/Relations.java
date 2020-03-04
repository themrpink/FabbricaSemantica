package it.uniroma1.metodologie2019.hw3;

import java.util.HashMap;
/**
 * 
 * Ogni elemento dell'enumerazione è una delle relazioni possibili tra synset
 * il metodo getID() permette di ottenere il valore da confrontare con i synset
 * 
 * @author stefano urani
 */
public enum Relations implements WordNetRelation{
	
	ANTONYM("!"),
	HYPERNYM("@"),
	INSTANCE_HYPERNYM("@i"),
	HYPONYM("~"),
	INSTANCE_HYPONYM("~i"),
	MEMBER_HOLONYM("#m"),
	SUBSTANCE_HOLONYM("#s"),
	PART_HOLONYM("#p"),
	MEMBER_MERONYM("%m"),
	SUBSTANCE_MERONYM("%s"),
	PART_MERONYM("%p"),
	ATTRIBUTE("="),
	DERIVATIONALLY_RELATED("+"),
	DOMAIN_OF_SYNSET_TOPIC(";c"),
	MEMBER_OF_THIS_DOMAIN_TOPIC("-c"),
	DOMAIN_OF_SYNSET_REGION(";r"),
	MEMBER_OF_THIS_DOMAIN_REGION("-r"),
	MEMBER_OF_SYNSET_USAGE(";u"),
	MEMBER_OF_THIS_DOMAIN_USAGE("-u"),
	ENTAILMENT("*"),
	CAUSE(">"),
	ALSO_SEE("^"),
	VERB_GROUP("$"),
	SIMILAR_TO("&"),
	PARTICIPLE_OF_VERB("<"),
	PERTAINYM("\\"),
	DERIVED_FROM_ADJECTICE("\\");
	
	
	String s;
	
	Relations (String s){ this.s=s;}
	
    @Override
    public String getID() {return s;}
    
    public static Relations valueOfLabel(String label) {
        for (Relations e : values()) {
            if (e.s.equals(label)) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public HashMap<String, String> getHS(){return null;}

}
