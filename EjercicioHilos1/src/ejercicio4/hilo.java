package ejercicio4;

public class hilo implements Runnable 
{

	@Override
	public void run() 
	{
		for(int i = 0; i < 5; ++i) 
		{
			System.out.println("Soy la iteracción " + i + " del proceso " + Thread.currentThread().getName());
		}
	}

}
