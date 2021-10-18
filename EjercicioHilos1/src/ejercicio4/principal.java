package ejercicio4;

public class principal 
{
	public static void main(String[] args) 
	{
		Thread t[] = new Thread[2];
		hilo h [] = new hilo [2];
		for(int i = 0; i < t.length; ++i) 
		{	
			h[i] = new hilo();
			t[i] = new Thread(h[i]);
			t[i].setName(String.valueOf(i));
			t[i].start();
		}
		
		try 
		{
			for(int i = 0; i < t.length; ++i)
				t[i].join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
