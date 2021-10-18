package ejercicio3v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class datos 
{
	private int numeroVocales = 0;
	private String frase;
	static char vocaleString [] = {'a', 'e', 'i', 'o', 'u'};
	private int numeroHilos;
	
	
	public datos(File fichero, int numeroHilos) 
	{
		this.frase = inicializarDato(fichero);
		this.numeroHilos = numeroHilos;
		
	}
	public  synchronized void aumentarNumVar(int n) 
	{
		this.numeroVocales += n;
	}

	public int getNumeroVocales() 
	{
		return this.numeroVocales;
	}
	public String getFrase(String i) 
	{
		int cortes = frase.length() / numeroHilos;
		if (Integer.valueOf(i) != numeroHilos - 1)
			return frase.substring((Integer.valueOf(i) * cortes), (Integer.valueOf(i) * cortes) + cortes);
		else 
			return frase.substring((Integer.valueOf(i) * cortes));
	}	
	
	
	public static char[] getVocaleString() {
		return vocaleString;
	}
	public void setVocaleString(char[] vocaleString) {
		this.vocaleString = vocaleString;
	}
	
	private static String inicializarDato(File f1) 
	{
		String	entrada;
		String 	frase;
		
		frase = "";
		try 
		{
			FileReader fr  = new FileReader(f1);
			BufferedReader br = new BufferedReader(fr);
			while((entrada = br.readLine()) != null)
					frase += entrada;
		} catch (IOException e) {System.out.println(e);}
		return frase;
	}

	

}
