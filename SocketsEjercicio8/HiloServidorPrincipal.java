package SocketsEjercicio8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloServidorPrincipal implements Runnable {

	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;

	public HiloServidorPrincipal(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Leemos la opción elegida por el usuario
			int puertoServidor;
			do {
				String opcion = br.readLine();
				puertoServidor = getPuerto(opcion);
				if (puertoServidor != -1) 
				{
					String direccionServidor = getDireccion(opcion);
					bw.write(direccionServidor + "/" + puertoServidor + "\n");
					bw.flush();
				}
			} while (puertoServidor != -1);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int getPuerto(String opcion) {
		int puerto = -1;
		try {
			int opcionInt = Integer.parseInt(opcion);
			if (opcionInt > 0 && opcionInt < 4)
				puerto = Datos.getPuerto(opcionInt);
		} catch (NumberFormatException e) {
			// Se recibe una opción no válida
		}
		return puerto;
	}

	private String getDireccion(String opcion) {
		String direccion = "";
		try {
			int opcionInt = Integer.parseInt(opcion);
			if (opcionInt > 0 && opcionInt < 4)
				direccion = Datos.getDireccion(opcionInt);
		} catch (NumberFormatException e) {
			// Se recibe una opción no válida
		}
		return direccion;
	}

}
