package bupt.liang.sendthread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import bupt.liang.tool.Parameter;

public class ShortMessageThread extends Thread{
	
	private DatagramPacket sendPacket = null;
	private DatagramSocket sendSocket = null;
    public static boolean Comment = false;
    public static String[] flag= {"false"};
    
    
	public ShortMessageThread(DatagramSocket sorket,DatagramPacket packet){
		this.sendPacket = packet;
		this.sendSocket = sorket;
	}
	
	@Override
	public void run() {
		while(!Comment)
		{
			try {				
				sendSocket.send(sendPacket);
				synchronized (flag){
					if(flag[0]=="false")
					{
						flag.wait(Parameter.time * Parameter.Weight);
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
		}
}
