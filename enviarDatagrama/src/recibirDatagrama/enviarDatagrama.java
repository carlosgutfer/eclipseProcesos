package recibirDatagrama;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class enviarDatagrama {

	public static void main(String[] args) {
		 DatagramSocket s = getSocket();
		 InetAddress direccionReceptor;

		 if(s != null)
			 if((direccionReceptor = getAddress("localhost")) != null) 
			 {
				  int puerto = 20000;
				  Scanner sc = new Scanner(System.in);
				  String mensaje = sc.nextLine();
				  //envaimos primero la longitud del mensaje al receptor,
				  //en forma de entero de 4 bytes
				  ByteBuffer bb = ByteBuffer.allocate(4);
				  bb.putInt(mensaje.length());
				  byte [] buffer = bb.array();
				  DatagramPacket paquete = new DatagramPacket(buffer,buffer.length,direccionReceptor, puerto);
				  enviarPaquete(paquete, s);
				  //Esperamos una confirmación del receptor
				  
				  
				  //Ahora enviamos el mensaje propiamente dicho
				  buffer = mensaje.getBytes();
				  paquete = new DatagramPacket(buffer,buffer.length,direccionReceptor, puerto);
				  enviarPaquete(paquete, s);
			 }
		 
			 
		
	}
	
	private  static void enviarPaquete(DatagramPacket paquete, DatagramSocket s) {
		try {
			s.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static DatagramSocket getSocket() {
		DatagramSocket s;
		
		s = null;
		try {
			s = new DatagramSocket();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	private static InetAddress getAddress(String dir) 
	{
		InetAddress direccionAddress = null;
		
		try {
			direccionAddress = InetAddress.getByName(dir);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return direccionAddress;
	}
}
