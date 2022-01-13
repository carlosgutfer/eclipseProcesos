package tuputamadre;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class cliente {
	
	public static void main(String[] args) {
		InetAddress direccionServidor = getDireccion("localhost");
		int puerto = 15000;
		Socket s = getSocket (direccionServidor,puerto);
		if (s!=null) {
			String mensaje = "hola mundo";
			try 
			{
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				bw.write("hola mundo" + '\n');
				bw.flush();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				System.out.println(String.valueOf(br.readLine()));
				s.close();
				
			} catch (IOException e) {
				System.out.println("Error al enviar el mensaje");
			}
		}
	}
	
	
	private static InetAddress getDireccion(String direccion) {
		InetAddress dir;
		try {
			// La dirección es la del servidor
			dir = InetAddress.getByName(direccion);
		} catch (UnknownHostException e) {
			dir = null;
		}
		return dir;
	}
	
	private static Socket getSocket(InetAddress dir, int puerto) {
		Socket socket;
		try {
			socket = new Socket(dir, puerto);
		} catch (IOException e) {
			socket = null;
		}
		return socket;
	}
	
	
}
