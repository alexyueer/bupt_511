package bupt.liang.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import bupt.liang.model.Packages;

public class ByteToFile {
	public Map<Integer, byte[]> packetMap=new HashMap<Integer, byte[]>();
	public int fileSize;
	
	public ByteToFile(Map<Integer, byte[]> packet,int size)
	{
		this.packetMap = packet;
		this.fileSize = size;
	}
	
	public byte[] getBytes()
	{
		byte[] bfile = new byte[fileSize];
		Arrays.fill(bfile, (byte)0);
//		System.out.println("bfile.length:"+ bfile.length);
		Set<Map.Entry<Integer,byte[]>> entrySet = this.packetMap.entrySet();
		for(Iterator<Map.Entry<Integer,byte[]>> it = entrySet.iterator();it.hasNext();)
		{
			Map.Entry<Integer, byte[]> itm = it.next();
			byte[] value = itm.getValue();
			int key = itm.getKey();
			if(key*(Packages.DATALENGTH-2) + value.length <= fileSize )
			{
				System.arraycopy(value, 0, bfile, key*(Packages.DATALENGTH-2),value.length );
			}
		}
		return bfile;
	}
	public void toFile(String filePath,String fileName) {
		
		byte[] bfile = getBytes();
		getFile(bfile,filePath,fileName);
	}  
    public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }

}
