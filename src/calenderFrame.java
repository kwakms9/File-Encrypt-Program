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
	      setTitle("�޷�"); 
	      setSize(600, 500);
	      setVisible(true); 
	      this.setLocationRelativeTo(null);
		  jp.setBounds(0, 461, 584, 0);
	   
		  jp.setLayout(new GridLayout(0, 7)); 
	       
	      jdata[0] = new JButton("��");
	      jdata[1] = new JButton("��");
	      jdata[2] = new JButton("ȭ");
	      jdata[3] = new JButton("��");
	      jdata[4] = new JButton("��");
	      jdata[5] = new JButton("��");
	      jdata[6] = new JButton("��");
	       
	      for(int i = 0; i<49;i++) {
	    	 jp.add(jdata[i]);
	    }
	     setVisible(true);
	     JButton btnNewButton = new JButton(ca.thisYear + "��"+ ca.thisMonth +"��");
	     getContentPane().add(btnNewButton, BorderLayout.NORTH);
		 getContentPane().add(jp, BorderLayout.CENTER);
		for(int i = 0; i<49 ;i++) {
		 jdata[i].addActionListener(this);
		}
		 
	     }
	     
	   
	    
	     @Override
	      public void actionPerformed(ActionEvent e) {
	    	 
	         
	            for(int i=7;i<49;i++) {// ��¥��ư�� ������ ������ ����
	            if (e.getSource() == jdata[i]) {
	            	date1 = (ca.thisYear+"-"+ca.thisMonth+"-"+ jdata[i].getText()); 
	            	String datePath =System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username+"\\";
	            	
	            	for (File date : new File(datePath).listFiles()) { 
	            		File datetmp = date;
	            		if (datetmp.isFile()) {	//������ �ִٸ�
	                        //System.out.println(date1+".txt"+"-----"+datetmp.getName());	//�����̸� ���
	                        if(datetmp.getName().equals(date1+".txt")) {
	                        	try {
								Desktop.getDesktop().edit(new File(System.getProperty("user.home") + "\\Documents\\FileEncrytProgram\\"+username+"\\"+date1 +".txt"));
							} catch (Exception e1) {
								// TODO Auto-generated catch blockkk
								JOptionPane.showMessageDialog(null, "�����Ͱ� �������� �ʽ��ϴ�!");
								//e1.printStackTrace();
							}
	                        }
	                        
	                    }
	                }
	            }
	          }
	      }
	   }
	     
