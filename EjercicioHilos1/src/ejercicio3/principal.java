package ejercicio3;



public class principal 
{
	public static void main(String argString []) 
	{
		datos datos = new datos("hola esto es una prueba de hilos");
		char vocaleString [] = {'a', 'e', 'i', 'o', 'u'};
		hilo[] hilo = inicializarHilos(datos, vocaleString);
		lanzarThreads(hilo, datos);
	}

	private static void lanzarThreads(hilo[] hilo, datos datos) 
	{
		Thread hilosThread [];
		
		hilosThread = new Thread [5];
		for(int i = 0; i < hilosThread.length; ++i) 
		{
			hilosThread[i] = new Thread(hilo[i]);
			hilosThread[i].start();
		}
		try 
		{
			for(int i = 0; i < hilosThread.length; ++i)
				hilosThread[i].join();
		} catch (Exception e) {}
		System.out.println("El valor es " +  datos.getNumeroVocales());
	}

	private static hilo[] inicializarHilos(datos datos, char[] vocaleString) 
	{
		hilo []	hilo;
		
		hilo = new hilo[5];
		for(int i = 0; i < hilo.length; ++i) 
			hilo[i] = new hilo(vocaleString[i], datos);
		return hilo;
	}

}
