import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Secundario {

	public static void main(String[] args) {
		
		FileReader fr;
		try {
			fr = new FileReader(new File("clave_secreta.txt"));
			BufferedReader br = new BufferedReader(fr);
			String claveString = br.readLine();
			byte[] clave = Base64.getDecoder().decode(claveString);
			String mensajeEncriptadoString = br.readLine();
			byte[] mensajeEncriptado = Base64.getDecoder().decode(mensajeEncriptadoString);
			
			SecretKeySpec k = new SecretKeySpec(clave, "AES");
			
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE,k);
			
			byte[] mensajeDesencriptado = c.doFinal(mensajeEncriptado);
			String mensajeDesencriptadoString = new String(mensajeDesencriptado);
			System.out.println(mensajeDesencriptadoString);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}
		

	}

}
