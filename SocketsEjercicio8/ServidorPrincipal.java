package SocketsEjercicio8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorPrincipal {

	public static void main(String[] args) {
		//Esto no sería necesario si los secundarios se arrancan de forma independiente
		//Ojo al parar el servidor principal, los servidores secundarios se quedan vivos
	//	arrancarServidoresSecundarios();
		
		try {
			ServerSocket ssocket = new ServerSocket(15000);
			while (true) 
			{
				Socket socket = ssocket.accept();
				HiloServidorPrincipal h = new HiloServidorPrincipal(socket);
				Thread t = new Thread(h);
				t.start();
			}
		} catch (IOException e) {
			System.out.println("Error al crear el socket. ¿Puerto ocupado?");
		}

	}
	
	private static void arrancarServidoresSecundarios() {
		for (int i=0;i<3;++i) {
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./bin","SocketsEjercicio8.ServidorSecundario",""+(i+1));
			try {
				pb.start();
			} catch (IOException e) {
				//Error al crear el proceso
			}
		}
	}
}
