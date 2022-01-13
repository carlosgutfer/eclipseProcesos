package practica3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class hiloservidor implements Runnable {

	private DatagramPacket paquete;
	private DatagramSocket socket;
	private InetAddress dirCliente;
	private int puertoCliente;
	
	public hiloservidor(DatagramPacket paquete) {
		this.paquete = paquete;
	}
	@Override
	public void run() 
	{
		dirCliente = paquete.getAddress();
		puertoCliente = paquete.getPort();
		socket = getSocket();
		if(socket != null) 
		{
			//Enviamos al cliente algo para que sepa que estamos ecuchando 
			//además para que sepa a qué puerto tiene que escribir a partir de ahora
			enviarMensaje("ok".getBytes());
			//Recibimos una respuesta del cliente:esta no ha sido mandada al peurto 20000,
			//si no al puerto habilitado por el hilo
			DatagramPacket respuesta = recibirMensaje();
			System.out.println("Mensaje recibido:" + new String(respuesta.getData()));
			socket.close();
			
		}
		
	}
	
	private  DatagramSocket getSocket() {	
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	private void enviarMensaje(byte[] buffer) 
	{
		DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, 
															dirCliente, puertoCliente);
		try {
			socket.send(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private DatagramPacket recibirMensaje() 
	{
		DatagramPacket paquete = new DatagramPacket(new byte[20], 20);
		try {
			socket.receive(paquete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paquete;
	}
}
