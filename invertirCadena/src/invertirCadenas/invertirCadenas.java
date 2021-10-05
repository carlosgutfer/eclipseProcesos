package invertirCadenas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class invertirCadenas {
	
	static Process p;
	public static void main(String args []) 
	{
		Scanner sc = new Scanner(System.in);
		String nuevaFrase;
		BufferedReader br;
		BufferedWriter bw;
;
		
		ProcessBuilder pb = new ProcessBuilder();
		File directorio = new File (".\\bin");
		pb.command("java", "invertirCadena.invertirCadena");
		pb.directory(directorio);
		try {
			p = pb.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		do 
		{
			System.out.println("Introduzca una frase para ser revertida 0 * para salir");
			nuevaFrase = sc.nextLine();
			if (nuevaFrase.equals("*"))
					break;
			
			
			try 
			{
				bw  = new BufferedWriter(new OutputStreamWriter (p.getOutputStream()));
				bw.write(nuevaFrase + '\n');
				bw.flush();
				br = new BufferedReader ( new InputStreamReader(p.getInputStream()));
				System.out.println(br.readLine());
			}catch(Exception e) {}
		}while(true);	
	}

}
