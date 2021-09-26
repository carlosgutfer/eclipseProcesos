package Maximo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Maximob {
	
	public static void main(String args []) 
	{
		int 		maximo;
		File		maximoText;
				
		maximo = Integer.parseInt(args[0]);
		for(int i = 1; i < args.length; i++) 
			if(Integer.parseInt(args[i]) > maximo)
				maximo = Integer.parseInt(args[i]);
		maximoText = new File ("maximo.txt");
		try {
			maximoText.createNewFile();
			FileWriter fw = new FileWriter(maximoText);
			fw.write(String.valueOf(maximo));
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
