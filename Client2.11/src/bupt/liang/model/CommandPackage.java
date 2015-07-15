package bupt.liang.model;

import java.util.Arrays;

import bupt.liang.tool.IntByte;

public class CommandPackage {
	public byte type[] = new byte[2];
	public byte[] length = new byte[2]; 
	public byte[] data;
	public byte[] crc = new byte[2];
	public byte[] TotalData = new byte[Packages.LENGTH];
	
	public int len;
	public int ty;
	/*
	 *  命令字     长度     数据    CRC
	 *  2     2       2 
	 */
	public CommandPackage(byte[] pack)
	{
		//System.out.println("pack.length:" + pack[1]);	
		this.type[0] = pack[0];
		this.type[1] = pack[1];
		this.length[0] = pack[2];
		this.length[1] = pack[3];
		this.len = IntByte.byte2ToInt(this.length);
		this.data = new byte[this.len];
		System.arraycopy(pack, 4, this.data, 0, this.len);
		this.crc[0] = pack[Packages.LENGTH - 2];
		this.crc[1] = pack[Packages.LENGTH - 1];

		this.ty =IntByte.byte2ToInt(this.type);
		this.TotalData = pack;

	}
	public CommandPackage(int ty,byte[] da)
	{
		this.type = IntByte.intToBytes(ty);
		this.length = IntByte.intToBytes(da.length);
		this.len = da.length;
		this.data = da;
		this.ty = ty;
		
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
	public boolean checkCRC()
	{
		byte[] buff = new byte[Packages.LENGTH -2];
	//	System.arraycopy(this.data, 0, buff, 5, this.data.length);
		System.arraycopy(this.TotalData, 0, buff, 0, buff.length);
		byte[] newCRC = Packages.getCRC(buff);
		if( newCRC[0] == this.crc[0] && newCRC[1] == this.crc[1])
			return true;
		else 
			return false;
	}
}
