package Ejercicio5;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ejercicio5 {

	
	public static void main(String [] args) throws IOException 
	{
		File registro = new File("registro.dat");
		registro.createNewFile();
		
		FileOutputStream  fos = new FileOutputStream(registro);
		DataOutputStream dos = new DataOutputStream(fos);

		dos.writeUTF("Paco_Pepe");
		dos.writeInt(32);
		dos.writeInt(2);
		dos.writeUTF("Soltero");
		
		dos.writeUTF("Luisa_Julia");
		dos.writeInt(30);
		dos.writeInt(1);
		dos.writeUTF("Casada");
		
		dos.close();
		fos.close();
		
		FileInputStream fis = new FileInputStream(registro);
		DataInputStream dis = new DataInputStream(fis);
		
		int contador = 0;
		int suma = 0;
		try 
		{
			while(true) 
			{
				dis.readUTF();
				suma += dis.readInt();
				dis.readInt();
				dis.readUTF();
				contador++;
			}
		}catch(EOFException e) {}
		dis.close();
		fis.close();
		System.out.println("La media es: " + suma / contador);
		
	}
}
