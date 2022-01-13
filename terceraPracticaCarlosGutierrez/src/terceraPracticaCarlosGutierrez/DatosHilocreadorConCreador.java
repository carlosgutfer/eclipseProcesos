package terceraPracticaCarlosGutierrez;

public class DatosHilocreadorConCreador 
{

	private Tarea nuevaTarea;
	private Boolean fin;
	
	public DatosHilocreadorConCreador() 
	{
		this.fin = false;
		nuevaTarea = null;
	}
	

	public synchronized void productor(Tarea tarea) 
	{

		while(nuevaTarea != null) 
		{
			try 
			{
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nuevaTarea = tarea;
		notifyAll();	
	}
	
	
	public synchronized Tarea consumidor() 
	{
		while(nuevaTarea == null) 
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Tarea aux = nuevaTarea;
		nuevaTarea = null;
		notifyAll();
		return aux;
	}


	public  boolean getFin() 
	{
		return fin;
	}

	public synchronized void setFin() 
	{
		fin = true;
		nuevaTarea = null;
		notifyAll();
	}
}
