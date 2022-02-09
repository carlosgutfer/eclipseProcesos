package terceraPracticaCarlosGutierrez;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HiloCreador implements Runnable {
	
	private int puertoCreadorTareas;
	private InetAddress dirCreadorTareas;
	private DatagramSocket socketCreadorTareas;
	private DatagramPacket packetCreadorTareas;
	private DatosHilocreadorConCreador datos;

	public HiloCreador(DatosHilocreadorConCreador datos) {
		this.datos = datos;
	}
	
	public void run() 
	{
		String horasTarea [];
		comunicacionCreadorTareas();
	do //El bucle se ejecuta hasta que la variable fin de la clase datos sea falsa
	{
		enviarMensajeCreadorTareas("new".getBytes()); 
		packetCreadorTareas = recibirMensajeCreadorTareas();
		horasTarea = (new String (packetCreadorTareas.getData())).split("-");// Parto el string recibido  en un array con split
		datos.productor(new Tarea(Integer.valueOf(horasTarea[0].trim()), Integer.valueOf(horasTarea[1].trim())));// Creo el objeto con la tarea recibida
	}while(!datos.getFin());
		enviarMensajeCreadorTareas("stop".getBytes());
		System.out.println("Apago el creador de tareas");
		socketCreadorTareas.close();
	}
	
	private void comunicacionCreadorTareas() 
	{
		puertoCreadorTareas = 30000;
		socketCreadorTareas = getSocket();
		dirCreadorTareas = getCreadorTareas();
	}
	
	//Método para inicializar el inetAdress
	private InetAddress getCreadorTareas() 
	{
		InetAddress inetAddress = null;	
		try 
		{
			inetAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		return inetAddress;
	}
	
	//Método qeu recibe el mensaje del creador de tareas
	private DatagramPacket recibirMensajeCreadorTareas() 
	{
		DatagramPacket packet = new DatagramPacket(new byte[20], 20);	
		try 
		{
			socketCreadorTareas.receive(packet);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	//Método para enviar el mensaje al creador de tareas
	private void enviarMensajeCreadorTareas(byte[] bytes) 
	{
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, dirCreadorTareas, puertoCreadorTareas);	
		try 
		{
			socketCreadorTareas.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

//Método para inicializar el socket
	private DatagramSocket getSocket() 
	{
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return socket;
	}

}


