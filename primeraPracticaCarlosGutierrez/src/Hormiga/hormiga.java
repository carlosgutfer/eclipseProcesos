package Hormiga;

import java.util.Scanner;

public class hormiga 
{
	private static int []  					tablero = new int [2];
	private static infoHormiga 				hormiga;

	public static void main(String [] args) 
	{
		inicializarHormigas(args);
		comunicacionDirector();
	}

	//Este método se encarga de controlar las comunicaciones con el proceso director
	private static void comunicacionDirector() 
	{
		String	entrada;
		Scanner sc;
		
		sc = new Scanner(System.in); 
		do
		{
			entrada = sc.nextLine();
			if (!entrada.equals("end")) 
			{
				System.out.println(hormiga.getX() + " " + hormiga.getY());
				entrada = sc.nextLine();
				nuevaPosicionHormigaYOrientacion(hormiga.getReglas()[Integer.parseInt(entrada)]);
				System.out.println(hormiga.getY() + " " + hormiga.getX());
			}
		}while(!entrada.equals("end"));
		sc.close();
	}

	/* 
	 * Al proceso por entrada ha llegado un array list con 2 registros 1º Tablero, 2º posicion, orientacion y reglas
	 * 1º Guardo el primer campo que llega en una array auxiliar, y cada registro que tiene es el ancho y largo max. del tablero
	 * 2º Guardo el segundo campo reescribiendo el auxiliar.
	 * 3º Cargo en el array reglas, el 3 registro auxiliar (que son las reglas separadas por -) y después creo el objeto hormiga con todos los datos.
	 */
	private static void inicializarHormigas(String[] informacionInicial) 
	{
		String [] 	auxiliar, reglas;

		auxiliar = informacionInicial[0].split(" ");
		for(int i = 0; i < auxiliar.length; ++i)
			tablero[i] = Integer.parseInt(auxiliar[i]) - 1;
		auxiliar = informacionInicial[1].split(" ");
		reglas = auxiliar[3].split("-");
		hormiga  = new infoHormiga(
									Integer.parseInt(auxiliar[0]),
									Integer.parseInt(auxiliar[1]),
									Integer.parseInt(auxiliar[2]), 
									reglas);
	}
		
	/* 
	 * En este método recivo la orientación de la hormiga, su posicion , orientacion.
	 * Cambio la orientación dependiendo si debe girar a la drch o la izq  y, dependiendo de donde está, modifico la posición de la hormiga. 
	 */
	private static void nuevaPosicionHormigaYOrientacion(String regla) 
	{
		boolean 	derecha;
		
		derecha = true;
		if(regla.equals("l"))
			derecha = false;
		if (derecha) 
		{
			hormiga.setOrientacion(hormiga.getOrientacion() + 1);
			if(hormiga.getOrientacion() == 5)
				hormiga.setOrientacion(1);
		}
		else 
		{
			hormiga.setOrientacion(hormiga.getOrientacion() - 1);
			if(hormiga.getOrientacion() == 0)
				hormiga.setOrientacion(4);
		}
		
		switch (hormiga.getOrientacion()) 
		{
			case 1:
				hormiga.setX(hormiga.getX() - 1);
				break;
			case 2:
				hormiga.setY(hormiga.getY() + 1);
				break;
			case 3:
				hormiga.setX(hormiga.getX() + 1);
				break;
			case 4:
				hormiga.setY(hormiga.getY() - 1);
				break;
		}
		comprobarHormigaDentroTablero();
	}
	
	//Comprueba que la hormiga al moverse sigue dentro del tablero, y si no la mete en el lugar correcto
	private static void comprobarHormigaDentroTablero() 
	{
		if (hormiga.getX() < 0)
			hormiga.setX(tablero[0] - 1);
		else if(hormiga.getX() >= (tablero[0] - 1))
			hormiga.setX(0);
		else if(hormiga.getY() < 0)
			hormiga.setY(tablero[1] - 1);
		else if (hormiga.getY() >= (tablero[1] - 1))
			hormiga.setY(0);
	}
}
