package ejerciciosClase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HiloComunicaciones implements Runnable{

	
	private datos datos;
	private InetAddress dirDestino;
	private int puertoDestino;
	
	public HiloComunicaciones(datos datos, DatagramPacket paequeteDelDestino) 
	{
		this.datos = datos;
		dirDestino = paequeteDelDestino.getAddress();
		puertoDestino = paequeteDelDestino.getPort();
	}
	
	@Override
	public void run() {
		
		DatagramSocket s = crearSocket();
		if(s !=null) 
		{
			byte [] envio = datos.getClave();
			DatagramPacket paquete = new DatagramPacket(envio, envio.length, dirDestino, puertoDestino);
			enviarPaquete(paquete, s);
			recibirMensaje(s);
			paquete = new DatagramPacket(datos.getClaveHash(), datos.getClaveHash().length, dirDestino,  puertoDestino);
			enviarPaquete(paquete, s);
			recibirMensaje(s);
		}
	}
	
	private void recibirMensaje(DatagramSocket s) 
	{
		DatagramPacket p = new DatagramPacket(new byte[1], 1);
		try 
		{
			  s.receive(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void enviarPaquete(DatagramPacket paquete, DatagramSocket s) {
		
		try 
		{
			s.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DatagramSocket crearSocket() 
	{
		DatagramSocket s = null;
		try 
		{
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

}
