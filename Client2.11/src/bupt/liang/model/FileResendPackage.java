package bupt.liang.model;

public class FileResendPackage extends Packages{
	public int reSendPackageNum;
	/*
	 * ack包内容：
	 * 类型 	长度	目的ID	源ID		序号
	 * 2字节	2字节	2字节		2字节		2字节
	 * 
	 */
	public FileResendPackage(byte[] pack) {
		super(pack);
		reSendPackageNum = this.len/2;
		// TODO Auto-generated constructor stub
	}

	public FileResendPackage(int ty,  byte[] dst,  byte[] da) {
		super(ty,dst, da);
		reSendPackageNum =  data.length/2;
	}

}
