package bupt.liang.receivethread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import bupt.liang.SendPackages;
import bupt.liang.model.AskForDistance;
import bupt.liang.model.CommandPackage;
import bupt.liang.model.Packages;
import bupt.liang.model.SelfCheck;
import bupt.liang.sendthread.AskForDistanceThread;
import bupt.liang.tool.Parameter;

public class ReceiveCommandThread {
	public static final int BUFFER_SIZE = 1024;
	private byte inBuf[] = null;
	private DatagramSocket receiveSocket = null;

	private DatagramPacket receivePacket = null;
	public void run() {
		System.out.println("等待接收底层信号");
        try{
            inBuf = new byte[BUFFER_SIZE];
            receivePacket = new DatagramPacket(inBuf,inBuf.length);
            receiveSocket = new DatagramSocket(Parameter.COMMAND_RECEIVE);
            inBuf = null;
        }catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        while (true) {
            if(receiveSocket == null){
                break;
            }else{
                try {
					receiveSocket.receive(receivePacket);
					byte[] temp = Arrays.copyOfRange(receivePacket.getData(), 0, Packages.LENGTH);
                    CommandPackage pa = new CommandPackage(temp);
                    if(!pa.checkCRC())
                    {
                    	System.out.println("CRC ERROR");
                    	continue;
                    }
                    
                    //sefCheck message
                    if(temp[0] == (byte)0x01 && temp[1] == (byte)0x00)
                    {
                    	SelfCheck pack = new SelfCheck(temp);
                    	Parameter.commandType =0;
                    	Parameter.TerminalType = pack.terminalType;
                    	SendPackages sendTool = new SendPackages();
                    	sendTool.sendCommandACk(true);
                    	sendTool.sendStartClock();
                    }
                    //ack
                    if(temp[0] == (byte)0x7F && temp[1] == (byte)0x00)
                    {
                    	if(pa.data[0] == 0xFF)
                    		continue;
                    	//类型分为四种：0 表示主时基，1 表示非主时基，2 表示普通终端，3 为其它
                        
                    	if(Parameter.commandType == 0 && (Parameter.TerminalType != 1 ))
                    	{
                    		Parameter.status = 0;
                    		Parameter.commandType = -1;
                    	}
                    	if(Parameter.commandType == 1)
                    	{
                    		Parameter.commandType = -1;
                        	synchronized (AskForDistanceThread.flag)
                        	{
                        		
                        		AskForDistanceThread.flag[0] = "true";
                        		AskForDistanceThread.flag.notify();
                        	} 
                    	}
                    }
                    //入网成功
                    if(temp[0] == (byte)0x03 && temp[1] == (byte)0x00)
                    {
                    	if(Parameter.status != 0)
                    	{
                        	SendPackages sendTool = new SendPackages();
                        	sendTool.sendCommandACk(true);
                    		Parameter.status = 0;
                    	}
                    }
                    //收到距离
                    if(temp[0] == (byte)0x05 && temp[1] == (byte)0x00)
                    {
                    	if(Parameter.status == 0)
                    	{
                    		AskForDistance pack = new AskForDistance(temp);
                        	SendPackages sendTool = new SendPackages();
                        	sendTool.sendCommandACk(true);
                    	}
                    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}
}
