package ejercicio3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class datos 
{
	private int numeroVocales = 0;
	private String frase;
	
	public datos(File fichero) 
	{
		this.frase = inicializarDato(fichero);
	}
	public  synchronized void aumentarNumVar(int n) 
	{
		this.numeroVocales += n;
	}

	public int getNumeroVocales() 
	{
		return this.numeroVocales;
	}
	public String getFrase() 
	{
		return frase;
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
