package Ejercicio13;

public class HiloConsumidor implements Runnable
{
	private Datos dato;
	
	
	public HiloConsumidor(Datos dato) 
	{
		super();
		this.dato = dato;
	}

	@Override
	public void run() 
	{
		while(!dato.consumir().equals("$--/Fin/--$"));		
	}

}
