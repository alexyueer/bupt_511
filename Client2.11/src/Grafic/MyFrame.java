package Grafic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import bupt.liang.tool.Parameter;

public class MyFrame extends JFrame implements ActionListener {
	public static MyFrame myFrame = null; 
	JButton[][] jButtons = new JButton[20][20];
	JTextArea[][] jTextAreas = new JTextArea[20][20];
	public static MyPanel body;
	private final Image image = new ImageIcon("src\\Picture\\grafic.png").getImage();// ��ͼ��λ��ʶ
	private int[][] lat;// ���ڴ��γ�ȵ��зֺ�����
	private int[][] lon;// ���ڴ�ž��ȵ��зֺ�����
	private double[][] dushu;// �ڶ�ά��0��ʾ���ȣ�1��ʾγ��
	public static boolean changeFlag = false;// �����Ƿ���µı�־λ
	private final int width = 700;
	private final int height = 500;
	private JLabel bilichi_name;
	private int num = 0;
	public MyFrame() throws Exception {
		new SplitPoint();//���ݳ�ʼ��
		num = SplitPoint.pointNum;
		this.setTitle("������Ϣ");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLayout(new BorderLayout());// ���ò��ֹ�����
		JPanel sub_head = new JPanel();
		sub_head.setBackground(Color.gray);
		sub_head.setPreferredSize(new Dimension(width, 25));// �ڲ��ֹ�������ʹ�õģ���setlayout(null)ʱ��ʹ��setsize����
		sub_head.setLayout(new GridLayout(1, num));
		for (int i = 0; i < num; i++) {
			int id = SplitPoint.computerNo[i];
			Constant.jLabelArr[id].addActionListener(this);
			System.out.println(i);
			sub_head.add(Constant.jLabelArr[id]);
		}
		body = new MyPanel();
		this.add(sub_head, BorderLayout.NORTH);
		this.add(body, BorderLayout.CENTER);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class MyPanel extends JPanel {
		private int flag = 0;
		private double[] distance;
		private int[][] zuobiao;
		private DataHandle dataHandle;
		public MyPanel() {
			setLayout(null);
			dataHandle = new DataHandle();
			dataHandle.init();
			if (num != 0) {
				zuobiao = new int[num][2];
				dushu = new double[num][2];
				lat = dataHandle.getLat();//����
				lon = dataHandle.getLon();//γ��
				dushu = dataHandle.getDushu();//0���� 1γ��
				distance = dataHandle.getDistance(dushu, num);
				flag = dataHandle.getFlag();
			}
			JLabel jp_bilichi = new JLabel();
			jp_bilichi.setLayout(new BorderLayout());
			jp_bilichi.setBackground(Color.gray);
			bilichi_name = new JLabel("0��----1��----2��", SwingConstants.CENTER);
			bilichi_name.setPreferredSize(new Dimension(100, 24));
			JLabel bilichi_pic = new JLabel("|-----------|-----------|",
					SwingConstants.CENTER);// ������ͼ��
			bilichi_pic.setPreferredSize(new Dimension(100, 24));
			jp_bilichi.add(bilichi_name, BorderLayout.CENTER);
			jp_bilichi.add(bilichi_pic, BorderLayout.SOUTH);
			jp_bilichi.setBounds(width-120, 200, 100, 60);
			this.add(jp_bilichi);
		}

		public int[][] getZuobiao(int[][] lat, int[][] lon) {
			if (num == 1) {
				zuobiao[0][0] = (width-150)/2;
				zuobiao[0][1] = (height-70)/2;
			} else {
				int serverId = -1;//����id�������е�λ��,�Ա���Ϊ����
				for(int i=0;i<num;i++){
					//System.out.println( "Parameter.serverID:" +  Parameter.serverID);
					if(SplitPoint.computerNo[i] == (Parameter.serverID - 101)){//����
						serverId = i;
						break;
					}
				}
				//������������λ��,������������ڱ�����λ��
				zuobiao[serverId][0] = (width-150)/2;
				zuobiao[serverId][1] = (height-70)/2;
				//��γ��ÿ��һ�ȴ�Լ��110����
				double diff_lon[] = new double[num]; 
				double diff_lat[] = new double[num];//��ž�γ�Ȳ��
				double max_diff_lon = 0.0;//��������
				double max_diff_lat = 0.0;//γ������
				for(int i=0;i<num;i++){
					diff_lon[i] = Math.abs(dushu[i][0]-dushu[serverId][0]);
					diff_lat[i] = Math.abs(dushu[i][1]-dushu[serverId][1]);
					if(diff_lon[i] > max_diff_lon)
						max_diff_lon = diff_lon[i];
					if(diff_lat[i] > max_diff_lat)
						max_diff_lat = diff_lat[i];
				}
//				System.out.println("max_diff_lon:" + max_diff_lon);
//				System.out.println("max_diff_lat:" + max_diff_lat);
				double lon_size = ((width-150)/2.0)/max_diff_lon;
//				System.out.println("lon_size:" + lon_size);
				double lat_size = ((height-70)/2.0)/max_diff_lat;
//				System.out.println("lat_size:" + lat_size);
				for(int i=0;i<num;i++){
					if(i == serverId)
						continue;
					zuobiao[i][0] = zuobiao[serverId][0] + (int)((dushu[i][0]-dushu[serverId][0])*lon_size);//����Խ��Խ����
					zuobiao[i][1] = zuobiao[serverId][1] - (int)((dushu[i][1]-dushu[serverId][1])*lat_size);//γ��Խ��Խ����
				}
			}
			return zuobiao;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, width-120, 10, 100, 100, this);
			for (int i = 0; i < num; i++) {
				if (i % 2 == 0)
					Constant.zbNameArr[SplitPoint.computerNo[i]].setBounds(zuobiao[i][0],
							zuobiao[i][1], 10, 10);
				else
					Constant.zbNameArr[SplitPoint.computerNo[i]].setBounds(zuobiao[i][0],
							zuobiao[i][1], 10, 10);
				body.add(Constant.zbNameArr[SplitPoint.computerNo[i]]);
			}
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);// ������һ������֮ǰ�������ݣ����ӵĻ�����
			g.setColor(Color.green);
			switch (flag) {
			case 0:
				bilichi_name.setText("0----1m----2m");// 0-100��
				break;
			case 1:
				bilichi_name.setText("0----10m----20m");// 100-1000��
				break;
			case 2:
				bilichi_name.setText("0--100m--200m");// ����1km
				break;
			case 3:
				bilichi_name.setText("0--1km--2km");// ����10km
				break;
			default:
				bilichi_name.setText("0----1m----2m");
				break;
			}
			zuobiao = getZuobiao(lat, lon);
			for (int i = 0; i < num; i++) {
				g.fillOval(zuobiao[i][0], zuobiao[i][1], 5, 5);//�������Ӧ���ȣ��������Ӧγ��			
			}

		}
	}

	public static boolean eql(String[][] a, String[][] b) {
		int m = a.length;
		int n = a[0].length;
		double[][] data1 = new double[m][n];
		double[][] data2 = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				data1[i][j] = Double.parseDouble(a[i][j]);
				data2[i][j] = Double.parseDouble(b[i][j]);
			}
		}
		for (int i = 0; i < a.length; i++) {
			if (!Arrays.equals(data1[i], data2[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Parameter.paintFlag = 0;
		DecimalFormat df = new DecimalFormat("0.0000");
		String commandName = e.getActionCommand();
		int j = -1;//��¼�������µ��ǵڼ��������
		int m = -1;//������Ŷ�Ӧ�������е�λ��
		for(int i=0;i<SplitPoint.pointNum;i++){
			if(commandName.equals(Constant.jLabelArr[SplitPoint.computerNo[i]].getText())){
				j = SplitPoint.computerNo[i];
				m = i;
				break;
			}
		}
		if(j != -1){//����x  ��������x(y)
				MessageUI messageUI = new MessageUI();
				messageUI.setTitle(Constant.jLabelArr[j].getText());
				System.out.println("m:" + m);
				String str = new StringBuffer().append("����")
						.append(df.format(dushu[m][0])).append("��    ")
						.append("��γ").append(df.format(dushu[m][1])).append("��")
						.append("\n").toString();
				for (int i = 0; i < SplitPoint.pointNum; i++) {
					if (i == m) {//�Լ�
						JButton jb = new JButton("�ҵ�λ�ã�");
						messageUI.add(jb);
						jb.setBounds(0, i * 30, 100, 20);
						jTextAreas[j][j] = new JTextArea();
						//System.out.println("create jt" + m + "--" + SplitPoint.computerNo[i]);
						messageUI.add(jTextAreas[j][j]);
						jTextAreas[j][j].setBounds(120, i * 30, 260, 20);
						jTextAreas[j][j].setText(str);
					} else {
						String strs = new StringBuffer(Constant.jLabelArrPrefix)
						.append(SplitPoint.computerNo[i]+1).append("(").append(j+1).append("):")
						.toString();
						jButtons[j][SplitPoint.computerNo[i]] = new JButton(strs);
						//System.out.println("create jb" + m + "--" + SplitPoint.computerNo[i]);
						jTextAreas[j][SplitPoint.computerNo[i]] = new JTextArea();
						messageUI.add(jButtons[j][SplitPoint.computerNo[i]]);
						jButtons[j][SplitPoint.computerNo[i]].setBounds(0, i * 30, 100, 20);
						jButtons[j][SplitPoint.computerNo[i]].addActionListener(this);
						messageUI.add(jTextAreas[j][SplitPoint.computerNo[i]]);
						jTextAreas[j][SplitPoint.computerNo[i]].setBounds(120, i * 30, 260, 20);
					}
				}
				JButton jb = new JButton("<html>��ȡ���о�<br>�뷽λ��Ϣ</html>(" + (j+1) + "):");
				messageUI.add(jb);
				jb.setBounds(0, 200, 100, 180);
				jb.addActionListener(this);
				jTextAreas[j][19] = new JTextArea();//��ά����ڶ�ά�ȵ����һλ�洢getAll
				messageUI.add(jTextAreas[j][19]);
				jTextAreas[j][19].setBounds(120, 200, 260, 180);
				messageUI.setVisible(true);
			}else{//˵�����µİ�ť������x(y)  ����������x
				//����5(4)
			int Index1 = commandName.indexOf(Constant.jLabelArrPrefix);
			int Index2 = commandName.indexOf("(");
			int Index3 = commandName.indexOf(")");
			//����5
			String prefix = commandName.substring(0,Index2);
			System.out.println("=================" + prefix);
			if("<html>��ȡ���о�<br>�뷽λ��Ϣ</html>".equals(prefix)){
				//4
				int c2Name = Integer.parseInt(commandName.substring(Index2+1, Index3));
				int y = 0;//��Ӧc2������������е�λ��
				for(int i=0;i<SplitPoint.pointNum;i++)
					if(SplitPoint.computerNo[i] + 1 == c2Name){
						y = i;
						break;
					}
				int dis;
				String dir;
				StringBuffer stb = new StringBuffer();
				for (int i = 0; i < SplitPoint.pointNum; i++) {
					if (i == y)//��׼λ��
						continue;
					dis = DataHandle.getDis(dushu[y][1], dushu[i][1], dushu[y][0],
							dushu[i][0]);
					dir = DataHandle.GetDirection(dushu[y][1], dushu[y][0],
							dushu[i][1], dushu[i][0]);
					stb.append(Constant.jLabelArrPrefix).append(SplitPoint.computerNo[i] + 1)
							.append("�������:").append(dis).append("��,���ҵ�:")
							.append(dir + "��").append("\n");
				}
				jTextAreas[c2Name-1][19].setText(stb.toString());
			}else{
				//5
				int c1Name = Integer.parseInt(prefix.substring(Constant.jLabelArrPrefix.length()));
				//4
				int c2Name = Integer.parseInt(commandName.substring(Index2+1, Index3));
				int x = 0;//��Ӧc1������������е�λ��
				int y = 0;//��Ӧc2������������е�λ��
				for(int i=0;i<SplitPoint.pointNum;i++){
					if(SplitPoint.computerNo[i] + 1 == c1Name)
						x = i;
					else 
						if(SplitPoint.computerNo[i] + 1 == c2Name)
							y = i;
				}
				int dis = DataHandle.getDis(dushu[x][1], dushu[y][1], dushu[x][0],
						dushu[y][0]);
				String dir = DataHandle.GetDirection(dushu[y][1], dushu[y][0],
						dushu[x][1], dushu[x][0]);
				jTextAreas[c2Name-1][c1Name-1].setText("���룺" + dis + "," + "��λ��" + dir);
			}
		}
	}
}
