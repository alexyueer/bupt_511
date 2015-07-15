package bupt.liang.model;

import bupt.liang.tool.IntByte;

public class SelfCheck extends CommandPackage{
	public byte terminalType;
	public byte terminalError;
	public byte terminalID[] = new byte[2];
	public int  tID;
	
	public  SelfCheck(byte[] pack)
	{
		super(pack);
		terminalType= this.data[0];
		terminalError = this.data[1];
		System.arraycopy(this.data, 2, this.terminalID, 0, 2);
		this.tID = IntByte.byte2ToInt(this.terminalID);
	}
	public SelfCheck(int dst,byte[] data)
	{
		super(dst, data);
		terminalType= this.data[0];
		terminalError = this.data[1];
		System.arraycopy(this.data, 2, this.terminalID, 0, 2);
		this.tID = IntByte.byte2ToInt(this.terminalID);
	}
}
