package bupt.liang.clientview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Grafic.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import bupt.liang.SendPackages;
import bupt.liang.model.Computer;
import bupt.liang.receivethread.ReceiveCommandThread;
import bupt.liang.receivethread.ReceiveDataThread;
import bupt.liang.tool.Parameter;
import bupt.liang.tool.ReadFile;

@SuppressWarnings("serial")
public class MenuView extends JFrame implements ItemListener, ActionListener {
	
	public static MenuView menuView = null;
	ReceiveDataThread receiveDataThread;
	ReceiveCommandThread commandThread;
	
	public static JCheckBox computerQuanxuan;
	public static JLabel jLabel;
	
	public static List<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
	public static List<JLabel> redPointList = new ArrayList<JLabel>();
	
	public static List<Computer> fileComputer = ReadFile.readTxtFile(Parameter.peizhiFilePath);
	public static List<Computer> selectedList = new ArrayList<Computer>();
	
	JMenuItem miFile = new JMenuItem("�ļ�");  
    JMenuItem miMessage = new JMenuItem("��Ϣ");  
    JMenuItem miVoice = new JMenuItem("��Ƶ");
    JMenuItem miVideo = new JMenuItem("��Ƶ");
    JMenuItem miPosition = new JMenuItem("λ��");
    JMenuItem RTT = new JMenuItem("RTT");
	
	public MenuView(){
		
	   List<JCheckBox> list1 = getComputerNames();
	   List<JLabel> list2 = getRedPoint();
       
//	   commandThread = new ReceiveCommandThread();
//	   commandThread.start();
	   receiveDataThread = new ReceiveDataThread();
	   receiveDataThread.start();
       
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setSize(240, 500);
	   this.getContentPane().setLayout(null);
	   this.add(getQuanxuan(), null);
	   for(int i=0; i<fileComputer.size(); i++){
		   this.add(list1.get(i));
	   }
	   for(int i=0; i<checkboxList.size(); i++){
		   this.add(list2.get(i));
	   }
       
       JMenuBar menuBar = new JMenuBar();
       menuBar.setLocation(100, 100);
       JMenu menuColor = new JMenu("����"); 
       menuBar.add(menuColor);  
       menuColor.add(miFile);  
       menuColor.add(miMessage);  
       menuColor.add(miVoice); 
       menuColor.add(miVideo); 
       menuColor.add(miPosition); 
       menuColor.add(RTT); 
       
       this.setJMenuBar(menuBar);
       miFile.addActionListener(this);  
       miMessage.addActionListener(this);  
       miVoice.addActionListener(this);  
       miVideo.addActionListener(this);
       miPosition.addActionListener(this);
       RTT.addActionListener(this);
       this.add(localID());
		//��ʼ��ȡ������Ϣ
		//Parameter.reader = new ReadSer();
		//Parameter.reader.init(Parameter.serverID);//��ʼ�������ļ�д�Լ��ĵ���λ����Ϣ
       this.setVisible(true);
	}
	
