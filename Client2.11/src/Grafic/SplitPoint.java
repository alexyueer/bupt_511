package Grafic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import bupt.liang.tool.Parameter;

//�Ի�õĵ�����зֳɶȷ��뻹��С������
public class SplitPoint {
	public static int pointNum;//��ͼ�ϵĵ���������������
	private static String[][] shuju = new String[10][2]; //0���� 1γ��
	public static Integer[] computerNo = new Integer[10];//��ż�������
	private static int[][] lat;//���ڴ��γ�ȵ��зֺ�����
	private static int[][] lon;//���ڴ�ž��ȵ��зֺ�����
//	private double[][] dushu;//����ת��Ϊ������ľ�γ����Ϣ
	private boolean stateFlag = false;
	Set<String> set = new HashSet<String>();//��ҪΪ��ɾ���ظ�������;
	public SplitPoint(){
		set.clear();
		FileReader fr = null;
		BufferedReader br = null;
		File file = new File(Parameter.PositionPath);
		File file1 = new File(Parameter.OtherPositionPath);//�����������λ����Ϣ����ļ�
		//�ȶ�ȡ����λ����Ϣ
		try {
			fr = new FileReader(file);//����λ�ô��·��
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
				fr = new FileReader(file1);//��ȡ�������������λ����Ϣ���·��
				br = new BufferedReader(fr);
				String line;
				while((line=br.readLine())!=null){
					//11621.027140,3957.591026,1001
					if("".equals(line))//��ȡ�ļ�����ڿ������������λ����Ϣ����������
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

	public static int getDu(String num){//��������㣬�������㾭γ�ȵĶ�
		int index = num.indexOf('.');//С�����λ��
		int du = Integer.parseInt(num.substring(0, index-2));
		return du;
	}
	public static int getFen(String num){//��������㣬�������㾭γ�ȵķ�
		int index = num.indexOf('.');//С�����λ��
		int fen = Integer.parseInt(num.substring(index-2, index));
		return fen;
	}
	public static int getMiao(String num){//��������㣬�������㾭γ�ȵ���
		int index = num.indexOf('.');//С�����λ��
		int miao = Integer.parseInt(num.substring(index+1, index+3));
		return miao;
	}
	
	public static int getXiaoshu(String num){//��������㣬�������㾭γ�ȵ�С��
		int index = num.indexOf('.');//С�����λ��
		int xiaoshu = Integer.parseInt(num.substring(index+3, num.length()));
		return xiaoshu;
	}
	//��ȡ�зֺ��γ����Ϣ����
	public static int[][] getSplitLatData(){
		for(int j1=0;j1<pointNum;j1++){
			lat[j1][0] = getDu(shuju[j1][1]);
			lat[j1][1] = getFen(shuju[j1][1]);
			lat[j1][2] = getMiao(shuju[j1][1]);
			lat[j1][3] = getXiaoshu(shuju[j1][1]);
		}
		return lat;
	}
	
	//��ȡ�зֺ�ľ�����Ϣ����
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
