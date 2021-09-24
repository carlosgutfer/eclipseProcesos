package pspEjercicio1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ejercicio1 {
	
	public static void main(String [] args) 
	{
		
		/* Código para ejercutar el cmd
		try 
		{
		ProcessBuilder pb  = new ProcessBuilder("cmd.exe", "/C", "start"); 
		Process cmd = pb.start();	
		}
		catch (IOException e) 
		{
			System.out.println("Error" + e);
		}*/
		
		ProcessBuilder pb = new ProcessBuilder("ping", "8.8.8.8", "-n", "1");
		try 
		{
			InputStream ping = pb.start().getInputStream();
			BufferedReader bf = new BufferedReader( new InputStreamReader (pb.start().getInputStream()));
			String salida = bf.readLine();
			
			while ( (salida = bf.readLine()) != null) 
			{
				System.out.println(salida);
			}
			
		}catch(IOException e)
		{
			System.out.println("Error" + e);
		}
	}
}
