package bupt.liang;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import bupt.liang.clientview.MessageUI;
import bupt.liang.model.ACK;
import bupt.liang.model.CommandPackage;
import bupt.liang.model.FileDataPackage;
import bupt.liang.model.FileResendPackage;
import bupt.liang.model.LocalPackage;
import bupt.liang.model.Packages;
import bupt.liang.model.PositionPackage;
import bupt.liang.model.StartClockPackage;
import bupt.liang.sendthread.AskForDistanceThread;
import bupt.liang.sendthread.AskForPositionThread;
import bupt.liang.sendthread.FileSendThread;
import bupt.liang.sendthread.ShortMessageThread;
import bupt.liang.tool.IntByte;
import bupt.liang.tool.Parameter;
import bupt.liang.tool.ReadConfig;

public class SendPackages {
	public void sendFile(int dst,String filePath)
	{
		Thread sendFileThread = new FileSendThread( (byte)dst,filePath);
		sendFileThread.start();
	}
	//
	public int sendPartFile(byte[] dst,InetAddress ip,ArrayList<Integer> reSendSet,int iterator,ArrayList<FileDataPackage> fileList,DatagramSocket sendSocket)
	{
		DatagramPacket sendPacket = null;
		for(int i = 1;i<reSendSet.size();i++)
		{
			try
			{
				byte[] temp = fileList.get(i).TotalPackage;
				sendPacket = new  DatagramPacket(temp,temp.length,ip,Parameter.serverPort);
//        		for(byte b : temp)
//				{
//					System.out.printf("%x\t",b);
//				}
        		Thread.sleep(Parameter.time);
//				System.out.println();
				sendSocket.send(sendPacket);
			}catch (Exception e) {
				e.printStackTrace();
				}
		}

		int sendSize = reSendSet.size() - 1;
		while(sendSize < Packages.WINDOW && iterator <fileList.size() )
		{
			try
			{
				byte[] temp = fileList.get(iterator).TotalPackage;
				sendPacket = new  DatagramPacket(temp,temp.length,ip,Parameter.serverPort);
				System.out.println(temp.length);
//        		for(byte b : temp)
//				{
//					System.out.printf("%x\t",b);
//				}
        		Thread.sleep(Parameter.time);
//				System.out.println();
				sendSocket.send(sendPacket);
				iterator++;
				sendSize++;
			}catch (Exception e) {
				e.printStackTrace();
				}
		}	
		return  iterator;
	}
	public void sendFileResend(int type, byte[] dstID, LinkedList<Integer> reSendPack) { 
		FileResendPackage messagePacket;
		if(reSendPack.size() == 0)
		{
			byte[] data = {0};
			messagePacket = new FileResendPackage(Parameter.FILERESENDACK,dstID,data);
		}else{
			byte[] data = new byte[reSendPack.size() *2];
			for(int i =0;i<reSendPack.size();i++)
			{
				byte[] temp = IntByte.intToBytes(reSendPack.get(i));
				data[i*2] =temp[0];
				data[i*2+1] = temp[1]; 				
			}
			messagePacket = new FileResendPackage(Parameter.FILERESENDACK,dstID,data);
		}

		try{
			InetAddress sendIP = InetAddress.getByName(Parameter.serverIP);
			DatagramPacket sendPacket =  new  DatagramPacket(messagePacket.TotalPackage,messagePacket.TotalPackage.length,sendIP,Parameter.serverPort);
			DatagramSocket sendSocket = new DatagramSocket(null);
			sendSocket.setReuseAddress(true);
			sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));
//			System.out.print("sendresendpac");
//    		for(byte b : messagePacket.TotalPackage)
//			{
//				System.out.printf("%x\t",b);
//			}
//			System.out.println();
			sendSocket.send(sendPacket);
			sendSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendACK(int type,byte[] dst)
	{
		try{
			InetAddress sendIP = InetAddress.getByName(Parameter.serverIP);
			ACK messagePacket = new ACK(type, dst);
			DatagramPacket sendPacket =  new  DatagramPacket(messagePacket.TotalPackage,messagePacket.TotalPackage.length,sendIP,Parameter.serverPort);
			DatagramSocket sendSocket = new DatagramSocket(null);
			sendSocket.setReuseAddress(true);
			sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));
			sendSocket.send(sendPacket);
//			System.out.println("ack");
//    		for(byte b : messagePacket.TotalPackage)
//			{
//				System.out.printf("%x\t",b);
//			}
//			System.out.println();
			sendSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void sendAskForPosition(int dstID)
	{
    	byte[] dst = IntByte.intToBytes(dstID);
    	
    	try{
    		InetAddress address = InetAddress.getByName(Parameter.serverIP);
    		byte[] data = {0};
    		PositionPackage pack = new PositionPackage(Parameter.ACKFORPOSITION, dst, data);
    		DatagramSocket sendSocket = new DatagramSocket(null);
    		sendSocket.setReuseAddress(true);
    		sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));	
    		DatagramPacket sendPacket =  new  DatagramPacket(pack.TotalPackage,pack.TotalPackage.length,address,Parameter.serverPort);
    		Thread  position=new AskForPositionThread(sendSocket,sendPacket);
    		position.start();			
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
	}

	public void sendPosition(int dstID) 
	{
		byte[] dst = IntByte.intToBytes(dstID);
		try{
			InetAddress sendIP = InetAddress.getByName(Parameter.serverIP);
			byte[] data = ReadConfig.getPosition().getBytes();
			PositionPackage pack = new PositionPackage(Parameter.GIVEPOSITION, dst, data);
			DatagramPacket sendPacket =  new  DatagramPacket(pack.TotalPackage,pack.TotalPackage.length,sendIP,Parameter.serverPort);
			DatagramSocket sendSocket = new DatagramSocket(null);
			sendSocket.setReuseAddress(true);
			sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));
			sendSocket.send(sendPacket);
