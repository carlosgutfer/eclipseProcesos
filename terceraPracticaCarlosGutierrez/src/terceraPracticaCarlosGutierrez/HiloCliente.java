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
			do //Este bucle que se ejecuta hasta que el cliente mánda el mensaje 'fin'
			{
				recibirMensje = recibirMensjeCliente();
				if(recibirMensje.equals("fin")) //Si es el mensaje fin, manda la tarea 0,0 para que el cliente también se detenga
					enviarMensajeCliente(new Tarea(0, 0));
				else 
				{
					if(recibirMensje.equals("new") && tarea != null) // si la tarea no es null, y el mensaje recibido es 'new' porque la tarea ha sido aceptada llama al método que inserta la tarea para después crear el informe
						datos.setTareaAceptada(tarea, Thread.currentThread().getName());
					else 
					{
						if(!datos.setTiempoExcedido(Thread.currentThread().getName()))// si la tarea ha sido rechazada, comprueba si este hilo ya la ha rechazado o no, para reenviar la tarea a otro hilo o descartarla definitivamente si ya están todos comprobados
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
	
	//Método que lee los mensajes que me da el cliente
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
	// Método para comunicarse con el cliente
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
