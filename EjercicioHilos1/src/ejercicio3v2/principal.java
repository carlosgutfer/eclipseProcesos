package ejercicio3v2;

import java.io.File;



public class principal 
{
	public    void main(String argString []) 
	{
		File f1 = new File("textoEntrada.txt");
		datos datos = new datos(f1, 5);
		hilo[] hilo = inicializarHilos(datos);
		lanzarThreads(hilo, datos);
	}

	
	private static void lanzarThreads(hilo[] hilo, datos datos) 
	{
		Thread hilosThread [];
		
		hilosThread = new Thread [5];
		for(int i = 0; i < hilosThread.length; ++i) 
		{
			hilosThread[i] = new Thread(hilo[i]);
			hilosThread[i].setName(String.valueOf(i));
			hilosThread[i].start();
		}
		try 
		{
			for(int i = 0; i < hilosThread.length; ++i)
				hilosThread[i].join();
		} catch (Exception e) {}
		System.out.println("El valor es " +  datos.getNumeroVocales());
	}

	private static hilo[] inicializarHilos(datos datos) 
	{
		hilo []	hilo;
		
		hilo = new hilo[5];
		for(int i = 0; i < hilo.length; ++i) 
			hilo[i] = new hilo(datos);
		return hilo;
	}


}
