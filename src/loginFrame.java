import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;

public class loginFrame extends JFrame  implements ActionListener{
	private JTextField textField;
	JLabel label1 = new JLabel("아이디");
	JLabel label2 = new JLabel("비밀번호");
	JButton button1 = new JButton("로그인하기");
	JButton button2 = new JButton("아이디 찾기");
	JButton button3 = new JButton("비밀번호 찾기");
	JLabel label3 = new JLabel("아이디 또는 비밀번호를 잊어버리셨나요?");
	private final JPasswordField passwordField = new JPasswordField();
	private final JButton button4 = new JButton("회원가입");
	
	public loginFrame() {
		setTitle("파일암호프로그램");
		getContentPane().setLayout(null);
		
		
		label1.setBounds(60, 47, 65, 18);
		getContentPane().add(label1);
		
		label2.setBounds(60, 89, 62, 18);
		getContentPane().add(label2);
		
		textField = new JTextField();
		textField.setBounds(136, 44, 116, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		button1.setBounds(279, 43, 105, 27);
		getContentPane().add(button1);
		
		button2.setFont(new Font("Gulim", Font.PLAIN, 15));
		button2.setBounds(88, 180, 121, 27);
		getContentPane().add(button2);
		
		button3.setBounds(225, 180, 121, 27);
		getContentPane().add(button3);
		
		label3.setBounds(88, 150, 313, 18);
		getContentPane().add(label3);
		passwordField.setBounds(136, 86, 116, 24);
		
		getContentPane().add(passwordField);
		button4.setBounds(279, 85, 105, 27);
		
		getContentPane().add(button4);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
		}
		else if (e.getSource() == button2) {
		}
		else if (e.getSource() == button3) {
		}
		else if (e.getSource() == button4) {
		}
	}
	

}
