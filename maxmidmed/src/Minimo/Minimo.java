package Minimo;

import java.util.Scanner;

public class Minimo {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args []) 
	{
		int minimo;
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		minimo = Integer.parseInt(elementos[0]);
		for(int i = 1; i < elementos.length; i++) 
			if(Integer.parseInt(elementos[i]) < minimo)
				minimo = Integer.parseInt(elementos[i]);
		System.out.println(minimo);
	}

}
