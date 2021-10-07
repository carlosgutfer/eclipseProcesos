package principal;

public class hilo implements Runnable {

	@Override
	public void run() {
		hilo2 h2 = new hilo2();
		Thread [] h = new Thread [5];
		for (int i = 0; i < h.length; i++) 
		{
			h[i] = new Thread(h2);
			h[i].setName("hilo");
			h[i].start();
		}
		
		for(int i = 0; i< h.length; ++i)
			try {
				h[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
