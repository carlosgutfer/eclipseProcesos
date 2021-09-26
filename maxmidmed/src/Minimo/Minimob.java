package Minimo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Minimob {
	
	public static void main(String args []) 
	{
		File	minimoText;
		int 	minimo;
		
		minimo = Integer.parseInt(args[0]);
		for(int i = 1; i < args.length; i++) 
			if(Integer.parseInt(args[i]) < minimo)
				minimo = Integer.parseInt(args[i]);
		minimoText = new File ("maximo.txt");
		try {
			minimoText.createNewFile();
			FileWriter fw = new FileWriter(minimoText);
			fw.write(String.valueOf(minimo));
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