//			System.out.println("ack");
//    		for(byte b : messagePacket.TotalPackage)
//			{
//				System.out.printf("%x\t",b);
//			}
//			System.out.println();
			sendSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public void sendCommandACk(boolean flag)
    {
    	byte[] data = new byte[Packages.LENGTH - 6];
    	byte da = 0;
    	if(!flag)
    	{
    		da = (byte) 0xFF;
    	}
    	Arrays.fill(data, da);
		try {
	    	CommandPackage pack = new CommandPackage(0x007F,data);
	    	DatagramSocket send = new DatagramSocket(null);
	    	send.setReuseAddress(true);
	    	send.bind(new InetSocketAddress(Parameter.COMMAND_SEND));	
			InetAddress address = InetAddress.getByName(Parameter.serverIP);
			DatagramPacket packet =  new  DatagramPacket(pack.TotalData,pack.TotalData.length,address,Parameter.serverPort);
			send.send(packet);
			send.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    public void sendStartClock()
    {
		try {
	    	StartClockPackage pack = new StartClockPackage();
	    	DatagramSocket send = new DatagramSocket(null);
	    	send.setReuseAddress(true);
	    	send.bind(new InetSocketAddress(Parameter.COMMAND_SEND));	
			InetAddress address = InetAddress.getByName(Parameter.serverIP);
			DatagramPacket packet =  new  DatagramPacket(pack.TotalData,pack.TotalData.length,address,Parameter.serverPort);
			send.send(packet);
			send.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void sendAskForDistance(int dstID)
	{
    	byte[] dst = IntByte.intToBytes(dstID);
    	try{
    		InetAddress address = InetAddress.getByName(Parameter.serverIP);
    		byte[] srcID = IntByte.intToBytes(Parameter.serverID);
    		byte[] data = {dst[0],dst[1],srcID[0],srcID[1]};
    		CommandPackage pack = new CommandPackage(Parameter.ASKFORDISTANCE, data);
    		DatagramSocket sendSocket = new DatagramSocket(null);
    		sendSocket.setReuseAddress(true);
    		sendSocket.bind(new InetSocketAddress(Parameter.COMMAND_SEND));	
    		DatagramPacket sendPacket =  new  DatagramPacket(pack.TotalData,pack.TotalData.length,address,Parameter.serverPort);
    		Thread  position=new AskForDistanceThread(sendSocket,sendPacket);
    		position.start();			
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
	}
//    public void sendRealTime(byte[] dst,int type)
//	{
//    	try{
//    		InetAddress address = InetAddress.getByName(Parameter.LOCALIP);
//    		LocalPackage pack = new LocalPackage(dst, type);
//    		DatagramSocket sendSocket = new DatagramSocket(null);
//    		sendSocket.setReuseAddress(true);
//    		sendSocket.bind(new InetSocketAddress(Parameter.COMMAND_SEND));	
//    		DatagramPacket sendPacket =  new  DatagramPacket(pack.data,pack.data.length,address,Parameter.serverPort);
//    		Thread  position=new AskForDistanceThread(sendSocket,sendPacket);
//    		position.start();			
//    		}catch (Exception e) {
//    			e.printStackTrace();
//    		}
//	}
    
    //发送短消息调用这个函数
    public void sendShortMessage(int dstID,String str)
	{
    	byte[] dst = IntByte.intToBytes(dstID);
    	byte[] data = str.getBytes();
    	try{
    		InetAddress address = InetAddress.getByName(Parameter.serverIP);
    		Packages messagePacket = new Packages(Parameter.SHORTMESSAGE,dst, data);
    		DatagramSocket sendSocket = new DatagramSocket(null);
    		sendSocket.setReuseAddress(true);
    		sendSocket.bind(new InetSocketAddress(Parameter.DATA_SEND));	
    		DatagramPacket sendPacket =  new  DatagramPacket(messagePacket.TotalPackage,messagePacket.TotalPackage.length,address,Parameter.serverPort);
    		Thread  Commendthread=new ShortMessageThread(sendSocket,sendPacket);
    		Commendthread.start();			
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
	}

    
    public void sendRealTime(int type, String dst)
	{
    	try{
    		Parameter.LOCALIP = InetAddress.getLocalHost().getHostAddress().toString();
    		InetAddress address = InetAddress.getByName(Parameter.LOCALIP);
    		LocalPackage pack = new LocalPackage(dst, type);
    		DatagramSocket sendSocket = new DatagramSocket(null);
    		sendSocket.setReuseAddress(true);
    		sendSocket.bind(new InetSocketAddress(8888));	
    		DatagramPacket sendPacket =  new  DatagramPacket(pack.data,pack.data.length,address,Parameter.LOCALPORT);
    		for(int i =0;i<pack.data.length;i++)
    			System.out.print(pack.data[i] +"\t");
    		System.out.println();
    		sendSocket.send(sendPacket);
		
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
	}
    
}
