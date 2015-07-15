package bupt.liang.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadInfo {
	/*
	 * 从文件中得到配置信息 例如 serverID serverIP serverPort格式如下：
	 * serverID：1
	 * serverIP：10.10.10.1
	 * serverPort：5060
	 */
	public static void getInfoFromFile(String path)
	{
		File file = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			line = reader.readLine();
			Parameter.serverID = Integer.parseInt(line.split(":")[1]);
			line = reader.readLine();
			Parameter.serverIP = line.split(":")[1];
			line = reader.readLine();
			Parameter.serverPort = Integer.parseInt(line.split(":")[1]);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
