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
	public static String longitude = null;//����
	public static String latitude = null;//γ��
	public static String high = null;//����
	public static ReadSer reader = null;
	public static String[] strGga;
	public static boolean flagGga = false;
	public static StringBuffer strBf = new StringBuffer();
	public static String str = new String();//���ַ���
	public static int flag = 0;
	public static int localId = 0;//���������id
	public static List<String[]> list = new ArrayList<String[]>();//���ڴ��strGga����ǰ��
	public void init(int id){
		try{
			localId = id;
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM3");//ȡ��com3�˿�
			System.out.println(portId.getName()+":����");
			Read reader = new Read(portId);
		}catch(Exception ex){
			System.out.println("com3�˿�δ��");
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
				in = serialPort.getInputStream();//��COM3��ȡ����
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
				serialPort.setSerialPortParams(9600,//������λ115200 
						SerialPort.DATABITS_8,//8λ����λ
						SerialPort.STOPBITS_1,//1λֹͣΪ
						SerialPort.PARITY_NONE);//��У��λ
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
		case SerialPortEvent.DATA_AVAILABLE://�˿��п������ݡ������������飬������ն�
			try{
				String s ="";
				Thread.sleep(5000);//�����ӻ�ȡһ�ε���λ����Ϣ
				while((s=in2.readLine())!=null){
					if(s.indexOf("GNGGA")!=-1){//GPGGAz���ָ�ʽ��ʱ��
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