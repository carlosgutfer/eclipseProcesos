package ejerciciosClase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Auxiliar {
	
	/*Peinsa la clave
	 * Tanto servidor como cliente le piden la calve por UDP
	 * L- calve viaje en base64
	 * A continuación de la clave se manda el hash de la clave
	 * 
	 * */
	public static void main(String[] args)
	{
		DatagramSocket ss = crearSocket();
		datos dato = new datos(128);
		while(true) 
		{
			
				DatagramPacket respuesta = recibirRespuesta(ss);
				HiloComunicaciones h = new HiloComunicaciones(dato, respuesta);
				Thread t = new Thread(h);
				t.start();
			
		}
		
	}

	private static DatagramPacket recibirRespuesta(DatagramSocket s) {
		DatagramPacket respuestaDatagramPacket = new DatagramPacket(new byte [1], 1);
		
		try {
			s.receive(respuestaDatagramPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuestaDatagramPacket;
	}

	private static DatagramSocket crearSocket()  {
		DatagramSocket s = null;
	
		try {
			s = new DatagramSocket(15000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
