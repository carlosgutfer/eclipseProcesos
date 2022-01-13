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
			do
			{
				tarea =  recibirMensaje();
				if(contadorDeHoras - tarea.getHoras() > -1) 
				{
					contadorDeHoras -= tarea.getHoras();
					if(contadorDeHoras != 0) 
						enviarMensaje("new");
					else 
						enviarMensaje("fin");
				}
				else
					enviarMensaje("Tiempo escedido");
			}while(tarea.getIdentificador() != 0);
			System.out.println("salí");
		} catch (Exception e) {System.out.println(e); }	
	}

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
