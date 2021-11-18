package Ejercicio13;

import java.io.File;

public class Principal 
{
	public  void main(String[] args) 
	{
		File file = new File("17 LEYES DEL TRABAJO EN EQUIPO.txt");
		Datos datos = new Datos("culo", file);
		
		HiloProductor hp = new HiloProductor(datos);
		HiloConsumidor hc[] = new HiloConsumidor[4];
		for (int i = 0; i < hc.length; ++i)
			hc[i] = new HiloConsumidor(datos);
		
		Thread  tp = new Thread(hp);
		tp.start();
		Thread tc[] = new Thread[4];
		for(int i = 0; i < tc.length; i++) 
		{
			tc[i] = new Thread(hc[i]);
			tc[i].start();
		}

		try 
		{
			tp.join();
			for(int i = 0; i < tc.length; ++i)
				tc[i].join();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		datos.imprimirTodas();
	}
}
