package SocketsEjercicio8;

public class Datos {
	private static int[] puertos=new int[] {15001,15002,15003};
	//Las siguientes podrían ser direcciones IP diferentes
	private static String[] direcciones = new String[] {"localhost","localhost","localhost"};
	
	
	public static int getPuerto(int id) {
		return puertos[id-1];
	}
	
	
	public static String getDireccion(int id) {
		return direcciones[id-1];
	}
}
