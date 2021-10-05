package invertirCadena;

import java.util.Scanner;

public class invertirCadena {
	
	static String fraseFinal = ""; 

	public static void main (String [] args) 
	{
		char aux;
		Scanner sc = new Scanner(System.in);
		char fraseInvertida [];
		do 
		{
			 fraseInvertida = sc.nextLine().toCharArray();
		
			int arrayLength = fraseInvertida.length  - 1;
		
			for (int i = 0; i < fraseInvertida.length / 2; i++) 
			{
				aux = fraseInvertida[arrayLength - i] ;
				fraseInvertida[arrayLength - i] = fraseInvertida[i];
				fraseInvertida[i] = aux;
			}
			for(int j = 0; j < fraseInvertida.length; j ++)
				fraseFinal += fraseInvertida[j];
			System.out.println(fraseFinal);
		}while (fraseInvertida[0] != ('*'));
	}

}
