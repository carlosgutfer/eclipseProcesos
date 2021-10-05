package Director;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class director {
	
	private static String	[][] 	tablero;
	private static String	[]  	colores;
	private static File 			tablerotxt;
	
	public static void main (String [] args) 
	{
			
		comunicacionConProcesos(cargarEstadoInicial());	
		
		
	}
	
	private static void comunicacionConProcesos(ArrayList <String> datosEntradaInicial) 
	{
		String			salida ;
		File 			dir;
		ProcessBuilder 	pb;
		String 			lectura;
		BufferedWriter bw;
		BufferedReader br;
	
		
		dir = new File(".\\bin");
		pb = new ProcessBuilder ();
		pb.directory(dir);
		pb.command(datosEntradaInicial);
		try 
		{
			int				i = 0;
			Process p = pb.start();
			lectura = "next";
			do 
			{
				if (++i == 10)
					lectura = "*";
				bw = new BufferedWriter(new OutputStreamWriter ( p.getOutputStream()));
				bw.write(lectura + '\n');
				bw.flush();
				br = new BufferedReader( new InputStreamReader (p.getInputStream()));
				//while((lectura = br.readLine()) != "exit")
					System.out.println(br.readLine());
			}while( !lectura.equals("*"));
		} catch (IOException e) {
			System.out.println(e);	
		}		
	}

	// carga el estado inicial del tablero y le da los valores desde el archivo txt
	private static ArrayList cargarEstadoInicial() 
	{
		File				estadoInicial;
		FileReader			fr;
		BufferedReader 		br;
		int					i;
		ArrayList <String> 	datosEntradaInicial;
		
		datosEntradaInicial = new ArrayList<String>();
		datosEntradaInicial.add("java");
		datosEntradaInicial.add("Hormiga.hormiga");
		estadoInicial = new File("estadoInicial.txt");
		tablerotxt = new File("tablero.txt");
		if (tablerotxt.exists())
			tablerotxt.delete();
		
		try {
			tablerotxt.createNewFile();	
			fr = new FileReader(estadoInicial);
			br = new BufferedReader(fr);
			i = -1;
			while(br.readLine() != null)
					iniciaLizarVariable(br.readLine(), ++i, datosEntradaInicial);
		} catch (IOException e) {
			System.out.println(e);	
		}
		return datosEntradaInicial;
	
	}
	
	// Dependiendo de la liena que esté leyendo del fichero estadoInicial, inicializa una de las variables 
	private static void iniciaLizarVariable(String lineaLeida, int i, ArrayList<String> datosEntradaInicial) 
	{
		String [] argumentos;
		
		argumentos = lineaLeida.split(" ");
		if (i == 0) 
		{	
			tablero = new String [Integer.parseInt(argumentos[0])][Integer.parseInt(argumentos[1])];
			inicializarTablero();
			datosEntradaInicial.add(lineaLeida);
		}
		else if(i == 1) 
		{
			colores = new String[argumentos.length];
			for(int j = 0; j < argumentos.length; ++j) 
			{
				if (argumentos[j].equals("32"))
					argumentos[j] = " ";
				colores[j] = argumentos[j];
			}
		}else 
			datosEntradaInicial.add(lineaLeida);
	}

	// Inicializo el tablero con los recuadros en blanco
	private static void inicializarTablero() 
	{
		for (int i = 0; i < tablero.length; ++i)
			for(int j = 0; j < tablero[i].length; ++j)
				tablero[i][j] = " ";
	}
	
	/*
	private static void pintarTablero() 
	{
		
		FileWriter 	fw;
		boolean		hormiga;
		
		try {
			fw = new FileWriter(tablerotxt, true);
			for(int i = 0; i < tablero.length; ++i) 
			{
				for (int j = 0; j < tablero[i].length; j++) 
				{
					hormiga = true;
					for (int k = 0; k < posicionHormigas.length && hormiga ; ++k) 
						if (posicionHormigas[k][0] == i && posicionHormigas[k][1] == j) 
						{
							fw.write((char) 165);
							hormiga = false; 
							//k = 4; modo feo salir no usar
						}else if(k == 3) 		
							fw.write(tablero[i][j]);
						fw.write(".");

				}
					fw.write('\n');
			}
			fw.write('\n');
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
