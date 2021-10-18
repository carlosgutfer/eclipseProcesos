package ejercicio3v2;


public class hilo implements Runnable
{
	private char vocales [];
	private datos dato;
	
	public hilo(datos dato)
	{
		this.dato = dato;
		this.vocales = datos.getVocaleString();
	}
	@Override
	public void run() 
	{
		int n = 0;
		char fraseChar[]  = dato.getFrase(Thread.currentThread().getName()).toCharArray();
		for(char c: fraseChar) 
		{
			for(char v: vocales)
				if(v == c)
					n++;
		}
		dato.aumentarNumVar(n);
	}

}
