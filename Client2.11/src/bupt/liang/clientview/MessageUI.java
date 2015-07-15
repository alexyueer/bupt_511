package bupt.liang.clientview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bupt.liang.SendPackages;
import bupt.liang.model.Computer;
import bupt.liang.tool.Parameter;


public class MessageUI extends JFrame implements ActionListener{
 public static JLabel jLabel;
 public static JTextField ipTextField;
 public static JTextField portTextField;
 public static JTextField idTextField;
 public static JTextArea sndText;
 public static JTextArea sendInfo;
 public static JButton jButton;
 public static MessageUI messageUI = null;
 
 public static List<Computer> selectedList;
 
public MessageUI()
{
   super();
   this.setSize(1000, 600);
   this.getContentPane().setLayout(null);
//   this.add(getJLabel(), null);
//   this.add(getJTextField(), null);
//   this.add(getJLabel2(), null);
//   this.add(getJTextField2(), null);
   this.add(getJLabel3(), null);
   this.add(getJTextField3(), null);
   this.add(getJLabel5(), null);
   this.add(getJTextArea(), null);
   this.add(getJLabel6(), null);
   this.add(getJTextArea1(), null);
   this.add(getJButton(), null);
   this.add(getBackJButton(), null);
   this.setTitle("Message");
}

public MessageUI(List<Computer> list)
{
   super();
   selectedList = list;
   this.setSize(1000, 600);
   this.getContentPane().setLayout(null);
//   this.add(getJLabel(), null);
//   this.add(getJTextField(), null);
//   this.add(getJLabel2(), null);
//   this.add(getJTextField2(), null);
   this.add(getJLabel3(), null);
   this.add(getJTextField3(), null);
   this.add(getJLabel5(), null);
   this.add(getJTextArea(), null);
   this.add(getJLabel6(), null);
   this.add(getJTextArea1(), null);
   this.add(getJButton(), null);
   this.add(getBackJButton(), null);
   this.setTitle("Message");
}

public static javax.swing.JLabel getJLabel() {
      jLabel = new javax.swing.JLabel();
      jLabel.setBounds(34, 49, 53, 18);
      jLabel.setText("IP:");
   
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
    jLabel.setBounds(34, 99, 53, 18);
    jLabel.setText("ID号:");
 
 return jLabel;
}

public static javax.swing.JLabel getJLabel5() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(34, 159, 70, 100);
    jLabel.setText("发送内容:");
 
 return jLabel;
}
public static javax.swing.JLabel getJLabel6() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(34, 379, 70, 100);
    jLabel.setText("发送过程:");
 
 return jLabel;
}

public static javax.swing.JTextField getJTextField() {
      ipTextField = new javax.swing.JTextField();
      ipTextField.setBounds(96, 50, 80, 20);
      ipTextField.setText("");
   
   return ipTextField;
}

public static javax.swing.JTextField getJTextField2() {
	  
	      portTextField = new javax.swing.JTextField();
	      portTextField.setBounds(236, 50, 150, 20);
	      portTextField.setText("");
	   
	   return portTextField;
	}

public static javax.swing.JTextField getJTextField3() {
	  
    idTextField = new javax.swing.JTextField();
    idTextField.setBounds(94, 99, 80, 20);
    String s="";
    if(selectedList.size()!=0){
    	for(int i=0; i<selectedList.size(); i++){
    		s = s + selectedList.get(i).getID() + " ";
    	}
    }
    idTextField.setText(s);
 
 return idTextField;
}


public static javax.swing.JTextArea getJTextArea() {
	  
	sndText = new javax.swing.JTextArea();
	sndText.setBounds(120, 129, 550, 150);
	sndText.setText("");
 
 return sndText;
}

public static javax.swing.JTextArea getJTextArea1() {
	  
	sendInfo = new javax.swing.JTextArea();
	sendInfo.setBounds(120, 379, 550, 150);
	sendInfo.setText("");
 
 return sendInfo;
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
					Parameter.serverIP = computer.getIp();
					try{
						Parameter.dstID = Integer.parseInt(computer.getID());
					}catch(NumberFormatException e1)
					{
						Parameter.dstID = -1;
					}
					if(Parameter.dstID == -1)
					{
						sendInfo.setText(sendInfo.getText()+"\n"+ "dstID输入错误");
					}
					else if(sndText.getText() == null)
					{
						sendInfo.setText(sendInfo.getText()+"\n"+ "未输入信息");
					}else
					{
			        	SendPackages sendTool = new SendPackages();
			        	//sendTool.sendAskForPosition(Parameter.dstID);
			        	//sendTool.sendShortMessage(IntByte.intToBytes(Parameter.dstID), IntByte.intToBytes(Parameter.serverID),sndText.getText().getBytes(),Parameter.serverIP);
			        	sendTool.sendShortMessage(Parameter.dstID, sndText.getText());
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



@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
		if("返回".equals(e.getActionCommand())){
			messageUI.setVisible(false);
			MainUI.mainUI.setVisible(true);
		}
}

}