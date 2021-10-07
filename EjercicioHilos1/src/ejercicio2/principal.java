package ejercicio2;

public class principal {

	public static void main(String [] args) 
	{
		datos dato = new datos();
		hilo h = new hilo(dato);
		Thread t[] = new Thread [100000];
		for (int i = 0; i < t.length; ++i) 
		{
			t[i] = new Thread(h);
			t[i].start();
		}
		for(int i = 0; i <t.length; ++i) 
		{
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("El valor es " +  dato.getVariable());
	}
}
