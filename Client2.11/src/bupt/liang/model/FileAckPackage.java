package bupt.liang.model;

import bupt.liang.tool.IntByte;

public class FileAckPackage extends Packages{
	public int iterator;
	public byte[] it = new byte[2];
	/*
	 * ack包内容： 要求接收方发送重新发送的包
	 * 类型 	长度	目的ID	源ID		iterator
	 * 2字节	2字节	2字节		2字节		2字节
	 * 
	 */
	public FileAckPackage(byte[] pack) {
		super(pack);
		System.arraycopy(this.data, 0, it, 0, 2);
		iterator = IntByte.byte2ToInt(it);
	}
	public FileAckPackage(int ty,byte[] dst,byte[] da)
	{
		super(ty,dst,da);
		System.arraycopy(da, 0, it, 0, 2);
		iterator = IntByte.byte2ToInt(it);
	}
	
	

}