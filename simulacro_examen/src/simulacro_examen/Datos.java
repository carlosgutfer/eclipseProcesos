package simulacro_examen;

import java.util.ArrayList;

public class Datos 
{
	private String contraseña;
	private ArrayList<String> contraseñasProbadas = new ArrayList<>();
	private boolean contraseñaEncontrada;
	private String numeroHIloString;
	
	public Datos() 
	{
		super();
		this.contraseña = crearContraseña();
		this.contraseñaEncontrada = false;
	}
	

	public synchronized boolean getcontraseñaEncontrada() 
	{
		return contraseñaEncontrada;
	}
	
	public  void setContraseñaEncontrada() 
	{
		this.contraseñaEncontrada = true;
	}
	
	public String getContraseña() 
	{
		return contraseña;
	}
	/*public int [] getCombinacionesProbar(int numeroHilo) 
	{
		int [] numerosParaProbar = new int[2];
		int divionNumerosPorHilo, aux;
		int A;
		
		A = 65;
		divionNumerosPorHilo = 50 / numeroTotalHilos;
		
		if(numeroHilo == 0) 
		{
			numerosParaProbar[0] = 65;
		}
			else 
		{
			 aux = numeroHilo * divionNumerosPorHilo;
			 if(A + aux < 91) 
			 {
				 numerosParaProbar[0] = A + aux;
			 }else 
			 {
				 numerosParaProbar[0] = ((A + aux) - 90) + 97;
			 }
		}
		
		if(numeroHilo == numeroTotalHilos - 1)
			numerosParaProbar[1] = 122;
		else 
		{
			aux = (numeroHilo + 1) * divionNumerosPorHilo;
			 if(A + aux < 91) 
			 {
				 numerosParaProbar[1] = A + aux;
			 }else 
			 {
				 numerosParaProbar[1] = ((A + aux) - 90) + 97;
			 }
		}
		return numerosParaProbar;
	}*/
	
	
	public  static String crearContraseña() 
	{
		String clave;
		int caracter;
		
		clave = "";
		while(clave.length() != 4) 
		{
			caracter = (int)((Math.random() * 241 - 97) + 97);
			if((caracter > 96 && caracter < 123) || (caracter > 64 && caracter < 91) || caracter == 241 || caracter == 209)
				clave += (char) caracter;
		}
		return clave;
	}

	public String getHiloGanador() {
		return numeroHIloString + " " + contraseña;
	}
}
