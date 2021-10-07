package Director;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class director {
	
	private static String		[][] 			tablero;
	private static ArrayList<String>	  		colores;
	private static File 						tablerotxt;
	private static int							numeroHormigas, rondasMaximas;
	
	public static void main (String [] args) 
	{	
		comunicacionConProcesos(cargarEstadoInicial());		
	}
	
	private static void comunicacionConProcesos(ArrayList <String> datosEntradaInicial) 
	{
		String			salida;
		ProcessBuilder 	pb;
		int 			indiceColorActual, i, j;
		String [] 		respuesta, hormigasTemporal;

		pb = crearProcesoHormiga(datosEntradaInicial);
		try 
		{
			i = 0;
			Process p = pb.start();
			salida = "next";
			hormigasTemporal = new String[numeroHormigas];
			do 
			{
				if (++i > rondasMaximas)
					salida = "end";
				j = -1;
				while(++j < numeroHormigas && salida != null) 
				{
					mandarMensaje(salida, p);
					if ((salida = mensajeRecibido(p)) != null)
					{
						respuesta = salida.split(" ");
						indiceColorActual = colores.indexOf(tablero[Integer.parseInt(respuesta[0])] [Integer.parseInt(respuesta[1])]);
						mandarMensaje(String.valueOf(indiceColorActual), p);
						salida = mensajeRecibido(p);
						cambiarColorCasilla(indiceColorActual, respuesta);
						hormigasTemporal[j] = salida; 
					}
				}
				pintarTablero();
			}while(salida != null);
		} catch (IOException e) {
		}		
	}

	private static ProcessBuilder crearProcesoHormiga(ArrayList<String> datosEntradaInicial) {
		File dir;
		ProcessBuilder pb;
		dir = new File(".\\bin");
		pb = new ProcessBuilder ();
		pb.directory(dir);
		pb.command(datosEntradaInicial);
		return pb;
	}

	private static void cambiarColorCasilla(int indiceColorActual, String[] respuesta) {
		if ((indiceColorActual + 1) == colores.size())
			tablero[Integer.parseInt(respuesta[0])] [Integer.parseInt(respuesta[1])] = colores.get(0);
		else
			tablero[Integer.parseInt(respuesta[0])] [Integer.parseInt(respuesta[1])] = colores.get(indiceColorActual + 1);
	}

	private static String mensajeRecibido(Process p) throws IOException 
	{
		BufferedReader	br;
		
		br = new BufferedReader( new InputStreamReader (p.getInputStream()));
		return br.readLine();
	}

	private static  void mandarMensaje(String lectura, Process p) throws IOException {
		BufferedWriter	bw;
		
		bw = new BufferedWriter(new OutputStreamWriter ( p.getOutputStream()));
		bw.write(lectura);
		bw.write("\n");
		bw.flush();
	}

	// carga el estado inicial del tablero y le da los valores desde el archivo txt
	private static ArrayList<String> cargarEstadoInicial() 
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
			numeroHormigas = i - 2;
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
			colores = new ArrayList<String>();
			for(int j = 0; j < argumentos.length; ++j) 
			{
				if (argumentos[j].equals("32"))
					argumentos[j] = " ";
				colores.add(argumentos[j]);
			}
		}else  if(i == 2)
		{
			rondasMaximas = Integer.parseInt(lineaLeida);
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
	
	
	private static void pintarTablero() 
	{
		
		FileWriter 	fw;
		
		try {
			fw = new FileWriter(tablerotxt, true);
			for(int i = 0; i < tablero.length; ++i) 
			{
				for (int j = 0; j < tablero[i].length; j++) 
				{
		
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
	}

}
