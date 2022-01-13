package recibirDatagrama;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import enviarDatagrama.recibirDatagrama;


public class RecibirDatagrama {
	public static void main(String[] args) {
		DatagramSocket s = getSocket();
		if(s != null) 
		{
			//recibimos 1er mensaje
			byte [] longitudCodificada = recibirmensaje(4, s);
			//recibimos  2ºmensje
			int longitud = ByteBuffer.wrap(longitudCodificada).getInt();
			byte [] mensaje = recibirmensaje(longitud, s);
			System.out.println("mensaje recibido "+ new String(mensaje));
		}
		
	}
	
	private static DatagramSocket getSocket() {
		DatagramSocket s;
		
		s = null;
		try {
			s = new DatagramSocket(20000);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	private static byte[] recibirmensaje(int tamanno, DatagramSocket s) 
	{
		byte[]  buffer = new byte[tamanno];
		DatagramPacket paquete = new DatagramPacket(buffer, tamanno);
		try {
			s.receive(paquete);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return buffer;
 	}

}
