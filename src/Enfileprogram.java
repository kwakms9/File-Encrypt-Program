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

public class Enfileprogram extends JFrame  implements ActionListener{// �α��� ���� GUI
	private JTextField textField;
	private JTextField textField_1;
	JLabel label1 = new JLabel("��ȣȭ �� ���� ����");
	JButton button1 = new JButton("���ϼ���");
	JButton button2 = new JButton("��ȣȭ");
	JLabel label2 = new JLabel("��ȣȭ �� ���� ����");
	JButton button3 = new JButton("���ϼ���");
	JButton button4 = new JButton("��ȣȭ");
	JButton button5 = new JButton("�޷º���");
	JButton button6 = new JButton("��ȣȭ ���� ����Ʈ");
	JButton button7 = new JButton("�� ����");
	
	public Enfileprogram() {
		setTitle("���Ͼ�ȣ���α׷�");
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
		if (e.getSource() == button1) {//���ϼ���
			FileMenu.main(null);
		}
		else if (e.getSource() == button2) {//��ȣȭ
			EncTool.encryptAESCTR();
			calender.filesave();
		}
		else if (e.getSource() == button3) {//���ϼ���
			FileMenu.main(null);
		}
		else if (e.getSource() == button4) {//��ȣȭ
			EncTool.decryptAESCTR();
			
		}
		else if (e.getSource() == button5) {//�޷�������
			calenderFrame.main(null);
			
		}
		else if (e.getSource() == button6) {//��ȣȭ����Ʈ
			calender.Enfilelist();
		}
		else if (e.getSource() == button7) {//������������
			MyInformation.main(null);
		}
	}
	
	
	
	
}
	
	
	
