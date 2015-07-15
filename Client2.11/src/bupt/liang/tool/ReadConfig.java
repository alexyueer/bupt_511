package bupt.liang.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadConfig {
	public static String getPosition()
	{
		File file = new File(Parameter.PositionPath);
		String position = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			position = reader.readLine();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return position;
		
	}
}
