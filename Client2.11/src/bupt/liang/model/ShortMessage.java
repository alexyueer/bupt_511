package bupt.liang.model;

public class ShortMessage extends Packages{

//	public ShortMessage(byte[] pack) {
//		super(pack);
//	}
	byte[] message;
	public ShortMessage(int ty,byte[] dst,byte[] da){
		super(ty,dst,da);
		this.message = this.data;
	}

	public ShortMessage(byte[] pack) {
		// TODO Auto-generated constructor stub
		super(pack);
	}

}
