package ejercicio3;

public class hilo implements Runnable{

	private char vocal;
	private datos dato;
	
	public hilo(char vocal, datos dato)
	{
		this.vocal = vocal;
		this.dato = dato;
	}
	@Override
	public void run() 
	{
		char fraseChar[]  = dato.getFrase().toCharArray();
		for(char c: fraseChar) 
		{
			if(c == vocal)
				dato.aumentarNumVar();
		}
	}
}
