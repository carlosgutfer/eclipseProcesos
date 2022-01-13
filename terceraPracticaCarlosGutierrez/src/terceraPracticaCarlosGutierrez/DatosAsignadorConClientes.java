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
	
	public boolean fin() 
	{	
		if(fin) 
			escribir_informe_final();
		return fin;
	}

	@SuppressWarnings("unchecked")
	private void escribir_informe_final(){
		Collections.sort(tareasAceptadas);
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
	
	public synchronized void setTareaAceptada(Tarea tarea, String numeroCliente) 
	{
		tarea.setNumeroCliente(Integer.valueOf(numeroCliente));
		tareasAceptadas.add(tarea);
	}

	public  synchronized boolean setTiempoEscedido(String name)
	{
		if(!hilosTiempoEscedido.contains(name)) 
		{
			hilosTiempoEscedido.add(name);
			if(hilosTiempoEscedido.size() >= todosLosHilosActivos) 
			{
				hilosTiempoEscedido.clear();
				return true;
			}
		} 
		return false;
	}

	public synchronized int numerohilosActivos() {return todosLosHilosActivos;}
}
