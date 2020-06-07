import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Label;

public class MyInformation extends JFrame  implements ActionListener{ // �궡�젙蹂� GUI
	private JTextField textField;
	private JTextField textField_1;
	JPasswordField passwordField;
	JLabel label1 = new JLabel("아이디");
	JLabel label2 = new JLabel("이메일 ");
	JLabel label3 = new JLabel("비밀번호");
	JButton button1 = new JButton("이메일변경");
	JButton button2 = new JButton("비밀번호변경");
	private JTextField textField_2;
	private UserLogin loadData;
	
	public MyInformation(UserLogin loadData) {
		this.loadData = loadData;
		setTitle("파일암호프로그램");
		setSize(600, 500); 
		setVisible(true);
		getContentPane().setLayout(null);
		
		
		label1.setBounds(63, 38, 62, 18);//�븘�씠�뵒
		getContentPane().add(label1);
		
		textField = new JTextField(loadData.getUsername());
		textField.setBounds(133, 37, 116, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		label2.setBounds(63, 77, 72, 18);// �씠硫붿씪
		getContentPane().add(label2);
		
		textField_1 = new JTextField(loadData.bringEmail(loadData.getUsername()));
		textField_1.setBounds(133, 75, 116, 24);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		button1.setBounds(263, 74, 127, 27);
		getContentPane().add(button1);
		
		
		button2.setBounds(261, 107, 127, 27);
		getContentPane().add(button2);
		
		
		label3.setBounds(63, 113, 57, 15);//鍮꾨�踰덊샇
		getContentPane().add(label3);
		this.setLocationRelativeTo(null);
		passwordField = new JPasswordField();
		passwordField.setBounds(133, 109, 116, 24);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);
		button1.addActionListener(this);
		button2.addActionListener(this);	
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			JOptionPane.showMessageDialog(null, "인증코드 전송중!");
			loadData.emailReset(loadData.getUsername(), textField_1.getText());
			}
		else if (e.getSource() == button2) {
			loadData.logedInPwReset(loadData.getUsername(), passwordField.getText());
		}
	}
}
