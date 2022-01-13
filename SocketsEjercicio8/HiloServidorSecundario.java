package SocketsEjercicio8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloServidorSecundario implements Runnable {

	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;
	private int numeroDeServidor;

	public HiloServidorSecundario(Socket socket, int numeroDeServidor) {
		this.socket = socket;
		this.numeroDeServidor = numeroDeServidor;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Leemos la opción elegida por el usuario
			String mensaje = br.readLine();
			bw.write("Bienvenido al servidor " + numeroDeServidor + ". Me has enviado " + mensaje + "\n");
			bw.flush();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
