package minusculas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class minusculas {

	
	public static void main(String [] args) 
	{
		llamarProcesoMayusculas();
	}

	private static void llamarProcesoMayusculas() {

		Scanner 		sc;
		String			texto;
		File			dir;
		ProcessBuilder	pb;
		Process 		p;
		
		dir = new File(".\\bin");
		pb = new ProcessBuilder("java", "mayusculas.mayusculas");
		pb.directory(dir);
		sc = new Scanner(System.in);
		do 
		{
			System.out.println("Introduce el texto que quieras que se convierta en mayusculas");
			texto = sc.nextLine();
			if (!texto.equals("*"))
				try 
				{	
					p = pb.start();
					parametrosEntrada(texto, p);
					parametrosSalida(p);
				}catch(Exception e) {
					System.out.println(e);
				}
		}while(!texto.equals("*"));
		
		
	}

	private static void parametrosSalida(Process p) throws IOException 
	{
		BufferedReader	br;
		
		br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		System.out.println(br.readLine());
		br.close();
	}

	private static void parametrosEntrada(String texto, Process p) throws IOException 
	{
		BufferedWriter	bw;
		
		bw = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()));
		bw.write(texto + "\n");
		bw.flush();
	}
}
