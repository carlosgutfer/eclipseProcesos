package terceraPracticaCarlosGutierrez;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DatosAsignadorConClientes 
{
	private Tarea nuevaTarea;
	private Boolean fin;
	private int todosLosHilosActivos;
	private ArrayList<Tarea> tareasAceptadas =   new ArrayList<>();	
	private ArrayList<String> hilosTiempoEscedido = new ArrayList<>();
	public DatosAsignadorConClientes(int todosLosHilosActivos) 
	{
		this.nuevaTarea = null;
		this.fin = false;
		this.todosLosHilosActivos = todosLosHilosActivos;
	}
	
	//M�todo que consume una tarea y cambia su valor a null 
	public synchronized Tarea consumidor() 
	{
		while(nuevaTarea == null) 
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		Tarea aux = nuevaTarea;
		nuevaTarea = null;
		notifyAll();
		return aux;
	}
	
	//M�todo que modifica el valor de tareas
	public synchronized void productor(Tarea tarea) 
	{
		while(nuevaTarea != null) 
		{
			try 
			{
				wait();
			} catch (InterruptedException e)
			{e.printStackTrace();}
		}
		nuevaTarea = tarea;
		notifyAll();
	}
	//va eliminando hilos cada vez que se mueren, si se han cerrado todos modifica la varable fin a true
	public synchronized void eliminarHilo() 
	{
		todosLosHilosActivos--;
		if(todosLosHilosActivos == 0) 
		{
			fin = true;
			notifyAll();
			nuevaTarea = null;
		}
	}
	//m�todo qeu devuelve si se ha terminado o no todos los hilos, si se ha terminado escribe el fichero
	public boolean fin() 
	{	
		if(fin) 
			escribir_informe_final();
		return fin;
	}

	//Escribe el informe final en el txt, para el sort de un objeto tuve que crear un m�todo especial en el objeto e implementar en el comparable
	@SuppressWarnings("unchecked")
	private void escribir_informe_final()
	{
		Collections.sort(tareasAceptadas);//ordeno por n�mero de cliente ascendente
		File f1 = new File("informe.txt");
		try 
		{
			FileWriter  fw = new FileWriter(f1);
			tareasAceptadas.forEach(t -> 
			{
				try 
				{
					fw.write(t.toString());
				}catch (IOException e) {
					e.printStackTrace();
				}
			});
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Guarda la tarea acpetada para despu�s poder imprimir todas, priemero modifica el n�mero de cliente en el objeto por el que la a aceptado
	public synchronized void setTareaAceptada(Tarea tarea, String numeroCliente) 
	{
		tarea.setNumeroCliente(Integer.valueOf(numeroCliente));
		tareasAceptadas.add(tarea);
	}

	//Este m�todo guarda los hilos que responden que han excedido su tiempo con la tarea enviada, si solo queda un hilo activo,
	//no guarda nada y devuelve true ya que el que llegue ser� el �nico posible.
	//Si quedan m�s y este no este no esta en el array lo guarda y comprueba si ya est�n todos los hilos o no, si est� lo limpia y devuelve true
	//En cualqueir otro caso devolver� false
	public  synchronized boolean setTiempoExcedido(String name)
	{
		if(todosLosHilosActivos == 1)
			return true;
		if(!hilosTiempoEscedido.contains(name)) 
		{
			hilosTiempoEscedido.add(name);
			if(hilosTiempoEscedido.size() == todosLosHilosActivos) 
			{
				hilosTiempoEscedido.clear();
				return true;
			}
		} 
		return false;
	}
	
	
}
