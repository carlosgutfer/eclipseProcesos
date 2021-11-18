package productor_consumidor;

public class Datos {
	
	private int informacion = -1;

	public synchronized void producir(int info) 
	{
		//Suponemos que los valores validos son 
		//mayores o iguales a 0 si no suponemos 
		//que información esta vacia
		while(info < 0) 
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		informacion = info;
		notifyAll();
	}
	
	public synchronized int consumir() 
	{
		while(informacion < 0) 
		{
			try 
			{
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		int aux = informacion;
		informacion = -1;
		notifyAll();
		return aux;
	}
}
