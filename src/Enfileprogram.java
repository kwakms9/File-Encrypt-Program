import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Enfileprogram extends JFrame  implements ActionListener{private static final ActionEvent ActionEvent = null;
// 로그인 이후 GUI
	
	
	static JTextField textField = new JTextField();
	static JTextField textField_1 = new JTextField();
	JLabel label1 = new JLabel("암호화 할 파일 선택");
	JButton button1 = new JButton("파일선택");
	JButton button2 = new JButton("암호화");
	JLabel label2 = new JLabel("복호화 할 파일 선택");
	JButton button3 = new JButton("파일선택");
	JButton button4 = new JButton("복호화");
	JButton button5 = new JButton("달력보기");
	JButton button6 = new JButton("암호화 파일 리스트");
	JButton button7 = new JButton("내 정보");
	static String filepath ;
	static String filepath2 ;
	private UserLogin loadData;
	private String prKey;
	
	
	public Enfileprogram(UserLogin loadData) {
		this.loadData = loadData;
		prKey = EncTool.makenNewHexString(loadData.getUsername());
		setSize(600, 500); 
		setTitle("파일암호프로그램");
		getContentPane().setLayout(null);
		
		
		label1.setBounds(14, 12, 133, 18);
		getContentPane().add(label1);
		
		
		button1.setBounds(161, 8, 105, 27);
		getContentPane().add(button1);
		
		
		//파일찾기에서 선택한 파일이름을 열 때( 파일 경로 )
		textField.setText(filepath);
		textField.setBounds(14, 40, 252, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		button2.setBounds(276, 39, 105, 27);
		getContentPane().add(button2);
		
		
		label2.setBounds(14, 76, 133, 18);
		getContentPane().add(label2);
		
		
		button3.setBounds(161, 72, 105, 27);
		getContentPane().add(button3);
		
		
		textField_1.setText(filepath2);
		textField_1 = new JTextField();
		textField_1.setBounds(14, 106, 252, 24);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		button4.setBounds(276, 105, 105, 27);
		getContentPane().add(button4);
		
		
		button5.setBounds(14, 136, 190, 27);
		getContentPane().add(button5);
		
		
		button6.setBounds(14, 175, 190, 27);
		getContentPane().add(button6);
		
		
		button7.setBounds(14, 214, 190, 27);
		getContentPane().add(button7);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//창닫으면 부수적인 프레임들 종료
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		textField.addActionListener(this);
		textField_1.addActionListener(this);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {//파일선택
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭 
				filepath = chooser.getSelectedFile().getAbsolutePath();
				textField.setText(filepath);
			}
			
		}
		else if (e.getSource() == button2) {//암호화
			EncTool.encryptAESCTRPrivate(filepath,prKey);
			calender.Enfilelist1(this,loadData.getUsername());
			calender.saveEnfilelist(this,loadData.getUsername());
			JOptionPane.showMessageDialog(null, "암호화완료!");
			
			
			
			
		}
		else if (e.getSource() == button3) {//파일선택
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭 
				filepath2 = chooser.getSelectedFile().getAbsolutePath();
				textField_1.setText(filepath2);
			 }
			
		}
		else if (e.getSource() == button4) {//복호화
			EncTool.decryptAESCTRPrivate(filepath2,prKey);
			JOptionPane.showMessageDialog(null, "복호화완료!");
			
		}
		else if (e.getSource() == button5) {//달력프레임
			new calenderFrame(loadData.getUsername());
			
			
		}
		else if (e.getSource() == button6) {//암호화리스트
			calender.openEnfilelist(loadData.getUsername());
			
		}
		else if (e.getSource() == button7) {//내정보프레임
			new MyInformation(loadData);
			
		}
	}
	
	public String getToday() { //날짜얻기
		Calendar cal = Calendar.getInstance();
        int year = cal.get ( cal.YEAR );
		int month = cal.get ( cal.MONTH ) + 1 ;
        int date = cal.get ( cal.DATE ) ;
        String today = (year + "-" + month + "-" + date);
        return today;

	}
	
	
	
	
}
	
	
	
