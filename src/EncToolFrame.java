import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;


public class EncToolFrame extends JFrame  implements ActionListener {
	public JButton button1 = new JButton("�α����ϱ�");
	public JButton button2 = new JButton("���������");
	public JButton button3 = new JButton("����ã��");
	
	public EncToolFrame() {
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		setBackground(Color.WHITE);
		setFont(null);
		setTitle("���Ͼ�ȣ���α׷�");
		getContentPane().setLayout(null);
		
		getContentPane().add(button1);
		button1.setForeground(Color.BLACK);
		button1.setBackground(Color.BLACK);
		button1.setBounds(150, 27, 124, 47);
		
		getContentPane().add(button2);
		button2.setBackground(Color.BLACK);
		button2.setBounds(150, 97, 124, 47);
		
		
		getContentPane().add(button3);
		button3.setBackground(Color.BLACK);
		button3.setBounds(150, 169, 124, 47);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button1) {
			loginFlame();
		}
		else if(e.getSource() == button2) {
			createUser();
		}
		else if(e.getSource() == button3) {
			findUser();
		}
	}
	
	public void loginFlame() {
		
	}
	
	public void createUser() {
		
	}

	public void findUser() {
		
	}
	
		public static void main(String[] args) {}
}


	







