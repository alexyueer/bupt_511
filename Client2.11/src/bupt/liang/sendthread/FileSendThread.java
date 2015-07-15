package bupt.liang.sendthread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import bupt.liang.SendPackages;
import bupt.liang.model.FileAckPackage;
import bupt.liang.model.FileInfoPackage;
import bupt.liang.model.ProcessFile;
import bupt.liang.tool.IntByte;
import bupt.liang.tool.Parameter;

public class FileSendThread extends Thread{

	private byte dstID;
	private byte[] dstByte;
	private ProcessFile file;
	private InetAddress ip;
	
	private DatagramPacket sendPacket = null;
	private DatagramSocket sendSocket = null;
	private int iterator = 0;
	private SendPackages sendTool = new SendPackages();
	
	public static boolean Comment = false;
	public static String[] flag= {"false"};
	public static ArrayList<Integer> reSendSet = new ArrayList<Integer>();
		
	
	public FileSendThread(byte dst,String filePath)
	{
		this.dstID = dst;
		this.dstByte = IntByte.intToBytes(this.dstID);
		this.file  = new ProcessFile(filePath, this.dstByte);
		try {
			this.ip =  InetAddress.getByName(Parameter.serverIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		//���� oxoob8
		while(!Comment)
		{
			try {	
				FileInfoPackage infoPackage = new FileInfoPackage(Parameter.FILEINFO,this.dstByte,file.info);
				sendPacket = new  DatagramPacket(infoPackage.TotalPackage,infoPackage.TotalPackage.length,this.ip,Parameter.serverPort);
				sendSocket = new DatagramSocket(null);
				sendSocket.setReuseAddress(true);
				sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));
//				for(byte b : infoPackage.packageData)
//				{
//					System.out.printf("%x\t",b);
//				}
//				System.out.println();
				Thread.sleep(Parameter.time);
				//���� ox00b8  �ȴ���Ӧ
				sendSocket.send(sendPacket);
				synchronized (flag){
					if(flag[0]=="false")
					{
						flag.wait(Parameter.time * Parameter.Weight);
						System.out.println("flag[0]:"+flag[0]);
					}
					if(flag[0]=="true")
					{
						Comment = true;
						flag[0] = "false";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}   	    				
		}
		Comment = false;
		reSendSet.add(-1);
		while(!((reSendSet.size() == 1) && (iterator > file.fileList.size() -1)))
		{
			iterator = sendTool.sendPartFile(dstByte,ip, reSendSet,iterator,file.fileList,sendSocket);
			while(!Comment)
			{
    			try {		
					FileAckPackage infoPackage = new FileAckPackage(Parameter.FILEASKFORRESEND, this.dstByte, IntByte.intToBytes(iterator));
					sendPacket = new  DatagramPacket(infoPackage.TotalPackage,infoPackage.TotalPackage.length,this.ip,Parameter.serverPort);
//					byte[] temp = infoPackage.TotalPackage;
//	        		for(byte b : temp)
//					{
//						System.out.printf("%x\t",b);
//					}
//					System.out.println();
	        		Thread.sleep(Parameter.time);
					sendSocket.send(sendPacket);								
					synchronized (reSendSet){
						if(reSendSet.get(0)== -1)
						{
							reSendSet.wait(Parameter.time * Parameter.Weight);
						}
						if(reSendSet.get(0)==0)
						{
							System.out.println("reSendSet[0]:"+reSendSet.get(0));
							Comment = true;
							reSendSet.set(0,-1);
						}
					}
			
				} catch (Exception e) {
					e.printStackTrace();
				}   	    				
			}
			Comment = false;
		}
		System.out.println("send end");
	}
	
}
