package bupt.liang.model;

import java.util.Arrays;
import java.util.Calendar;

import bupt.liang.tool.IntByte;
import bupt.liang.tool.Parameter;

public class StartClockPackage {
	public byte type[] = new byte[2];
	public byte[] length;
	public  int len;
	public byte[] data = new byte[6];
	public byte[] crc = new byte[2];
	public byte[] TotalData = new byte[Packages.LENGTH];
	public StartClockPackage()
	{
		this.type = IntByte.intToBytes(Parameter.STARTCLOCK);
		this.length = IntByte.intToBytes(7);
		this.len = 7;

		
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		data[0] = (byte) year;
		data[1] = (byte) (now.get(Calendar.MONTH) + 1);
		data[2] = (byte) now.get(Calendar.DAY_OF_MONTH); 
		data[3] = (byte) now.get(Calendar.HOUR_OF_DAY); 
		data[4] = (byte) now.get(Calendar.MINUTE); 
		data[5] = (byte) now.get(Calendar.SECOND); 
		
		byte[] buff = new byte[Packages.LENGTH -2];
		Arrays.fill(buff, (byte)0);
		buff[0] = type[0];
		buff[1] = type[1];
		buff[2] = this.length[0];
		buff[3] = this.length[1];
		System.arraycopy(data, 0, buff, 4, data.length);
		this.crc = Packages.getCRC(buff);
		
		System.arraycopy(buff, 0, this.TotalData, 0, buff.length);
		this.TotalData[Packages.LENGTH - 2] = crc[0];
		this.TotalData[Packages.LENGTH - 1] = crc[1];
	}
}
