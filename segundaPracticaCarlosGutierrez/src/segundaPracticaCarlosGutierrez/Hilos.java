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

		if(recibirDeUnoEnUno)//si recibe de 1 en 1 llama al método datos que va actualizando el num mínimo a analizar hasta que este sea mayor que el máximo donde no lo analiza y para 
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
			while(numerosAnalizar[0].compareTo(numerosAnalizar[1]) < 1)//va mandando números a datos para que calcule  y aumenta el mínimo hasta que sea mayor que el máximo a analizar el cual no manda y se detiene el hilo
			{
				comprobarNumero(numerosAnalizar[0]);
				numerosAnalizar[0] = numerosAnalizar[0].add(BigInteger.valueOf(1));
			}
		}
	}
	
	//Este método recibe un número y comprueba si cumple la secuencia 4 2 1, si no guarda el número en una variable.
	public  void comprobarNumero(BigInteger numeroComprobar) 
	{
		ArrayList <BigInteger> 	numerosDeLaSecuencia = new ArrayList<>(); //Guardo la secuencia de cada número en una variable local para analizar hasta que encuentre un número repetido
		BigInteger				num;
		Boolean 				nuevoBucle;
		int						contador;
		int						comprobarLongitud;

		contador = 0;
		nuevoBucle = false;
		num = numeroComprobar;
		while(!nuevoBucle) 
		{
			comprobarLongitud = dato.comprobarSiSeLongitud(num); // Guardo la longitud que hay desde ese número hasta su bucle si se ha calculado ya, si no se ha calculado devuelve -1
			if (comprobarLongitud != -1 && !revisarSecuenciasExploradas) //Si el número es una secuencia ya comprobado y el se ha definido que no se revisen las no exploradas se suma la longitud a la ya  comprobada y se sale del bucle
			{
				contador += comprobarLongitud;
				nuevoBucle = true;
			}
			else 
			{   
				dato.añadirLongitud(num);//Como no está en los números que ya se su longitud llamo al método que lo añade
				num = dato.nuevoNumeroDeLaSecuencia(num);
				dato.comprobarSiEsElNuevoMaximo(num);
				if(numerosDeLaSecuencia.indexOf(num) == -1) //si el número no está en la cadena lo guarda del número actual lo guarda
				{
					numerosDeLaSecuencia.add(num);
				}
				else // si está, sale del bucle y lo guarda si es distinto de 4(número que rompe la hipótesis) 
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


	
	

	

