package Maximo;

public class Maximo {
	
	public static void main(String args []) 
	{
		int maximo;
		
		maximo = Integer.parseInt(args[0]);
		for(int i = 1; i < args.length; i++) 
			if(Integer.parseInt(args[i]) > maximo)
				maximo = Integer.parseInt(args[i]);
		System.out.println(maximo);
	}

}
