package Media;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Media {

	public static void main(String args []) 
	{
		double		media;
		
		media = 0;
		for(int i = 0; i < args.length; i++) 
			media += Integer.parseInt(args[i]);
		System.out.println(media / args.length);
		
	}
}
