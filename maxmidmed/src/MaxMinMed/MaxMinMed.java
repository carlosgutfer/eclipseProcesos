package MaxMinMed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MaxMinMed {
	
	public static void main(String args []) 
	{
		File			dir;
		ProcessBuilder 	pb;
			
		dir = new File(".\\bin");
		pb = new ProcessBuilder();
		pb.directory(dir);
		String [] arrayProcesos = {"Media.Media", "Maximo.Maximo", "Minimo.Minimo"};
		llamadaProceso(arrayProcesos, "8 7 5");


		
	}

	private static void llamadaProceso(String [] arrayProcesos, String entrada) 
	{
		BufferedReader 		br;
		BufferedWriter 		bw;
		File				dir;
		ProcessBuilder		pb;
		
		for(int i = 0; i < arrayProcesos.length; i ++) 
		{
			dir = new File(".\\bin");
			pb = new ProcessBuilder("java", arrayProcesos[i]);
			pb.directory(dir);
			
			try {
				Process p = pb.start();
				bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				bw.write(entrada + '\n');
				bw.flush();
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				System.out.println(br.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
