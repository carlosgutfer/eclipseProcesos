package ejercicioNumeroPrimo;

public class datos 
{
	private int 	RaizNumero;
	private int		hilosTotales;
	private boolean	primo;
	private int	numero;

	public datos(int n, int numeroHilos) 
	{
		this.RaizNumero = (int) Math.sqrt(n);
		this.hilosTotales = numeroHilos;
		this.primo = true;
		this.numero = n;
	}
	
	public int [] getNumeros(int numeroHilo) 
	{
		int [] 	numerosComprobar;

		numerosComprobar = new int [2];
		if(hilosTotales != numeroHilo) 
		{
			numerosComprobar[0] = RaizNumero / hilosTotales * numeroHilo;
			numerosComprobar[1] = (RaizNumero / hilosTotales * numeroHilo) + RaizNumero / hilosTotales;
		}
		else
		{
			numerosComprobar[0] = RaizNumero / hilosTotales * numeroHilo;
			numerosComprobar[1] = RaizNumero;
		}
		if(numerosComprobar[0] == 0)
			numerosComprobar[0] = 2;
		return numerosComprobar;
	}
	
	public synchronized void setPrimo() 
	{
		primo = false;
	}
	
	public boolean getPrimo() 
	{
		return primo;
	}
	
	public int getNumero()
	{
		return numero;
	}
}
