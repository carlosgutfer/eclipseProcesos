package enviarDatagrama;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EnviarDatagrama {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		String mensaje = sc.nextLine();
		
		byte [] buffer = mensaje.getBytes();
		
		try 
		{
			InetAddress dir = InetAddress.getByName("localhost");
			int puerto = 15300;
			DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, dir, puerto);
			DatagramSocket socket = new DatagramSocket();
			socket.send(paquete);
		} 
		catch (UnknownHostException | SocketException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
