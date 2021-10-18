package Director;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class director {
	
	private static String		[][] 			tablero;
	private static ArrayList<String>	  		colores;
	private static int							rondasMaximas, numeroReglas;
	
	public static void main (String [] args) 
	{	
		comunicacionConProcesos(cargarEstadoInicial());		
	}

	//Es el método principal que comunica el director con los procesos hormiga/s y grabador.
	private static void comunicacionConProcesos(ArrayList <String> datosEntradaInicial) 
	{
		String										salida;
		int 										i;
		String  [] 									todasHormigasTemporal;
		ArrayList<Process> 							p;
		ArrayList<ProcessBuilder> 					pb;
		
		p = new ArrayList<Process>();
		pb = new ArrayList<ProcessBuilder>();
		try 
		{
			i = 0;
			crearProcesoHormigaGrabador(datosEntradaInicial, pb, p);
			salida = "next";
			todasHormigasTemporal = new String[pb.size() - 1];
			do 
			{
				if (++i > rondasMaximas)
					salida = "end";
				for( int j = 0;  j < p.size() - 1 && salida != null; ++j) 
					salida = comunicacionHormiga(salida, j, todasHormigasTemporal, p);
				comunicacionGrabador(todasHormigasTemporal, i, p.get(p.size() - 1));
			}while(salida != null);
		} catch (IOException e) {}		
	}

	//Este método se encarga de las comunicaciones con los procesos  hormiga, (manda si debe moverse o morir, recibe su posición, manda el color y por último cambia de color la casilla y recibe la nueva posición)
	private static String comunicacionHormiga(String salida, int j, String[] todasHormigasTemporal, ArrayList<Process> p) throws IOException 
	{
		int			indiceColorActual;
		String[]	posicionHormigaTemporal;
		
		mandarMensaje(salida, p.get(j));
		if ((salida = mensajeRecibido(p.get(j))) != null)
		{
			posicionHormigaTemporal = salida.split(" ");
			indiceColorActual = colores.indexOf(tablero[Integer.parseInt(posicionHormigaTemporal[0])] [Integer.parseInt(posicionHormigaTemporal[1])]);
			mandarMensaje(String.valueOf(indiceColorActual), p.get(j));
			salida = mensajeRecibido(p.get(j));
			cambiarColorCasilla(indiceColorActual, posicionHormigaTemporal);
			todasHormigasTemporal[j] = salida; 
		}
		return salida;
	}

	/* 
	 * Creo cada proceso y lo inicializo, a cada proceso debo pasarles los 3 primeros parámetros de entrada (java, programa, tablero) y los datos hormigas 
	 *  Después borro el registro hormiga que ya he usado, inizializo el proceso y paso a inicializar el siguiente.
	 *	Cuando termino los procesos hormiga, creo e inicializo el proceso grabador (linea 97 - 99)
	 */
	private static void crearProcesoHormigaGrabador(ArrayList<String> datosEntradaInicial,ArrayList<ProcessBuilder> pb,ArrayList<Process> p) 
	{
		File dir;
		ArrayList <String>	datosEntradaHormiga = new ArrayList<String>();
		
		dir = new File(".\\bin");
		try {
			for(int i = 0; i < datosEntradaInicial.size(); i++) 
			{
				pb.add(new ProcessBuilder ());
				pb.get(i).directory(dir);
				for(int j = 0; j < 4; ++j)
					datosEntradaHormiga.add(datosEntradaInicial.get(j));
				datosEntradaInicial.remove(3);
				pb.get(i).command(datosEntradaHormiga);
				p.add(pb.get(i).start());
				datosEntradaHormiga.clear();
			}
			pb.add(new ProcessBuilder ("java", "Grabador.grabador"));
			pb.get(pb.size() - 1).directory(dir);
			p.add(pb.get(pb.size() -1).start());
		} catch (IOException e) {}
	}

	//Con la posición de la hormiga recibida cambio el color de esa casilla al siguiente
	private static void cambiarColorCasilla(int indiceColorActual, String[] respuesta) 
	{
		if ((indiceColorActual + 1) == numeroReglas)
			tablero[Integer.parseInt(respuesta[0])] [Integer.parseInt(respuesta[1])] = colores.get(0);
		else
			tablero[Integer.parseInt(respuesta[0])] [Integer.parseInt(respuesta[1])] = colores.get(indiceColorActual + 1);
	}

	//Método para leer la respuesta del proceso
	private static String mensajeRecibido(Process p) throws IOException 
	{
		BufferedReader	br;
		
		br = new BufferedReader( new InputStreamReader (p.getInputStream()));
		return br.readLine();
	}
	
	// Método para mandar los datos al proceso
	private static  void mandarMensaje(String lectura, Process p) 
	{
		BufferedWriter	bw;
		try 
		{
			bw = new BufferedWriter(new OutputStreamWriter ( p.getOutputStream()));
			bw.write(lectura);
			bw.write("\n");
			bw.flush();
		}catch (IOException e) {}
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
		
		try 
		{
			fr = new FileReader(estadoInicial);
			br = new BufferedReader(fr);
			i = -1;
			while(br.readLine() != null)
					iniciaLizarVariables(br.readLine(), ++i, datosEntradaInicial);
		} catch (IOException e) {
			System.out.println(e);	
		}
		return datosEntradaInicial;
	
	}
	
	// Dependiendo de la liena que esté leyendo del fichero estadoInicial, inicializa las variables 
	private static void iniciaLizarVariables(String lineaLeida, int i, ArrayList<String> datosEntradaInicial) 
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
		
			rondasMaximas = Integer.parseInt(lineaLeida);
		else if(i == 3)
			numeroReglas = Integer.parseInt(lineaLeida);
		else 
			datosEntradaInicial.add(lineaLeida);
	}

	// Inicializo el tablero con los recuadros en blanco
	private static void inicializarTablero() 
	{
		for (int i = 0; i < tablero.length; ++i)
			for(int j = 0; j < tablero[i].length; ++j)
				tablero[i][j] = " ";
	}
	
	// Guarda los caracteres contenidos en el tablero en un string y después se los manda al proceso grabador.
	private static void comunicacionGrabador(String[] todasHormigasTemporal, int numeroInteraccion, Process p) 
	{
		String		mensaje;
		Boolean		encontrada;

		try {
			mensaje = numeroInteraccion + "\n";
			for(int i = 0; i < tablero.length; ++i) 
			{
				for (int j = 0; j < tablero[i].length; j++) 
				{   
					encontrada = false;
					for(int k = 0; k < todasHormigasTemporal.length && !encontrada; ++k) 
					{
						if(todasHormigasTemporal[k].equals(i + " " + j)) 
						{
							mensaje += (char) 165;
							encontrada = true;
						}else if(k + 1 == todasHormigasTemporal.length )
							mensaje += tablero[i][j];
					}
					mensaje += ".";
				}
					mensaje += "\n";
			}
			mensaje += "\n";
			if(numeroInteraccion == rondasMaximas)
				mensaje += "fin";
			mandarMensaje(mensaje, p);
		} catch (Exception e) 
		{
		}
	}

}
