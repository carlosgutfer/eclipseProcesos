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

	//Este m�todo comrpueba si el n�mero ya ha sido comprobado o no
	public boolean comprobarNumeroEnTabla(BigInteger num) 
	{
		if(tablaNumerosComprobados.containsKey(num)) 
			return true;
		else
			return false;
	}	

	//Recibe el n�mero que ha comprobado y el contador y lo a�ade a la tabla, por otro lado si el contador es mayor edita el los n�meros mas largos
	public  synchronized void comprobarSecuenciaMasLargayAnnadirLongitud(BigInteger numeroComprobar, int contador) 
	{
		tablaNumeroLongitud.put(numeroComprobar, contador); 
		if (contador > longitudDeLaSecuenciaMasLarga) //Si el contador (longitud del n�mero actual hasa el bucle), es mayor que la secuencia m�s larga limpio el array actual y le doy el nuevo valor a el y a la longitud m�s larga
		{
			numerosConLaSecuenciasMasLargas.clear();
			numerosConLaSecuenciasMasLargas.add(numeroComprobar);
			longitudDeLaSecuenciaMasLarga = contador;
		}
		else if(contador == longitudDeLaSecuenciaMasLarga) 
			numerosConLaSecuenciasMasLargas.add(numeroComprobar);
	}

	//He extraido el metodo que compruebe para evitar que un n�mero pueda pisar a otro al guardarlo creo esta funci�n sincronizada
	public synchronized void comprobarSiEsElNuevoMaximo(BigInteger num) 
	{
		if (numeroMaximoEncontrado.compareTo(num) == -1)//Comprueba si el n�evo numero es el m�s grande analizado nunca
				numeroMaximoEncontrado = num;
	}
	
	//Este proceso se encarga de a�adir al hasmap numero longitud, la longitud de un n�mero hasta el bucle 4 2 1. 
	public void a�adirLongitud(BigInteger num) 
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

	//Este m�todo devuelve el siguiente n�mero de la secuencia, el cual puede ser el value de una key de los n�meros comprobados, o un n�mero n�mero
	public BigInteger nuevoNumeroDeLaSecuencia(BigInteger num) 
	{
		if (comprobarNumeroEnTabla(num)) //este if comprubea si el n�mero ya se ha comprobado en la tabla de secuencias, si est� devuelve el valor que tiene
			num = tablaNumerosComprobados.get(num);
		else 
			num = ecuacionParaNuevoNuemero(num);
		return num;
	}
	
	//Este metodo realiza la ecuaci�n dependiendo de si es par o impar y devuelve el resulado
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
	
	//Este m�todo devuelve el rango de n�meros que debe analizar cada hilo en el caso de que no haya que mand�rselos de 1 en 1
	public BigInteger [] numerosParaAnalizar(int hiloActual) 
	{
		BigInteger [] 	numerosAnalizar = new BigInteger [2];
		BigInteger 		rangoNumeros;
		
		rangoNumeros = numeroMaximoYMinimoParaAnalizar[1].subtract(numeroMaximoYMinimoParaAnalizar[0]);
		if (hiloActual != 0) //Formula primer numero min analizar para cualquier hilo menos el 1�. Min =valorInicial + ((rangoNumero / hilos) * numHilo) + 1
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual)).add(BigInteger.valueOf(1)));
		else 
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0];
		if (hiloActual != numeroTotalHilos - 1) //Formula calcular m�ximo analizar para cualquier hilo menos el �ltimo. Max = valorInicial + ((rangoNumero / numHilos) * (HiloActual + 1))
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual + 1)));
		else
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[1];
		return numerosAnalizar;
	}
	
	//Este es el m�todo para imprimir los resultados por pantalla
	public void mostrarNumerosMasAltos(Double tiempoFin, boolean comprobarYaExplorados) 
	{
	    System.out.println("Tiempo de proceso: " + tiempoFin + " segundos");
	    System.out.println("Secuencias m�s largas: ");
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
		System.out.println("El n�mero m�s alto alcanzado es: " + numeroMaximoEncontrado);
		System.out.print("N�meros que no terminan en el n�mero 1: " );
		if (numeroConDistintoBucle == null)
			System.out.println("<ninguno>");
		else 
			System.out.println(numeroConDistintoBucle);	
	}

	//Este m�todo devuelve el n�mero que te le toca analizar al hilo 
	public synchronized BigInteger getNumeroAnalizar()
	{
		BigInteger numeroAnalizar;
		
		numeroAnalizar = numeroMaximoYMinimoParaAnalizar[0];
		numeroMaximoYMinimoParaAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add(BigInteger.valueOf(1));
		return numeroAnalizar;
	}

	//En este m�todo entra el hilo para saber si ya se han terminado de analizar todos los n�meros del rango en el caso de 1 en 1			
	public  boolean todosAnalizados() 
	{
		if(numeroMaximoYMinimoParaAnalizar[1].compareTo(numeroMaximoYMinimoParaAnalizar[0]) == -1)
			return true;
		else 
			return false;
	}

	// Devuelvo la longitud que hay desde ese n�mero hasta su bucle si se ha calculado ya, si no se ha calculado devuelve -1
	public int comprobarSiSeLongitud(BigInteger n) 
	{
		if (tablaNumeroLongitud.get(n) != null)  //Este primer if comprueba si el n�mero ya est� comprobado en la tabla contador, si est� sale bucle. Con su implementaci�n el programa tarda 1/3 menos de media
			return tablaNumeroLongitud.get(n);
		else 
			return -1;
	}

	// Cambio el valor del n�meroConDistintoBucle
	public void setNumeroConDistintoBucle(BigInteger num) 
	{
		numeroConDistintoBucle = num;
	}
}
