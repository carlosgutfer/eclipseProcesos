package invertirCadena;

public class invertirCadena {
	
	public static void main (String [] args) 
	{
		char aux;
		char fraseInvertida [] = args[0].toCharArray();
		String fraseFinal = "";
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
	}

}
