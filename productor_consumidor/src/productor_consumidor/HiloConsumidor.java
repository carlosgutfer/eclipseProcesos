package productor_consumidor;

public class HiloConsumidor implements Runnable{

	private Datos datos;
	
	
	public HiloConsumidor(Datos datos) {
		super();
		this.datos = datos;
	}


	@Override
	public void run() 
	{
		for(int i = 0; i < 10; ++i) 
		{
			int info = datos.consumir();
			try {
				Thread.sleep((long)(Math.random()*500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datos.consumir();
			System.out.println("consumo " + info);
		}
		
	}

}
