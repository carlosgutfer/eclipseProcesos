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
		while(!mensaje.equals("stop")) 
		{
			mensaje = crearTarea() + (++tarea);
			setMensaje(mensaje.getBytes());
			packet = getMensaje();
			mensaje = (new String(packet.getData())).trim();
		}
		socket.close();
	}

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

	private static String crearTarea() 
	{
		String mensaje = "";
		int numeroAleatorio;	
		numeroAleatorio = (int)(Math.random() * 3) + 1;
		mensaje += numeroAleatorio + "-";	
		return mensaje;
	}

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
