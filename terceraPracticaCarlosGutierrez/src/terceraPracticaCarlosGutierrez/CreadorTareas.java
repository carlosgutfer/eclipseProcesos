package terceraPracticaCarlosGutierrez;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CreadorTareas 
{
	
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static InetAddress dirHiloCreador;
	private static int puertoHiloCreado;
	
	public static void main(String[] args) 
	{
		socket = getSocket();
		packet = getMensaje();
		puertoHiloCreado = packet.getPort();
		dirHiloCreador = packet.getAddress();
		String mensaje = new String(packet.getData());
		int tarea = 0;
		while(!mensaje.equals("stop")) //Este bucle se ejecuta hasta que el mensaje de llegaa sea distinto a stop
		{
			mensaje = crearTarea() + (++tarea);// creo una tarea
			setMensaje(mensaje.getBytes()); //la envio al hilo creaddor
			packet = getMensaje();// recibo el packet de respuesta del hilo
			mensaje = (new String(packet.getData())).trim();
		}
		socket.close();
	}

	//Método para enviar mensaje al hilo creador
	private static void setMensaje(byte[] bytes) 
	{
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, dirHiloCreador, puertoHiloCreado);
		try {
			socket.send(packet);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	//método para crear las horas de las tareas, número aleatorio entre 1 - 3 
	private static String crearTarea() 
	{
		String mensaje = "";
		int numeroAleatorio;	
		numeroAleatorio = (int)(Math.random() * 3) + 1;
		mensaje += numeroAleatorio + "-";	
		return mensaje;
	}
	//método para recibir mensaje del hilo creador
	private static DatagramPacket getMensaje() 
	{
		DatagramPacket packet = new DatagramPacket(new byte[20], 20);
		try 
		{
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return packet;
	}

	private static DatagramSocket getSocket() 
	{
		DatagramSocket socket = null;
		try 
		{
			socket = new DatagramSocket(30000);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
		return socket;
	}

}
