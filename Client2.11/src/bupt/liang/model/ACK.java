
package bupt.liang.model;



public class ACK extends Packages{
//	public ShortMessage(byte[] pack) {
//	super(pack);
//}
	
public ACK(int ty,byte[] dst){
	super(ty, dst,"OK".getBytes());
}

public ACK(byte[] pack) {
	// TODO Auto-generated constructor stub
	super(pack);
}
}
