package Grafic;
//�Ӻ�˻�ȡ���ݽ������ݴ���
public class DataHandle{
	private static final double r = 6378.137;//����뾶ǧ��
	private double[][] dushu;//����ת��Ϊ������ľ�γ����Ϣ��0���� 1γ��
	private int[][] lat;//���ڴ��γ�ȵ��зֺ�����
	private int[][] lon;//���ڴ�ž��ȵ��зֺ�����
	private double[] distance; 
	private int PointNum;
	//������ֳ�60�̶ȣ�������ֳ�60�̶�.
	private int flag;//�̶ȳߵ�λ��0:1�ף�1:2�ף�2:4�ף�3:8�� 4:16��  5:32��  6:50�� 
	private int disLength = 0;	
	private double maxDistance = 0.0;
	public DataHandle(){
		
	}
	//��ʼ������
	public void init(){
		if(SplitPoint.pointNum==0){
			try {
				throw new Exception("û�������");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(SplitPoint.pointNum==1){
			disLength = 0;
		}else{
			disLength = SplitPoint.pointNum*(SplitPoint.pointNum-1)/2;
		}
		lat = new int[SplitPoint.pointNum][4];
		lon = new int[SplitPoint.pointNum][4];
		dushu = new double[SplitPoint.pointNum][2];
		distance = new double[disLength];
		lat = SplitPoint.getSplitLatData();//����зֺ��γ����Ϣ
		lon = SplitPoint.getSplitLonData();//����зֺ�ľ�����Ϣ
		
		//�����зֺ�����ݷ���ת����Ķ���
		for(int i=0;i<SplitPoint.pointNum;i++){
			dushu[i][1] =lat[i][0]+(double)lat[i][1]/60+(lat[i][2]+lat[i][3]/10000.0)/3600;//���ֿ�ľ���ת��Ϊ����
			dushu[i][0] =lon[i][0]+(double)lon[i][1]/60+(lon[i][2]+lon[i][3]/10000.0)/3600;//���ֿ��γ��ת��Ϊ����
		} 
	}
	public int[][] getLat() {
		return lat;
	}
	
	public int[][] getLon() {
		return lon;
	}
	public double[][] getDushu() {
		return dushu;
	}
	//���ؾ�����
	public int getDisLength(){
		return disLength;
	}
	private static double rad(double d)
	{
	    return d * Math.PI / 180.0;
	}
	//���ݾ�γ�ȼ������Ĺ�ʽ
	//	C = sin(LatA)*sin(LatB) + cos(LatA)*cos(LatB)*cos(MLonA-MLonB)
	//	Distance = R*Arccos(C)*Pi/180
	//���ؾ�������
	public double[] getDistance(double[][] dushu,int pointNum){
		if(pointNum==1){//����Ϊһ����ʱ���׳��쳣
			
		}else{
			int k=0;
			//����ÿ������֮��ľ���
			for(int i=0;i<pointNum;i++){
	    		for(int j=i+1;j<pointNum;j++){
//	    			double radLat1 = rad(dushu[i][1]);
//				    double radLat2 = rad(dushu[j][1]);
//				    double a = radLat1 - radLat2;
//				    double b = rad(dushu[i][0]) - rad(dushu[j][0]);
//				    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
//				    	     Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
//				    s = s * r;
//				    distance[k++] = Math.round(s * 10000) / 10000;
	    			distance[k++] = getDis(dushu[i][1],dushu[j][1],dushu[i][0],dushu[j][0]);
	    		}
	    	}
		}
    	return distance;
    }
	//��ȡ������֮��ľ���
	public static int getDis(double lat1,double lat2,double lon1,double lon2){
		double c = Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1-lon2);
		return (int)(r*Math.acos(c)*Math.PI/180*1000);//A B�����ľ�����
	}
	//��ȡ������������λ����Ϣ latγ�ȣ�lon����
	//��������2���������1�ķ�λ
	public static double GetJiaoDu(double lat1,double lon1,double lat2,double lon2){
		double x1 = lon1;
        double y1 = lat1;
        double x2 = lon2;
        double y2 = lat2;
        double pi = Math.PI;
        double w1 = y1 / 180 * pi;
        double j1 = x1 / 180 * pi;
        double w2 = y2 / 180 * pi;
        double j2 = x2 / 180 * pi;
        double ret;
        if (j1 == j2)
        {
            if (w1 > w2) return 270; //γ��Խ��Խ����
            else if (w1 < w2) return 90;
            else return -1;//λ����ȫ��ͬ
        }
        if(w1 == w2){
        	if(j1 > j2) return 180;
        	else if (j1 < j2) return 0;
        	else return -1;
        }
        ret = 4 * Math.pow(Math.sin((w1 - w2) / 2), 2) - Math.pow(Math.sin((j1 - j2) / 2) * (Math.cos(w1) - Math.cos(w2)), 2);
        ret = Math.sqrt(ret);
        double temp = (Math.sin(Math.abs(j1 - j2) / 2) * (Math.cos(w1) + Math.cos(w2)));
        ret = ret / temp;
        ret = Math.atan(ret) / pi * 180;
        System.out.println("ret" + ret);
        if (j1 > j2) // 2Ϊ�ο�������
        {
            if (w1 > w2) ret += 180;
            else ret = 180 - ret;
        }
        else if (w1 > w2) ret = 360 - ret;
        return ret;
    }
	 public static String GetDirection(double lat1, double lon1, double lat2, double lon2)
	    {
		 	double jiaodu1 = DataHandle.GetJiaoDu(lat1, lon1, lat2, lon2);
		 	System.out.println("jiaodu1:" + jiaodu1);
		 	double jiaodu = 0;
		 	if(jiaodu1 < 0){
		 		jiaodu = (360 + jiaodu1)%360;
		 	}else{
		 		jiaodu = jiaodu1%360;
		 	}
	       
	        if ((jiaodu <= 10 ) || (jiaodu > 350)) return "��"+(int)jiaodu+"��";
	        if ((jiaodu > 10) && (jiaodu <= 80)) return "����"+(int)jiaodu+"��";
	        if ((jiaodu > 80) && (jiaodu <= 100)) return "��"+(int)jiaodu+"��";
	        if ((jiaodu > 100) && (jiaodu <= 170)) return "����"+(int)jiaodu+"��";
	        if ((jiaodu > 170) && (jiaodu <= 190)) return "��"+(int)jiaodu+"��";
	        if ((jiaodu > 190) && (jiaodu <= 260)) return "����"+(int)jiaodu+"��";
	        if ((jiaodu > 260) && (jiaodu <= 280)) return "��"+(int)jiaodu+"��";
	        if ((jiaodu > 280) && (jiaodu <= 350)) return "����"+(int)jiaodu+"��";
	        return "";
	    }
	public int getFlag(){
		for(int i=0;i<distance.length;i++){
			if(distance[i]>maxDistance){
				maxDistance = distance[i];
			}
		}
		if(maxDistance<100){//0-100
			flag=0;
		}else if(maxDistance<1000){//100-1000
				flag=1;
			}else{//����1KM
				flag=2;
			}
		return flag;
	}
}