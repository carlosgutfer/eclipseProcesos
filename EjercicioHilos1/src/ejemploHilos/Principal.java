package ejemploHilos;

public class Principal {
	
	public static void main(String [] args)
	{
		Hilo hilo = new Hilo(5835727463775927281L);
		Thread t = new Thread(hilo);
		//t.setName("hilo 1");
		t.start();
		//hilo.run();
		Hilo hilo2 = new Hilo(1139245789900835381l);
		Thread h = new Thread(hilo2);
		//h.setName("hilo 2");
		//h.start();
		h.run();
		
	}
}
