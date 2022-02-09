package cuartapracticaCarlosGutierrez;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServidorDHCP {

	static DatagramSocket socket;
	static InetAddress inetAddress;
	public static void main(String[] args) 
	{
		socket = getServerSocket();
		inetAddress = getCreadorTareas();
		DatagramPacket packetCreadorTareas = null; 
		while(true) 
		{

			packetCreadorTareas = recibirMensajeCreadorTareas();
			if(packetCreadorTareas != null)
				System.out.println(packetCreadorTareas.getData().toString());
		}
		
	}
	
	private static InetAddress getCreadorTareas() 
	{
		InetAddress inetAddress = null;	
		try 
		{
			inetAddress = InetAddress.getByName("10.0.2.50");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		return inetAddress;
	}

	private static DatagramSocket getServerSocket()  
	{
		DatagramSocket Socket = null;
		try 
		{
			 Socket = new DatagramSocket(67);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Socket;
	}
	
	//Método qeu recibe el mensaje del creador de tareas
		private static DatagramPacket recibirMensajeCreadorTareas() 
		{
			DatagramPacket packet = new DatagramPacket(new byte[576], 576);	
			try 
			{
				socket.receive(packet);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			return packet;
		}
}
