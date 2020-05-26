import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;

public class loginFrame extends JFrame  implements ActionListener, KeyListener{ //프로그램 첫 실행 GUI
	private JTextField id = new JTextField();
	JLabel label1 = new JLabel("아이디");
	JLabel label2 = new JLabel("비밀번호");
	JButton button1 = new JButton("로그인하기");
	JButton button2 = new JButton("아이디 찾기");
	JButton button3 = new JButton("비밀번호 찾기");
	JLabel label3 = new JLabel("아이디 또는 비밀번호를 잊어버리셨나요?");
	private JPasswordField password = new JPasswordField();
	private JButton button4 = new JButton("회원가입");
	private UserLogin loadData = new UserLogin();
	
	public loginFrame() {
		setTitle("파일암호프로그램");
		getContentPane().setLayout(null);
		
		
		label1.setBounds(60, 47, 65, 18);
		getContentPane().add(label1);
		
		label2.setBounds(60, 89, 62, 18);
		getContentPane().add(label2);
		
		id.setBounds(136, 44, 116, 24);
		getContentPane().add(id);
		id.setColumns(10);
		
		button1.setBounds(279, 43, 105, 27);
		getContentPane().add(button1);
		
		button2.setBounds(88, 180, 121, 27);
		getContentPane().add(button2);
		
		button3.setBounds(225, 180, 121, 27);
		getContentPane().add(button3);
		
		label3.setBounds(88, 150, 313, 18);
		getContentPane().add(label3);
		password.setBounds(136, 86, 116, 24);
		
		getContentPane().add(password);
		button4.setBounds(279, 85, 105, 27);
		
		getContentPane().add(button4);
		
		this.setSize(450, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button1.addKeyListener(this);
		id.addKeyListener(this);
		password.addKeyListener(this);
		
	}
	
	public void action1() {
		if(!id.getText().equals(""))
		{
			if(!password.getText().equals("")) {
				if(loadData.login(id.getText(), password.getText())) {// 로그인하기
					this.dispose();
					/********성공했을 경우 메뉴로*********/
				}	
			}else {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요!");
			}
		}else {
			JOptionPane.showMessageDialog(null, "아이디를 입력하세요!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			action1();
		}
		else if (e.getSource() == button2) {
			loadData.findData("ID");
		}
		else if (e.getSource() == button3) {
			loadData.findData("PW");
		}
		else if (e.getSource() == button4) {
			loadData.getSignUpFrame();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			action1();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
