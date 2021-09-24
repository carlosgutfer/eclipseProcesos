package entornos;

/**
 * @author     Carlos Guti�rrez 
 * @version     1.1                 
 * 
 */

/*clase para calcular la circunferencia, la escala y mover el centro de los c�rculos*/
public class Circulo {        
	private double centroX;        
	private double centroY;        
	private double radio; 

/*Constructor de la clase c�rculo
 * @param cx centro de x
 * @param cy centro de y
 * @param r radio*/
public Circulo(double cx, double cy, double r) {              
	centroX = cx;              
	centroY = cy;              
	radio = r; 
} 

/*metodo para obtener el centro del c�rculo*/
public double getCentroX() {              
	return centroX; 
} 

/*m�todo para hayar la circunferencia del c�rculo*/
public double getCircunferencia() {              
	return 2 * Math.PI * radio; 
} 
/*m�todo para mover el centro del c�rculo
 * @param deltaX modificador de distancia para centroX
 * @param deltaY modificador de distancia para centroY 
 */
public void mueve(double deltaX, double deltaY) {              
	centroX = centroX + deltaX;               
	centroY = centroY + deltaY; 
} 
/*M�todo para hayar la escala del c�rculo
 *@param s valor de la escala para el c�rculo
 */
 public void escala(double s) {               
	 radio = radio * s; 
 } 
} 
