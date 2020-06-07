import java.util.Calendar;
import java.util.Scanner;
import javax.swing.JButton;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class calender {
	static int intDateNum=1;
	static String calendate;
	 static int thisYear;
	 static int thisMonth;
	 EncTool en = new EncTool();
    
	
	private final int[] maxDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	public static void getMonthGalendar(){
		  Calendar cal = Calendar.getInstance(); // Ķ���� ��ü ����
	      thisYear = cal.get(Calendar.YEAR); //���� ��
	      thisMonth = cal.get(Calendar.MONTH)+1; //���� ��
	      
	      getMonthGalendar(thisYear,thisMonth, null); // ������� �޷��� ���
	 }
	 
     public static void getMonthGalendar(int intYear, int intMmonth, calenderFrame aa){ //���� �޷� ��� �żҵ�
    	  Calendar cal = Calendar.getInstance();
		  
	      
	      thisYear = cal.get(Calendar.YEAR); //���� ��
	      thisMonth = cal.get(Calendar.MONTH)+1; //���� ��
	      int today = cal.get(Calendar.DATE); 
	      
	      boolean booToday = (intYear==thisYear)&&(thisMonth==intMmonth);
	      cal.set(intYear,intMmonth,1); //Ķ������ü�� �Է¹��� ��, ��, �׸��� Date�� 1�μ���
	      
	      int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1���� ���� ������
	      int endDate = cal.getActualMaximum(Calendar.DATE); //���� �������� ���
	      
	      
	      System.out.println("===================== "+ thisYear+"��  " + (thisMonth+1) + "�� ==================");
	      //���ϸ� ���
	      System.out.println(" ��\t ��\t ȭ\t ��\t ��\t ��\t ��\t");  
	      System.out.println("====================================================");
	      //1���� ���۵Ǵ� ������ ���� �������� ä��� 
	      
	      intDateNum = 1; //����� date�� ������ ����
	     
	      for (int i = 1; intDateNum <= endDate ; i++) {   // ����� Date ����(intDateNum)�� 1�Ϻ��� ���������� �ɶ����� �ݺ�.         
	    	  
	          if(i<sDayNum) {
	        	  System.out.printf("%3s\t","��");
	        	  
	        	  aa.jdata[i+6]=new JButton("");//i�� ���ϼ��ں��� ������ �������� ä���   
	          }
	          
	         
	          else{
	        	  aa.jdata[i+6]=new JButton(""+intDateNum);
	        	 
	              if(booToday && intDateNum==today) System.out.printf("[%2d]\t",intDateNum); //���� ��¥ ǥ��
	              else System.out.printf("%3d\t",intDateNum); //�Ϲ� ���
	              
	              intDateNum++; //����� date ����
	          }            
	          if(i%7==0) System.out.println(); // i�� 7�ǹ���� �Ǹ� �ٹٲ�
	         
	          if(i>6) {
	        	   calendate = (thisYear+"-"+thisMonth+"-"+intDateNum);
	        	  
	        	}
	          if(intDateNum > endDate) {
	        	  for( ; i + 6 < 49; i++) {
	        		  aa.jdata[i+6]=new JButton("");
	        	  }
	          }
	       }
	     
	      System.out.println();
	  }
	  
	  
	 
	  public int getFirstDayOfMonth(int year, int month){ //���� ù�� �ޱ�
		    int baseYear = 1970;
		    int baseDay = 4;
		    int count = 0;
		    for(int i=baseYear; i<year; i++){
		      count += isLeapYear(i)? 366: 365;
		    }
		    for(int i=1; i<month; i++){
		      count += maxDaysOfMonth(year, i);
		    }
		    int day = (count+baseDay) % 7;
		    if (day == 0)
		      return 7;
		    return day;
		  }
	  
	  public boolean isLeapYear(int year){ //����
		    if(year%4 == 0 && (year%100 != 0 || year%400 == 0)){
		      return true;
		    }
		    return false;
		  }
	  
	  public int maxDaysOfMonth(int year, int month){ //���� �ִ� ��
		    if(isLeapYear(year) && month == 2){
		      return 29;
		    }
		    return maxDays[month - 1];
		  }
	  
	  void printCalendar(){ //�޷� ��� �żҵ�
		  int year;
		  int month;
		  System.out.println("\n========== �޷� ������α׷� ====================");
		  Scanner sc = new Scanner(System.in);
		  System.out.println("�⵵�� �Է��ϼ��� : ");
		  year = sc.nextInt();
		  System.out.println("���� �Է��ϼ��� : "); 
		  month = sc.nextInt();
		 
	    int maxDays = maxDaysOfMonth(year, month);
	    int first = getFirstDayOfMonth(year, month);
	    System.out.printf("=====================%4d�� %3d�� ==================\n" ,year, month);
	    
	    System.out.println(" ��\t ��\t ȭ\t ��\t ��\t ��\t ��\t");
	    
	    System.out.println("====================================================");
	    intDateNum = 1; //����� date�� ������ ����
	    
	    for (int i = 1; intDateNum <= maxDays ; i++) {   // ����� Date ����(intDateNum)�� 1�Ϻ��� ���������� �ɶ����� �ݺ�.         
	        
	        if(i<first) System.out.printf("%3s\t","��"); //i�� ���ϼ��ں��� ������ �������� ä���   
	        else{
	           
	             System.out.printf("%3d\t",intDateNum); //�Ϲ� ���
	            
	            intDateNum++; //����� date ����
	        }            
	        if(i%7==0) System.out.println(); // i�� 7�ǹ���� �Ǹ� �ٹٲ�
	       
	    }//for-----------------
	    System.out.println();

	  }
	  
	 
	
	 public static void saveEnfilelist(Enfileprogram aaa,String username) {// ��ȣ��ü���ϸ���Ʈ
    	
    	
    	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username;
    	String today =	aaa.getToday();
    	try{
            File filePath = new File(fileName) ;
            File file = new File(fileName+"\\enlist.txt");
            if (!file.exists()) {//������ ���ٸ�
    			try {
    				filePath.mkdir();	//������ ���� ��� ����
    				file.createNewFile(); // ���� ����				
    			} catch (Exception e) {
    				e.getStackTrace();
    			}
            }
            FileWriter fw = new FileWriter(file, true) ;
            if(EncTool.success) {// �����ϸ�
            	
            fw.write(aaa.textField.getText() + "\n" + today + "\n");

            fw.flush();
            fw.close(); 
           
            }
          
        }catch(Exception e){

            e.printStackTrace();

        }
  }
    
public static void openEnfilelist(String username) {// ��ȣ��ü���ϸ���Ʈ
    	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username;
        File filePath = new File(fileName) ;
        File file = new File(fileName+"\\enlist.txt");
        if (!file.exists()) {//������ ���ٸ�
			try {
				filePath.mkdir();	//������ ���� ��� ����
				file.createNewFile(); // ���� ����				
			} catch (Exception e) {
				e.getStackTrace();
			}
        }
        try {
			FileWriter fw = new FileWriter(file, true) ;
			Desktop.getDesktop().edit(new File(System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username+"\\enlist.txt"));
         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
  }

     public static void readfile() {
    	 BufferedReader in = null;
         String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\enlist";
         try {
         in = new BufferedReader(new FileReader(savePath  + ".txt"));
         String l;
        while ((l = in.readLine()) != null) {
           System.out.println(l);
          }
       
         }
         catch(IOException e) {e.printStackTrace();}
         }
    
  

  public static void Enfilelist1(Enfileprogram aaa,String username) { // ��¥���� �����ϴ� �޼ҵ�
  	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\" +username ;
  	String today = aaa.getToday();
  	try{
        File filePath = new File(fileName) ;
        File file = new File(fileName+"\\"+today +".txt");
        if (!file.exists()) {//������ ���ٸ�
			try {
				filePath.mkdir();	//������ ���� ��� ����
				file.createNewFile(); // ���� ����				
			} catch (Exception e) {
				e.getStackTrace();
			}
        }
           FileWriter fw = new FileWriter(file, true) ;

          if(aaa.textField.getText() != null) {
          fw.write(aaa.textField.getText() + "��" + today + "�� ��ȣȭ �Ǿ����ϴ�" + "\n");

          fw.flush();
          fw.close(); 
          }
  	}catch(Exception e){

        e.printStackTrace();
      }
    }
   
}
    
    

