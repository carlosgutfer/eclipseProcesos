package ejercicio9;


public class principal 
{
	public static void main(String[] args) 
	{
		Datos 		dato;
		Hilo	[] 	hilos;
		Thread	[] 	threads;
		dato = new Datos(3);
		hilos = new Hilo[3];
		threads = new Thread[hilos.length];
		inicializarHilos(hilos, dato);
		inicializarThreads(hilos, threads);
		crearHilos(threads, dato);
	}

	private static void crearHilos(Thread[] threads, Datos dato) 
	{
		try 
		{
			for(int i = 0; i < threads.length; ++i) 
			{	
				threads[i].setName(String.valueOf(i));
				threads[i].start();
			}
		}catch (Exception e) {}
		try 
		{
			for(int i = 0; i < threads.length; ++i) 
			{	
				threads[i].join();
			}
		} catch (Exception e) {}
		dato.imprimirGanador();

	}

	private static void inicializarHilos(Hilo[] hilos, Datos dato) 
	{
		
		for (int i = 0; i < hilos.length; ++i)
			hilos[i] = new Hilo(dato);
	}
	
	private static void inicializarThreads(Hilo[] hilos, Thread [] threads) 
	{
		for (int i = 0; i < threads.length; ++i)
			threads[i] = new Thread(hilos[i]);
	}
	

}
