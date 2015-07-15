package bupt.liang.receivethread;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Grafic.MyFrame;
import bupt.liang.SendPackages;
import bupt.liang.clientview.FileUI;
import bupt.liang.model.Computer;
import bupt.liang.model.FileAckPackage;
import bupt.liang.model.FileDataPackage;
import bupt.liang.model.FileInfoPackage;
import bupt.liang.model.FileResendPackage;
import bupt.liang.model.Packages;
import bupt.liang.model.PositionPackage;
import bupt.liang.model.ShortMessage;
import bupt.liang.sendthread.AskForPositionThread;
import bupt.liang.sendthread.FileSendThread;
import bupt.liang.sendthread.ShortMessageThread;
import bupt.liang.tool.ByteToFile;
import bupt.liang.tool.IntByte;
import bupt.liang.tool.Parameter;
import bupt.liang.tool.ReadFile;

public class ReceiveDataThread extends Thread{
	
	public static final int BUFFER_SIZE = 1024;
	public static int receiveCount = 0;
	public static int totalCount = 0;
    private byte inBuf[] = null; //接收数据的缓冲数组
    private int positionNum = 0;//位置信息计算机数目
 //   private byte outBuf[] = null; //发送数据的缓冲数组
    //声明接受信息的数据报套结字
    private DatagramSocket receiveSocket = null;
    //声明接受信息的数据报
    private DatagramPacket receivePacket = null;
    @Override
	public void run() {
        System.out.println("服务器  启动接收文件线程。。。");
        try{
            inBuf = new byte[BUFFER_SIZE];
            receivePacket = new DatagramPacket(inBuf,inBuf.length);
            receiveSocket = new DatagramSocket(Parameter.DATA_RECEIVE);
            inBuf = null;
        }catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        int fileSize = 0;
        int last = 0;
        String fileName = null;
        boolean fl = false;
        Map<Integer, byte[]> packetMap=new HashMap<Integer, byte[]>();
        while (true) {
            if(receiveSocket == null){
                break;
            } else {
                try {
                    receiveSocket.receive(receivePacket);
                    
                    byte[] temp = Arrays.copyOfRange(receivePacket.getData(), 0, Packages.LENGTH);
                    Packages pac = new Packages(temp);
//            		for(byte b : temp)
//    				{
//    					System.out.printf("%x\t",b);
//    				}
//    				System.out.println();
                    if(!pac.checkCRC())
                    {
                    	System.out.println("CRC ERROR");
                    	continue;
                    }
                    //System.out.println(IntByte.byte2ToInt(pac.dstID) +""+ IntByte.byte2ToInt(pac.srcID));
                    
                    if((IntByte.byte2ToInt(pac.dstID) != Parameter.serverID))
                    {
                    	continue;
                    }
                    /*
                     * 接收文件
                     * 
                     */
                    //如果为文件的信息包
                    if(temp[0] == (byte)0xB8 && temp[1] == (byte)0x00)
                    { 
                    	FileInfoPackage pack = new FileInfoPackage(temp);
                    	totalCount = pack.pacNum;
                    	fileSize = pack.fileSize;
                    	fileName = new String(pack.fileName);
                    	fl = true;
                    	packetMap.clear();
                    	SendPackages tool = new SendPackages();
                    	String id = String.valueOf(IntByte.byte2ToInt(pac.srcID)); 
                    	List<Computer> fileComputerList = ReadFile.readTxtFile(Parameter.peizhiFilePath);
                    	Computer computer = ReadFile.findComputerById(id, fileComputerList);
                    	Parameter.serverIP = computer.getIp();
                    	System.out.println(Parameter.serverIP); 
                    	//发送Ack 00BA
                    	tool.sendACK(Parameter.FILEINFOACK,pac.srcID);
                    }
                    //接收到信息包的AcK
                    if(temp[0] == (byte)0xBA && temp[1] == (byte)0x00)
                    {
                    	
                    	synchronized (FileSendThread.flag)
                    	{
                    		
                    		FileSendThread.flag[0] = "true";
                    		FileSendThread.flag.notify();
                    	} 
                    }
                    	//接收到数据包
                    if(temp[0] == (byte)0xBB && temp[1] == (byte)0x00)
                    {
                    	FileDataPackage pack = new FileDataPackage(temp);
                    	if(!packetMap.containsKey(pack.seq)) {
                    		receiveCount++;
//                        	System.out.println("pack.se"+pack.seq+ "\t");
                        	packetMap.put(pack.seq,pack.fileData);
                    	};

                    }
                    //收到要求重发包 00BC
                    if(temp[0] == (byte)0xBC && temp[1] == (byte)0x00)
                    {
                    	FileAckPackage pack = new FileAckPackage(temp);
                    	last = pack.iterator;
                		LinkedList<Integer> reSendPack = new LinkedList<Integer>();
                		for(int i = 0; i<last; i++)
                		{
                			if(!packetMap.containsKey(i))
                				reSendPack.add(i);
                		}
                		//如果接收完毕 保存文件
                		if(last > (totalCount-1) && reSendPack.size() == 0 && fl )
                		{
                			System.out.println("end");
                			ByteToFile getFile = new ByteToFile(packetMap,fileSize);
                			//文件接收完毕
                			getFile.toFile(Parameter.tempPath,fileName);
                			synchronized (FileUI.receiveOver)
                        	{
                        		
                				FileUI.receiveOver[0] = "true";
                				FileUI.receiveOver.notify();
                        	}

                			totalCount = 0;
                			receiveCount = 0;
                			fl = false;
                		}
                    	SendPackages tool = new SendPackages();
                    	tool.sendFileResend(Parameter.FILERESENDACK,pack.srcID,reSendPack);
                    }
                    // 收到需要重新发送的包序号
                    if(temp[0] == (byte)0xBD && temp[1] == (byte)0x00)
                    {
                    	FileResendPackage pack = new FileResendPackage(temp);
                    	synchronized (FileSendThread.reSendSet)
                    	{
                    		FileSendThread.reSendSet.clear();
                    		FileSendThread.reSendSet.add(0);
                    		//将重发包序号放入 reSendSet
                    		for(int i =0;i < pack.reSendPackageNum;i++)
                    		{
                    			byte[] te={pack.data[i*2],pack.data[i*2+1]};
                    			FileSendThread.reSendSet.add(IntByte.byte2ToInt(te));
                				System.out.print(IntByte.byte2ToInt(te)+"\t");
                     		}
                        	FileSendThread.reSendSet.notify();
                    	} 
                    } 
                    /*
                     * 处理位置信息 
                     * 
                     */
                    if(temp[0] == (byte)0xB0 && temp[1] == (byte)0x00)
                    {
                    	
                    	PositionPackage pack = new PositionPackage(temp);
                    	SendPackages tool = new SendPackages();
                    	String id = String.valueOf(IntByte.byte2ToInt(pac.srcID)); 
                    	List<Computer> fileComputerList = ReadFile.readTxtFile(Parameter.peizhiFilePath);
                    	Computer computer = ReadFile.findComputerById(id, fileComputerList);
                    	Parameter.serverIP = computer.getIp();
                    	tool.sendPosition(IntByte.byte2ToInt(pack.srcID));
                    }
                    //收到位置信息 并保存
                  
                    if(temp[0] == (byte)0xB1 && temp[1] == (byte)0x00)
                    {
                    	//
                    	 PositionPackage pack = new PositionPackage(temp);
                    	 //接收到其他中断位置信息          id: pack.srcID
                    	 String position = new String(pack.data);
                    	 
                    	// System.out.println("----" + IntByte.byte2ToInt(pack.srcID)+ ","+position);
                    	 File file=new File(Parameter.OtherPositionPath);
                    	 RandomAccessFile fis = new RandomAccessFile(file, "rw");   
                         FileChannel fcin=fis.getChannel();    
                         FileLock flin=null;    
                         while(true){    
                             try {  
                                 flin = fcin.tryLock();  
                                 break;  
                             } catch (Exception e) {  
                                  System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒");   
                                  sleep(1000);    
                             }  
                               
                         } 
                         BufferedWriter writer = new BufferedWriter(new FileWriter(file,true)); 
                         try { 
                        	 positionNum++;
                        	 System.out.println("positionNum:" + positionNum);
                             // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                             writer.append(position + "\r\n");
                            // writer.newLine();
                         } catch (IOException e) {     
                             e.printStackTrace();     
                         }
                    	synchronized (AskForPositionThread.flag)
                    	{
                    		AskForPositionThread.flag[0] = "true";
                    		AskForPositionThread.flag.notify();
                    	}
                        flin.release();    
                        fcin.close();    
                        fis.close();    
                        fis=null; 
                        writer.flush();
                        writer.close();
                        if(Parameter.graficNum == positionNum){
                        	System.out.println("receive");
                        	if(null != MyFrame.myFrame){//已经存在，需要先让其不可见
                        		MyFrame.myFrame.setVisible(false);//不论存不存在先隐藏
                        		MyFrame.myFrame = null;
                        	}
                        	MyFrame.myFrame = new MyFrame();//重新绘制地图
                        	MyFrame.myFrame.setVisible(true);
                        	positionNum = 0;
                        	Parameter.graficNum = 0;
                        }
                    }
                    /*
                     * 位置信息结束
                     */
                    
                    /*
                     *	接收短消息 
                     */
                    if(temp[0] == (byte)0xC0 && temp[1] == (byte)0x00)
                    {
                    	
                    	ShortMessage pack = new ShortMessage(temp);
                    	SendPackages tool = new SendPackages();
                    	
                    	//收到的短消息的内容  为message
                    	String messgae = new String(pack.data);
                    	System.out.println("接收端接收到的"+messgae);
                    	
                    	String id = String.valueOf(IntByte.byte2ToInt(pac.srcID)); 
                    	List<Computer> fileComputerList = ReadFile.readTxtFile(Parameter.peizhiFilePath);
                    	Computer computer = ReadFile.findComputerById(id, fileComputerList);
                    	Parameter.serverIP = computer.getIp();
                    	
                    	tool.sendACK(Parameter.SHORTMESSAGEACK,pack.srcID);
                    }
                    if(temp[0] == (byte)0xC1 && temp[1] == (byte)0x00)
                    {
                    	//System.out.println("客户端。。。。端口 ：8888");
                    	synchronized (ShortMessageThread.flag)
                    	{
                    		ShortMessageThread.flag[0] = "true";
                    		ShortMessageThread.flag.notify();
                    	} 
                    }
                    
                    /*
                     * 接收短消息完毕
                     */
                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        }
    }
    public void closeSocket(){
    	
    }
}
