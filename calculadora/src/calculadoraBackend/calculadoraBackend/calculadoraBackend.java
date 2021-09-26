package calculadoraBackend;

public class calculadoraBackend {
	
	 public static void main(String[] args) {
	        
	        switch(args[0])
	        {
	            case "suma":
	                    System.out.println(Integer.parseInt(args[1]) + Integer.parseInt(args[2]));
	                break;
	            case "resta":
	                    System.out.println(Integer.parseInt(args[1]) - Integer.parseInt(args[2]));
	                break;
	            case "multiplicacion":
	                    System.out.println(Integer.parseInt(args[1]) * Integer.parseInt(args[2]));
	                break;
	            case "division":
	                try
	                {
	                    System.out.println(Integer.parseInt(args[1]) / Integer.parseInt(args[2]));
	                }catch(Exception e){System.out.println(e);}
	                break;
	            default:
	                System.out.println("El primer parámetro no es suma/resta/multiplicacion/divison");  
	        }      
	    }
}
