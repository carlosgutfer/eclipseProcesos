package primeraPracticaCarlosGutierrez;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class director {
	
	private static String	[][] 	tablero;
	private static int		[][] 	posicionHormigas;
	private static int 		[]		orientacion;
	private static File 			tablerotxt;
	public static void main (String [] args) 
	{
		
		cargarEstadoInicial();
		moverHormiga(tablero, posicionHormigas, orientacion, tablerotxt);	
		
		
	}
	// carga el estado inicial del tablero y le da los valores
	private static void cargarEstadoInicial() 
	{
		File			estadoInicial;
		FileReader		fr;
		BufferedReader 	br;
		int				i;
		
		tablerotxt = new File("tablero.txt");
		if (tablerotxt.exists())
			tablerotxt.delete();
		
		try {
			tablerotxt.createNewFile();
			estadoInicial = new File("estadoInicial.txt");
			fr = new FileReader(estadoInicial);
			br = new BufferedReader(fr);
			br.readLine();
			i = 0;
			while(br.readLine() != null)
				{
					
					iniciaLizarVariable(br.readLine(), i);
					i++;
				}
			inicializarTablero(tablero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	// Dependiendo de la liena que esté leyendo del fichero estadoInicial, inicializa una de las variables 
	private static void iniciaLizarVariable(String readLine, int i) 
	{
		String [] argumentos;
		if (i == 0) 
		{
			argumentos = readLine.split(" ");
			tablero = new String [Integer.parseInt(argumentos[0])][Integer.parseInt(argumentos[1])];
		}
		else if(i == 1) 
		{
			int	l;
			
			l = 0;
			argumentos = readLine.split(" ");
			posicionHormigas = new int [argumentos.length / 2][2];
			for(int j = 0; j < posicionHormigas.length; ++j) 
			{
				for(int k = 0; k < posicionHormigas[j].length; ++k) 
				{
					posicionHormigas[j][k] =Integer.parseInt(argumentos[l]);
					l++;
				}
			}
		}else 
		{	
			argumentos  = readLine.split(" ");
			orientacion = new int [argumentos.length];
			for(int j = 0; j < orientacion.length; ++j)
				orientacion[j] = Integer.parseInt(argumentos[j]);
		}
	}

	// Inicializo el tablero con los recuadros en blanco
	private static void inicializarTablero(String[][] tablero) 
	{
		for (int i = 0; i < tablero.length; ++i)
			for(int j = 0; j < tablero[i].length; ++j)
				tablero[i][j] = " ";
	}
	
	/*Este es el método principal que se encarga de mover la hormiga e ir cambiando el color de cada posición del tablero
	 * Si quieres añadir una nueva regla debes escribir en el case lo que se encuentra en el tablero y dentro del case al caracter que se cambia
	 * Además, escribir el método decir si derecha es true or false
	 */
	private static void moverHormiga(String[][] tablero, int[][] posicionHormiga, int [] orientacion,File tablerotxt) 
	{
		int				maxInteracciones = 12000;
		int				i;
		boolean			derecha;
		
		i = -1;
		derecha = true;
		while(++i < maxInteracciones) 
		{
			for (int j = 0; j < posicionHormiga.length;++j) 
			{
				switch (tablero[posicionHormiga[j][0]][posicionHormiga[j][1]]) 
				{
					case " ":
						tablero[posicionHormiga[j][0]][posicionHormiga[j][1]] = "#";
						derecha = true;
						break;
					case "#":
						tablero[posicionHormiga[j][0]][posicionHormiga[j][1]] = "/";
						derecha = true;
						break;
					case "/":
						tablero[posicionHormiga[j][0]][posicionHormiga[j][1]] = "&";
						derecha = false;
						break;
					case "&":
						tablero[posicionHormiga[j][0]][posicionHormiga[j][1]] = " ";
						derecha = false;
						break;
				}
				nuevaPosicionHormigaYOrientacion(posicionHormiga, derecha, j, orientacion);
				comprobarHormigaDentroTablero(tablero, posicionHormiga, j);
			}
			pintarTablero(tablero, posicionHormiga, tablerotxt);		
		}
	}
	
	//Comprueba que la hormiga al moverse sigue dentro del tablero, y si no la mete en el lugar correcto
	private static void comprobarHormigaDentroTablero(String[][] tablero, int[][] posicionHormiga,int j) 
	{
		if (posicionHormiga[j][0] < 0)
			posicionHormiga[j][0] = tablero.length - 1;
		else if(posicionHormiga[j][0] == tablero.length)
			posicionHormiga[j][0] = 0;
		else if(posicionHormiga[j][1] < 0)
			posicionHormiga[j][1] = tablero[0].length - 1;
		else if (posicionHormiga[j][1] == tablero[0].length)
			posicionHormiga[j][1] = 0;
	}

	/* En este método recivo la orientación de la hormiga, su posicion , orientacion y la hormiga que toca moficar.
	 *Cambio la orientación dependiendo si debe girar a la drch o la izquierda y dependiendo de donde está modifico la posición de la hormiga. 
	*/ 
	private static void nuevaPosicionHormigaYOrientacion(int[][] posicionHormiga, boolean derecha, int j, int [] orientacion) 
	{
		
		if (derecha)
			orientacion[j] += 1;
		else
			orientacion[j] -= 1;
		if(orientacion[j] == 5)
			orientacion[j] = 1;
		else if(orientacion[j] == 0)
			orientacion[j] = 4;
		
		if(orientacion[j] == 1)
			posicionHormiga[j][0]--;
		else if(orientacion[j] == 2)
			posicionHormiga[j][1]++;
		else if(orientacion[j] == 3)
			posicionHormiga[j][0]++;
		else if (orientacion[j] == 4)
			posicionHormiga[j][1]--;
		
		
		
		
	}
	
	private static void pintarTablero(String[][] tablero, int [][] posicionHormiga, File tablerotxt) 
	{
		
		FileWriter 	fw;
		
		try {
			fw = new FileWriter(tablerotxt, true);
			for(int i = 0; i < tablero.length; ++i) 
			{
				for (int j = 0; j < tablero[i].length; j++) 
				{
					for (int k = 0; k < posicionHormiga.length; ++k) 
						if (posicionHormiga[k][0] == i && posicionHormiga[k][1] == j) 
						{
							fw.write((char) 165);
							k = 4;
						}else if(k == 3) 		
							fw.write(tablero[i][j]);
				}
					fw.write('\n');
			}
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
