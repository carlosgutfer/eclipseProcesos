package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Datos 
{
	private ConcurrentHashMap<BigInteger,BigInteger>  	tablaNumerosComprobados = new ConcurrentHashMap<>();
	private ConcurrentHashMap<BigInteger, Integer> 		tablaNumeroLongitud = new ConcurrentHashMap<>();
	private BigInteger [] 								numeroMaximoYMinimoParaAnalizar;
	private ArrayList<BigInteger> 						numerosConLaSecuenciasMasLargas = new ArrayList<>();;
	private int											longitudDeLaSecuenciaMasLarga = 0;
	private int											numeroTotalHilos;
	private BigInteger									numeroMaximoEncontrado = new BigInteger("0");
	private BigInteger									numeroConDistintoBucle;

	public Datos(BigInteger[] maxMinNumerosAnalizar, int numeroHilos) 
	{
		super();
		this.numeroMaximoYMinimoParaAnalizar = maxMinNumerosAnalizar;
		this.numeroTotalHilos = numeroHilos;
	}

	//Este método comrpueba si el número ya ha sido comprobado o no
	public boolean comprobarNumeroEnTabla(BigInteger num) 
	{
		if(tablaNumerosComprobados.containsKey(num)) 
			return true;
		else
			return false;
	}	

	//Recibe el número que ha comprobado y el contador y lo añade a la tabla, por otro lado si el contador es mayor edita el los números mas largos
	public  synchronized void comprobarSecuenciaMasLargayAnnadirLongitud(BigInteger numeroComprobar, int contador) 
	{
		tablaNumeroLongitud.put(numeroComprobar, contador); 
		if (contador > longitudDeLaSecuenciaMasLarga) //Si el contador (longitud del número actual hasa el bucle), es mayor que la secuencia más larga limpio el array actual y le doy el nuevo valor a el y a la longitud más larga
		{
			numerosConLaSecuenciasMasLargas.clear();
			numerosConLaSecuenciasMasLargas.add(numeroComprobar);
			longitudDeLaSecuenciaMasLarga = contador;
		}
		else if(contador == longitudDeLaSecuenciaMasLarga) 
			numerosConLaSecuenciasMasLargas.add(numeroComprobar);
	}

	//He extraido el metodo que compruebe para evitar que un número pueda pisar a otro al guardarlo creo esta función sincronizada
	public synchronized void comprobarSiEsElNuevoMaximo(BigInteger num) 
	{
		if (numeroMaximoEncontrado.compareTo(num) == -1)//Comprueba si el núevo numero es el más grande analizado nunca
				numeroMaximoEncontrado = num;
	}
	
	//Este proceso se encarga de añadir al hasmap numero longitud, la longitud de un número hasta el bucle 4 2 1. 
	public void añadirLongitud(BigInteger num) 
	{
		int 			contador;
		BigInteger      key;

		contador  = 0;
		key = num;
		while(!tablaNumeroLongitud.containsKey(num) && num.compareTo(BigInteger.valueOf(1)) != 0)
		{
			num = ecuacionParaNuevoNuemero(num);
			contador++;		
		}
		try 
		{
			contador += tablaNumeroLongitud.get(num);
		}catch (Exception e) {}
		tablaNumeroLongitud.put(key, contador);		
	}

	//Este método devuelve el siguiente número de la secuencia, el cual puede ser el value de una key de los números comprobados, o un número número
	public BigInteger nuevoNumeroDeLaSecuencia(BigInteger num) 
	{
		if (comprobarNumeroEnTabla(num)) //este if comprubea si el número ya se ha comprobado en la tabla de secuencias, si está devuelve el valor que tiene
			num = tablaNumerosComprobados.get(num);
		else 
			num = ecuacionParaNuevoNuemero(num);
		return num;
	}
	
	//Este metodo realiza la ecuación dependiendo de si es par o impar y devuelve el resulado
	private BigInteger ecuacionParaNuevoNuemero(BigInteger num) 
	{
		BigInteger[] auxiliarBigIntegers;
		BigInteger   resultado;
		auxiliarBigIntegers = num.divideAndRemainder(BigInteger.valueOf(2)); //calcula el resto de dividir el numero entre 2
		if (auxiliarBigIntegers[1] != BigInteger.valueOf(0)) // si es impar num = num * 3 + 1
			resultado = num.multiply(BigInteger.valueOf(3)).add(BigInteger.valueOf(1));
		else 
			resultado = num.divide(BigInteger.valueOf(2)); //si es par num = num / 2 
		tablaNumerosComprobados.put(num, resultado);
		return resultado;
	}
	
	//Este método devuelve el rango de números que debe analizar cada hilo en el caso de que no haya que mandárselos de 1 en 1
	public BigInteger [] numerosParaAnalizar(int hiloActual) 
	{
		BigInteger [] 	numerosAnalizar = new BigInteger [2];
		BigInteger 		rangoNumeros;
		
		rangoNumeros = numeroMaximoYMinimoParaAnalizar[1].subtract(numeroMaximoYMinimoParaAnalizar[0]);
		if (hiloActual != 0) //Formula primer numero min analizar para cualquier hilo menos el 1º. Min =valorInicial + ((rangoNumero / hilos) * numHilo) + 1
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual)).add(BigInteger.valueOf(1)));
		else 
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0];
		if (hiloActual != numeroTotalHilos - 1) //Formula calcular máximo analizar para cualquier hilo menos el último. Max = valorInicial + ((rangoNumero / numHilos) * (HiloActual + 1))
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual + 1)));
		else
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[1];
		return numerosAnalizar;
	}
	
	//Este es el método para imprimir los resultados por pantalla
	public void mostrarNumerosMasAltos(Double tiempoFin, boolean comprobarYaExplorados) 
	{
	    System.out.println("Tiempo de proceso: " + tiempoFin + " segundos");
	    System.out.println("Secuencias más largas: ");
	    numerosConLaSecuenciasMasLargas.forEach(num ->
	    {
	    		while(num.compareTo(BigInteger.valueOf(1)) != 0) 
	    		{
	    			System.out.print(num + " ");
	    			num = tablaNumerosComprobados.get(num);
	    		}
	    		System.out.print(num);
	    });
	    if (!comprobarYaExplorados)
	    	longitudDeLaSecuenciaMasLarga += 1;
	    System.out.println("\nSu longitud es: " + longitudDeLaSecuenciaMasLarga);
		System.out.println("El número más alto alcanzado es: " + numeroMaximoEncontrado);
		System.out.print("Números que no terminan en el número 1: " );
		if (numeroConDistintoBucle == null)
			System.out.println("<ninguno>");
		else 
			System.out.println(numeroConDistintoBucle);	
	}

	//Este método devuelve el número que te le toca analizar al hilo 
	public synchronized BigInteger getNumeroAnalizar()
	{
		BigInteger numeroAnalizar;
		
		numeroAnalizar = numeroMaximoYMinimoParaAnalizar[0];
		numeroMaximoYMinimoParaAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add(BigInteger.valueOf(1));
		return numeroAnalizar;
	}

	//En este método entra el hilo para saber si ya se han terminado de analizar todos los números del rango en el caso de 1 en 1			
	public  boolean todosAnalizados() 
	{
		if(numeroMaximoYMinimoParaAnalizar[1].compareTo(numeroMaximoYMinimoParaAnalizar[0]) == -1)
			return true;
		else 
			return false;
	}

	// Devuelvo la longitud que hay desde ese número hasta su bucle si se ha calculado ya, si no se ha calculado devuelve -1
	public int comprobarSiSeLongitud(BigInteger n) 
	{
		if (tablaNumeroLongitud.get(n) != null)  //Este primer if comprueba si el número ya está comprobado en la tabla contador, si está sale bucle. Con su implementación el programa tarda 1/3 menos de media
			return tablaNumeroLongitud.get(n);
		else 
			return -1;
	}

	// Cambio el valor del númeroConDistintoBucle
	public void setNumeroConDistintoBucle(BigInteger num) 
	{
		numeroConDistintoBucle = num;
	}
}
