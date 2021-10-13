package ejercicio3;

public class datos 
{
	private int numeroVocales = 0;
	private String frase;
	
	public datos(String frase) 
	{
		this.frase = frase;
	}
	public  synchronized void aumentarNumVar() 
	{
		this.numeroVocales++;
	}

	public int getNumeroVocales() {
		return this.numeroVocales;
	}
	public String getFrase() {
		return frase;
	}	

}
