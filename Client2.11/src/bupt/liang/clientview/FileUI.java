package bupt.liang.clientview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bupt.liang.SendPackages;
import bupt.liang.model.Computer;
import bupt.liang.tool.Parameter;
public class FileUI extends JFrame implements ActionListener{
 public static JLabel jLabel;
 public static JTextField pathField;
 public static JTextField dstIDField;
 public static JTextArea sendInfoArea;
 public static JButton jButton;
 public static JButton sendButton;
 public static FileUI fileUI = null;
 
 public static String[] receiveOver= {"false"};
 public static boolean comment = false;
 
 
 public static List<Computer> selectedList; 
 
public FileUI()
{
   super();
   this.setSize(1000, 600);
   this.getContentPane().setLayout(null);
   this.add(getJLabel(), null);
   this.add(getJTextField(), null);
//   this.add(getJLabel2(), null);
//   this.add(getJTextField2(), null);
   this.add(getJLabel3(), null);
   this.add(getJTextField3(), null);
   this.add(getJLabel5(), null);
   this.add(getJTextArea(), null);
   sendButton = getJButton();
   this.add(sendButton, null);
   this.add(getBackJButton(), null);
   this.add(fileButton(), null);
   //this.add(getJFileChooser(), null);
   this.setTitle("File");
}

public FileUI(java.util.List<Computer> list) {
	   super();
	   selectedList = list;
	   this.setSize(1000, 600);
	   this.getContentPane().setLayout(null);
	   this.add(getJLabel(), null);
	   this.add(getJTextField(selectedList), null);
	   this.add(getJLabel3(), null);
	   this.add(getJTextField3(), null);
	   this.add(getJLabel5(), null);
	   this.add(getJTextArea(), null);
	   sendButton = getJButton();
	   this.add(sendButton, null);
	   this.add(getBackJButton(), null);
	   this.add(fileButton(), null);
	   this.setTitle("File");
	}


public static javax.swing.JLabel getJLabel() {
      jLabel = new javax.swing.JLabel();
      jLabel.setBounds(34, 49, 53, 18);
      jLabel.setText("dstID:");
   
   return jLabel;
}

public static javax.swing.JLabel getJLabel2() {
	  
	      jLabel = new javax.swing.JLabel();
	      jLabel.setBounds(180, 49, 53, 18);
	      jLabel.setText("端口号:");
	   
	   return jLabel;
	}
public static javax.swing.JLabel getJLabel3() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(34, 99, 60, 18);
    jLabel.setText("文件路径:");
 
 return jLabel;
}

public static javax.swing.JLabel getJLabel5() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(34, 159, 73, 18);
    jLabel.setText("发送内容:");
 
 return jLabel;
}

public static JTextField getJTextField(java.util.List<Computer> list) {
	  
	dstIDField = new javax.swing.JTextField();
	dstIDField.setBounds(80, 49, 180, 18);
	String s="";
	for(int i =0;i<list.size();i++){
		s = s + list.get(i).getID() + " ";
	}
	dstIDField.setText(s);

	return dstIDField;
}

public static javax.swing.JTextField getJTextField() {
	  
	dstIDField = new javax.swing.JTextField();
	dstIDField.setBounds(80, 49, 180, 18);
	dstIDField.setText("0");
 
 return dstIDField;
}



public static javax.swing.JTextField getJTextField3() {
	  
	pathField = new javax.swing.JTextField();
	pathField.setBounds(94, 99, 160, 20);
	pathField.setText("");
 
 return pathField;
}


public static javax.swing.JTextArea getJTextArea() {
	  
	sendInfoArea = new javax.swing.JTextArea();
	sendInfoArea.setBounds(96, 129, 550, 350);
	sendInfoArea.setText("");
 
 return sendInfoArea;
}


public  javax.swing.JFileChooser getJFileChooser(){


	JFileChooser fc = new JFileChooser();
	fc.setDialogTitle("请选择要上传的文件...");
	fc.setApproveButtonText("确定");
	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
		pathField.setText(fc.getSelectedFile().getPath());
		Parameter.sourceFilePath = pathField.getText();
	}
	return fc;
}


public  javax.swing.JButton fileButton() {
	   
      jButton = new javax.swing.JButton();
      jButton.setBounds(280, 99, 90, 20);
      jButton.setText("选择文件");
      
      jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getJFileChooser();
		}
	});
	   
	   return jButton;
}

public static javax.swing.JButton getJButton() {
   
      jButton = new javax.swing.JButton();
      jButton.setBounds(400, 99, 71, 20);
      jButton.setText("发送");
      jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(selectedList.size() != 0){
				for(int i=0; i<selectedList.size(); i++){
					Computer computer = selectedList.get(i);
					
					//下面这些是参数的设置
					if(SetupUI.ipCheck(computer.getIp()))
					{
						Parameter.serverIP = computer.getIp();
						Parameter.setflag = true;
					//	warningDialog("ip地址错误");
					}
					
					//下面这些是每次发送的设置
					System.out.println(Parameter.sourceFilePath);
					try{
						Parameter.dstID = Integer.parseInt(computer.getID());
					}catch(NumberFormatException e1)
					{
						Parameter.dstID = -1;
					}
					if(Parameter.setflag && Parameter.sourceFilePath != null && Parameter.dstID != -1)
					{
			        	SendPackages sendTool = new SendPackages();
		        	
						sendTool.sendFile((byte)Parameter.dstID,Parameter.sourceFilePath);
						
						while(!comment){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							comment = true;
						}
						 
			        	
					}else if(!Parameter.setflag)
					{
						sendInfoArea.setText("设置错误");
					}else if(Parameter.sourceFilePath != null){
						sendInfoArea.setText("未选择文件");
					}else if(Parameter.dstID != -1)
					{
						sendInfoArea.setText("未填写dstID");
					}
				}
			}

		}
	});
   
   return jButton;
}
public javax.swing.JButton getBackJButton() {
	  
	      jButton = new javax.swing.JButton("返回");
	      jButton.setBounds(500, 99, 71, 20);
	      jButton.addActionListener(this);
	   return jButton;
	}


//public static void main(String[] args)
//{
//   fileUI = new FileUI();
//   fileUI.setVisible(true);
//}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if("返回".equals(e.getActionCommand())){
		fileUI.setVisible(false);
		MenuView.menuView.setVisible(true);
	}
}

}