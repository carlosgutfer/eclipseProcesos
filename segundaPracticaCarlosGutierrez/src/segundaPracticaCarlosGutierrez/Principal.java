package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.Scanner;

public class Principal 
{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) 
	{
		long 			tiempoInicio;
		Datos			dato;
		int 			numeroHilos;
		Hilos		[] 	h;
		BigInteger 	[] 	rangoComprobar;

		rangoComprobar =  inicializarRangoComprobar();
		numeroHilos = inicializarNumeroHilos(rangoComprobar);
		dato = new Datos(rangoComprobar, numeroHilos);
		h = inicializarHilos(dato, numeroHilos);
		tiempoInicio =  System.currentTimeMillis();
		inicializarYEjeructarThreads(numeroHilos, h, tiempoInicio, dato);
	}

	//Este m�todo recibe el n�mero de hilos que se desean crear 
	private static int inicializarNumeroHilos(BigInteger[] rangoComprobar) 
	{
		int numeroHilos;

		System.out.println("Introduzca el n�mero de Hilos");
		numeroHilos = sc.nextInt();
		//si n� hilos a crear es superior a los n�meros a analizar se devuelen solo el n� hilos necesario
		if (rangoComprobar[1].subtract(rangoComprobar[0]).compareTo(BigInteger.valueOf(numeroHilos)) == -1)
		{
			numeroHilos =Integer.valueOf(String.valueOf(rangoComprobar[1].subtract(rangoComprobar[0])));
			if(numeroHilos == 0)
				numeroHilos++;
		}
		return numeroHilos;
	}

	// Guardo el n�mero minimo y m�ximo en un array para despu�s mand�rselo al constructor dato, si el final es menor que el inicial manda un mensaje al usuario y le pide que meta un rango v�lido
	private static BigInteger[] inicializarRangoComprobar() 
	{
		BigInteger	numeroFinalAnalizar;
		BigInteger	numeroInicialAnalizar;
		

		do
		{	
			System.out.println("Introduzca el n�mero m�s peque�o a analizar (mayor que 2)");
 				numeroInicialAnalizar  = sc.nextBigInteger();
 			System.out.println("Introduzca el n�mero m�s grande a analizar");
 				numeroFinalAnalizar	= sc.nextBigInteger();
		}while(numeroFinalAnalizar.compareTo(numeroInicialAnalizar) == -1 || numeroInicialAnalizar.compareTo(BigInteger.valueOf(2)) == -1);
	
		BigInteger [] rangoComprobarBigInteger = {numeroInicialAnalizar, numeroFinalAnalizar};
		return rangoComprobarBigInteger;
	}

	// Este m�todo se encarga de inicializar los Threads, ejecutarlos y por �ltimo llama al m�todo que impirme los resultados por consola
	private static void inicializarYEjeructarThreads(int numeroHilos, Hilos[] h, long tiempoInicio, Datos dato) 
	{
		Thread	[] 		t;
		Double 			tiempoFin;

		t = new Thread[numeroHilos];
		for(int i = 0; i < t.length; ++i) 
		{
			t[i] = new Thread(h[i]);
			t[i].setName(String.valueOf(i));
			t[i].start();
		}
		try 
		{
			for(int i = 0; i < t.length; ++i)
				t[i].join();
		} catch (Exception e) {}
		tiempoFin = (double) (System.currentTimeMillis() - tiempoInicio) / 1000;
		dato.mostrarNumerosMasAltos(tiempoFin, h[0].getComprobarYaExploradas());
	}

	//Este m�todo pregunta si se desea que los hilos reciban los n�meros de 1 en 1 o si por el contrario prefiere todos al principio y despu�s inicializa los hilos
	private static Hilos[] inicializarHilos(Datos dato, int numeroHilos) 
	{
		Hilos[]	h;
		Boolean recibirDeUnoEnUno, revisarSecuenciasExploradas;
		
		System.out.println("Escriba:\n1 si desea que los hilos pidan los n�meros de 1 o en 1\n2 si desea que el hilo pida el rango a analizar");
		if(sc.nextInt() == 1)
			recibirDeUnoEnUno = true;
		else 
			recibirDeUnoEnUno = false;
		System.out.println("Escriba:\n1 si desea que las secuencias que ya han sido exploradas se vuelvan a revisar\n2 si desea que no se haga");
		if(sc.nextInt() == 1)
			revisarSecuenciasExploradas = true;
		else 
			revisarSecuenciasExploradas = false;
		h = new Hilos[numeroHilos];
		for(int i = 0; i < numeroHilos; ++i)
			h[i] = new Hilos(dato, i, recibirDeUnoEnUno, revisarSecuenciasExploradas);
		sc.close();
		return h;
	}
	
	

}
