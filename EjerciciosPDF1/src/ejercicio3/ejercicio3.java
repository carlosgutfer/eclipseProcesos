package ejercicio3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ejercicio3 {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String [] args) throws IOException 
	{
		File newFile = new File("parrafo.txt");
		newFile.createNewFile();
		escribirFichero(newFile);
		leerYMostrarFrase(newFile);
		
	}

	private static void escribirFichero(File newFile) throws IOException {
		String nuevaFrase;
		FileWriter fw = new FileWriter(newFile);
		
		
		do 
		{
			System.out.println("Introduzca una frase o * para salir");
			nuevaFrase = sc.next();
			if(!nuevaFrase.equals("*"))
				fw.write(nuevaFrase + '\n');
			
		}while(!nuevaFrase.equals("*"));
		
		fw.close();
	}

	private static void leerYMostrarFrase(File newFile) throws FileNotFoundException, IOException {
		int contador = 0, eleccion;
		
		BufferedReader br = new BufferedReader(new FileReader(newFile)); 
		while (br.readLine() != null) 
			contador++;
		br.close();
		
		do 
		{
		System.out.println("Introduzca un número entre 1 y " + contador);
		eleccion = sc.nextInt();
		}while (eleccion < 0 || eleccion > contador);
		br = new BufferedReader(new FileReader(newFile)); 
		String frase = br.readLine();
		while (frase != null) 
		{
			if(contador == eleccion)
				System.out.println(frase);
			frase = br.readLine();
			contador--;
		}
	}
	
}
