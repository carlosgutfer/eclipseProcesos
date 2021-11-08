package segundaPracticaCarlosGutierrez;

import java.math.BigInteger;

public class Hilos implements Runnable 
{
	private Datos dato;
	private int numeroHilo;
	private boolean recibirDeUnoEnUno;

	public Hilos(Datos dato, int numeroHilo, boolean recibirDeUnoEnUno) 
	{
		super();
		this.dato = dato;
		this.numeroHilo = numeroHilo;
		this.recibirDeUnoEnUno = recibirDeUnoEnUno;
	}

	public void run() 
	{
		BigInteger [] numerosAnalizar;

		if(recibirDeUnoEnUno) 
		{
			while(!dato.comprobarNumeroDeUnoEnUno(Integer.parseInt(Thread.currentThread().getName())));
		}
		else
		{
			numerosAnalizar = dato.numerosParaAnalizar(numeroHilo);
			while(numerosAnalizar[0].compareTo(numerosAnalizar[1]) < 1)
			{
				dato.comprobarNumero(numerosAnalizar[0]);
				numerosAnalizar[0] = numerosAnalizar[0].add(BigInteger.valueOf(1));
			}
		}
	}
}


	
	

	

