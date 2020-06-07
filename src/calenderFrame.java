import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;




public class calenderFrame extends JFrame  implements ActionListener{
	    private static final LayoutManager North = null;
		JButton[] jdata = new JButton[49];
	    JPanel jp = new JPanel();
	    calender ca = new calender();
	    String date1;
		String date2;
		String username;
		 
	     public calenderFrame(String username) {
	      this.username = username;
	      ca.getMonthGalendar(Calendar.YEAR,Calendar.MONTH-4,this);
	      setSize(600, 500);
	      setTitle("달력"); 
	      setSize(600, 500);
	      setVisible(true); 
	      this.setLocationRelativeTo(null);
		  jp.setBounds(0, 461, 584, 0);
	   
		  jp.setLayout(new GridLayout(0, 7)); 
	       
	      jdata[0] = new JButton("일");
	      jdata[1] = new JButton("월");
	      jdata[2] = new JButton("화");
	      jdata[3] = new JButton("수");
	      jdata[4] = new JButton("목");
	      jdata[5] = new JButton("금");
	      jdata[6] = new JButton("토");
	       
	      for(int i = 0; i<49;i++) {
	    	 jp.add(jdata[i]);
	    }
	     setVisible(true);
	     JButton btnNewButton = new JButton(ca.thisYear + "년"+ ca.thisMonth +"월");
	     getContentPane().add(btnNewButton, BorderLayout.NORTH);
		 getContentPane().add(jp, BorderLayout.CENTER);
		for(int i = 0; i<49 ;i++) {
		 jdata[i].addActionListener(this);
		}
		 
	     }
	     
	   
	    
	     @Override
	      public void actionPerformed(ActionEvent e) {
	    	 
	         
	            for(int i=7;i<49;i++) {// 날짜버튼을 누를때 열리는 파일
	            if (e.getSource() == jdata[i]) {
	            	date1 = (ca.thisYear+"-"+ca.thisMonth+"-"+ jdata[i].getText()); 
	            	String datePath =System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username+"\\";
	            	
	            	for (File date : new File(datePath).listFiles()) { 
	            		File datetmp = date;
	            		if (datetmp.isFile()) {	//파일이 있다면
	                        //System.out.println(date1+".txt"+"-----"+datetmp.getName());	//파일이름 출력
	                        if(datetmp.getName().equals(date1+".txt")) {
	                        	try {
								Desktop.getDesktop().edit(new File(System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username+"\\"+date1 +".txt"));
							} catch (Exception e1) {
								// TODO Auto-generated catch blockkk
								JOptionPane.showMessageDialog(null, "데이터가 존재하지 않습니다!");
								//e1.printStackTrace();
							}
	                        }
	                        
	                    }
	                }
	            }
	          }
	      }
	   }
	     
