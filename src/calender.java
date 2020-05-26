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
	      Calendar cal = Calendar.getInstance(); // 캘린더 객체 생성
	      int thisYear = cal.get(Calendar.YEAR); //현재 년
	      int thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
	     
	      getMonthGalendar(thisYear,thisMonth); // 현재달의 달력을 출력
	 }
	  
	  public static void getMonthGalendar(int intYear, int intMmonth){ //현재 달력 출력 매소드
		 calenderFrame aa= new calenderFrame();
		  
		  Calendar cal = Calendar.getInstance();
	      
	      int thisYear = cal.get(Calendar.YEAR); //현재 년
	      int thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
	      int today = cal.get(Calendar.DATE); //
	      
	      boolean booToday = (intYear==thisYear)&&(thisMonth==intMmonth);
	      cal.set(intYear,intMmonth-1,1); //캘린더객체에 입력받은 년, 달, 그리고 Date을 1로설정
	      
	      int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
	      int endDate = cal.getActualMaximum(Calendar.DATE); //달의 마지막일 얻기
	      
	     
	       System.out.println("===================== "+ thisYear+"년  " + (thisMonth+1) + "월 ==================");
	      //요일명 출력
	      System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");  
	      System.out.println("====================================================");
	      //1일이 시작되는 이전의 요일 공백으로 채우기 
	      
	      intDateNum = 1; //출력할 date를 저장할 변수
	  
	      for (int i = 1; intDateNum <= endDate ; i++) {   // 출력한 Date 변수(intDateNum)가 1일부터 마지막일이 될때까지 반복.         
	          
	          if(i<sDayNum) System.out.printf("%3s\t","★"); //i가 요일숫자보다 작으면 공백으로 채우기   
	          else{
	              if(booToday && intDateNum==today) System.out.printf("[%2d]\t",intDateNum); //오늘 날짜 표시
	              else System.out.printf("%3d\t",intDateNum); //일반 출력
	              
	              intDateNum++; //출력할 date 증가
	          }            
	          if(i%7==0) System.out.println(); // i가 7의배수가 되면 줄바꿈
	          if(i>6) {
	        	  aa.jdata[i].setText(""+intDateNum);
	        	  calendate = (thisYear+"-"+thisMonth+"-"+intDateNum);
	        	  
               }
	          
	      }
	      System.out.println();
	  }
	 
	  public int getFirstDayOfMonth(int year, int month){ //달의 첫날 받기
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
	  
	  public boolean isLeapYear(int year){ //윤년
		    if(year%4 == 0 && (year%100 != 0 || year%400 == 0)){
		      return true;
		    }
		    return false;
		  }
	  
	  public int maxDaysOfMonth(int year, int month){ //달의 최대 일
		    if(isLeapYear(year) && month == 2){
		      return 29;
		    }
		    return maxDays[month - 1];
		  }
	  
	  void printCalendar(){ //달력 출력 매소드
		  int year;
		  int month;
		  System.out.println("\n========== 달력 출력프로그램 ====================");
		  Scanner sc = new Scanner(System.in);
		  System.out.println("년도를 입력하세요 : ");
		  year = sc.nextInt();
		  System.out.println("월을 입력하세요 : "); 
		  month = sc.nextInt();
		 
	    int maxDays = maxDaysOfMonth(year, month);
	    int first = getFirstDayOfMonth(year, month);
	    System.out.printf("=====================%4d년 %3d월 ==================\n" ,year, month);
	    
	    System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");
	    
	    System.out.println("====================================================");
	    intDateNum = 1; //출력할 date를 저장할 변수
	    
	    for (int i = 1; intDateNum <= maxDays ; i++) {   // 출력한 Date 변수(intDateNum)가 1일부터 마지막일이 될때까지 반복.         
	        
	        if(i<first) System.out.printf("%3s\t","★"); //i가 요일숫자보다 작으면 공백으로 채우기   
	        else{
	           
	             System.out.printf("%3d\t",intDateNum); //일반 출력
	            
	            intDateNum++; //출력할 date 증가
	        }            
	        if(i%7==0) System.out.println(); // i가 7의배수가 되면 줄바꿈
	       
	    }//for-----------------
	    System.out.println();

	  }
	  
	 
	 static String creatDate;
	  
    public void FileCreationDate(){// 파일생성날짜 메소드

		   File file = new File( EncTool.inFile ); 

		   BasicFileAttributes attrs;
		   try {
			    attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			    FileTime time = attrs.creationTime();
			    
			    String pattern = "yyyy-MM-dd HH:mm:ss";
			    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				
			    String creatDate = simpleDateFormat.format( new Date( time.toMillis() ) );

			    System.out.println( file + " 는 " + creatDate + "에 암호화 되었습니다");
			} catch (IOException e) { 
			    e.printStackTrace();
			}
		}
	  
    public static void filesave() {//암호파일저장 메소드
        
    	PrintWriter out = null;
    	String savePath = System.getProperty("user.home") + "\\Documents\\암호파일";
    	
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
    	
    
    public static void Enfilelist() {//암호파일 전체 출력 메소드
    	BufferedReader in = null;
    	String savePath = System.getProperty("user.home") + "\\Documents\\암호파일";
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
    
    

