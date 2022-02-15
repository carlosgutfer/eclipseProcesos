package ejerciciosClase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Servidor {
		
	/*servidor y cliente se mandan algo codificado con la clave por tcp
	 */
	private static InetAddress dirServidor;
	private static int puertoServidor;
	
	public static void main(String[] args) {
		
		ServerSocket ss = crearSocket();
		obtenerClave();
		
		
	}

	private static DatagramSocket creardgs() {
		DatagramSocket dSocket  = null;
		try 
		{
			dSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dSocket;
	}

	private static ServerSocket crearSocket() {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(50000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serverSocket;
	}
	
	private static DatagramPacket recibirrespuesta(DatagramSocket s)
	{
		DatagramPacket  paquete = new DatagramPacket(new byte[250], 250);
		
		try {
			s.receive(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return paquete;
	}
	
	
	private static void enviarMensaje(DatagramSocket s) 
	{
		
		DatagramPacket paquete = new DatagramPacket(new byte [2], 2, dirServidor, puertoServidor);
		try {
			s.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void obtenerClave() 
	{
		InetAddress address = null;
		
		try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(address != null) 
		{
			DatagramSocket ds = creardgs();
			enviarMensaje(ds);
			DatagramPacket paquete = recibirrespuesta(ds);
			dirServidor = paquete.getAddress();
			puertoServidor = paquete.getPort();
			byte [] clave = paquete.getData();
			enviarMensaje(ds);
			byte [] has = recibirrespuesta(ds).getData();
			DatosClienteServidor datosClienteServidor = new DatosClienteServidor(clave, has);
		}
		
	}
}
