package bupt.liang.tool;

public class IntByte {
	public static int byte2ToInt(byte[] by)
	{
	    int  value =  (by[0]&0x000000ff)|((by[1] & 0x000000ff) << 8);
	    return value;
	}
	public static  byte[] intToBytes(int i)
	{
		byte[] temp = new byte[2];
		temp[0] = (byte) (0xff & i);
		temp[1] = (byte) ((0xff00 & i) >> 8);
		return temp;
	}
	public static  byte[] intTo4Byte(int i) {  
	      byte[] result = new byte[4];  
	      //�ɸ�λ����λ
	      result[0] = (byte)((i >> 24) & 0xFF);
	      result[1] = (byte)((i >> 16) & 0xFF);
	      result[2] = (byte)((i >> 8) & 0xFF);
	      result[3] = (byte)(i & 0xFF);
	      return result;
	    }
	public static int byte4ToInt(byte[] bytes) {
	      int value= 0;
	      //�ɸ�λ����λ
	      for (int i = 0; i < 4; i++) {
	          int shift= (4 - 1 - i) * 8;
	          value +=(bytes[i] & 0x000000FF) << shift;//���λ��
	      }
	      return value;
	}
}
