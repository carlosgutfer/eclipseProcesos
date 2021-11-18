package Ejercicio14;

public class Principal 
{
	public static void main(String[] args) 
	{
		String parking [] = {"L", "L", "L", "L", "L", "L"};
		Datos datos = new Datos(parking);
		
		//HiloProductor hp = new HiloProductor(datos);
		HiloConsumidor hc [] = new HiloConsumidor [10];
		
		for(int i = 0; i < hc.length; ++i)
			hc[i] = new HiloConsumidor(datos);
		
		//Thread thp = new Thread(hp);
		//thp.start();
		
		Thread [] thc = new Thread[10];
		for(int i = 0; i < thc.length; ++i)
		{
			thc[i] = new Thread(hc[i]);
			thc[i].setName(String.valueOf(i + 1));
			thc[i].start();
		}
		
		try {
			//thp.join();
			for(int i = 0; i < thc.length; ++i)
				thc[i].join();
			} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
