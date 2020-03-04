package it.uniroma1.metodologie2019.hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * La classe Testo permette di apire un file, estrarne il contenuto come testo
 * e di suddividerlo, per ogni riga, in un elemento Riga, contenuri in una ArrayList
 * 
 *  @author stefano urani
 *
 */

public class Testo 
{
	
	private ArrayList<Riga> righe;
	private String nomeFile;
	private int start;
	
	/**
	 * costruttore 
	 * 
	 * lancia il metodo estraiFile() 
	 * @param nomeFile  percorso del file da aprire
	 * @param start		riga all'interno del file da cui iniziare a estrarre righe
	 */
	public Testo(String nomeFile, int start)
	{
		this.start=start;
		this.nomeFile=nomeFile;
		this.righe=new ArrayList<Riga>();
		estraiFile();
	}
	
	/**
	 * apre il file, estrae il contenuto riga per riga, e per ognuna di esse
	 * istanzia un oggetto di tipo Riga. Infine le salva in righe
	 */
	public void estraiFile() 
	{
		BufferedReader br = null;
		int check=0;
		try
		{
			br = new BufferedReader(new FileReader(nomeFile));
			while(br.ready())
			{
				String st=br.readLine().trim();
				if(check>=start)righe.add(new Riga(st));
				check++;
				
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				if(br!=null) br.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * 
	 * @return  ArrayList  Lista di righe Riga
	 */
	public ArrayList<Riga> getTesto(){return righe;}
	
}