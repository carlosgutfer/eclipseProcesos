package SocketsEjercicio8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private static Socket s;
	private static BufferedWriter bw;
	private static BufferedReader br;
	private static Scanner sc;

	public static void main(String[] args) 
	{
		InetAddress direccionServidor = getDireccion("localhost");
		int puerto = 15000;
		s = getSocket(direccionServidor, puerto);
		if (s != null) {
			try {
				sc = new Scanner(System.in);
				bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				int opcion;
				do 
				{
					opcion = 1;
					Socket socketSecundario = obtenerConexionConServidorSecundario(opcion);
					System.out.println("¿Qué mensaje quieres transmitir?");
					String mensaje = sc.nextLine();
					comunicarConServidorSecundario(socketSecundario, mensaje);
				} while (opcion != 4);

				s.close();
			} catch (IOException e) {
				System.out.println("Error al crear los canales de comunicación.");
			}
		}
	}



	private static Socket obtenerConexionConServidorSecundario(int opcion) {
		Socket socketSecundario = null;
		try {
			bw.write(opcion + "\n");
			bw.flush();
			// Del servidor principal nos llegará información en la forma
			// localhost/15001
			String[] datos = br.readLine().split("/");
			int puerto = getPuerto(datos[1]);
			if (datos[0].length() > 0 && puerto != -1) {
				InetAddress dirSecundario = getDireccion(datos[0]);
				socketSecundario = new Socket(dirSecundario, puerto);
			}
		} catch (IOException e) {
			System.out.println("Error al enviar el mensaje.");
		}
		return socketSecundario;
	}
	
	private static void comunicarConServidorSecundario(Socket socket, String mensaje) {
		try {
			BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw2.write(mensaje+"\n");
			bw2.flush();
			String respuesta=br2.readLine();
			System.out.println("Recibido del servidor:");
			System.out.println(respuesta);
		} catch (IOException e) {
			System.out.println("Error al establecer los canales de comunicación");
		}
	}

	private static int getPuerto(String puertoString) {
		int puerto = -1;
		try {
			puerto = Integer.parseInt(puertoString);
		} catch (NumberFormatException e) {
			// Se recibe una opción no válida
		}
		return puerto;
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
