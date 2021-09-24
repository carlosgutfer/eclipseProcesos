package ejercicio4;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ejercicio4 {
	
	
	
	public static void main(String [] args) throws IOException 
	{
		File aux = new File("auxiliar.txt");
		aux.createNewFile();
		File parrafo = new File("parrafo.txt");
		parrafo.createNewFile();
	
		
		escribirFichero(parrafo);
		modificarFichero(parrafo, aux);
	}
	
	private static void modificarFichero(File parrafo, File aux) throws IOException {
		int num;
		
		FileReader fr = new FileReader(parrafo);
		String cadena = "";
		
		
		do 
		{
			num = fr.read();
			if (num != 97 && num !=101 && num != 105 && num != 111 && num != 117 && num!= -1)
						cadena += (char) num;	
		}while(num != -1);
		fr.close();
		FileWriter fw = new FileWriter(aux);
			fw.write(cadena);	
		fw.close();
		parrafo.delete();
		aux.renameTo(parrafo);
	}

	private static void escribirFichero(File newFile) throws IOException {
		String nuevaFrase;
		FileWriter fw = new FileWriter(newFile);
		Scanner sc = new Scanner(System.in);
		do 
		{
			System.out.println("Introduzca una frase o * para salir");
			nuevaFrase = sc.next();
			if(!nuevaFrase.equals("*"))
				fw.write(nuevaFrase);
			
		}while(!nuevaFrase.equals("*"));
		
		fw.close();
		sc.close();
	}
}
