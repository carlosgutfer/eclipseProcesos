package ejerciciosClase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

public class DatosClienteServidor {

	private SecretKeySpec clave;
   public DatosClienteServidor(byte [] claveBase64, byte[] hash) 
   {
		claveBase64 = limpiarMensaje(claveBase64);
		hash = limpiarMensaje(hash);
		if(validarHash(claveBase64, hash))
		{
			clave = reconstruirClave(claveBase64);
		}
	}
   
   
   public SecretKeySpec getClave() {
	return clave;
}





private byte[] limpiarMensaje(byte[] mensaje) 
  {
	   int longitud = 0;
	   for(int i = mensaje.length - 1; i >= 0 && longitud == 0; --i)
		   if(mensaje[i] != 0)
			   longitud = i +1;
	   byte [] mensajeLimpio = new byte[longitud];
	   for(int i = 0; i < longitud; ++i) 
	   {
		   mensajeLimpio[i] = mensaje[i];
		   
	   }
	   return mensajeLimpio;
  }	
   
   
   private boolean validarHash(byte[] mensaje, byte [] hash) 
   {
		MessageDigest md;
		boolean valido = false;
		try {
			md = MessageDigest.getInstance("MDS");
			md.update(mensaje);
			byte [] nuevoHash = md.digest();
			
			String hasString = Base64.getEncoder().encodeToString(hash);
			String nuevoHasString = Base64.getEncoder().encodeToString(nuevoHash);
			valido = hasString.equals(nuevoHasString);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valido;
   }
   
   private SecretKeySpec reconstruirClave(byte [] clave64) 
   {
	   byte [] claveDecodifica = Base64.getDecoder().decode(clave64);
	   return new SecretKeySpec(claveDecodifica, "AES");
	   
	   
   }
   
}
