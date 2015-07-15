package bupt.liang.model;

import bupt.liang.tool.IntByte;


public class FileDataPackage extends Packages{
	public byte[] sequence = new byte[2];
	public int seq;
	public byte[] fileData;
	/*
	 * 文件数据包类型 00BB
	 *  类型  		长度	            目的ID    源ID	  序列号          数据	CRC
	 *  2字节          2字节		2字节		2字节                  2字节                      
	 * 
	 */
	public FileDataPackage(int ty, byte[] dst,byte[] da) {
		super(ty,dst, da);
		this.sequence[0] = da[0];
		this.sequence[1] = da[1];
		this.seq = IntByte.byte2ToInt(this.sequence);
		this.fileData = new byte[this.len -2];
		System.arraycopy(this.data, 2, this.fileData, 0, this.len - 2);		
		// TODO Auto-generated constructor stub
	}
	public FileDataPackage(byte[] temp) {
		// TODO Auto-generated constructor stub
		super(temp);
		this.sequence[0] = this.data[0];
		this.sequence[1] = this.data[1];
		this.fileData = new byte[this.len -2];
		this.seq = IntByte.byte2ToInt(sequence);
		System.arraycopy(this.data, 2, this.fileData, 0, this.len - 2);
	}
		
}
