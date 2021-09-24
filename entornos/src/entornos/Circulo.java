package entornos;

/**
 * @author     Carlos Gutiérrez 
 * @version     1.1                 
 * 
 */

/*clase para calcular la circunferencia, la escala y mover el centro de los círculos*/
public class Circulo {        
	private double centroX;        
	private double centroY;        
	private double radio; 

/*Constructor de la clase círculo
 * @param cx centro de x
 * @param cy centro de y
 * @param r radio*/
public Circulo(double cx, double cy, double r) {              
	centroX = cx;              
	centroY = cy;              
	radio = r; 
} 

/*metodo para obtener el centro del círculo*/
public double getCentroX() {              
	return centroX; 
} 

/*método para hayar la circunferencia del círculo*/
public double getCircunferencia() {              
	return 2 * Math.PI * radio; 
} 
/*método para mover el centro del círculo
 * @param deltaX modificador de distancia para centroX
 * @param deltaY modificador de distancia para centroY 
 */
public void mueve(double deltaX, double deltaY) {              
	centroX = centroX + deltaX;               
	centroY = centroY + deltaY; 
} 
/*Método para hayar la escala del círculo
 *@param s valor de la escala para el círculo
 */
 public void escala(double s) {               
	 radio = radio * s; 
 } 
} 
