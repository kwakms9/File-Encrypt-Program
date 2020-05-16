import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class UserLogin {
	private String username = null; // null �̸� logout ����
	//private String password = null;
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//�������� ���
	private String divUnit = "##";
	//out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.bin")));//true�����Ƿ� �����
	private String userData[][];
	String tmpUserData = "";
	/**********�������� id##pw##email\n************/
	UserLogin() {	//�α����� ���� ���� �ҷ�����
		
		File Folder = new File(savePath);
		File data = new File(savePath+"\\data.txt");
		if (!Folder.exists()) {
			try {
				Folder.mkdir();	//������ ���� ��� ����
				data.createNewFile(); // ���� ����
			} catch (Exception e) {
				e.getStackTrace();
			}
		}else if(!data.exists()) {//���������� ���� ������
			try {
				data.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		fileLoad();
		
	}
	
	
	public void fileLoad() {
		try {
			BufferedReader in = null;
			in = new BufferedReader(new FileReader(savePath+"\\data.txt"));
			
			String temp;
			while((temp=in.readLine()) != null) { tmpUserData+=temp+"\n"; }	//����� ���� �б�
			//System.out.println(userData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(!tmpUserData.equals("")) {//������ ������� Ȯ��
			String splitTmp[] =tmpUserData.split("\n");
			userData = new String[splitTmp.length][];	//����� ����ŭ ���� �Ҵ�
			
			for(int i=0;i<splitTmp.length;i++) {
				userData[i] = splitTmp[i].split(divUnit);//���̵� ������� �и�  0 id, 1 pw, 2 email
			}
		}
	}
	
	
	public void login(String username, String password) {
		this.username = username;
		//this.password = password;//gui���ӽ�
		
		if(idOrPwCheck(username,password)) {
			System.out.println("�α��� ����");
		}else {
			System.out.println("����");
		}
	}
	
	
	public boolean signUp(String id,String pw) {//���Խ� �ߺ�üũ�� �����Է� 
		if(!idOrPwCheck(id)) {	//�ߺ��� �ƴҰ��
			//this.username=id;
			//this.password=pw;
			savefile(id+divUnit+pw);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("�ߺ��� ID");
		}
		return false;
	}
	
	public boolean signUp(String id,String pw,String email) {//���Խ� �ߺ�üũ�� �����Է�  @�̸��Ͽ� ���� �����ε�
		if(!idOrPwCheck(id)) {	//�ߺ��� �ƴҰ��
			savefile(id+divUnit+pw+divUnit+email);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("�ߺ��� ID");
		}
		return false;
	}
	
	
	public void savefile(String str) {
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt",true)));//true�� append
			out.write(str+"\n");	//�ٳѱ� �����ϱ� �߰�
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	public void saveUpdatefile() {//���� update
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt")));//true=�����Ƿ� �����, true�� append
			String tmp=null;
			for(int i=0;i<userData.length;i++) {
				tmp="";
				for(int j=0;j<userData[i].length;j++){
					tmp+=userData[i][j];
					if(j<1) {	tmp+=divUnit;
					}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//�̸��� �ִ� ���� ��� �ϳ��� 
				}
				out.write(tmp+"\n");
			}
			//out.write(tmp);	//�ٳѱ� �����ϱ� �߰�
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	public boolean idOrPwCheck(String username) {	//���̵� �ִ��� Ȯ��
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				System.out.println("�ִ¾��̵� ID");
				return true;	//�ִ�
				}
		}
		return false;	//��ã������� �ߺ����� �ʴ� ���̵�
	}
	
	public boolean idOrPwCheck(String username, String password) {	//���� ��� Ȯ��
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				if(userData[i][1].equals(password)) {
					return true;
				}
			}
		}
		return false;	//��ã�������
	}
	/******************************�̰Ŷ�*****************/
	public String bringSignUpId(String email) {//�̸��Ϸ� id ��������
		String tmp="";
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
				if(email.equals(userData[i][2])) {//�̸��� ��ġ ���� Ȯ��
					tmp+= userData[i][0]+" ";
				}
			}
		}
		if(!tmp.equals(""))	
		{
			return tmp;	// ���̵�� ������
		}
		return null;	//����ġ
	}
	
	public String bringEmail(String id) {//id�� �̸��� ã��
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
				if(id.equals(userData[i][0])) {//�̸��� ��ġ ���� Ȯ��
					return userData[i][2];	//�Ѱ��� �̸��Ϸ� 2�� �̻� �����������������Ƿ�
				}
			}
		}
		return null;
	}
	
	private void setPw(String id,String newPw) {
		for(int i=0;i<userData.length;i++) {
			if(id.equals(userData[i][0])) {//�̸��� ��ġ ���� Ȯ��
				userData[i][1] = newPw;
				saveUpdatefile();
				fileLoad();
				break;
			}
		}

	}
	/******************************�̰� ��ġ�� �͵�? +��й�ȣ ã�� �ִ°� ����*****************/
	public void findId(String email) {	//�̸����� ��ϵ� ��츸 ����
		String id = bringSignUpId(email);
		if(id !=null) {//�̸��� ��ġ ���� Ȯ��
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "����� ���̵�� "+id+" �Դϴ�");
			System.out.println("����� �̸��Ϸ� ���̵� ���۵Ǿ����ϴ�.");
			return;
		}else {
		System.out.println("�ش� �̸��Ϸ� ���Ե� ������ �����ϴ�.");
		}
	}
	
	
	public void findPwAndReset(String id) {
		boolean result = false;
		result=VerificationCode(id);	//�����ڵ� ���
		if(result) {
			new NewPassword(id);
		}
	}
	
	
	public boolean VerificationCode(String id) {	//ã�� id �Է¹�����
		System.out.println("�̸��Ϸ� �����ڵ�߼�");
		SendEmail mail = new SendEmail();
		String email = bringEmail(id);
		String verificationCode="";
		
		if(email != null ) {//�̸��� ��ġ ���� Ȯ��
			for(int j=0;j<6;j++) {
			verificationCode+=""+(int)Math.random()*10;
			}
			mail.sender(email, "File-Encrypt-Program", "�����ڵ�: "+verificationCode);
			System.out.println("����� �̸��Ϸ� �����ڵ尡 ���۵Ǿ����ϴ�. ��ȿ�ð�:3��");
			CodeFrame confirmWindow =new CodeFrame(verificationCode);
			verificationCode = null;	//�����ڵ� �ʱ�ȭ
			
			if(!frameTimer(180,confirmWindow)) {	//�ð� ���� �Է����� �� ���� ��� 3��
				JOptionPane.showMessageDialog(null, "�����ڵ� ��ȿ�ð��� �ʰ��Ͽ����ϴ�.");
				System.out.println("�����ڵ� ��ȿ�Ⱓ�� �ʰ��Ͽ����ϴ�.");
				return false;	// fail
			}
			System.out.println("finish");
			return true;	//success
		}
		System.out.println("�ش� ���̵� ��ϵ� �̸����� �����ϴ�.");
		return false;
		
	}
	
	public void comfirmCode(String verificationCode) {		/*******GUi���� ����Ȯ�α����ϱ�*********�Ϸ�*/
		
	}
	
	public boolean frameTimer(int sec,CodeFrame window) {
		long starTime;
		SimpleDateFormat format = new SimpleDateFormat ( "mm:ss");
		int currentTime;
		starTime=System.currentTimeMillis();
		do {
			currentTime = (int)(System.currentTimeMillis()-starTime)/1000;
			window.time.setText("  �����ð� "+format.format((sec-currentTime)*1000));
			System.out.println(format.format((sec-currentTime)*1000)+"����"+window.closeWin+window.recognized);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(currentTime<=sec&&!(window.closeWin));	//�����ư ������
		window.dispose();
		return window.recognized;
	}

	
	class CodeFrame extends JFrame implements ActionListener, KeyListener, WindowListener{	//�����ڵ� â
		private boolean recognized = false;
		boolean closeWin = false;
		JLabel text = new JLabel("�����ڵ�:");
		JLabel time = new JLabel("");
		JTextField textCode = new JTextField(10);
		JPanel panel = new JPanel();
		JButton confirm = new JButton("Ȯ��");
		private String verificationCode;
		
		
		CodeFrame(String verificationCode){
			this.verificationCode = verificationCode;
			panel.add(text);
			panel.add(textCode);
			panel.add(confirm);
			
			this.setLayout(new GridLayout(3,3));
			this.add(time);
			this.add(panel);
			this.pack();
			this.setSize(300, 170);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("�����ڵ� Ȯ��");
			
			confirm.addActionListener(this);
			confirm.addKeyListener(this);
			textCode.addKeyListener(this);   
			addWindowListener(this);
		} 
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(textCode.getText().equals(verificationCode)) {
					dispose();
					JOptionPane.showMessageDialog(null, "��������!");
					closeWin = true;
					recognized = true;
				}else {
					JOptionPane.showMessageDialog(null, "������ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==confirm) {	//press confirm button
				if(textCode.getText().equals(verificationCode)) {	//compare code
					dispose();
					JOptionPane.showMessageDialog(null, "��������!");
					closeWin = true;
					recognized = true;
				}else {
					JOptionPane.showMessageDialog(null, "������ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
			}
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void windowClosing(WindowEvent e) {
			closeWin=true;
		}
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub	
		}
	}
	
	class NewPassword extends JFrame implements ActionListener, KeyListener{	//�����ڵ� â
		JLabel text1 = new JLabel("�� ��й�ȣ:");
		JLabel text2 = new JLabel("��й�ȣ Ȯ��:");
		JPasswordField pwtext = new JPasswordField(10);
		JPasswordField repwtext = new JPasswordField(10);
		JPanel panel = new JPanel(new GridLayout(2,2));
		JButton complete = new JButton("�Ϸ�");
		String id;
		
		NewPassword(String id){
			this.id = id;
			panel.add(text1);
			panel.add(pwtext);
			panel.add(text2);
			panel.add(repwtext);
			
			this.setLayout(new GridLayout(3,3));
			//this.add();
			this.add(panel);
			this.add(complete,BorderLayout.LINE_END);
			this.pack();
			this.setSize(300, 200);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("��й�ȣ ����");
			
			complete.addActionListener(this);
		} 
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==complete) {	//press confirm button
				if(!pwtext.getText().equals("")) {
					if(pwtext.getText().equals(repwtext.getText())) {	//compare code
						dispose();
						setPw(id,pwtext.getText());//���� pw��  �������� ����ġ�� ���ο� pw save
						System.out.println("����Ϸ�");
						JOptionPane.showMessageDialog(null, "����Ϸ�!");
						
					}else {
						JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�!");
					}
				}else { JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��Ͻʽÿ�."); }
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {	//press confirm button
				if(!pwtext.getText().equals("")) {
					if(pwtext.getText().equals(repwtext.getText())) {	//compare code
						dispose();
						setPw(id,pwtext.getText());//���� pw��  �������� ����ġ�� ���ο� pw save
						System.out.println("����Ϸ�");
						JOptionPane.showMessageDialog(null, "����Ϸ�!");
						
					}else {
						JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�!");
					}
				}else { JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��Ͻʽÿ�."); }
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}