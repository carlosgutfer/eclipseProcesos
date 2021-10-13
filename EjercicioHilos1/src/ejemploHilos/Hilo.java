package ejemploHilos;

public class Hilo implements Runnable 
{
	//System.out.println("SOy el hilo " +Thread.currentThread().getName() +" y voy por: " + i);
	
	private long numero;
	
	public Hilo(Long numero) 
	{
		this.numero = numero;
	}
	@Override
	public void run() 
	{
		double raiz = Math.sqrt(numero);
		boolean primo = true;
		for (int i = 2; i <= raiz && primo; ++i)
			if (numero % 1 == 0)
				primo = false;
		if (primo) 
			System.out.println("El número es primo " + numero);
		else 
			System.out.println("El número no es primo " + numero);
	}
	
}
