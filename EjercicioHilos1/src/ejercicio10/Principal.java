package ejercicio10;

public class Principal 
{
	
	public static void main(String[] args) 
	{
		Datos datos = new Datos(10);
		Hilo [] h = new Hilo[10];
		for(int i = 0; i < h.length; ++i)
			h[i] = new Hilo(datos);
		Thread [] t = new Thread[10];
		for(int i =0; i < t.length; ++i) 
		{
			t[i] = new Thread(h[i]);
			t[i].start();
		}
		try 
		{
			for(int i = 0; i < t.length; ++i)
				t[i].join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(datos.calculoPi());
	}

}
