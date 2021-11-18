package Ejercicio13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Datos 
{
	private ArrayList<String> palabrasConLaClave = new ArrayList<>();
	private String clave;
	private String palabra;
	private File f;
	
	public Datos(String clave, File f) 
	{
		this.clave = clave;
		this.f = f;
	}
	
	public String getTextoFromFile() 
	{
		String line;
		String texto = "";

		this.palabra = "";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			try {
				while((line = br.readLine()) != null) 
				{
					texto += line;
				}
			} catch (IOException e) {e.printStackTrace();}	
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		return texto;
	}

	public synchronized void producir(String palabra) 
	{
		try 
		{
			while(this.palabra != "")
						wait();
		} catch (Exception e) {}
		this.palabra = palabra;
		notifyAll();
	}

	public synchronized String consumir() 
	{
		try 
		{
			while(palabra == "")
				wait();
		} catch (Exception e) {}
		if(palabra.contains(clave))
			palabrasConLaClave.add(palabra);
		if(!palabra.equals("$--/Fin/--$"))
			palabra = "";
		notifyAll();
		return palabra;
	}

	public void imprimirTodas() 
	{
		palabrasConLaClave.forEach(palabra->
		{
			System.out.print(palabra + " ");
		});
	}
}
