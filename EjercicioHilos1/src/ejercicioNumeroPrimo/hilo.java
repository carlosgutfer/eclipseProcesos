package ejercicioNumeroPrimo;

public class hilo implements  Runnable 
{
	private datos dato;
	
	public hilo(datos dato) 
	{
		this.dato = dato;
	}
	public void run() 
	{
		int [] numerosComprobar;
		
		numerosComprobar = dato.getNumeros(Integer.parseInt(Thread.currentThread().getName()));
		for (int i = numerosComprobar[0]; i < numerosComprobar[1] ; ++i) 
		{
			if(dato.getNumero() % i == 0)
				dato.setPrimo();
		}
	}


}
