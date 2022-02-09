import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {

	public static void main(String[] args) 
	{
		String mensajeString = "En un lugar de La Mancha, de cuyo nombre no quiero acordarme";
		
		// codificamos el mensaje
		MessageDigest m = getMessageDIgest("SHA-256");
		if(m != null) 
		{
			//podemos modificar este mensaje añadiendo todas las veces que queramos
			m.update(mensajeString.getBytes());
			byte [] mensajeCodificado = m.digest();
			
			for(int i = 0; i< mensajeCodificado.length; ++i)
				System.out.print(mensajeCodificado[i] + ",");
			
			String mensajeModificado = Base64.getEncoder().encodeToString(mensajeCodificado);
			System.out.println('\n' + mensajeModificado);
			byte [] mensajeCompuesto = Base64.getDecoder().decode(mensajeModificado);
			for (int i = 0; i < mensajeCompuesto.length; i++) 
				System.out.print(mensajeCompuesto[i] + ",");
			
		}
	}

	private static MessageDigest getMessageDIgest(String algoritmo) {
		MessageDigest m = null;
		try 
		{
			m = MessageDigest.getInstance(algoritmo);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
}
