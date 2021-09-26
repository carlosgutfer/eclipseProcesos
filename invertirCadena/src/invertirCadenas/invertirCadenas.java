package invertirCadenas;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class invertirCadenas {
	
	public static void main(String args []) 
	{
		Scanner sc = new Scanner(System.in);
		String nuevaFrase;
		BufferedReader br;
		
		ProcessBuilder pb = new ProcessBuilder();
		File directorio = new File (".\\bin");
		do 
		{
			System.out.println("Introduzca una frase para ser revertida 0 * para salir");
			nuevaFrase = sc.nextLine();
			if (nuevaFrase.equals("*"))
					break;
			pb.command("java", "invertirCadena.invertirCadena", nuevaFrase);
			pb.directory(directorio);
			try 
			{
				br = new BufferedReader ( new InputStreamReader(pb.start().getInputStream()));
				System.out.println(br.readLine());
			}catch(Exception e) {}
			
		}while(true);	
	}

}
