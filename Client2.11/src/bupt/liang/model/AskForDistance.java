package bupt.liang.model;

import bupt.liang.tool.IntByte;

public class AskForDistance extends CommandPackage{
	byte[] srcID = new byte[2];
	int src;
	byte[] dstID = new byte[2];
	int dst;
	byte[] dis = new byte[2];
	int distance;
			

	public AskForDistance(byte[] pack)
	{
		super(pack);
		System.arraycopy(this.data, 0, dstID, 0, 2);
		System.arraycopy(this.data, 2, srcID, 0, 2);
		System.arraycopy(this.data, 6, dis, 0, 2);
		this.src = IntByte.byte2ToInt(this.srcID);
		this.dst = IntByte.byte2ToInt(this.dstID);
		this.distance = IntByte.byte2ToInt(this.dis) * 15;
	}
	public AskForDistance(int type,byte[] data)
	{
		super(type,data);
		System.arraycopy(this.data, 0, dstID, 0, 2);
		System.arraycopy(this.data, 2, srcID, 0, 2);
		System.arraycopy(this.data, 6, dis, 0, 2);
		this.src = IntByte.byte2ToInt(this.srcID);
		this.dst = IntByte.byte2ToInt(this.dstID);
		this.distance = IntByte.byte2ToInt(this.dis) * 15;
	}

}
