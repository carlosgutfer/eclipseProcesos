package productor_consumidor;

public class HiloProductor implements Runnable {
	
	
	Datos datos;
	
	
	public HiloProductor(Datos datos) {
		super();
		this.datos = datos;
	}


	public void run()
	{
		for(int i = 0; i < 10; ++i) 
		{
			int numero = (int)(Math.random()*100);
			try {
				Thread.sleep((long)(Math.random()*500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Inserto número producido
			datos.producir(numero);
			System.out.println(numero);
		}
	}
 

}
