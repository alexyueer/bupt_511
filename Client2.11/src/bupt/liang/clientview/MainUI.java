package bupt.liang.clientview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import bupt.liang.receivethread.ReceiveDataThread;

public class MainUI extends JFrame implements ActionListener{
	public static int flag = 0;//��ֹ��λ�����ؽ�����
	public static MainUI mainUI= null;
	 public static JButton jButton;
	 public static String[] name = {"����","�ļ�","����Ϣ","����","��λ��Ϣ"};
	 ReceiveDataThread dataThread;

	 public static List<JButton> list = new ArrayList<JButton>();
	 public MainUI(){
		 super();
		 dataThread = new ReceiveDataThread();
		 dataThread.start();

		 CommandUI.commandUI = new CommandUI();//��������ڵ��ǲ���ʾ
		 FileUI.fileUI = new FileUI();
		 MessageUI.messageUI = new MessageUI();
		 SetupUI.setupUI = new SetupUI();
		
		 list = getJButton();
		 this.setSize(700,400);
		 this.getContentPane().setLayout(null);
		 for(int i=0;i<list.size();i++){
			 this.add(list.get(i),null);
			 if("����".equals(list.get(i).getText())){
				 list.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainUI.setVisible(false);
						CommandUI.commandUI.setVisible(true);
					}
				});
			 }else
				 if("�ļ�".equals(list.get(i).getText())){
					 list.get(i).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								mainUI.setVisible(false);
								FileUI.fileUI.setVisible(true);
							}
						});
				 }else
					 if("����Ϣ".equals(list.get(i).getText())){
						 list.get(i).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mainUI.setVisible(false);
									MessageUI.messageUI.setVisible(true);
								}
							});
					 }else 
						 if("����".equals(list.get(i).getText())){
							 list.get(i).addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										mainUI.setVisible(false);
										SetupUI.setupUI.setVisible(true);
									}
								});
						 }
						 else {
							 list.get(i).addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										mainUI.setVisible(false); 
										if(flag==0){
//											 LocationUI.locationUI = new LocationUI();
										}
//										LocationUI.locationUI.setVisible(true);
									}
								});
						 }
		 }
	 }
	 public List<JButton> getJButton() {
		   for(int i=0;i<5;i++){
		      jButton = new JButton();
		      jButton.setBounds(20+120*i,20,100,40);
		      jButton.setText(name[i]);
		      list.add(jButton);
		   }  
		   return list;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		mainUI = new MainUI();
		mainUI.setVisible(true);
	}
}
