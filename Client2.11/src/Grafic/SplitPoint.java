package Grafic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import bupt.liang.tool.Parameter;

//对获得的点进行切分成度分秒还有小数部分
public class SplitPoint {
	public static int pointNum;//地图上的点数，假设两个点
	private static String[][] shuju = new String[10][2]; //0经度 1纬度
	public static Integer[] computerNo = new Integer[10];//存放计算机编号
	private static int[][] lat;//用于存放纬度的切分后数据
	private static int[][] lon;//用于存放经度的切分后数据
//	private double[][] dushu;//用于转换为度数后的经纬度信息
	private boolean stateFlag = false;
	Set<String> set = new HashSet<String>();//主要为了删除重复的数据;
	public SplitPoint(){
		set.clear();
		FileReader fr = null;
		BufferedReader br = null;
		File file = new File(Parameter.PositionPath);
		File file1 = new File(Parameter.OtherPositionPath);//其它计算机的位置信息存放文件
		//先读取自身位置信息
		try {
			fr = new FileReader(file);//自身位置存放路径
			br = new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null && !("".equals(line)))
				set.add(line);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(Parameter.graficNum != 0){
			try {
				fr = new FileReader(file1);//获取的其它计算机的位置信息存放路径
				br = new BufferedReader(fr);
				String line;
				while((line=br.readLine())!=null){
					//11621.027140,3957.591026,1001
					if("".equals(line))//读取文件会存在空行情况，发送位置信息函数的问题
						continue;
					set.add(line);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally{
				try {
					fr.close();
					br.close();
					file1.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		pointNum = set.size();
		Iterator<String> it = set.iterator();
		int j = 0;
		String[] str = new String[3];
		while(it.hasNext()){
			String tmp = it.next();
			if("".equals(tmp))
				continue;
			str = tmp.split(",");
			computerNo[j] = Integer.parseInt(str[0])-101;
			shuju[j][0] = str[1];
			shuju[j][1] = str[2];
			j++;
		}
		lat = new int[pointNum][4];
		lon = new int[pointNum][4];
	}
	
	public static int getPointNum() {
		return pointNum;
	}

	public void setPointNum(int pointNum) {
		SplitPoint.pointNum = pointNum;
	}
	
	public static String[][] getShuju() {
		return shuju;
	}

	public void setShuju(String[][] shuju) {
		SplitPoint.shuju = shuju;
	}

	public static int getDu(String num){//传入坐标点，获得坐标点经纬度的度
		int index = num.indexOf('.');//小数点的位置
		int du = Integer.parseInt(num.substring(0, index-2));
		return du;
	}
	public static int getFen(String num){//传入坐标点，获得坐标点经纬度的分
		int index = num.indexOf('.');//小数点的位置
		int fen = Integer.parseInt(num.substring(index-2, index));
		return fen;
	}
	public static int getMiao(String num){//传入坐标点，获得坐标点经纬度的秒
		int index = num.indexOf('.');//小数点的位置
		int miao = Integer.parseInt(num.substring(index+1, index+3));
		return miao;
	}
	
	public static int getXiaoshu(String num){//传入坐标点，获得坐标点经纬度的小数
		int index = num.indexOf('.');//小数点的位置
		int xiaoshu = Integer.parseInt(num.substring(index+3, num.length()));
		return xiaoshu;
	}
	//获取切分后的纬度信息数组
	public static int[][] getSplitLatData(){
		for(int j1=0;j1<pointNum;j1++){
			lat[j1][0] = getDu(shuju[j1][1]);
			lat[j1][1] = getFen(shuju[j1][1]);
			lat[j1][2] = getMiao(shuju[j1][1]);
			lat[j1][3] = getXiaoshu(shuju[j1][1]);
		}
		return lat;
	}
	
	//获取切分后的经度信息数据
	public static int[][] getSplitLonData(){
		for(int i1=0;i1<pointNum;i1++){
			lon[i1][0] = getDu(shuju[i1][0]);
			lon[i1][1] = getFen(shuju[i1][0]);
			lon[i1][2] = getMiao(shuju[i1][0]);
			lon[i1][3] = getXiaoshu(shuju[i1][0]);
		}
		return lon;
	}
}
