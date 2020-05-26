import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import java.awt.event.ActionEvent;

public class Enfileprogram extends JFrame  implements ActionListener{// 로그인 이후 GUI
	private JTextField textField;
	private JTextField textField_1;
	JLabel label1 = new JLabel("암호화 할 파일 선택");
	JButton button1 = new JButton("파일선택");
	JButton button2 = new JButton("암호화");
	JLabel label2 = new JLabel("복호화 할 파일 선택");
	JButton button3 = new JButton("파일선택");
	JButton button4 = new JButton("복호화");
	JButton button5 = new JButton("달력보기");
	JButton button6 = new JButton("암호화 파일 리스트");
	JButton button7 = new JButton("내 정보");
	
	public Enfileprogram() {
		setTitle("파일암호프로그램");
		getContentPane().setLayout(null);
		
		
		label1.setBounds(14, 12, 133, 18);
		getContentPane().add(label1);
		
		
		button1.setBounds(161, 8, 105, 27);
		getContentPane().add(button1);
		
		textField = new JTextField();
		textField.setBounds(14, 40, 252, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		button2.setBounds(276, 39, 105, 27);
		getContentPane().add(button2);
		
		
		label2.setBounds(14, 76, 133, 18);
		getContentPane().add(label2);
		
		
		button3.setBounds(161, 72, 105, 27);
		getContentPane().add(button3);
		
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {//파일선택
			FileMenu.main(null);
		}
		else if (e.getSource() == button2) {//암호화
			EncTool.encryptAESCTR();
			calender.filesave();
		}
		else if (e.getSource() == button3) {//파일선택
			FileMenu.main(null);
		}
		else if (e.getSource() == button4) {//복호화
			EncTool.decryptAESCTR();
			
		}
		else if (e.getSource() == button5) {//달력프레임
			calenderFrame.main(null);
			
		}
		else if (e.getSource() == button6) {//암호화리스트
			calender.Enfilelist();
		}
		else if (e.getSource() == button7) {//내정보프레임
			MyInformation.main(null);
		}
	}
	
	
	
	
}
	
	
	
