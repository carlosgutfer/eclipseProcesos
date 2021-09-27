package calculadoraFrontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class calculadoraFrontend {

	public static void main(String args[]) 
	{
		Scanner 		sc = new Scanner(System.in);
		File 			ruta = new File(".\\bin");
		ProcessBuilder 	pb = new ProcessBuilder();
		String 			operacion, salida;

		System.out.println("Introduzca la operción (suma/resta/multiplicacion/division) seguida de dos números: ");
		operacion = sc.nextLine();
		pb.directory(ruta);
		pb.command("java", "calculadoraBackend.calculadoraBackend", operacion);
		try 
		{
			BufferedReader bf = new BufferedReader( new InputStreamReader (pb.start().getInputStream()));
			salida = bf.readLine();
			if (salida.equals("E1"))
				System.out.println("Error en la entrada, escriba suma/resta/multiplicacion/division");
			else
				System.out.println("El resultado es: " + salida);
			bf.close();
		}catch(Exception e) 
		{
			System.out.println(e);
		}
	}
	
	//v2
	/*ProccessBuilder pb = new ProcessBuilder ("java",
	* "-cp",
	* "ruta desde c",
	* "calculadoraBackend.calculadoraBackend")
	* BufferedWriter  bw = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()));
	* bw.write(mensaje + "\n");
	* bw.flush();
	* String resultado = br.readLine();
	* System.out.println("Resultado "+ resultado);
	* bw.close();
	* br.close();
	*/
}
