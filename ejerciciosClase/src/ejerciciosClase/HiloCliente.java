package ejerciciosClase;

import java.io.BufferedWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class HiloCliente implements Runnable{

	
	private DatosClienteServidor datosClienteServidor;
	
	private Socket socket;
	public HiloCliente(DatosClienteServidor datosClienteServidor, Socket socket) {
		this.datosClienteServidor = datosClienteServidor;
		this.socket = socket;
	}
	@Override
	public void run() {
		SecretKeySpec clave = datosClienteServidor.getClave();
	}
	
	
	private void enviarMensaje(String mensaje) {
		 
	}
	
	
	private String codificarMensaje(String mensaje, SecretKeySpec clave) 
	{
		String mensajeCodificadoString = null;
		try {
			Cipher cifrador = Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, clave);

			byte [] mensajeCodificado = cifrador.doFinal(mensaje.getBytes());
			mensajeCodificadoString = Base64.getEncoder().encodeToString(mensajeCodificado);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
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
		return mensajeCodificadoString;
	}
	
	
	private String decodificarMensaje(String mensaje, SecretKeySpec clave)
	{
		String mensajedeCodificadoString = null;
		try {
			Cipher cifrador = Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, clave);

			byte [] mensajeDecoficado = Base64.getDecoder().decode(mensaje);
			
			mensajedeCodificadoString = new String(cifrador.doFinal(mensajeDecoficado));

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
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
		return mensajedeCodificadoString;
		
	}
	
	
	
	
}
