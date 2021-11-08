package ejercicio10;

public class Hilo implements Runnable
{
	
	private Datos 	dato;
	
	public Hilo(Datos dato) 
	{
		this.dato = dato;
	}
	
	public void run()
	{
		double x, y;
		
		x = crearCoordenada();
		y = crearCoordenada();
		if(dato.calcularSiCirculo(x, y))
			dato.aumentarContador();	
	}

	private double crearCoordenada() 
	{
		return  Math.random();
	}
}
