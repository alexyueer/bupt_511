package bupt.liang.model;

import java.util.Arrays;

import bupt.liang.tool.IntByte;


public class LocalPackage {
	public byte[] data;
	public LocalPackage(String str,int type)
	{
		byte[] da= str.getBytes();
		byte[] ty = IntByte.intTo4Byte(type);
		data = new byte[da.length + 5];
		System.arraycopy(da, 0, data, 4, da.length);
		for(int i =0;i<ty.length;i++)
		{
			data[3-i] = ty[i];
		}
		data[data.length - 1] = '\0';
	}
}
