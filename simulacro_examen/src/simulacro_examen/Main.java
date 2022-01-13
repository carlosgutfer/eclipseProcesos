package simulacro_examen;


public class Main 
{
	public static void main(String[] args) 
	{
		int numeroHilos;
		Datos datos;
		Hilo hilos [];
		
		numeroHilos = (int)(Math.random() * 10 - 1) + 1;
		datos = new Datos();
		hilos = inicializarHilos(16, datos);
		inicializarYEjecutarThread(16, hilos);
		System.out.println(datos.getHiloGanador());

	}

	private static void inicializarYEjecutarThread(int numeroHilos, Hilo[] hilos) 
	{
		Thread[] thread;
		
		thread = new Thread[numeroHilos];
		for(int i = 0; i < thread.length; ++i) 
		{
			thread[i] = new Thread(hilos[i]);
			thread[i].setName(String.valueOf(i));
			thread[i].start();
		}
		try {
			for(int i = 0; i < thread.length; ++i)
				thread[i].join();
		} catch (Exception e) {}
	}

	private static Hilo[] inicializarHilos(int numeroHilos, Datos datos) {
		Hilo[] hilos;
		hilos = new Hilo[numeroHilos];
		for(int i = 0; i < hilos.length; ++i) 
			hilos[i] = new Hilo(datos);
		return hilos;
	}
	
	
}
