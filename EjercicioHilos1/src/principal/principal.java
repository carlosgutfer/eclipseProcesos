package principal;


public class principal {

public static void main(String [] args) 
{	
	hilo hilo = new hilo();
	Thread t = new Thread(hilo);
	//t.setName("hilo 1");
	t.start();
	
	try {
		t.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("fin");
}
}
