package simulacro_examen;

import java.util.ArrayList;

public class Hilo  implements  Runnable {
	
	private Datos datos;
	private ArrayList<String> contraseñasProbadas = new ArrayList<>();	
	private String contraseña;
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
		tipoCombinacion = 1; //Combinacion por defecto si el número está dentro del rango 65 - 90 ó 97 - 122
		if(combinacionesProbar[1] == 122)
			tipoCombinacion = 3;
		else if(combinacionesProbar[0] < 91 && combinacionesProbar[1] > 121)
			tipoCombinacion = 2;*/
		contraseña = datos.getContraseña();
		while(!datos.getcontraseñaEncontrada()) 
		{
			/*System.out.println(cargarClave());
			nuevaClave(combinacionesProbar);*/
			contraseñaEncontrada(crearContraseña());
		};
		
		
	}
	
	
	public  void contraseñaEncontrada(String con)
	{	
		if(!contraseñasProbadas.contains(con)) 
		{
			if (contraseña.equals(con))
			{
				datos.setContraseñaEncontrada();
			
			}
			else 
				contraseñasProbadas.add(contraseña);	
		}
	}
	
	

	private String crearContraseña() 
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
