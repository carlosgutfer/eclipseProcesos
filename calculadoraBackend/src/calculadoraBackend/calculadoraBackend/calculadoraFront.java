package calculadoraBackend;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class calculadoraFront {

	public static void main(String args[]) 
	{
	
		ProcessBuilder pb = new ProcessBuilder("calculadoraBackend", "suma", "1", "1");
		try 
		{
		pb.start();
			BufferedReader bf = new BufferedReader( new InputStreamReader (pb.start().getInputStream()));
			String salida = bf.readLine();
			System.out.println(salida);
		}catch(Exception e) 
		{
			System.out.println(e);
		}
	}
}