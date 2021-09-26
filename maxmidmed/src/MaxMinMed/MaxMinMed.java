package MaxMinMed;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MaxMinMed {
	
	public static void main(String args []) 
	{
		File			dir;
		ProcessBuilder 	pb;
			
		dir = new File(".\\bin");
		pb = new ProcessBuilder();
		pb.directory(dir);

		llamadaProceso(args, pb, "Media.Media");
		llamadaProceso(args, pb, "Maximo.Maximo");
		llamadaProceso(args, pb, "Minimo.Minimo");

		
	}

	private static void llamadaProceso(String[] args, ProcessBuilder pb, String nombreProceso) 
	{
		ArrayList<String> scriptParaEjecutar = new ArrayList<String>();
		
		scriptParaEjecutar.add("java");
		scriptParaEjecutar.add(nombreProceso);
		for(int i = 0; i < args.length; i++)
			scriptParaEjecutar.add(args[i]);
		pb = new ProcessBuilder(scriptParaEjecutar);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader( pb.start().getInputStream()));
			System.out.println(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
