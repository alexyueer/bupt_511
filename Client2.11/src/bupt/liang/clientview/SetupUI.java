package bupt.liang.clientview;

import javax.swing.*;
import bupt.liang.tool.Parameter;

import java.awt.event.*;
public class SetupUI extends JFrame implements ActionListener{
	
	public static SetupUI setupUI = null;
	private static JLabel jLabel;
	private static JTextField ipTextField;
	private static JTextField portTextField;
	private static JTextField srcIDTextField;
	private static JTextField vField;
	public static JButton jButton;
 
public SetupUI()
{
   super();
   this.setSize(1000, 600);
   this.getContentPane().setLayout(null);
   this.add(getJLabel(), null);
   this.add(getJTextField(), null);
   this.add(getJLabel2(), null);
   this.add(getJTextField2(), null);
   this.add(getJLabel3(), null);
   this.add(getJTextField3(), null);
   this.add(getJLabel4(), null);
   this.add(getJTextField4(), null);
   this.add(getJButton(), null);
   this.add(getBackJButton(), null);
   this.setTitle("Setup");
}

public static javax.swing.JLabel getJLabel() {
      jLabel = new javax.swing.JLabel();
      jLabel.setBounds(34, 49, 53, 18);
      jLabel.setText("�˿ں�:");
   
   return jLabel;
}

public static javax.swing.JLabel getJLabel2() {
	  
	      jLabel = new javax.swing.JLabel();
	      jLabel.setBounds(180, 49, 53, 18);
	      jLabel.setText("IP:");
	   
	   return jLabel;
	}
public static javax.swing.JLabel getJLabel3() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(390, 49, 53, 18);
    jLabel.setText("srcID:");
 
 return jLabel;
}
public static javax.swing.JLabel getJLabel4() {
	  
    jLabel = new javax.swing.JLabel();
    jLabel.setBounds(610, 49, 70, 18);
    jLabel.setText("�������ʣ�");
 
 return jLabel;
}


public static javax.swing.JTextField getJTextField() {
	portTextField = new javax.swing.JTextField();
	portTextField.setBounds(96, 50, 80, 20);
	portTextField.setText("5061");
   
   return portTextField;
}

public static javax.swing.JTextField getJTextField2() {
	  
	ipTextField = new javax.swing.JTextField();
	ipTextField.setBounds(236, 50, 150, 20);
	ipTextField.setText("10.103.26.64");
	   
	   return ipTextField;
	}
public static javax.swing.JTextField getJTextField3() {
	  
	srcIDTextField = new javax.swing.JTextField();
	srcIDTextField.setBounds(450, 50, 150, 20);
	srcIDTextField.setText("1");
	   
	   return srcIDTextField;
	}
public static javax.swing.JTextField getJTextField4() {
	  
	vField = new javax.swing.JTextField();
	vField.setBounds(680, 50, 150, 20);
	vField.setText("1");
	   
	   return vField;
	}
public  javax.swing.JFileChooser getJFileChooser(){


	JFileChooser fc = new JFileChooser();
	fc.setDialogTitle("��ѡ��Ҫ�ϴ����ļ�...");
	fc.setApproveButtonText("ȷ��");
	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
		//pathField.setText(fc.getSelectedFile().getPath());
	}
	return fc;
}

public static javax.swing.JButton getJButton() {
	  
    jButton = new javax.swing.JButton("ȷ��");
    jButton.setBounds(400, 80, 60, 30);
    jButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(ipCheck(ipTextField.getText()))
			{
				Parameter.serverIP = ipTextField.getText();
				Parameter.setflag = true;
			//	warningDialog("ip��ַ����");
			}
			try{
				Parameter.serverPort = Integer.parseInt(portTextField.getText());
			}catch(NumberFormatException e1)
			{
				Parameter.serverPort = -1;
				if(Parameter.setflag)
					Parameter.setflag = false;
			}
			try{
				Parameter.serverID = Integer.parseInt(srcIDTextField.getText());
			}catch(NumberFormatException e1)
			{
				Parameter.serverID = -1;
				if(Parameter.setflag)
					Parameter.setflag = false;
			}
			try{
				Parameter.time = Integer.parseInt(vField.getText());
			}catch(NumberFormatException e1)
			{
				Parameter.time = -1;
				if(Parameter.setflag)
					Parameter.setflag = false;
			}
		}
	});
    return jButton;
}

public javax.swing.JButton getBackJButton() {
	  
    jButton = new javax.swing.JButton("����");
    jButton.setBounds(500, 80, 60, 30);
    jButton.addActionListener(this);
    jButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		}
		});
    return jButton;
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if("����".equals(e.getActionCommand())){
		setupUI.setVisible(false);
		MenuView.menuView.setVisible(true);
	}
}
public static boolean ipCheck(String text) {
    if (text != null && !text.isEmpty()) {
        // ����������ʽ
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        // �ж�ip��ַ�Ƿ���������ʽƥ��
        if (text.matches(regex)) {
            // �����ж���Ϣ
            return true;
        } else {
            // �����ж���Ϣ
            return false;
        }
    }
    // �����ж���Ϣ
    return false;
}
public static void warningDialog(String mesg)  
{  
    JOptionPane  
            .showMessageDialog(  
                null,  
                "<html><font color=\"yellow\"  style=\"font-weight:bold;" +  
                "background-color:#666666\" >"  
                    + mesg + "</font></html>", "WARNING",  
                JOptionPane.WARNING_MESSAGE);  
}  

}