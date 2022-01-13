package simulacro_examen;

import java.util.ArrayList;

public class Datos 
{
	private String contrase�a;
	private ArrayList<String> contrase�asProbadas = new ArrayList<>();
	private boolean contrase�aEncontrada;
	private String numeroHIloString;
	
	public Datos() 
	{
		super();
		this.contrase�a = crearContrase�a();
		this.contrase�aEncontrada = false;
	}
	

	public synchronized boolean getcontrase�aEncontrada() 
	{
		return contrase�aEncontrada;
	}
	
	public  void setContrase�aEncontrada() 
	{
		this.contrase�aEncontrada = true;
	}
	
	public String getContrase�a() 
	{
		return contrase�a;
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
	
	
	public  static String crearContrase�a() 
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
		return numeroHIloString + " " + contrase�a;
	}
}
