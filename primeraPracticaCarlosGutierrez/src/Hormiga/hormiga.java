package Hormiga;

import java.util.ArrayList;
import java.util.Scanner;

public class hormiga 
{
	private static int []  					tablero = new int [2];
	private static ArrayList <infoHormiga> 	todasHormigas = new ArrayList<infoHormiga>(); 

	public static void main(String [] args) 
	{
		String	entrada;
		Scanner sc;
		int		hormigaActual;
		
		hormigaActual = 0;
		sc = new Scanner(System.in); 
		inicializarHormigas(args);
		do
		{
			entrada = sc.nextLine();
			if (!entrada.equals("end")) 
			{
				System.out.println(todasHormigas.get(hormigaActual).getX() + " " + todasHormigas.get(hormigaActual).getY());
				entrada = sc.nextLine();
				nuevaPosicionHormigaYOrientacion(todasHormigas.get(hormigaActual).getReglas()[Integer.parseInt(entrada)], hormigaActual);
				System.out.println(todasHormigas.get(hormigaActual).getY() + " " + todasHormigas.get(hormigaActual).getX());
				++hormigaActual;
				if (hormigaActual == todasHormigas.size())
					hormigaActual = 0;	
			}
		}while(!entrada.equals("end"));
		sc.close();
	}

	/* 
	 * 1º Guardo el primer campo  del array que llega en una array auxiliar, y cada registro que tiene es el ancho y largo max. del tablero
	 * 2º Desde el segundo campo, cargo la informacion referente a cada hormiga, cada hormiga se divide en 4 (x, y, orientacion, reglas[])
	 *    por ello, cargo en el array reglas, el 3 registro auxiliar y después creo el objeto hormiga con todos los datos.
	 */
	private static void inicializarHormigas(String[] informacionInicial) 
	{
		String [] 	auxiliar;

		auxiliar = informacionInicial[0].split(" ");
		for(int i = 0; i < auxiliar.length; ++i)
			tablero[i] = Integer.parseInt(auxiliar[i]) - 1;
		for(int j = 1; j < informacionInicial.length; ++j) 
		{
			auxiliar = informacionInicial[j].split(" ");
			String reglas [] = auxiliar[3].split("-");
			todasHormigas.add(new infoHormiga(
												Integer.parseInt(auxiliar[0]),
												Integer.parseInt(auxiliar[1]),
												Integer.parseInt(auxiliar[2]), 
												reglas));
		}
	}
		


/* 
 * En este método recivo la orientación de la hormiga, su posicion , orientacion y la hormiga que toca moficar.
 *Cambio la orientación dependiendo si debe girar a la drch o la izquierda y dependiendo de donde está modifico la posición de la hormiga. 
 */
	private static void nuevaPosicionHormigaYOrientacion(String regla, int j) 
	{
		infoHormiga hormigaActual;
		boolean 	derecha;
		
		derecha = true;
		hormigaActual = todasHormigas.get(j);
		if(regla.equals("l"))
			derecha = false;
		if (derecha) 
		{
			hormigaActual.setOrientacion(hormigaActual.getOrientacion() + 1);
			if(hormigaActual.getOrientacion() == 5)
				hormigaActual.setOrientacion(1);
		}
		else 
		{
			hormigaActual.setOrientacion(hormigaActual.getOrientacion() - 1);
			if(hormigaActual.getOrientacion() == 0)
				hormigaActual.setOrientacion(4);
		}
		
		switch (hormigaActual.getOrientacion()) 
		{
			case 1:
				hormigaActual.setX(hormigaActual.getX() - 1);
				break;
			case 2:
				hormigaActual.setY(hormigaActual.getY() + 1);
				break;
			case 3:
				hormigaActual.setX(hormigaActual.getX() + 1);
				break;
			case 4:
				hormigaActual.setY(hormigaActual.getY() - 1);
				break;
		}
		comprobarHormigaDentroTablero(hormigaActual, j);
	}
	
	//Comprueba que la hormiga al moverse sigue dentro del tablero, y si no la mete en el lugar correcto
	private static void comprobarHormigaDentroTablero(infoHormiga hormigaActual, int j) 
	{
		if (hormigaActual.getX() < 0)
			hormigaActual.setX(tablero[0] - 1);
		else if(hormigaActual.getX() >= (tablero[0] - 1))
			hormigaActual.setX(0);
		else if(hormigaActual.getY() < 0)
			hormigaActual.setY(tablero[1] - 1);
		else if (hormigaActual.getY() >= (tablero[1] - 1))
			hormigaActual.setY(0);
	}
	
	
	
		/*
		 * Este es el método principal que se encarga de mover la hormiga e ir cambiando el color de cada posición del tablero
		 * Si quieres añadir una nueva regla debes escribir en el case lo que se encuentra en el tablero y dentro del case al caracter que se cambia
		 * Además, escribir el método decir si derecha es true or false
		 
		private static void moverHormiga() 
		{
			int				maxInteracciones = 20000;
			int				i;
			boolean			derecha;
			
			i = -1;
			derecha = true;
			while(++i < maxInteracciones) 
			{
				for (int j = 0; j < posicionHormigas.length;++j) 
				{
					switch (tablero[posicionHormigas[j][0]][posicionHormigas[j][1]]) 
					{
						case " ":
							tablero[posicionHormigas[j][0]][posicionHormigas[j][1]] = "#";
							derecha = true;
							break;
						case "#":
							tablero[posicionHormigas[j][0]][posicionHormigas[j][1]] = "/";
							derecha = true;
							break;
						case "/":
							tablero[posicionHormigas[j][0]][posicionHormigas[j][1]] = "&";
							derecha = false;
							break;
						case "&":
							tablero[posicionHormigas[j][0]][posicionHormigas[j][1]] = " ";
							derecha = false;
							break;
					}
					nuevaPosicionHormigaYOrientacion(derecha, j);
					comprobarHormigaDentroTablero(j);
				}
				pintarTablero();		
			}
		}*/
		

}
