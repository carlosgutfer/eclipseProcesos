package Ejercicio13;


public class HiloProductor implements Runnable 
{
	private Datos datos;
	private String [] texto;

	public HiloProductor(Datos datos) 
	{
		super();
		this.datos = datos;
	}

	@Override
	public void run() 
	{
		texto = (datos.getTextoFromFile()).split(" ");
		for(int i = 0; i < texto.length; ++i) 
		{
			datos.producir(texto[i]);
		}
		datos.producir("$--/Fin/--$");
	}

}
