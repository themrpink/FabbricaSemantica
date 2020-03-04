package it.uniroma1.metodologie2019.hw3;


import java.util.HashMap;

/**
 * 
 * enumerazione di tipi diversi di synset, e per ogni tipo delle possibili
 * relazioni tra synset
 * 
 * implementa WordNetRelation che fornisce un metodo astratto implementato per ogni 
 * elemento dell`enumerazione
 *
 *@author stefano urani
 */
public enum POS implements WordNetRelation
	
	{
		NOUN("n")
		{
			@Override
			public HashMap<String, String> getHS()
			{
				relazioni.put("!", "Antonym");
				relazioni.put("@", "Hypernym");
				relazioni.put("@i", "Instance Hypernym");
				relazioni.put("~", "Hyponym");
				relazioni.put("~i", "Instance Hyponym");
				relazioni.put("#m", "Member holonym");
				relazioni.put("#s", "Substance holonym");
				relazioni.put("#p", "Part holonym");
				relazioni.put("%m", "Member meronym");
				relazioni.put("%s", "Substance meronym");
				relazioni.put("%p", "Part meronym");
				relazioni.put("=", "Attribute");
				relazioni.put("+", "Derivationally related form");
				relazioni.put(";c", "Domain of Synset - TOPIC");
				relazioni.put("-c", "Member of this domain - TOPIC");
				relazioni.put(";r", "Domain of synset - REGION" );
				relazioni.put("-r", "Member of this domain - REGION");
				relazioni.put(";u", "Member of synset - USAGE");
				relazioni.put("-u", "Member of this domain- USAGE");
				return relazioni;
			}

		},
		
		VERB("v")
		{
			@Override
			public HashMap<String, String> getHS()
			{
				relazioni.put("!", "Antonym");
				relazioni.put("@", "Hypernym");
				relazioni.put("~", "Hyponym");
				relazioni.put("*", "Entailment");
				relazioni.put(">", "Cause");
				relazioni.put("^", "Also see");
				relazioni.put("$", "Verb group");
				relazioni.put("+", "Derivationally related form");
				relazioni.put(";c", "Domain of synset - TOPIC");
				relazioni.put(";r", "Domain of synset - REGION ");
				relazioni.put(";u", "Domain of synset - USAGE ");
				return relazioni;
			}
		}, 
	
		ADJECTIVE("a")
		{	
			@Override
			public HashMap<String, String> getHS()
			{
				
				relazioni.put("!", "Antonym");
				relazioni.put("&", "Similar to");
				relazioni.put("<", "Participle of verb");
				relazioni.put("\\", "Pertainym (pertains to noun");
				relazioni.put("=", "Attribute");
				relazioni.put("^", "Also see");
				relazioni.put(";c", "Domain of synset - TOPIC");
				relazioni.put(";r", "Domain of synset - REGION ");
				relazioni.put(";u", "Domain of synset - USAGE ");
				return relazioni;
			}
			
		}, 
		
		ADVERB("r")
		{
				@Override
				public HashMap<String, String> getHS()
				{
					relazioni.put("!", "Antonym");
					relazioni.put("\\", "Derived from adjective" );
					relazioni.put(";c", "Domain of synset - TOPIC");
					relazioni.put(";r", "Domain of synset - REGION ");
					relazioni.put(";u", "Domain of synset - USAGE ");
					return relazioni;
				}
			};
		
		private String s;
		HashMap<String, String> relazioni;
		
		POS(String s)
		{
			this.s=s;
			relazioni = new HashMap<String, String>();
		}
		@Override
		public String getID() {return s;}
		
		
	}