package Media;


import java.util.Scanner;

public class Media {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String args []) 
	{
		int		media;
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		media = 0;
		for(int i = 0; i < elementos.length; i++) 
			media += Integer.parseInt(elementos[i]);
		System.out.println(media / elementos.length);	
	}
}
