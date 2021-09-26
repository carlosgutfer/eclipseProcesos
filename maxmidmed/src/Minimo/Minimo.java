package Minimo;

public class Minimo {
	
	public static void main(String args []) 
	{
		int minimo;
		
		minimo = Integer.parseInt(args[0]);
		for(int i = 1; i < args.length; i++) 
			if(Integer.parseInt(args[i]) < minimo)
				minimo = Integer.parseInt(args[i]);
		System.out.println(minimo);
	}

}
