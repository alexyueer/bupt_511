package bupt.liang.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import bupt.liang.tool.FileToByte;
import bupt.liang.tool.IntByte;
import bupt.liang.tool.Parameter;

public class ProcessFile {
	public ArrayList<FileDataPackage> fileList = new ArrayList<FileDataPackage>();
	public byte[] fileData;
	public byte[] length = new byte[4];
	public byte[] info;
	public ProcessFile(String filePath, byte[] dst)
	{
		this.fileData = FileToByte.getBytes(filePath);
		this.fileList = Split(this.fileData, dst);
		this.length = IntByte.intTo4Byte(this.fileData.length);
		byte[] packNum = IntByte.intToBytes(fileList.size());
		File tempFile =new File( filePath.trim());  
	    byte[] fileName = tempFile.getName().getBytes(); 
		this.info = new byte[6 + fileName.length];
		System.arraycopy(length, 0, info, 0, 4);
		System.arraycopy(packNum, 0, info, 4, 2);
		System.arraycopy(fileName, 0, info, 6, fileName.length);
	}
	public  ArrayList<FileDataPackage> Split(byte[] datagram, byte[] dst)
	{
		int chunks = datagram.length / (Packages.DATALENGTH-2);
		int remainder = datagram.length % (Packages.DATALENGTH-2);
		ArrayList<FileDataPackage> packages = new ArrayList<FileDataPackage>();
		int total = chunks;
		if ( remainder > 0) 
			total =total +1;
		for(int i=0; i < chunks; i++)
		{
		    byte[] data = new byte[Packages.DATALENGTH];
		    byte[] sequ = IntByte.intToBytes(i);
		    data[0] = sequ[0];
		    data[1] = sequ[1];
		    System.arraycopy(datagram, i*(Packages.DATALENGTH-2), data, 2, Packages.DATALENGTH-2);
		    FileDataPackage pack = new FileDataPackage(Parameter.FILEDATA,dst,data);
			packages.add(pack);
		}
		if(remainder > 0)
		{   
		    byte[] data = new byte[remainder +2];
		    Arrays.fill(data, (byte)0);
		    byte[] sequ = IntByte.intToBytes(chunks);
		    data[0] = sequ[0];
		    data[1] = sequ[1];		    
		    System.arraycopy(datagram, chunks*(Packages.DATALENGTH-2), data, 2, remainder);
		    FileDataPackage pack = new FileDataPackage(Parameter.FILEDATA,dst,data);
			packages.add(pack);
		}
		return packages;
	}

}
