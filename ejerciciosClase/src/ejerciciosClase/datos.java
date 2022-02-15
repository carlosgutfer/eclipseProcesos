package ejerciciosClase;

import java.lang.Enum.EnumDesc;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.security.DrbgParameters.NextBytes;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class datos {

	private byte[] claveHash;
	private byte [] clave;
	public datos(int longitud_clave) 
	{
		 clave = createSecretKey(128).getEncoded();
		 claveHash = getEncode(clave);
		 
	}
	
	private SecretKey createSecretKey(int longitud_clave) 
	{
		SecretKey key = null;
		try 
		{
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(longitud_clave, null);
			key = kg.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
	
	private byte[] getEncode(byte[] clave) 
	{
		byte[] claveHash = null;
		try {
			byte [] clave64 = Base64.getEncoder().encode(clave);
			MessageDigest md = MessageDigest.getInstance("MDS");
			md.update(clave64);
			claveHash = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return claveHash;
	}
	
	public byte[] getClaveHash() {return this.claveHash;}

	public byte[] getClave() {
		return clave;
	}

	
	
}
