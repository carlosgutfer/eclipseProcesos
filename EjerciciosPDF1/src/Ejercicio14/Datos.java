package Ejercicio14;


public class Datos 
{
	private String [] parking;
 	private boolean todasOcupadas;
	private boolean fin;
	
	public Datos(String[] parking) {
		super();
		this.parking = parking;
		this.todasOcupadas = false;
		this.fin = false;
	}
	
	public synchronized void productor(int plaza) 
	{
		try 
		{
			while(!todasOcupadas) 
					wait();
		} catch (Exception e) {}
		parking[plaza] = "L";
		todasOcupadas = false;
		notifyAll();
		imprimirParking();

	}
	
	public synchronized int  consumidor(String hilo)
	{
		boolean aux;
		int plaza;
		
		aux = true;
		plaza = 0;
		try 
		{
			while(todasOcupadas)
				wait();	
		} catch (Exception e) {}
		for(int i = 0; i < parking.length && aux; ++i) 
		{
			if(parking[i].equals("L")) 
			{
				parking[i] = hilo;
				aux = false;
				plaza = i;
			}
		}
		todasOcupadas = aux;
		notifyAll();
		return plaza;
	}

	public void setFin() 
	{
		this.fin =  true;
	}

	public boolean getFin() {
		return fin;
	}

	public void imprimirParking() 
	{
		for(int i = 0; i < parking.length; ++i)
			System.out.print(parking[i] + " # ");
		System.out.println();
	}

}
