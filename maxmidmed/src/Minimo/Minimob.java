package Minimo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minimob {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args []) 
	{
		
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		crearArchivo(elementos);
	}

	private static void crearArchivo(String[] elementos) {
		File		minimoText;
		int 		minimo;
		FileWriter 	fw;
		
		minimo = Integer.parseInt(elementos[0]);
		for(int i = 1; i < elementos.length; i++) 
			if(Integer.parseInt(elementos[i]) < minimo)
				minimo = Integer.parseInt(elementos[i]);
		minimoText = new File ("minimo.txt");
		try {
			minimoText.createNewFile();
			fw = new FileWriter(minimoText);
			fw.write(String.valueOf(minimo));
			System.out.println(minimoText);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
