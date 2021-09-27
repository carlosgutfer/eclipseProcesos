package calculadoraBackend;

public class calculadoraBackend {
	
	
	//V1 recogiendo los datos que pasan desde el parámetro
	public static void main(String[] args) 
	 {
	     String datosEntrada [] = args[0].split(" ");
		 seleccionarOperacion(datosEntrada);
	 }
	
	private static void seleccionarOperacion(String[] args) {
		int resultado = 0;
		 boolean operacion = true; 
	        switch(args[0])
	        {
	            case "suma":
	                    resultado = suma(args);
	                break;
	            case "resta":
	                    resultado = resta(args);
	                break;
	            case "multiplicacion":
	                    resultado = multiplicacion(args);
	                break;
	            case "division":
	                try
	                {
	                    resultado = division(args);
	                }catch(Exception e){System.out.println(e);}
	                break;
	            default:
	            	System.out.println("E1"); 
	                operacion = false;
	        }
	        if (operacion)
	        	System.out.println(resultado);
	}
	
	private static int division(String[] args) {
		return  (Integer.parseInt(args[1]) / Integer.parseInt(args[2]));
	}

	private static int multiplicacion(String[] args) {
		return Integer.parseInt(args[1]) * Integer.parseInt(args[2]);
	}

	private static int resta(String[] args) {
		return Integer.parseInt(args[1]) - Integer.parseInt(args[2]);
	}

	private static int suma(String[] args) {
		return Integer.parseInt(args[1]) + Integer.parseInt(args[2]);
	}
	 	
	 //v2 con split y scanner
	 /*Scanner scanner = new Scanner(System.in);
	  * public static void main(String args []
	  * {
	  * String mensaje = scanner.nextLine();
	  * String[] elementos = mensaje.split(' ');
	  *  seleccionarOperacion(elementos);
	  * }
	  * 
	  */
}
