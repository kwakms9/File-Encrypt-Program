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
		  Calendar cal = Calendar.getInstance(); // 캘린더 객체 생성
	      thisYear = cal.get(Calendar.YEAR); //현재 년
	      thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
	      
	      getMonthGalendar(thisYear,thisMonth, null); // 현재달의 달력을 출력
	 }
	 
     public static void getMonthGalendar(int intYear, int intMmonth, calenderFrame aa){ //현재 달력 출력 매소드
    	  Calendar cal = Calendar.getInstance();
		  
	      
	      thisYear = cal.get(Calendar.YEAR); //현재 년
	      thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
	      int today = cal.get(Calendar.DATE); 
	      
	      boolean booToday = (intYear==thisYear)&&(thisMonth==intMmonth);
	      cal.set(intYear,intMmonth,1); //캘린더객체에 입력받은 년, 달, 그리고 Date을 1로설정
	      
	      int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
	      int endDate = cal.getActualMaximum(Calendar.DATE); //달의 마지막일 얻기
	      
	      
	      System.out.println("===================== "+ thisYear+"년  " + (thisMonth+1) + "월 ==================");
	      //요일명 출력
	      System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");  
	      System.out.println("====================================================");
	      //1일이 시작되는 이전의 요일 공백으로 채우기 
	      
	      intDateNum = 1; //출력할 date를 저장할 변수
	     
	      for (int i = 1; intDateNum <= endDate ; i++) {   // 출력한 Date 변수(intDateNum)가 1일부터 마지막일이 될때까지 반복.         
	    	  
	          if(i<sDayNum) {
	        	  System.out.printf("%3s\t","★");
	        	  
	        	  aa.jdata[i+6]=new JButton("");//i가 요일숫자보다 작으면 공백으로 채우기   
	          }
	          
	         
	          else{
	        	  aa.jdata[i+6]=new JButton(""+intDateNum);
	        	 
	              if(booToday && intDateNum==today) System.out.printf("[%2d]\t",intDateNum); //오늘 날짜 표시
	              else System.out.printf("%3d\t",intDateNum); //일반 출력
	              
	              intDateNum++; //출력할 date 증가
	          }            
	          if(i%7==0) System.out.println(); // i가 7의배수가 되면 줄바꿈
	         
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
	  
	 
	
	 public static void saveEnfilelist(Enfileprogram aaa,String username) {// 암호전체파일리스트
    	
    	
    	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username;
    	String today =	aaa.getToday();
    	try{
            File filePath = new File(fileName) ;
            File file = new File(fileName+"\\enlist.txt");
            if (!file.exists()) {//파일이 없다면
    			try {
    				filePath.mkdir();	//폴더가 없을 경우 생성
    				file.createNewFile(); // 파일 생성				
    			} catch (Exception e) {
    				e.getStackTrace();
    			}
            }
            FileWriter fw = new FileWriter(file, true) ;
            if(EncTool.success) {// 성공하면
            	
            fw.write(aaa.textField.getText() + "\n" + today + "\n");

            fw.flush();
            fw.close(); 
           
            }
          
        }catch(Exception e){

            e.printStackTrace();

        }
  }
    
public static void openEnfilelist(String username) {// 암호전체파일리스트
    	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username;
        File filePath = new File(fileName) ;
        File file = new File(fileName+"\\enlist.txt");
        if (!file.exists()) {//파일이 없다면
			try {
				filePath.mkdir();	//폴더가 없을 경우 생성
				file.createNewFile(); // 파일 생성				
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
    
  

  public static void Enfilelist1(Enfileprogram aaa,String username) { // 날짜별로 저장하는 메소드
  	String fileName = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\" +username ;
  	String today = aaa.getToday();
  	try{
        File filePath = new File(fileName) ;
        File file = new File(fileName+"\\"+today +".txt");
        if (!file.exists()) {//파일이 없다면
			try {
				filePath.mkdir();	//폴더가 없을 경우 생성
				file.createNewFile(); // 파일 생성				
			} catch (Exception e) {
				e.getStackTrace();
			}
        }
           FileWriter fw = new FileWriter(file, true) ;

          if(aaa.textField.getText() != null) {
          fw.write(aaa.textField.getText() + "는" + today + "에 암호화 되었습니다" + "\n");

          fw.flush();
          fw.close(); 
          }
  	}catch(Exception e){

        e.printStackTrace();
      }
    }
   
}
    
    

