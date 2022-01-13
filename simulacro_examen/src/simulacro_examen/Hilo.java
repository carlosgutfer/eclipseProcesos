package simulacro_examen;

import java.util.ArrayList;

public class Hilo  implements  Runnable {
	
	private Datos datos;
	private ArrayList<String> contrase�asProbadas = new ArrayList<>();	
	private String contrase�a;
	public Hilo(Datos datos) 
	{
		this.datos = datos;
	}
	
	public void run() 
	{
		/*int [] combinacionesProbar = new int [2]; 
		combinacionesProbar = datos.getCombinacionesProbar(Integer.valueOf(Thread.currentThread().getName())); 
		System.out.println(combinacionesProbar[0] + " - "  +combinacionesProbar[1]);
		inicializarClave(combinacionesProbar);
		
		int tipoCombinacion;
		tipoCombinacion = 1; //Combinacion por defecto si el n�mero est� dentro del rango 65 - 90 � 97 - 122
		if(combinacionesProbar[1] == 122)
			tipoCombinacion = 3;
		else if(combinacionesProbar[0] < 91 && combinacionesProbar[1] > 121)
			tipoCombinacion = 2;*/
		contrase�a = datos.getContrase�a();
		while(!datos.getcontrase�aEncontrada()) 
		{
			/*System.out.println(cargarClave());
			nuevaClave(combinacionesProbar);*/
			contrase�aEncontrada(crearContrase�a());
		};
		
		
	}
	
	
	public  void contrase�aEncontrada(String con)
	{	
		if(!contrase�asProbadas.contains(con)) 
		{
			if (contrase�a.equals(con))
			{
				datos.setContrase�aEncontrada();
			
			}
			else 
				contrase�asProbadas.add(contrase�a);	
		}
	}
	
	

	private String crearContrase�a() 
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

}
