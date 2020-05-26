import java.util.Calendar;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class calender {
	static int intDateNum;
	static String calendate;
	private final int[] maxDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	public static void getMonthGalendar(){
	      Calendar cal = Calendar.getInstance(); // Ķ���� ��ü ����
	      int thisYear = cal.get(Calendar.YEAR); //���� ��
	      int thisMonth = cal.get(Calendar.MONTH)+1; //���� ��
	     
	      getMonthGalendar(thisYear,thisMonth); // ������� �޷��� ���
	 }
	  
	  public static void getMonthGalendar(int intYear, int intMmonth){ //���� �޷� ��� �żҵ�
		 calenderFrame aa= new calenderFrame();
		  
		  Calendar cal = Calendar.getInstance();
	      
	      int thisYear = cal.get(Calendar.YEAR); //���� ��
	      int thisMonth = cal.get(Calendar.MONTH)+1; //���� ��
	      int today = cal.get(Calendar.DATE); //
	      
	      boolean booToday = (intYear==thisYear)&&(thisMonth==intMmonth);
	      cal.set(intYear,intMmonth-1,1); //Ķ������ü�� �Է¹��� ��, ��, �׸��� Date�� 1�μ���
	      
	      int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1���� ���� ������
	      int endDate = cal.getActualMaximum(Calendar.DATE); //���� �������� ���
	      
	     
	       System.out.println("===================== "+ thisYear+"��  " + (thisMonth+1) + "�� ==================");
	      //���ϸ� ���
	      System.out.println(" ��\t ��\t ȭ\t ��\t ��\t ��\t ��\t");  
	      System.out.println("====================================================");
	      //1���� ���۵Ǵ� ������ ���� �������� ä��� 
	      
	      intDateNum = 1; //����� date�� ������ ����
	  
	      for (int i = 1; intDateNum <= endDate ; i++) {   // ����� Date ����(intDateNum)�� 1�Ϻ��� ���������� �ɶ����� �ݺ�.         
	          
	          if(i<sDayNum) System.out.printf("%3s\t","��"); //i�� ���ϼ��ں��� ������ �������� ä���   
	          else{
	              if(booToday && intDateNum==today) System.out.printf("[%2d]\t",intDateNum); //���� ��¥ ǥ��
	              else System.out.printf("%3d\t",intDateNum); //�Ϲ� ���
	              
	              intDateNum++; //����� date ����
	          }            
	          if(i%7==0) System.out.println(); // i�� 7�ǹ���� �Ǹ� �ٹٲ�
	          if(i>6) {
	        	  aa.jdata[i].setText(""+intDateNum);
	        	  calendate = (thisYear+"-"+thisMonth+"-"+intDateNum);
	        	  
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
	  
	 
	 static String creatDate;
	  
    public void FileCreationDate(){// ���ϻ�����¥ �޼ҵ�

		   File file = new File( EncTool.inFile ); 

		   BasicFileAttributes attrs;
		   try {
			    attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			    FileTime time = attrs.creationTime();
			    
			    String pattern = "yyyy-MM-dd HH:mm:ss";
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				
			    String creatDate = simpleDateFormat.format( new Date( time.toMillis() ) );

			    System.out.println( file + " �� " + creatDate + "�� ��ȣȭ �Ǿ����ϴ�");
			} catch (IOException e) { 
			    e.printStackTrace();
			}
		}
	  
    public static void filesave() {//��ȣ�������� �޼ҵ�
        
    	PrintWriter out = null;
    	String savePath = System.getProperty("user.home") + "\\Documents\\��ȣ����";
    	
    	try {
    		String filename = EncTool.inFile;
    		String CreatDate = creatDate;
    		out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"data.txt")));
      		out.write(filename + "\n");
    		out.write(CreatDate);
    		out.flush();
    		}
    		catch(IOException e) {e.printStackTrace();}
    	 finally {
    		 if (out != null)  {
     			out.close();
     		  }
    		} 
    	}
    	
    
    public static void Enfilelist() {//��ȣ���� ��ü ��� �޼ҵ�
    	BufferedReader in = null;
    	String savePath = System.getProperty("user.home") + "\\Documents\\��ȣ����";
    	try {
    	in = new BufferedReader(new FileReader(savePath + "\\list.txt"));
    	String l;
		while ((l = in.readLine()) != null) {
			System.out.println(l);
		  }
    	}
    	catch(IOException e) {e.printStackTrace();}
    	}

}
    
    

