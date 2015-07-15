package bupt.liang.model;

import bupt.liang.tool.IntByte;

public class FileInfoPackage extends Packages {
	//
	public int fileSize;
	public byte[] size = new byte[4];
	public int pacNum;
	public byte[] packNum = new byte[2];
	public byte[] fileName;
	public int fileNameLength;
	
	/*
	 * 文件信息包格式：
	 *  类型  		长度	            目的ID    源ID		size	包个数		文件名      其他	CRC
	 *  2字节          2字节		2字节		2字节               4字节                  2字节             n个字节
	 */
	public FileInfoPackage(byte[] pack)
	{
		super(pack);
		System.arraycopy(this.data, 0, this.size, 0, 4);
		this.fileNameLength = super.len - 6;
		this.fileName = new byte[this.fileNameLength];
		System.arraycopy(this.data, 6,this.fileName, 0, this.fileNameLength);
		packNum[0] = this.data[4];
		packNum[1] = this.data[5];
		fileSize = IntByte.byte4ToInt(this.size);
		pacNum = IntByte.byte2ToInt(this.packNum);
	}
	
	public FileInfoPackage(int ty,byte[] dst,byte[] da)
	{
		super(ty,dst, da);
		System.arraycopy(this.data, 0, this.size, 0, 4);
		this.fileNameLength = this.len - 6;
		this.fileName = new byte[this.fileNameLength];
		System.arraycopy(this.data, 6,this.fileName, 0, this.fileNameLength);
		packNum[0] = da[4];
		packNum[1] = da[5];
		fileSize = IntByte.byte4ToInt(this.size);
		pacNum = IntByte.byte2ToInt(this.packNum);
	}

}
