package tuputamadre;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class servidor {
	

	public static void main(String[] args) {

		try {
			@SuppressWarnings("resource")
			ServerSocket ssocket = new ServerSocket(15000);
			while (true) {

				Socket socket = ssocket.accept();

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(br.readLine());
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bw.write("cojones" + '\n');
				bw.flush();
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error al crear el socket. ¿Puerto ocupado?");
		}

	}

	
}
