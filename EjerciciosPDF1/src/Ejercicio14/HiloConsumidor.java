package Ejercicio14;


public class HiloConsumidor implements Runnable {
	
	private Datos dato;
	
	
	public HiloConsumidor(Datos dato) {
		super();
		this.dato = dato;
	}


	@Override
	public void run() 
	{
		int plaza = 0;
		for(int i = 0; i < 20; ++i) 
		
					
			plaza = dato.consumidor(Thread.currentThread().getName());
			try 
			{
				Thread.sleep((long)(Math.random()*100));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
			}
			dato.productor(plaza);
			try 
			{
				Thread.sleep((long) (Math.random()*100));
			} catch (Exception e) {
				// TODO: handle exception
				}
		}
		
	
	
}
