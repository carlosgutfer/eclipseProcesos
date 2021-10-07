package ejercicio2;

public class hilo implements Runnable {

	private datos dato; 
	public hilo(datos datos) 
	{
		this.dato = datos;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 10; i++) 
		{
			dato.aumentarVariable();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	}

}
