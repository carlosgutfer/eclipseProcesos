package ejercicio2;

public class datos {

	private int variable = 0;
	
	public synchronized void aumentarVariable() 
	{
		++variable;
	}
	public int getVariable() {
		return this.variable;
	}
}
