package mayusculas;

import java.util.Scanner;

public class mayusculas {
	
	private static Scanner sc = new Scanner(System.in);
	public static void main(String args []) 
	{
		String mensaje;
		
		mensaje = sc.nextLine();
		
		cambiarMayusculas(mensaje);
	}
	
	private static void cambiarMayusculas(String mensaje) 
	{
		System.out.println(mensaje.toUpperCase());
		
	}

}
