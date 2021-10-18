package ejercicioNumeroPrimo;


public class principal 
{
	public static void main(String[] args) 
	{
		datos	dato;
		
		dato = new datos(977, 5);
		hilo [] h = new hilo[5];
		for(int i = 0; i < h.length; ++i) 
			h[i] = new hilo(dato);
		Thread [] t = new Thread [5];
		for(int i = 0; i < t.length; ++i) 
		{
			t[i] = new Thread(h[i]);
			t[i].setName(String.valueOf(i + 1));
			t[i].start();
		}
		
		try 
		{
		for(int i = 0; i < t.length; ++i)
			t[i].join();	
		}catch (Exception e){}
		String mensaje;
		if(dato.getPrimo())
			mensaje = " es primo";
		else
			mensaje = "no es primo";
		System.out.println("El número " + dato.getNumero() + " " + mensaje);
	}
}
