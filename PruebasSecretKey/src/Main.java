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

public class Main {

	public static void main(String[] args) {
		
		try 
		{
			KeyGenerator kGenerator = KeyGenerator.getInstance("AES");
			kGenerator.init(128, null);
			SecretKey K = kGenerator.generateKey();
			
			
			
			byte [] claveEnBytes = K.getEncoded();
			String claveEnString = Base64.getEncoder().encodeToString(claveEnBytes);
			FileWriter fWriter = new FileWriter(new File("claveSecreta.txt"), true);
			fWriter.write(claveEnString);
			
		
			
			
			Cipher cipher = Cipher.getInstance(kGenerator.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, K);
			
			String mensaje= "atacaremos al amanecer";
			
			byte [] mensajeEncriptado = cipher.doFinal(mensaje.getBytes());
			
			String mensajeEncriptadoResultado = Base64.getEncoder().encodeToString(mensajeEncriptado); 
			
			
			fWriter.write(mensajeEncriptadoResultado);

			fWriter.close();
			
			byte [] mensajeEncriptado2 = Base64.getDecoder().decode(mensajeEncriptadoResultado);
			
			cipher.init(Cipher.DECRYPT_MODE, K);
			
			byte [] mensajeDesencriptado = cipher.doFinal(mensajeEncriptado2);
			String mensaje2 = new String(mensajeDesencriptado);
			System.out.println(mensaje2);
			
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
