import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Principal {

	public static void main(String[] args) {

		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128, null);
			SecretKey k = kg.generateKey();
			
			byte[] claveEnBytes = k.getEncoded();
			String claveEnString = Base64.getEncoder().encodeToString(claveEnBytes);
			FileWriter fw = new FileWriter(new File("clave_secreta.txt"));
			fw.write(claveEnString+"\n");
			
			
			
			
			//Codificar
			
			Cipher c = Cipher.getInstance(k.getAlgorithm());
			
			c.init(Cipher.ENCRYPT_MODE, k);
			
			String mensaje = "atacaremos al amanecer";
			byte[] mensajeEncriptado = c.doFinal(mensaje.getBytes());
			
			String mensajeEncriptadoBase64= Base64.getEncoder().encodeToString(mensajeEncriptado);
			System.out.println(mensajeEncriptadoBase64);
			fw.write(mensajeEncriptadoBase64);
			fw.close();
			
			
			/*
			//Decodificar
			byte[] mensajeEncriptado2 = Base64.getDecoder().decode(mensajeEncriptadoBase64);
			
			c.init(Cipher.DECRYPT_MODE,k);
			
			byte[] mensajeDesencriptado = c.doFinal(mensajeEncriptado2);
			
			String mensaje2 = new String(mensajeDesencriptado);
			System.out.println(mensaje2);
			*/
			
			
			

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
