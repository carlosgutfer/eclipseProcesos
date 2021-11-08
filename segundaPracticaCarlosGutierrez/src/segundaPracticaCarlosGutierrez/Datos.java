package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Datos 
{
	private HashMap<BigInteger,BigInteger>  tablaNumerosComprobados = new HashMap<>();
	private HashMap<BigInteger, Integer> 	tablaNumeroContador = new HashMap<>();
	private BigInteger [] 					maxMinNumerosAnalizar;
	private ArrayList<BigInteger> 			numerosMaximo;
	private int								secuenciaMasLarga;
	private int								numeroTotalHilos;
	private BigInteger						numeroMaximoEncontrado;
	private BigInteger						numeroConDistintoBucle;


	public Datos(BigInteger[] maxMinNumerosAnalizar, int numeroHilos) 
	{
		super();
		this.maxMinNumerosAnalizar = maxMinNumerosAnalizar;
		this.numerosMaximo = new ArrayList<>();
		this.secuenciaMasLarga = 0;
		this.numeroTotalHilos = numeroHilos;
		this.numeroMaximoEncontrado = new BigInteger("0");
	}
	
	
	public boolean comprobarNumeroEnTabla(BigInteger num) 
	{
		if(tablaNumerosComprobados.containsKey(num)) 
			return true;
		else
			return false;
	}
	
	public synchronized void comprobarNumero(BigInteger numeroComprobar) 
	{
		
		ArrayList <BigInteger> 	numerosDeLaSecuencia = new ArrayList<>();
		BigInteger				num;
		Boolean 				nuevoBucle;
		int						contador;

		contador = 0;
		nuevoBucle = false;
		num = numeroComprobar;
		
		while(!nuevoBucle) 
		{	
			contador ++;
			if (tablaNumeroContador.containsKey(num))  //Este primer if comprueba si el número ya está comprobado en la tabla contador, si está sale bucle. Con su implementación el programa tarda 1/3 menos de media
			{
				contador += tablaNumeroContador.get(num);
				nuevoBucle = true;
			}
			else 
			{
				num = nuevoNumeroDeLaSecuencia(num);
				if (numeroMaximoEncontrado.compareTo(num) == -1)
						numeroMaximoEncontrado = num;
				if(numerosDeLaSecuencia.indexOf(num) == -1) //si el número no está en la cadena lo guarda
					numerosDeLaSecuencia.add(num);
				else // si está como aun no hemos llegado al 1, sale del bucle y lo guarda (número que rompe la hipótesis) 
				{
					nuevoBucle = true;
					if(num.compareTo(BigInteger.valueOf(4)) != 0)
						numeroConDistintoBucle = num;			
				}
			}
		}
		tablaNumeroContador.put(numeroComprobar, contador); 
		if (contador > secuenciaMasLarga) 
		{
			numerosMaximo.clear();
			numerosMaximo.add(numeroComprobar);
			secuenciaMasLarga = contador;
		}
		else if(contador == secuenciaMasLarga) 
			numerosMaximo.add(numeroComprobar);
	}

	//Este método devuelve el siguiente número de la secuencia, el cual puede ser el value de una key de los números comprobados, o un número número
	private BigInteger nuevoNumeroDeLaSecuencia(BigInteger num) 
	{
		
		BigInteger[] 	auxiliarBigIntegers;
		BigInteger 		keyMap;
		
		if (comprobarNumeroEnTabla(num)) //este if comprubea si el número ya se ha comprobado en la tabla de secuencias, si está devuelve el valor que tiene
			num = tablaNumerosComprobados.get(num);
		else 
		{
			keyMap = num; //guardo el número que voy a añadir como key en el keymap
			auxiliarBigIntegers = num.divideAndRemainder(BigInteger.valueOf(2)); //calcula el resto de dividir el numero entre 2
			if (auxiliarBigIntegers[1] != BigInteger.valueOf(0)) // si es impar num = num * 3 + 1
				num = num.multiply(BigInteger.valueOf(3)).add(BigInteger.valueOf(1));
			else 
				num = num.divide(BigInteger.valueOf(2)); //si es par num = num / 2 
			tablaNumerosComprobados.put(keyMap, num);
		}
		return num;
	}
	
	//Este método devuelve el rango de números que debe analizar cada hilo en el caso de que no haya que mandárselos de 1 en 1
	public BigInteger [] numerosParaAnalizar(int hiloActual) 
	{
		BigInteger [] 	numerosAnalizar = new BigInteger [2];
		BigInteger 		rangoNumeros;
		
		rangoNumeros = maxMinNumerosAnalizar[1].subtract(maxMinNumerosAnalizar[0]);
		if (hiloActual != 0) //Formula primer numero min analizar para cualquier hilo menos el 1º. Min =valorInicial + ((rangoNumero / hilos) * numHilo) + 1
			numerosAnalizar[0] = maxMinNumerosAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual)).add(BigInteger.valueOf(1)));
		else 
			numerosAnalizar[0] = maxMinNumerosAnalizar[0];
		if (hiloActual != numeroTotalHilos - 1) //Formula calcular máximo analizar para cualquier hilo menos el último. Max = valorInicial + ((rangoNumero / numHilos) * (HiloActual + 1))
			numerosAnalizar[1] = maxMinNumerosAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual + 1)));
		else
			numerosAnalizar[1] = maxMinNumerosAnalizar[1];
		return numerosAnalizar;
	}
	
	//Este es el método para imprimir los resultados por pantalla
	public void mostrarNumerosMasAltos(Double tiempoFin) 
	{
		int contador;
		
		contador = 0;
	    System.out.println("Timepo de proceso: " + tiempoFin + " segundos");
	    System.out.println("Secuencias más largas: ");
		for(int i = 0; i < numerosMaximo.size(); ++i) //Imprimo los números de las secuencias más lar
		{
			contador = 1;
			System.out.print(numerosMaximo.get(i) + ",");
			while (numerosMaximo.get(i).compareTo(BigInteger.valueOf(1)) != 0) 
			{
				System.out.print(tablaNumerosComprobados.get(numerosMaximo.get(i)) + ", ");
				numerosMaximo.set(i, tablaNumerosComprobados.get(numerosMaximo.get(i)));
				contador++;
			}
			
		}
		
		System.out.println("\nSu longitud es: " + contador);
		System.out.println("El número más alto alcanzado es: " + numeroMaximoEncontrado);
		System.out.print("Números que no terminan en el número 1: " );
		if (numeroConDistintoBucle == null)
			System.out.println("<ninguno>");
		else 
			System.out.println(numeroConDistintoBucle);
		
	}
	
	public synchronized  boolean comprobarNumeroDeUnoEnUno(int hiloActual)
	{
		Boolean finDelJuego;
		
		finDelJuego = false;
		try 
		{
			if(maxMinNumerosAnalizar[1].compareTo(maxMinNumerosAnalizar[0]) == 0)
			{				
				notifyAll();
				finDelJuego = true;
			}
			else 
			{ 
				if(hiloActual >= numeroTotalHilos - 1 && numeroTotalHilos !=1 )
				{
					notifyAll();
				}
				else 
				{
					comprobarNumero(maxMinNumerosAnalizar[0]);
					maxMinNumerosAnalizar[0] = maxMinNumerosAnalizar[0].add(BigInteger.valueOf(1));
					if (numeroTotalHilos !=1)
						wait();
				}
				
			}
		} catch (Exception e){}
		return finDelJuego;
	}
				
}
