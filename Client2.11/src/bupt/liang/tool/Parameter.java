package bupt.liang.tool;

import java.net.InetAddress;

import Grafic.ReadSer;

public class Parameter {
	/*
	 * 配置参数
	 */
    public static  String serverIP = null;
    public static int serverPort = -1;
    public static int serverID = -1;
    //速率
    public static int time = -1;
    public static String tempPath = "D://";  //文件接收位置
    public static String PositionPath = "D://PositionInfo.txt"; //从GPS得到的自身位置信息
    public static String OtherPositionPath ="D://GetPositionInfo.txt";//接收到其他终端的位置信息
    public static String peizhiFilePath = "src\\file\\file.txt";
    public static boolean repaintFlag = false;//重新绘制地图标识
    public static int graficNum = 0;//获取的计算机位置数
    public static ReadSer reader = null;//读取本机位置信息并写入文件
    /*
     * 
     */
    
    public static int Weight = 10;
    public static boolean setflag = false;
    public static int dstID = -1;
    
    public static  int LOCALPORT = 23456;
    public static  String LOCALIP = "127.0.0.1";
    public static int TerminalType = -1; //终端类型  类型分为四种：0 表示主时基，1 表示非主时基，2 表示普通终端，3 为其它
    
    public static int status = -1; // status  为-1 表示未开始  0 表示入网成功 
    public static int commandType = -1; // commandType 表示command的类型 0 表示启动 1 表示位置信息 用于区分 ack
    


    
    
    
    public static  String sourceFilePath = null;
    public static final int DATA_SEND = 5060;
    public static final int DATA_RECEIVE = 5061;
    
   

    public static final int COMMAND_SEND = 5040;  
    public static final int COMMAND_RECEIVE = 5041;
    
    public static final int SHORTMESSAGE = 0x00C0;
    public static final int SHORTMESSAGEACK = 0x00C1;
    
    public static final int FILEINFO = 0x00B8;
    public static final int FILEINFOACK = 0x00BA;
    public static final int FILEDATA = 0x00BB;    
    public static final int FILEASKFORRESEND = 0x00BC;
    public static final int FILERESENDACK = 0x00BD;
    
    public static final int ACKFORPOSITION = 0x00B0;
    public static final int GIVEPOSITION = 0x00B1;
    
    
    public static final int STARTCLOCK = 0x0001;
    public static final int ASKFORDISTANCE = 0x0004;

    

}