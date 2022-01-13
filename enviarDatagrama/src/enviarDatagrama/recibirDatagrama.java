package enviarDatagrama;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class recibirDatagrama {

	public static void main(String[] args) 
	{
		while(true) 
		{
			
			byte[] buffer = new byte[250];
			DatagramPacket  paquete = new DatagramPacket(buffer, buffer.length);
			try 
			{
				DatagramSocket  socket = new DatagramSocket(15300);
				socket.receive(paquete);
				System.out.println("Mensaje recibido: " +new String(paquete.getData()));
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
