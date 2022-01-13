package terceraPracticaCarlosGutierrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCliente implements Runnable {

	private Socket socket;
	private String recibirMensje;
	private DatosAsignadorConClientes datos;
	private BufferedReader br;
	private ObjectOutputStream output;
	
	public HiloCliente(Socket socket, DatosAsignadorConClientes datos)
	{
		this.socket = socket;
		this.datos = datos;
	}
	
	@Override
	public void run() 
	{
		Tarea tarea = null;
		if(socket != null) 
		{
			try 
			{
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {e.printStackTrace();}
			do 
			{
				recibirMensje = recibirMensjeCliente();
				if(recibirMensje.equals("fin")) 
					enviarMensajeCliente(new Tarea(0, 0));
				else 
				{
					if(recibirMensje.equals("new") && tarea != null) 
					{
						datos.setTareaAceptada(tarea, Thread.currentThread().getName());
					}
					else 
					{
						if(!datos.setTiempoEscedido(Thread.currentThread().getName()))
							datos.productor(tarea);
						System.out.println(recibirMensje);
					}
				}
				tarea = datos.consumidor();
				enviarMensajeCliente(tarea);	
			}while(!recibirMensje.equals("fin"));
		}
		datos.eliminarHilo();
		try 
		{
			socket.close();
		} catch (IOException e) {e.printStackTrace();}
		System.out.println("Cierro mi comunicación con el cliente");
	}
	
	private String recibirMensjeCliente() 
	{
		String mensaje = "";
		try {
			mensaje = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mensaje;
	}

	private void enviarMensajeCliente(Tarea tarea) 
	{
		try 
		{
			output.writeObject(tarea);
			output.flush();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
