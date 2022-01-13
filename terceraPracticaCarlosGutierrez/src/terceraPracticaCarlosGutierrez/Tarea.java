package terceraPracticaCarlosGutierrez;

import java.io.Serializable;

@SuppressWarnings({ "serial", "rawtypes" })
public class Tarea implements Serializable, Comparable
{
	private int numeroCliente;
	private int horas;
	private int identificador;
	
	public Tarea(int tarea, int identificador) 
	{
		super();
		this.horas = tarea;
		this.identificador = identificador;
		this.numeroCliente = -1;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int tarea) {
		this.horas = tarea;
	}

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
	public void setNumeroCliente(int numero) 
	{
		this.numeroCliente = numero;
	}
	public int getNumeroCliente() 
	{
		return numeroCliente;
	}


	/*@Override
	public int compareTo(Object o) 
	{
		int compNumCLiente=((Tarea)o).getNumeroCliente();
        return this.numeroCliente - compNumCLiente;		
	}*/
	
	@Override
	public int compareTo(Object o) 
	{
		int compNumCLiente=((Tarea)o).getIdentificador();
        return this.identificador - compNumCLiente;		
	}

	@Override
	public String toString() 
	{
		return "Numero de empleado: " + numeroCliente+ "\t Horas de la tarea: " + horas + "\t Identificador de tarea: " + identificador + "\n";
	}


}
