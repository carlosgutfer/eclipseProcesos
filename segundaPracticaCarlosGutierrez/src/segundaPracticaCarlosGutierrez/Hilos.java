package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.ArrayList;

public class Hilos implements Runnable 
{
	private Datos dato;
	private int numeroHilo;
	private boolean recibirDeUnoEnUno;
	private boolean revisarSecuenciasExploradas;

	public Hilos(Datos dato, int numeroHilo, boolean recibirDeUnoEnUno, boolean revisarSecuenciasExploradas) 
	{
		super();
		this.dato = dato;
		this.numeroHilo = numeroHilo;
		this.recibirDeUnoEnUno = recibirDeUnoEnUno;
		this.revisarSecuenciasExploradas = revisarSecuenciasExploradas;
	}

	public void run() 
	{
		BigInteger [] numerosAnalizar;
		BigInteger    numeroRecibido;

		if(recibirDeUnoEnUno)//si recibe de 1 en 1 llama al m�todo datos que va actualizando el num m�nimo a analizar hasta que este sea mayor que el m�ximo donde no lo analiza y para 
		{
			
			do 
			{
				numeroRecibido = dato.getNumeroAnalizar();
				comprobarNumero(numeroRecibido);
			}while(!dato.todosAnalizados());
		}
		else
		{
			numerosAnalizar = dato.numerosParaAnalizar(numeroHilo); //recibe el rango de numeros a analizar ej. [1 - 10]
			while(numerosAnalizar[0].compareTo(numerosAnalizar[1]) < 1)//va mandando n�meros a datos para que calcule  y aumenta el m�nimo hasta que sea mayor que el m�ximo a analizar el cual no manda y se detiene el hilo
			{
				comprobarNumero(numerosAnalizar[0]);
				numerosAnalizar[0] = numerosAnalizar[0].add(BigInteger.valueOf(1));
			}
		}
	}
	
	//Este m�todo recibe un n�mero y comprueba si cumple la secuencia 4 2 1, si no guarda el n�mero en una variable.
	public  void comprobarNumero(BigInteger numeroComprobar) 
	{
		ArrayList <BigInteger> 	numerosDeLaSecuencia = new ArrayList<>(); //Guardo la secuencia de cada n�mero en una variable local para analizar hasta que encuentre un n�mero repetido
		BigInteger				num;
		Boolean 				nuevoBucle;
		int						contador;
		int						comprobarLongitud;

		contador = 0;
		nuevoBucle = false;
		num = numeroComprobar;
		while(!nuevoBucle) 
		{
			comprobarLongitud = dato.comprobarSiSeLongitud(num); // Guardo la longitud que hay desde ese n�mero hasta su bucle si se ha calculado ya, si no se ha calculado devuelve -1
			if (comprobarLongitud != -1 && !revisarSecuenciasExploradas) //Si el n�mero es una secuencia ya comprobado y el se ha definido que no se revisen las no exploradas se suma la longitud a la ya  comprobada y se sale del bucle
			{
				contador += comprobarLongitud;
				nuevoBucle = true;
			}
			else 
			{   
				dato.a�adirLongitud(num);//Como no est� en los n�meros que ya se su longitud llamo al m�todo que lo a�ade
				num = dato.nuevoNumeroDeLaSecuencia(num);
				dato.comprobarSiEsElNuevoMaximo(num);
				if(numerosDeLaSecuencia.indexOf(num) == -1) //si el n�mero no est� en la cadena lo guarda del n�mero actual lo guarda
				{
					numerosDeLaSecuencia.add(num);
				}
				else // si est�, sale del bucle y lo guarda si es distinto de 4(n�mero que rompe la hip�tesis) 
				{
					nuevoBucle = true;
					if(num.compareTo(BigInteger.valueOf(4)) == 1)
						dato.setNumeroConDistintoBucle(num);			
				}
				contador ++;
			}
		}
		dato.comprobarSecuenciaMasLargayAnnadirLongitud(numeroComprobar, contador);
	}

	public  boolean getComprobarYaExploradas() 
	{
		return revisarSecuenciasExploradas;
	}
}


	
	

	

