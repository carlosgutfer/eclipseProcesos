package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;
import java.util.Scanner;

public class Principal 
{

	public static void main(String[] args) 
	{
		long 			tiempoInicio;
		Datos			dato;
		int 			numeroHilos;
		Hilos		[] 	h;
		BigInteger 	[] 	rangoComprobar;

		tiempoInicio =  System.currentTimeMillis();
		rangoComprobar = inicializarRangoComprobar();
		numeroHilos = inicializarNumeroHilos(16, rangoComprobar);
		dato = new Datos(rangoComprobar, numeroHilos);
		h = inicializarHilos(dato, numeroHilos);
		inicializarYEjeructarThreads(numeroHilos, h, tiempoInicio, dato);
	}

	//Este método recibe el número de hilos que se desean crear 
	private static int inicializarNumeroHilos(int numeroHilos, BigInteger[] rangoComprobar) 
	{
		//si nº hilos a crear es superior a los números a analizar se devuelen solo el nº hilos necesario
		if (rangoComprobar[1].subtract(rangoComprobar[0]).compareTo(BigInteger.valueOf(numeroHilos)) == -1)
		{
			numeroHilos =Integer.valueOf(String.valueOf(rangoComprobar[1].subtract(rangoComprobar[0])));
			if(numeroHilos == 0)
				numeroHilos++;
		}
		return numeroHilos;
	}
	
	// Guardo el número minimo y máximo en un array para después mandárselo al constructor dato, si el final es menor que el inicial manda un mensaje al usuario y le pide que meta un rango válido
	private static BigInteger[] inicializarRangoComprobar() 
	{
		BigInteger	numeroFinalAnalizar;
		BigInteger	numeroInicialAnalizar;
		Scanner 	sc;

		
 		numeroInicialAnalizar = new BigInteger("100001");
		numeroFinalAnalizar	= new BigInteger("200001");
		while(numeroFinalAnalizar.compareTo(numeroInicialAnalizar) == -1)
		{
			sc = new Scanner(System.in);
			System.out.println("El número inicial a analizar no puede ser mayor al número final para analizar " + numeroFinalAnalizar + " introduzca un número para inicializar válido");
			numeroInicialAnalizar = new BigInteger(sc.nextLine());
			sc.close();
		}
		BigInteger [] rangoComprobarBigInteger = {numeroInicialAnalizar, numeroFinalAnalizar};
		return rangoComprobarBigInteger;
	}

	private static void inicializarYEjeructarThreads(int numeroHilos, Hilos[] h, long tiempoInicio, Datos dato) 
	{
		Thread	[] 		t;
		Double 			tiempoFin;

		t = new Thread[numeroHilos];
		for(int i = 0; i < t.length; ++i) 
		{
			t[i] = new Thread(h[i]);
			t[i].start();
		}
		try 
		{
			for(int i = 0; i < t.length; ++i)
				t[i].join();
		} catch (Exception e) {}
		tiempoFin = (double) (System.currentTimeMillis() - tiempoInicio) / 1000;
		dato.mostrarNumerosMasAltos(tiempoFin);
	}

	private static Hilos[] inicializarHilos(Datos dato, int numeroHilos) 
	{
		Hilos[]	h;
		Boolean recibirDeUnoEnUno;
		
		recibirDeUnoEnUno = false;
		h = new Hilos[numeroHilos];
		for(int i = 0; i < numeroHilos; ++i)
			h[i] = new Hilos(dato, i, recibirDeUnoEnUno);
		return h;
	}

}
