package terceraPracticaCarlosGutierrez;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	private static Socket socket;
	private static InetAddress dirServidor;
	private static int puertoServidor;
	private static int contadorDeHoras;
	private static BufferedWriter bw;
	private static ObjectInputStream input;
	
	public static void main(String[] args) 
	{	
		Tarea tarea;
		dirServidor = getServidor();
		puertoServidor = 20000;
		socket = getSocket();
		contadorDeHoras = 40;
		try 
		{	
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			input =  new ObjectInputStream(socket.getInputStream());
			enviarMensaje("new");
			do // Este bucle se ejecuta hasta que el hiloCliente manda una tarea con el identificador 0
			{
				tarea =  recibirMensaje();
				if(contadorDeHoras - tarea.getHoras() > -1) // Si las horas disponibles, menso las recibidas es mayor que 0 se ejecuta esto
				{
					contadorDeHoras -= tarea.getHoras();
					if(contadorDeHoras != 0) 
						enviarMensaje("new");
					else 
						enviarMensaje("fin");
				}
				else
					enviarMensaje("Tiempo excedido");
			}while(tarea.getIdentificador() != 0);
			System.out.println("salí");
		} catch (Exception e) {System.out.println(e); }	
	}

	//Método para recibir mensajes del hiloCliente
	private static Tarea recibirMensaje() 
	{
		Tarea tarea;
		
		tarea = null;
		try 
		{
			tarea = (Tarea) input.readObject();
		}catch (IOException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}		
		return tarea;
	}
	
	//Método para enviar menajses al HiloCliente
	private static void enviarMensaje(String mensaje) 
	{		
		try 
		{
			bw.write(mensaje + "\n");
			bw.flush();		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}
	
	//Método para tomar la dirección del servidor
	private static InetAddress getServidor() 
	{
		InetAddress dir = null;
		
		try 
		{
			dir = InetAddress.getByName("localhost");
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}		
		return dir;
	}
		// Método para crear el socket con el servidor
	private static Socket getSocket() 
	{	
		Socket s = null;

			try 
			{
				s = new Socket(dirServidor, puertoServidor);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		return s;
	}
	
}
