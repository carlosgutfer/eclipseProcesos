package Media;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Mediab {
	
	private static Scanner sc = new Scanner(System.in);
	public static void main(String args []) 
	{
		
		String 		mensaje = sc.nextLine();
		String[] 	elementos = mensaje.split(" ");	
		
		crearArchivo(elementos);
		/*ProcessBuilder fichero  = new ProcessBuilder("vim", "media.txt");
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
		}*/
	}

	private static void crearArchivo(String[] elementos) {
		double		media;
		File 		mediaText;
		FileWriter 	fw;
		
		media = 0;
		for(int i = 0; i < elementos.length; i++) 
			media += Integer.parseInt(elementos[i]);
		mediaText = new File ("media.txt");
		try {
			mediaText.createNewFile();
			fw = new FileWriter(mediaText);
			fw.write(String.valueOf((double)(media / elementos.length)));
			System.out.println(mediaText);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
