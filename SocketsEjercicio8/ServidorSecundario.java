package SocketsEjercicio8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSecundario {

	//Podemos utilizar args para recibir el identificador del servidor, con el cual
	//tomamos de Datos el puerto por el que debemos escuchar
	public static void main(String[] args) {
		int idServidor = Integer.parseInt(args[0]);
		try {
			ServerSocket ssocket = new ServerSocket(Datos.getPuerto(idServidor));
			while (true) 
			{
				Socket socket = ssocket.accept();
				HiloServidorSecundario h = new HiloServidorSecundario(socket,idServidor);
				Thread t = new Thread(h);
				t.start();
			}
		} catch (IOException e) {
			System.out.println("Error al crear el socket. ¿Puerto ocupado?");
		}
	}
}
