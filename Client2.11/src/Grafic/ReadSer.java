package Grafic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import bupt.liang.tool.Parameter;

public class ReadSer{
	public static String longitude = null;//经度
	public static String latitude = null;//纬度
	public static String high = null;//经度
	public static ReadSer reader = null;
	public static String[] strGga;
	public static boolean flagGga = false;
	public static StringBuffer strBf = new StringBuffer();
	public static String str = new String();//空字符串
	public static int flag = 0;
	public static int localId = 0;//本计算机的id
	public static List<String[]> list = new ArrayList<String[]>();//用于存放strGga传回前端
	public void init(int id){
		try{
			localId = id;
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM3");//取得com3端口
			System.out.println(portId.getName()+":开启");
			Read reader = new Read(portId);
		}catch(Exception ex){
			System.out.println("com3端口未打开");
			ex.printStackTrace();
		}
	}
	class Read implements SerialPortEventListener{
		InputStream in;
		BufferedReader in2;
		SerialPort serialPort;
		Thread readThread;
		public Read(CommPortIdentifier portId) throws InterruptedException{
			try{
				serialPort = (SerialPort)portId.open("MyReader", 2000);
			}catch(PortInUseException ex){
				ex.printStackTrace();
			}
			try{
				in = serialPort.getInputStream();//从COM3获取数据
				in2 = new BufferedReader(new InputStreamReader(in));
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
			try{
				serialPort.addEventListener(this);
			}catch(TooManyListenersException ex){
				ex.printStackTrace();
			}
			
			serialPort.notifyOnDataAvailable(true);
			try{
				serialPort.setSerialPortParams(9600,//比特率位115200 
						SerialPort.DATABITS_8,//8位数据位
						SerialPort.STOPBITS_1,//1位停止为
						SerialPort.PARITY_NONE);//无校验位
			}catch(UnsupportedCommOperationException ex){
				ex.printStackTrace();
			}
	}
	public void serialEvent(SerialPortEvent event){
		switch(event.getEventType()){
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE://端口有可用数据。读到缓冲数组，输出到终端
			try{
				String s ="";
				Thread.sleep(5000);//五秒钟获取一次地理位置信息
				while((s=in2.readLine())!=null){
					if(s.indexOf("GNGGA")!=-1){//GPGGAz这种格式的时候
						strGga=s.trim().split(",");
						BufferedWriter out = new BufferedWriter(new FileWriter(Parameter.PositionPath));
				        out.write(localId + "," + strGga[4] + "," + strGga[2]);
				        out.close();
					}
				}	 
			}catch(IOException | InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	}
}