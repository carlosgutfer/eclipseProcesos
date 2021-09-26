package Media;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Mediab {
	public static void main(String args []) 
	{
		double		media;
		
		media = 0;
		for(int i = 0; i < args.length; i++) 
			media += Integer.parseInt(args[i]);
		
		ProcessBuilder fichero  = new ProcessBuilder("vim", "media.txt");
		try {
			Process iniciarFichero = fichero.start();
			OutputStream os = iniciarFichero.getOutputStream();
			BufferedWriter ficheroEscritura = new BufferedWriter(new OutputStreamWriter(os));
			ficheroEscritura.write("i");
			// 4. Escribo el contenido del archivo : `Hola mundo vi`
			ficheroEscritura.write(String.valueOf(media));
			// 5. Para terminar la edición presiono la tecla escape
			ficheroEscritura.write(0x1b);
			// 6. Para guardar los cambios y salir escribo `:wq` y presiono enter.
			ficheroEscritura.write(":wq");
			ficheroEscritura.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
