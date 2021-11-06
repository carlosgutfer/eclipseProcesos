package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Datos 
{
	private HashMap<BigInteger,BigInteger>  numerosComprobados = new HashMap<>();
	private HashMap<BigInteger, Integer> 	numeroContador = new HashMap<>();
	private BigInteger [] 					maxMinNumerosAnalizar;
	private ArrayList<BigInteger> 			numerosMaximo;
	private int								secuenciaMasLarga;
	private int								numeroTotalHilos;
	private int								hiloActual;
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
		this.hiloActual = 0;
	}
	
	
	public boolean comprobarNumeroEnTabla(BigInteger num) 
	{
		if(numerosComprobados.containsKey(num)) 
			return true;
		else
			return false;
	}
	
	public synchronized void comprobarNumero(BigInteger numeroComprobar) 
	{
		
		BigInteger [] 			auxiliarBigIntegers = new BigInteger[2];
		ArrayList <BigInteger> 	numerosDeLaSecuencia = new ArrayList<>();
		BigInteger 				keyMap;
		BigInteger				num;
		Boolean 				nuevoBucle;
		int						contador;

		contador = 0;
		nuevoBucle = false;
		num = numeroComprobar;
		if (numeroComprobar.compareTo(BigInteger.valueOf(77031)) == 0)
			System.out.println();
		
		while(!num.equals(BigInteger.valueOf(1)) && !nuevoBucle) 
		{	
			contador ++;
			if (numeroContador.containsKey(num)) 
			{
				contador += numeroContador.get(num);
				nuevoBucle = true;
			}
			else 
			{
				if (comprobarNumeroEnTabla(num))
				{
					num = numerosComprobados.get(num);
				}
				else 
				{
					keyMap = num; //guardo el número que voy a añadir como key en el keymap
					auxiliarBigIntegers = num.divideAndRemainder(BigInteger.valueOf(2)); //calcula el resto de dividir el numero entre 2
					if (auxiliarBigIntegers[1] != BigInteger.valueOf(0)) // si es impar num = num * 3 + 1
						num = num.multiply(BigInteger.valueOf(3)).add(BigInteger.valueOf(1));
					else 
						num = num.divide(BigInteger.valueOf(2)); //si es par num = num / 2 
					numerosComprobados.put(keyMap, num);
				}
				if(numerosDeLaSecuencia.indexOf(num) == -1) //si el número no está en la cadena lo guarda
					numerosDeLaSecuencia.add(num);
				else // si está como aun no hemos llegado al 1, sale del bucle y lo guarda (número que rompe la hipótesis) 
				{
					nuevoBucle = true;
					numeroConDistintoBucle = num;			
				}
			}
		}
		numeroContador.put(numeroComprobar, contador);
		if (contador > secuenciaMasLarga) 
		{
			numerosMaximo.clear();
			numerosMaximo.add(numeroComprobar);
			secuenciaMasLarga = contador;
		}
		else if(contador == secuenciaMasLarga) 
			numerosMaximo.add(numeroComprobar);
	}
	
	public BigInteger [] numerosParaAnalizar(int hiloActual) 
	{
		BigInteger [] numerosAnalizar = new BigInteger [2];
		BigInteger rangoNumeros = maxMinNumerosAnalizar[1].subtract(maxMinNumerosAnalizar[0]);
		
		if(hiloActual != 0) //Formula primer numero min analizar para cualquier hilo menos el 1º. Min =valorInicial + ((rangoNumero / hilos) * numHilo) + 1
			numerosAnalizar[0] = maxMinNumerosAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual)).add(BigInteger.valueOf(1)));
		else 
			numerosAnalizar[0] = maxMinNumerosAnalizar[0];
		if(hiloActual != numeroTotalHilos - 1) //Formula calcular máximo analizar para cualquier hilo menos el último. Max = valorInicial + ((rangoNumero / numHilos) * (HiloActual + 1))
			numerosAnalizar[1] = maxMinNumerosAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual + 1)));
		else
			numerosAnalizar[1] = maxMinNumerosAnalizar[1];
		return numerosAnalizar;
	}
	
	public void mostrarNumerosMasAltos(Double tiempoFin) 
	{
		int contador;
		
		contador = 0;
	    System.out.println("Timepo de proceso: " + tiempoFin + " segundos");
	    System.out.println("Secuencias más largas: ");
		for(int i = 0; i < numerosMaximo.size(); ++i) 
		{
			contador = 0;
			System.out.print(numerosMaximo.get(i) + ",");
			while (numerosMaximo.get(i).compareTo(BigInteger.valueOf(1)) != 0) 
			{
				System.out.print(numerosComprobados.get(numerosMaximo.get(i)) + ", ");
				numerosMaximo.set(i, numerosComprobados.get(numerosMaximo.get(i)));
				if (numeroMaximoEncontrado.compareTo(numerosMaximo.get(i)) == -1)
					numeroMaximoEncontrado = numerosMaximo.get(i);
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
	
	public synchronized  boolean comprobarNumeroDeUnoEnUno()
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
					hiloActual = 0;
				}
				else 
				{
					hiloActual++;
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
