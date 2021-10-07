package Hormiga;

import java.util.Arrays;

public class infoHormiga {
	
	private int x;
	private int y;
	private int orientacion;
	private String [] reglas;
	
	public infoHormiga(int x, int y, int posicion, String[] reglas) {
		super();
		this.x = x;
		this.y = y;
		this.orientacion = posicion;
		this.reglas = reglas;
	}

	

	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(int posicion) {
		this.orientacion = posicion;
	}

	public String[] getReglas() 
	{
		return reglas;
	}

	public void setReglas(String[] reglas) {
		this.reglas = reglas;
	}



	@Override
	public String toString() {
		return "infoHormiga [x= " + x + ", y= " + y + ", posicion= " + orientacion + ", reglas= " + Arrays.toString(reglas)
				+ "]";
	}
	
	

}
