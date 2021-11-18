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
	private int											longitudDeLaSecuenciaMasLarga;
	private int											numeroTotalHilos;
	private BigInteger									numeroMaximoEncontrado;
	private BigInteger									numeroConDistintoBucle;
	private BigInteger									uno;//He creado esta variable porque la usaba en bastántes métodos y asi mejoraba un poco la legibilidad

	public Datos(BigInteger[] maxMinNumerosAnalizar, int numeroHilos) 
	{
		super();
		this.numeroMaximoYMinimoParaAnalizar = maxMinNumerosAnalizar;
		this.numeroTotalHilos = numeroHilos;
		this.uno = new BigInteger("1");
		this.numeroMaximoEncontrado = new BigInteger("0");
		this.longitudDeLaSecuenciaMasLarga = 0;
	}

	//Recibe el número que ha comprobado y el contador y lo añade a la tabla, por otro lado si el contador es mayor edita el los números mas largos
	public  synchronized void comprobarSecuenciaMasLargaYAnnadirLongitud(BigInteger numeroComprobar, int contador) 
	{
		añadirLongitud(numeroComprobar, contador);
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
	private void añadirLongitud(BigInteger num, int contador) 
	{
		
		boolean salir;
		salir = true;
		while(num.compareTo(uno) != 0 && salir) 
		{
			tablaNumeroLongitud.put(num, contador);
			contador--;
			num = tablaNumerosComprobados.get(num);
			if(tablaNumeroLongitud.get(num) != null)
				salir = false;
		}
	}

	//Este método devuelve el siguiente número de la secuencia, el cual puede ser el value de una key de los números comprobados, o un número número
	public BigInteger nuevoNumeroDeLaSecuencia(BigInteger num) 
	{
		//Como no está en los números que ya se su longitud llamo al método que lo añade
		if(tablaNumerosComprobados.containsKey(num)) 
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
			resultado = num.multiply(BigInteger.valueOf(3)).add(uno);
		else 
			resultado = num.divide(BigInteger.valueOf(2)); //si es par num = num / 2 
		tablaNumerosComprobados.put(num, resultado);
		return resultado;
	}
	
	//Este método devuelve el rango de números que debe analizar cada hilo en el caso de que no haya que mandárselos de 1 en 1
	public ArrayList<BigInteger> numerosParaAnalizar(int hiloActual) 
	{
		BigInteger [] 			numerosAnalizar = new BigInteger [2];
		BigInteger 				rangoNumeros;
		ArrayList<BigInteger> 	rangoDeNumerosAnalizar = new ArrayList<BigInteger>();
 		
		rangoNumeros = numeroMaximoYMinimoParaAnalizar[1].subtract(numeroMaximoYMinimoParaAnalizar[0]);
		if (hiloActual != 0) //Formula primer numero min analizar para cualquier hilo menos el 1º. Min =valorInicial + ((rangoNumero / hilos) * numHilo) + 1
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual)).add(uno));
		else 
			numerosAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0];
		if (hiloActual != numeroTotalHilos - 1) //Formula calcular máximo analizar para cualquier hilo menos el último. Max = valorInicial + ((rangoNumero / numHilos) * (HiloActual + 1))
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[0].add((rangoNumeros.divide(BigInteger.valueOf(numeroTotalHilos))).multiply(BigInteger.valueOf(hiloActual + 1)));
		else
			numerosAnalizar[1] = numeroMaximoYMinimoParaAnalizar[1];
		
		for(BigInteger i = numerosAnalizar[0]; i.compareTo(numerosAnalizar[1]) < 1; i = i.add(uno)) 
			rangoDeNumerosAnalizar.add(i);
		
		return rangoDeNumerosAnalizar;
	}
	
	//Este es el método para imprimir los resultados por pantalla
	public void mostrarNumerosMasAltos(Double tiempoFin) 
	{
	    System.out.println("Tiempo de proceso: " + tiempoFin + " segundos");
	    System.out.println("Secuencias más largas: ");
	    numerosConLaSecuenciasMasLargas.forEach(num ->
	    {
	    		while(num.compareTo(uno) != 0) 
	    		{
	    			System.out.print(num + " ");
	    			num = tablaNumerosComprobados.get(num);
	    		}
	    		System.out.print(num);
	    });
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
		numeroMaximoYMinimoParaAnalizar[0] = numeroMaximoYMinimoParaAnalizar[0].add(uno);
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
