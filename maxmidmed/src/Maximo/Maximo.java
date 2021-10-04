package Maximo;

import java.util.Scanner;

public class Maximo {
	
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String args []) 
	{
		int 		maximo;
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		maximo = Integer.parseInt(elementos[0]);
		for(int i = 1; i < elementos.length; i++) 
			if(Integer.parseInt(elementos[i]) > maximo)
				maximo = Integer.parseInt(elementos[i]);
		System.out.println(maximo);
	}

}
