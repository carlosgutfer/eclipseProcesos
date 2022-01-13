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
	do 
	{
		enviarMensajeCreadorTareas("new".getBytes());
		packetCreadorTareas = recibirMensajeCreadorTareas();
		horasTarea = (new String (packetCreadorTareas.getData())).split("-");
		datos.productor(new Tarea(Integer.valueOf(horasTarea[0].trim()), Integer.valueOf(horasTarea[1].trim())));
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


	private DatagramSocket getSocket() 
	{
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socket;
	}

}


