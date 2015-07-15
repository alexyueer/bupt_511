package bupt.liang.tool;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bupt.liang.model.Computer;
 
/**
 * @author ��ũС��
 * ReadFile.java
 * 2012-10-12����11:40:21
 */
public class ReadFile {
	
	public static List<Computer> fileComputer = new ArrayList<Computer>();

	
    /**
     * ���ܣ�Java��ȡtxt�ļ�������
     * ���裺1���Ȼ���ļ����
     * 2������ļ��������������һ���ֽ���������Ҫ��������������ж�ȡ
     * 3����ȡ������������Ҫ��ȡ�����ֽ���
     * 4��һ��һ�е������readline()��
     * ��ע����Ҫ���ǵ����쳣���
     * @param filePath
     */
    @SuppressWarnings("rawtypes")
	public static List readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//���ǵ������ʽ
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        String[] str = lineTxt.split(" ");
                        //System.out.println(str[0]);
                        Computer computer = new Computer(str[0], str[1], str[2], str[3]);
                        fileComputer.add(computer);
                    }
                    read.close();
        }else{
            System.out.println("�Ҳ���ָ�����ļ�");
        }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }
        return fileComputer;
     
    }
    
    public static Computer findComputerById(String id, List<Computer> list){
    	Computer computer = null;
    	for(int i = 0; i<list.size(); i++){
    		System.out.println(list.get(i).getID());
    		if(id.equals(list.get(i).getID())){
    			computer = list.get(i);
    			break;
    		}
    	}
    	return computer;
    }
     
//    public static void main(String argv[]){
//        //String filePath = "L:\\Apache\\htdocs\\res\\20121012.txt";
//        
//        readTxtFile(filePath);
//        System.out.println(fileComputer.size());
//    }
     
     
 
}
