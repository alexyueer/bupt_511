package Grafic;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MessageUI extends JFrame{
 public MessageUI() {
	  this.setLayout(null);
	  this.setSize(400, 400);//�趨�����С
      int w = Toolkit.getDefaultToolkit().getScreenSize().width/2;//�����Ļ��
	  int h = Toolkit.getDefaultToolkit().getScreenSize().height/2;//�����Ļ��
      this.setLocation(w, h);
}
}