import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MyInformation extends JFrame  implements ActionListener{ // ������ GUI
	private JTextField textField;
	private JTextField textField_1;
	JLabel label1 = new JLabel("�̸��� ");
	JLabel label2 = new JLabel("��й�ȣ ");
	JButton button1 = new JButton("�̸��Ϻ���");
	JButton button2 = new JButton("��й�ȣ����");
	
	public MyInformation() {
		setTitle("���Ͼ�ȣ���α׷�");
		getContentPane().setLayout(null);
		
		
		label1.setBounds(63, 78, 62, 18);
		getContentPane().add(label1);
		
		textField = new JTextField();
		textField.setBounds(133, 75, 116, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		label2.setBounds(63, 130, 72, 18);
		getContentPane().add(label2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(133, 127, 116, 24);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		button1.setBounds(263, 74, 127, 27);
		getContentPane().add(button1);
		
		
		button2.setBounds(263, 126, 127, 27);
		getContentPane().add(button2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			}
		else if (e.getSource() == button2) {
		}
	}

}
