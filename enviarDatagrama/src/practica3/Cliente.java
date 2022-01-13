package practica3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente {
	
	private static DatagramSocket socket;
	private static int puertoServidor;
	private static InetAddress dirServidor;
	
	public static void main(String[] args) {
		//El cliente tiene que mandar un mensaje de saludo,
		//luego recibe un mensaje del hilo
		//y envia otro mensaje por los loles
		socket = getSocket();
		dirServidor = getDireccionServidor();
		puertoServidor = 20000;
		enviarMensaje(new byte[1]);
		//Ahora recibimos un mensaje
		DatagramPacket  paquete = recibirMensaje();
		//actualizamos el peurto del servidor
		puertoServidor = paquete.getPort();
		//Enviamos un último mensaje al peurto correspondiente.
		enviarMensaje(("Adios " + Math.random()).getBytes());
		socket.close();
	}
	
	private static DatagramSocket getSocket() {
		
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	private static InetAddress getDireccionServidor() 
	{
		InetAddress dir;
		dir = null;
		try {
			 dir = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dir;
	}
	
	private static void enviarMensaje(byte[] buffer) 
	{
		DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, dirServidor, puertoServidor);
		try {
			socket.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static DatagramPacket recibirMensaje() 
	{
		DatagramPacket paquete = new DatagramPacket(new byte[2], 2);
		try {
			socket.receive(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paquete;
	}

}
