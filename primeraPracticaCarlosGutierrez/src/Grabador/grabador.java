package Grabador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class grabador {

	static	File		tablero;

	public static void main(String args []) 
	{
		pintarTablero();	
	}
	// Método que recibe la entrada de director y la escribe en el fichero de tablero, la 1ª vez borra el fichero si existe 
	private static void pintarTablero() 
	{
		Scanner		sc;
		String		entrada;
		FileWriter	fw;
		
		sc = new Scanner(System.in);
		tablero = new File("tablero.txt");
		if(tablero.exists())
			tablero.delete();
		try 
		{
			tablero.createNewFile();
			fw = new FileWriter(tablero, true);
			do 
			{
				entrada = sc.nextLine();
				fw.write(entrada);
				fw.write('\n');
			}while(entrada.indexOf("fin") == -1);
			fw.close();
		} catch (IOException e) {}
		sc.close();
	}
}
