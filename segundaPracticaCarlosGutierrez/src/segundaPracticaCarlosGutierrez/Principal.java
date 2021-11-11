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

	//Este método recibe el número de hilos que se desean crear 
	private static int inicializarNumeroHilos(BigInteger[] rangoComprobar) 
	{
		int numeroHilos;

		System.out.println("Introduzca el número de Hilos");
		numeroHilos = sc.nextInt();
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
		

		do
		{	
			System.out.println("Introduzca el número más pequeño a analizar (mayor que 2)");
 				numeroInicialAnalizar  = sc.nextBigInteger();
 			System.out.println("Introduzca el número más grande a analizar");
 				numeroFinalAnalizar	= sc.nextBigInteger();
		}while(numeroFinalAnalizar.compareTo(numeroInicialAnalizar) == -1 || numeroInicialAnalizar.compareTo(BigInteger.valueOf(2)) == -1);
	
		BigInteger [] rangoComprobarBigInteger = {numeroInicialAnalizar, numeroFinalAnalizar};
		return rangoComprobarBigInteger;
	}

	// Este método se encarga de inicializar los Threads, ejecutarlos y por último llama al método que impirme los resultados por consola
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

	//Este método pregunta si se desea que los hilos reciban los números de 1 en 1 o si por el contrario prefiere todos al principio y después inicializa los hilos
	private static Hilos[] inicializarHilos(Datos dato, int numeroHilos) 
	{
		Hilos[]	h;
		Boolean recibirDeUnoEnUno, revisarSecuenciasExploradas;
		
		System.out.println("Escriba:\n1 si desea que los hilos pidan los números de 1 o en 1\n2 si desea que el hilo pida el rango a analizar");
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
