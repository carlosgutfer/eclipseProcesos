package ejercicio9;

import java.util.ArrayList;

public class Datos 
{
	private int distancia [] ;
	private int numeroHilos;
	private int contador;
	private int longitudCarrera = 30;
	private int[] numeroIteracciones;

	public Datos(int numeroHilos) 
	{
		super();
		this.distancia = new int [numeroHilos];
		this.numeroHilos = numeroHilos;
		for(int i = 0; i < distancia.length; ++i)
			distancia[i] = 0;
		this.numeroIteracciones = new int [numeroHilos];
		for(int i = 0; i < numeroIteracciones.length; ++i)
			distancia[i] = 0;
		this.contador = 0;
	}
	
	public synchronized boolean  movimientoAnimal(int posicion) 
	{	
		boolean ganador;
		
		ganador = false;
		if (algunGanador()) 
		{	
			igualarTurnos();
			ganador = true;
			notifyAll();
		} 
		else
		{
			try 
			{	
				distancia[++contador] += (int)(Math.random() * 5);
				numeroIteracciones[contador]++;
				if (contador + 1 >= numeroHilos) 
				{
					notifyAll();
					contador = -1;
				}else 
					wait();			
			}
			catch (Exception e){}
		}
		return ganador;
	}

	private boolean algunGanador() 
	{
		boolean ganador;
		
		ganador = false;
		for (int i = 0; i < numeroHilos && !ganador; ++i)
			if(distancia[i] >= longitudCarrera)
				ganador = true;
		return ganador;
	}

	public int getDistancia(int hilo) 
	{
		return distancia[hilo];
	}
	
	private void igualarTurnos() 
	{
		for(int i = 0; i < numeroIteracciones.length; ++i) 
		{
			for (int j = i + 1; j < numeroIteracciones.length; ++j) 
			{
					if(numeroIteracciones[i] > numeroIteracciones[j]) 
					{
						distancia[j] += (int)(Math.random() * 5);
						numeroIteracciones[j]++;
					}else if(numeroIteracciones[i] < numeroIteracciones[j]) 
					{
						distancia[i] += (int)(Math.random() * 5);
						numeroIteracciones[i]++;
					}
			}
		}
	}

	
	public void imprimirGanador() 
	{
		ArrayList<Integer> ganadores = new ArrayList<>();
		ganadores.add(distancia[0]);
		for(int i = 1; i < distancia.length; ++i)
					
	}

}
