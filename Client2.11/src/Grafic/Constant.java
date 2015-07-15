package Grafic;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Constant {
	//jLabelArr jLabelArrPrefix需要同时改变，否则出错
	public static JButton[] jLabelArr = {new JButton("坐标1"),new JButton("坐标2"),
			new JButton("坐标3"),new JButton("坐标4"),
			new JButton("坐标5"),new JButton("坐标6"),new JButton("坐标7"),
			new JButton("坐标8"),new JButton("坐标9"),new JButton("坐标10")};
	public static String jLabelArrPrefix = "坐标";//jLabelArr的前缀
	//显示在地图上坐标点位置的坐标标识
	public static JLabel[] zbNameArr = {new JLabel("1"),new JLabel("2"),
			new JLabel("3"),new JLabel("4"),
			new JLabel("5"),new JLabel("6"),
			new JLabel("7"),new JLabel("8"),
			new JLabel("9"),new JLabel("10")};
	//开机标识0
	public static int startUpFlag = 0;
}
