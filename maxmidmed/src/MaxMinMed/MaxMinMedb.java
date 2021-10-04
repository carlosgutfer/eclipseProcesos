package MaxMinMed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxMinMedb {
	
	public static void main(String args []) 
	{
		
	
		
		/*dir = new File(".\\bin");
		pb = new ProcessBuilder();
		pb.directory(dir);
		
		//llamadaProceso(args, pb, "Media.Mediab");
		//llamadaProceso(args, pb, "Maximo.Maximob");
		//llamadaProceso(args, pb, "Minimo.Minimob");*/
		
		menu();

		
	}


	private static void menu() {
		String 				entrada;
		Scanner 			sc;
		
		
		sc = new Scanner(System.in);
		System.out.println("Introduzca los números que quiere comparar seguidos de espacios ");
		entrada = sc.nextLine();
		llamadaProcesov2("Media.Mediab", entrada);
		llamadaProcesov2("Maximo.Maximob", entrada);
		llamadaProcesov2("Minimo.Minimob",entrada);
	}

	
	/*v1 
	private static void llamadaProceso(String[] args, ProcessBuilder pb, String nombreProceso) 
	{
		ArrayList<String> scriptParaEjecutar = new ArrayList<String>();
		
		scriptParaEjecutar.add("java");
		scriptParaEjecutar.add(nombreProceso);
		for(int i = 0; i < args.length; i++)
			scriptParaEjecutar.add(args[i]);
		pb = new ProcessBuilder(scriptParaEjecutar);
		try {
			pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
			String salida = br.readLine();
			br.close();
			File archivoLectura = new File(salida);
			FileReader fr = new FileReader(archivoLectura);
			br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null)
				System.out.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	//v2
	private static void llamadaProcesov2(String nombreProceso, String entrada) 
	{
		String 				salida, line;
		BufferedReader 		br;
		BufferedWriter 		bw;
		File				dir, archivoLectura;
		ProcessBuilder		pb;
		
		dir = new File(".\\bin");
		pb = new ProcessBuilder("java", nombreProceso);
		pb.directory(dir);
		try {
			Process p = pb.start();
			bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			bw.write(entrada + '\n');
			bw.flush();
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			salida = br.readLine();
			archivoLectura = new File(".\\bin\\"+salida);
			FileReader fr = new FileReader(archivoLectura);
			br = new BufferedReader(fr);
			while((line = br.readLine()) != null)
				System.out.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
