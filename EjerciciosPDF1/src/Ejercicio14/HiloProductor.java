package Ejercicio14;


public class HiloProductor implements Runnable
{
	private Datos dato;
	
	
	public HiloProductor(Datos dato) {
		super();
		this.dato = dato;
	}


	@Override
	public void run() 
	{		
		
		dato.setFin();
		
	}
	
}
