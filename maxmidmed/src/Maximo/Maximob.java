package Maximo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Maximob {
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String args []) 
	{
		
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		crearArchivo(elementos);
	}

	private static void crearArchivo(String[] elementos) {
		int maximo;
		File maximoText;

		maximo = Integer.parseInt(elementos[0]);
		for(int i = 1; i < elementos.length; i++) 
			if(Integer.parseInt(elementos[i]) > maximo)
				maximo = Integer.parseInt(elementos[i]);
		maximoText = new File ("maximo.txt");
		try {
			maximoText.createNewFile();
			FileWriter fw = new FileWriter(maximoText);
			fw.write(String.valueOf(maximo));
			fw.close();
			System.out.println(maximoText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
