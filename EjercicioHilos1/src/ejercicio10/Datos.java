package ejercicio10;

public class Datos 
{
	private int contador;
	private int numeroDardos;
	
	public Datos(int numeroDardos) 
	{
		super();
		this.contador = 0;
		this.numeroDardos = numeroDardos;
	}

	public boolean calcularSiCirculo(double x, double y) 
	{
		double		resultado;
		boolean dentro;
		
		dentro = false;
		resultado = Math.sqrt(Math.pow((x - 0.5), 2) + Math.pow((y - 0.5), 2));
		if(resultado <= 0.5)
			dentro = true;
		return dentro;
	}

	public synchronized void aumentarContador() 
	{
		contador++;
	}
	
	public double calculoPi() 
	{
		return  (double) 4 * contador/ numeroDardos;
	}
}
