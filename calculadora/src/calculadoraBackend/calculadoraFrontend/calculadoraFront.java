package calculadoraFrontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class calculadoraFront {

	public static void main(String args[]) 
	{
	
		File ruta = new File(".\\\\bin");
		ProcessBuilder pb = new ProcessBuilder("java", "calculadoraBackend.calculadoraBackend", "resta", "1", "1");
		pb.directory(ruta);
		try 
		{
			BufferedReader bf = new BufferedReader( new InputStreamReader (pb.start().getInputStream()));
			String salida = bf.readLine();
			System.out.println(salida);
		}catch(Exception e) 
		{
			System.out.println(e);
		}
	}
}
