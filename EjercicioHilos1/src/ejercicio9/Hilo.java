package ejercicio9;

public class Hilo implements Runnable{
	
	Datos dato;
	
	public Hilo(Datos dato) 
	{
		this.dato = dato;
	}
	@Override
	public void run() 
	{
		do 
		{
			System.out.println(Thread.currentThread().getName() + " " +dato.getDistancia(Integer.valueOf(Thread.currentThread().getName())));
		}while(!dato.movimientoAnimal(dato.getDistancia(Integer.valueOf(Thread.currentThread().getName()))));
		
	}
	

}