	public static JCheckBox getQuanxuan(){
		computerQuanxuan = new JCheckBox("ȫѡ");
		computerQuanxuan.setBounds(48, 49, 80, 30);
        computerQuanxuan.setSelected(false);
        computerQuanxuan.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object obj=e.getItem(); 
				List<JCheckBox> list = checkboxList;
                if(obj.equals(computerQuanxuan)){ 
                    if(computerQuanxuan.isSelected()){ 
                    	for(int i=0;i<checkboxList.size(); i++){
                    		list.get(i).setSelected(true);
        				}
                    }else{ 
                    	for(int i=0;i<checkboxList.size(); i++){
                    		list.get(i).setSelected(false);
        				}
                    } 
                } 	
			}
		});  
        return computerQuanxuan;
	}
	
	public List<JCheckBox> getComputerNames(){
		for( int i=0; i<fileComputer.size(); i++){
			final JCheckBox jcb = new JCheckBox("�����"+fileComputer.get(i).getID());
			jcb.setBounds(48, 74+25*i, 110, 30);
			jcb.addItemListener(new ItemListener() {				
				@Override
				public void itemStateChanged(ItemEvent e) {
					Object obj=e.getItem();				
					if(jcb.isSelected()){  
						System.out.println(jcb.getText() +"ѡ��");
						String id = jcb.getText().substring(3);
						//System.out.println(ReadFile.findComputerById(id, fileComputer).toString());
						selectedList.add(ReadFile.findComputerById(id, fileComputer));
					}else{
						System.out.println(jcb.getText() +"ȡ��");
						String id = jcb.getText().substring(3);
						selectedList.remove(ReadFile.findComputerById(id, fileComputer));
					}
				}
			});
			checkboxList.add(jcb);
		}
		return checkboxList;
	}
	
	public List<JLabel> getRedPoint(){
		for(int i=0; i<checkboxList.size(); i++){
			final JLabel jl = new JLabel();
			jl.setBounds(164, 74+25*i, 100, 30);
			jl.setText("��");
			jl.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					jl.setText("��");
					
				}
			});
			redPointList.add(jl);
		}
		return redPointList;
	}
	
	public JLabel localID(){
		JLabel j = new JLabel();
		j.setBounds(52, 10, 100, 30);
		for(int i=0; i<fileComputer.size(); i++){
			String ip = "";
			try {
				ip = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if(ip.endsWith(fileComputer.get(i).getIp())){
				try{					
					Parameter.serverID = Integer.parseInt(fileComputer.get(i).getID());
					
				}				
				catch(NumberFormatException e1)
				{
					Parameter.serverID = -1;
					if(Parameter.setflag)
					Parameter.setflag = false;
				}
				try{
					Parameter.serverPort = Integer.parseInt(fileComputer.get(i).getPort());
					
				}catch(NumberFormatException e1)
				{
					Parameter.serverPort = -1;
					if(Parameter.setflag)
					Parameter.setflag = false;
				}
				try{
					Parameter.time = Integer.parseInt(fileComputer.get(i).getSpeed());
				}catch(NumberFormatException e1)
				{
					Parameter.time = -1;
					if(Parameter.setflag)
					Parameter.setflag = false;
				}
				break;
			}
		}
		j.setText("����ID : " + Parameter.serverID);
		return j;
	}
	
	public static JButton getButtonFile(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("�ļ�");
		button.setLocation(205, 83);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("123");
				FileUI.fileUI = new FileUI(selectedList);
				FileUI.fileUI.setVisible(true);
				//System.out.println("aaaaaaaaa"+selectedList.size());
			}
		});
		return button;
	}
	public static JButton getButtonMessage(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("��Ϣ");
		button.setLocation(205, 113);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				MessageUI.messageUI = new MessageUI(selectedList);
//				MessageUI.messageUI.setVisible(true);
//				System.out.println("aaaaaaaaa"+selectedList.size());
			}
		});
		return button;
	}
	public static JButton getButtonVoice(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("��Ƶ");
		button.setLocation(205, 143);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		return button;
	}
	public static JButton getButtonVideo(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("��Ƶ");
		button.setLocation(205, 173);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		return button;
	}
	
	public static JButton getButtonPosition(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("λ��");
		button.setLocation(205, 203);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedList.size()!=0){//��ȡ�ļ����λ����Ŀ
					Parameter.graficNum = selectedList.size();
					for(int i = 0; i<selectedList.size(); i++){
						Computer computer = selectedList.get(i);
						//������Щ�ǲ���������
						if(SetupUI.ipCheck(computer.getIp()))
						{
							Parameter.serverIP = computer.getIp();
							Parameter.setflag = true;
						//	warningDialog("ip��ַ����");
						}
						try{
							Parameter.dstID = Integer.parseInt(computer.getID());
						}catch(NumberFormatException e1)
						{
							Parameter.dstID = -1;
						}
						if(Parameter.dstID == -1)
						{
							System.out.println("dstID�������");
						}
						else
						{
				        	SendPackages sendTool = new SendPackages();
				        	sendTool.sendAskForPosition(Parameter.dstID);
				        	try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				        //	sendTool.sendShortMessage(IntByte.intToBytes(Parameter.dstID), IntByte.intToBytes(Parameter.serverID),sndText.getText().getBytes(),Parameter.serverIP);
						}
					}
				}
				
			}
		});
		return button;
	}
	
	public static JButton getButtonSetup(){
		JButton button = new JButton();
		button.setSize(80, 30);
		button.setText("����");
		button.setLocation(205, 233);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetupUI.setupUI = new SetupUI();
				SetupUI.setupUI.setVisible(true);
			}
		});
		return button;
	}
	
	public static javax.swing.JLabel getJLabel() {
	      jLabel = new javax.swing.JLabel();
	      jLabel.setBounds(34, 49, 53, 18);
	      jLabel.setText("dstID:");
	   
	   return jLabel;
	}

	

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miFile) {  
			FileUI.fileUI = new FileUI(selectedList);
			FileUI.fileUI.setVisible(true);
			System.out.println("aaaaaaaaa"+selectedList.size());
      } else if (e.getSource() == miMessage) {  
    	  MessageUI.messageUI = new MessageUI(selectedList);
		  MessageUI.messageUI.setVisible(true);
      } else if (e.getSource() == miVoice) {  
    	  if(selectedList.size()==1){
    		  for(int i = 0; i<selectedList.size(); i++){
    			  Computer computer = selectedList.get(i);
    			  String ip = computer.getIp();
    			  new SendPackages().sendRealTime(3, ip);
    		  }
    	  }
    	  
           
      } else if (e.getSource() == miVideo) {  
    	  if(selectedList.size()==1){
    		  for(int i = 0; i<selectedList.size(); i++){
    			  Computer computer = selectedList.get(i);
    			  String ip = computer.getIp();
    			  new SendPackages().sendRealTime(1, ip);
    		  }
    	  }
      } else if (e.getSource() == miPosition) {  

			if(selectedList.size()!=0){
				Parameter.graficNum = selectedList.size();
				System.out.println("Parameter.graficNum:" + Parameter.graficNum);
				for(int i = 0; i<selectedList.size(); i++){
					
					Computer computer = selectedList.get(i);
					
					//������Щ�ǲ���������
					if(SetupUI.ipCheck(computer.getIp()))
					{
						Parameter.serverIP = computer.getIp();
						Parameter.setflag = true;
					//	warningDialog("ip��ַ����");
					}
					
					try{
						Parameter.dstID = Integer.parseInt(computer.getID());
					}catch(NumberFormatException e1)
					{
						Parameter.dstID = -1;
					}
					if(Parameter.dstID == -1)
					{
						System.out.println("dstID�������");
					}
					else
					{
			        	SendPackages sendTool = new SendPackages();
			        	sendTool.sendAskForPosition(Parameter.dstID);
			        	
			        	try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        //	sendTool.sendShortMessage(IntByte.intToBytes(Parameter.dstID), IntByte.intToBytes(Parameter.serverID),sndText.getText().getBytes(),Parameter.serverIP);
					}
				}
			}else{//��һ��Ҳûѡ�е�ʱ��,��ʾ�����ڵ�ͼ��
				Parameter.graficNum = 0;//����Ҫ��
				try {
					System.out.println("�Լ�");
					MyFrame.myFrame = new MyFrame();
					MyFrame.myFrame.setVisible(true);
					} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		
      }
      else if(e.getSource() == RTT){
    	  
      }
		
	}
	public static void main(String[] args) {
		menuView = new MenuView();
		menuView.setVisible(true);

	}

}