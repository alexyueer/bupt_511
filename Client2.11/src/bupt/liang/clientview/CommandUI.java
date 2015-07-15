package bupt.liang.clientview;

import javax.swing.*;

import bupt.liang.SendPackages;

import java.awt.event.*;
@SuppressWarnings("serial")
public class CommandUI extends JFrame implements ActionListener,ItemListener{
 public static JLabel jLabel;
 public static JTextField contentField;
 public static JTextField lengthField;
 public static JTextField jiaoyanField;
 public static JTextArea jTextArea;
 public static JButton jButton;
 public static CommandUI commandUI =  null;
 public static int type = -1;
 @SuppressWarnings("rawtypes")
public static JComboBox comboBox;
 public static String command;
 
public CommandUI()
{
   super();
   this.setSize(1000, 600);
   //this.getContentPane().setLayout(null);
   this.add(getJLabel(), null);
   this.add(getJLabel2(), null);
   this.add(getJTextField2(), null);
//   this.add(getJLabel3(), null);
//   this.add(getJTextField3(), null);
//   this.add(getJLabel4(), null);
//   this.add(getJTextField4(), null);
   this.add(getJLabel5(), null);
   this.add(getJTextArea(), null);
   this.add(getJButton(), null);
   this.add(getBackJButton(), null);
   this.getContentPane().add(getJPanel());
   this.setTitle("Command");
}

public static javax.swing.JLabel getJLabel() {
      jLabel = new javax.swing.JLabel();
      jLabel.setBounds(34, 49, 53, 18);
      jLabel.setText("������:");
   
   return jLabel;
}

public static javax.swing.JLabel getJLabel2() {
	  
	      jLabel = new javax.swing.JLabel();
	      jLabel.setBounds(190, 49, 53, 18);
	      jLabel.setText("����:");
	   
	   return jLabel;
	}
public static javax.swing.JLabel getJLabel3() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(390, 49, 53, 18);
    jLabel.setText("����:");
 
 return jLabel;
}
public static javax.swing.JLabel getJLabel4() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(520, 49, 53, 18);
    jLabel.setText("У����:");
 
 return jLabel;
}

public static javax.swing.JLabel getJLabel5() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(34, 99, 73, 18);
    jLabel.setText("��������:");
 
 return jLabel;
}


public javax.swing.JPanel getJPanel() {

	JPanel contentPane=new JPanel();
	contentPane.setLayout(null);
	//JLabel label=new JLabel("֤������:");
	//contentPane.add(label);
	String s[] = { "0x0001���ز�������","0x0002��ʼ����","0x0003Ԥ���·�","0x0004����IP��ַ","0x0005����ϵͳ��ʼʱ��","0x0006����������","0x0007������ƵƵ��������",
			"0x0008������Ƶ��������","0x0009����������Ƶͼ��","0x000A����������Ƶͼ��","0x000Bƽ̨������Ϣ","0x000C���ʿ��Ʋ���","0x000Dͨ��˥������","0x000ENPG��IP��Ӧ��","0x000F��Ϣ������NPG��Ӧ��",
			"0x0011ͬ��ͷ��Ƶͼ��","0x0012ͬ��ͷ��Ƶͼ��","0x0013������ƵƵ�㼯","0x0014����������Ƶ��","0x0015��֪����","0x0016��֪�ر�","0x0017Ƶ�ʼ���ѯ","0x0018��֪����",
			"0x0019������Ϣ��־","0x001A����Ƶ��","0x001B�ر�Ƶ��","0x001CƵ����Ϣ","0x001D��ʼʱ϶��־","0x8001���ز��������Ӧ��","0x8002��ʼ����Ӧ��","0x8003Ԥ��Ӧ��",
			"0x8004����IP��ַӦ��","0x8005����ϵͳ��ʼʱ��Ӧ��","0x8006����������Ӧ��","0x8007������ƵƵ��������Ӧ��","0x8008������Ƶ��������Ӧ��","0x8009������Ƶͼ��Ӧ��","0x800A������Ƶͼ��Ӧ��","0x800Bƽ̨������Ϣ",
			"0x800C���ʿ��Ʋ���Ӧ��","0x800Dͨ��˥������Ӧ��","0x800ENPG��IP��ӦӦ��","0x800F��Ϣ������NPG��Ӧ��Ӧ��","0x8010�������","0x8011ͬ��ͷ��Ƶͼ��Ӧ��","0x8012ͬ��ͷ��Ƶͼ��Ӧ��","0x8013����������ƵƵ�㼯Ӧ��",
			"0x8014����������Ƶ��Ӧ��","0x8015��֪����Ӧ��","0x8016��֪�ر�Ӧ��","0x8017Ƶ�ʼ���Ӧ"};
    comboBox=new JComboBox(s);
    comboBox.addItemListener(this);
    comboBox.setBounds(80, 45, 100, 30);
    contentPane.add(comboBox);

   
   return contentPane;
}

public static javax.swing.JTextField getJTextField2() {
	  
	contentField = new javax.swing.JTextField();
	contentField.setBounds(236, 50, 150, 20);
	contentField.setText("");
	   
	   return contentField;
	}

public static javax.swing.JTextField getJTextField3() {
	  
    lengthField = new javax.swing.JTextField();
    lengthField.setBounds(436, 50, 80, 20);
    lengthField.setText("");
 
 return lengthField;
}
public static javax.swing.JTextField getJTextField4() {
	  
    jiaoyanField = new javax.swing.JTextField();
    jiaoyanField.setBounds(570, 50, 80, 20);
    jiaoyanField.setText("");
 
 return jiaoyanField;
}

public static javax.swing.JTextArea getJTextArea() {
	  
	jTextArea = new javax.swing.JTextArea();
	jTextArea.setBounds(96, 99, 550, 350);
	jTextArea.setText("");
 
 return jTextArea;
}


public static javax.swing.JButton getJButton() {
   if(jButton == null) {
      jButton = new javax.swing.JButton();
      jButton.setBounds(690, 50, 71, 20);
      jButton.setText("����");
      jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			 byte data[] = contentField.getText().getBytes();
	         command=(String)comboBox.getSelectedItem();
	         command = command.substring(2, 6);
	         type = 0;
	         for(int i =0;i < 4;i++)
	         {
	        	 type = type*16 + (command.charAt(i) -'0');
	         }
		     SendPackages sendTool = new SendPackages();
		    // sendTool.sendCommand(type,data,Parameter.serverIP);
		}
	});
   }
   return jButton;
}
public javax.swing.JButton getBackJButton() {
	      jButton = new javax.swing.JButton("����");
	      jButton.setBounds(780, 50, 71, 20);
	      jButton.addActionListener(this);
	   return jButton;
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if("����".equals(e.getActionCommand())){
		commandUI.setVisible(false);
		MainUI.mainUI.setVisible(true);
	}
}

@Override
public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
	if(e.getStateChange() == ItemEvent.SELECTED)
    {
         command=(String)comboBox.getSelectedItem();
         command = command.substring(2, 6);
         type = 0;
         for(int i =0;i < 4;i++)
         {
        	 type = type*16 + (command.charAt(i) -'0');
         }
    }
}

}