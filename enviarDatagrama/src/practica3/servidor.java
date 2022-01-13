package practica3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class servidor {

	public static void main(String[] args)
{
		DatagramSocket socket = getSocket();
		//Bucle infinito para recibir mensajes de clientes
		byte [] buffer;
		DatagramPacket paquete;
		while(true) 
		{
			buffer = new byte[1];
			paquete = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(paquete);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Thread hilo = new Thread(new hiloservidor(paquete));
			hilo.start();
		}
	}
	
private static  DatagramSocket getSocket() {
		
		DatagramSocket s = null;
		try {
			s = new DatagramSocket(20000);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	
	
}
