package Grafic;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MessageUI extends JFrame{
 public MessageUI() {
	  this.setLayout(null);
	  this.setSize(400, 400);//设定窗体大小
      int w = Toolkit.getDefaultToolkit().getScreenSize().width/2;//获得屏幕宽
	  int h = Toolkit.getDefaultToolkit().getScreenSize().height/2;//获得屏幕高
      this.setLocation(w, h);
}
}