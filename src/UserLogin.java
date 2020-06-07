import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.SwingConstants;


public class UserLogin {
	private String username = null; // null �̸� logout ����
	//private String password = null;
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//�������� ���
	private String divUnit = "#%&&!#";	//������ ������
	private String userData[][];
	String tmpUserData = "";
	/**********�������� id##pw##email\n************/
	UserLogin() {	//�α����� ���� ���� �ҷ�����
		
		File Folder = new File(savePath);
		File data = new File(savePath+"\\data.tmp");
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
			userData =null;	//������ �ִ� ������ �����
			BufferedReader in = null;
			EncTool.decryptAESCTR(savePath+"\\data.enc", savePath+"\\data.tmp");	//��ȣȭ�� ���� ��ȣȭ
			
			in = new BufferedReader(new FileReader(savePath+"\\data.tmp"));	
			String temp;
			while((temp=in.readLine()) != null) { if(temp.contains(divUnit)) tmpUserData+=temp+"\n"; }	//����� ���� �б�
			//System.out.println(userData);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("���Ͼ��� �ʱ����");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.gc();
			new File(savePath+"\\data.tmp").delete();	//���Ϸε尡 ������ ����� ������ �ı�
		} 
		
		if(!tmpUserData.equals("")) {//������ ������� Ȯ��
			//tmpUserData=tmpUserData.substring(0, tmpUserData.length()-1);
			String splitTmp[] =tmpUserData.split("\n");
			userData = new String[splitTmp.length][];	//����� ����ŭ ���� �Ҵ�
			System.out.println(userData.length);
			for(int i=0;i<splitTmp.length;i++) {
				System.out.println(splitTmp[i]);
				userData[i] = splitTmp[i].split(divUnit);//���̵� ������� �и�  0 id, 1 pw, 2 email
			}
		}
		
	}
	
	
	public boolean login(String username, String password) {
		this.username = username;
		//this.password = password;//gui���ӽ�
		
		if(idOrPwCheck(username,password)) {
			System.out.println("�α��� ����");
			JOptionPane.showMessageDialog(null, "�α��� ����!");
			return true;	//�α��μ���
		}else {
			System.out.println("����");
			JOptionPane.showMessageDialog(null, "�α��� ����!");
			return false;
		}
		
	}
	
	public void signUp(String id,String pw,String email) {  //@�̸��Ͽ� ���� ����
		if(email.contains("@")) {
			saveUpdatefile(id+divUnit+pw+divUnit+email);
		}else {
			saveUpdatefile(id+divUnit+pw);
		}
			fileLoad();	//reload file
	}
	
	/*
	public void savefile(String str) {	//append
		try {
			EncTool.decryptAESCTR(savePath+"\\data.enc", savePath+"\\data.tmp");
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp",true)));//true�� append
			out.write(str+"$$$");	//�ٳѱ� �����ϱ� �߰�
			out.flush();
			out.close();
			EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//��ȣȭ
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				//new File(savePath+"\\data.tmp").delete();	//��ȣȭ�� ����
			} 
	}*/
	
	public void saveUpdatefile(String str) {//���� update �����
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));//true=�����Ƿ� �����, true�� append
			String tmp=null;
			try {
				for(int i=0;i<userData.length;i++) {
					tmp="";
					for(int j=0;j<userData[i].length;j++){
						tmp+=userData[i][j];
						if(j<1) {	tmp+=divUnit;
						}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//�̸��� �ִ� ���� ��� �ϳ��� 
					}
					out.write(tmp+"\n"+str+"\n");
				}
			}catch(NullPointerException e) {}	//�ƹ� ���̵�  ���»���
			out.flush();
			out.close();
			EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//��ȣȭ
			new File(savePath+"\\data.tmp").delete();	//��ȣȭ �� ����
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				new File(savePath+"\\data.tmp").delete();	//��ȣȭ�� ����
			} 
	}
	
	public void saveUpdatefile() {//���� update �����
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));//true=�����Ƿ� �����, true�� append
			String tmp=null;
			try {
				for(int i=0;i<userData.length;i++) {
					tmp="";
					for(int j=0;j<userData[i].length;j++){
						tmp+=userData[i][j];
						if(j<1) {	tmp+=divUnit;
						}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//�̸��� �ִ� ���� ��� �ϳ��� 
					}
					out.write(tmp+"\n");
				}
			}catch(NullPointerException e) {}	//�ƹ� ���̵�  ���»���
			out.flush();
			out.close();
			EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//��ȣȭ
			new File(savePath+"\\data.tmp").delete();	//��ȣȭ �� ����
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				new File(savePath+"\\data.tmp").delete();	//��ȣȭ�� ����
			} 
	}
	
	public boolean idOrPwCheck(String username) {	//���̵� �ִ��� Ȯ��
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i][0].equals(username)) {
					System.out.println("�ִ¾��̵� ID");
					return true;	//�ִ�
					}
			}
		} catch(NullPointerException e) {}	//�ƹ� ���̵�  ���»���
			return false;	//��ã������� �ߺ����� �ʴ� ���̵�
	}
	
	public boolean idOrPwCheck(String username, String password) {	//���� ��� Ȯ��
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i][0].equals(username)) {
					if(userData[i][1].equals(password)) {
						return true;
					}
				}
			}
		} catch(NullPointerException e) {}	//�ƹ� ���̵�  ���»���
		return false;	//��ã�������
	}
	/******************************���Ե� �̸��ϰ� ��ġ�� id��������*****************/
	public String bringSignUpId(String email) {//�̸��Ϸ� id ��������
		String tmp="";
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
					if(email.equals(userData[i][2])) {//�̸��� ��ġ ���� Ȯ��
						tmp+= userData[i][0]+" ";//�Ѱ��� �̸��Ϸ� 2�� �̻� �����������������Ƿ�
					}
				}
			}
		} catch(NullPointerException e) {}	//�ƹ� ���̵�  ���»���
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
					return userData[i][2];	
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
	
	
	private void setEmail(String id,String newEmail) {	//�̸��� ���� �� ����
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
				if(id.equals(userData[i][0])) {//�̸��� ��ġ ���� Ȯ��
					userData[i][2] = newEmail;
					saveUpdatefile();
					fileLoad();
					break;
				}
			}
		}

	}
	
	
	/******************************���̵� ��й�ȣ ã�� *****************/
	public void findData(String mode) {
		if(mode.equals("ID")) {	new FindIdOrPwFrame("ID");
		}else if(mode.equals("PW")) { new FindIdOrPwFrame("PW");
		}else { System.out.println("�߸��� �Է�");}
	}
	
	public void findId(String email) {	//�̸����� ��ϵ� ��츸 ����
		String id = bringSignUpId(email);
		if(id !=null) {//�̸��� ��ġ ���� Ȯ��
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "����� ���̵�� "+id+" �Դϴ�");
			System.out.println("����� �̸��Ϸ� ���̵� ���۵Ǿ����ϴ�.");
			JOptionPane.showMessageDialog(null, "����� �̸��Ϸ� ���̵� ���۵Ǿ����ϴ�.");
			return;
		}else {
		System.out.println("�ش� �̸��Ϸ� ���Ե� ������ �����ϴ�.");
		JOptionPane.showMessageDialog(null, "�ش� �̸��Ϸ� ���Ե� ������ �����ϴ�.");
		}
	}
	
	
	public void findPwAndReset(String id) {	//��й�ȣ ã�� �� �缳��
		Thread pwThread =new Thread()
		{
			@Override
			public void run()
			{
				boolean result = false;
				result=VerificationCode(id);	//�����ڵ� ���
				if(result) {
					new NewPassword(id);
				}
			}
		};
		pwThread.start();
	}
	
	
	public void logedInPwReset(String id,String pw) {	//�α����� ��й�ȣ ����
		if(idOrPwCheck(id,pw)) {	//��ġ�ϸ�
			new NewPassword(id);	//��й�ȣ ��
		}
	}
	
	
	public void emailReset(String id,String newEmail) {	//�̸��� �缳��
		Thread emailThread =new Thread()
		{
			@Override
			public void run()
			{
				boolean result = false;
				result=VerificationCode(id);	//�����ڵ� ���
				if(result) {
					String email =bringEmail(id);
					if(email == null) {	//�̸����� ���ٸ� �迭 ������ �����ϹǷ� ���� �Ҵ�
						for(int i=0;i<userData.length;i++) {
							if(id.equals(userData[i][0])) {//�̸��� ��ġ ���� Ȯ��
								userData[i]=(userData[i][0]+divUnit+userData[i][1]+divUnit+"temp").split(divUnit);	//ĭ �ٽ� Ȯ��
							}
						}
					}
					setEmail(id,newEmail);	//�̸��� ����
					JOptionPane.showMessageDialog(null, "�̸����� ��ϵǾ����ϴ�!");
				}
			}
		};
		emailThread.start();
			
		
	}
	
	public boolean VerificationCode(String id) {	//ã�� id �Է¹�����
		SendEmail mail = new SendEmail();
		String email = bringEmail(id);
		String verificationCode="";
		
		if(email != null ) {//�̸��� ��ġ ���� Ȯ��
			for(int j=0;j<6;j++) {
				verificationCode+=""+(int)(Math.random()*10);
			}
			mail.sender(email, "File-Encrypt-Program", "�����ڵ�: "+verificationCode);
			System.out.println("����� �̸��Ϸ� �����ڵ尡 ���۵Ǿ����ϴ�. ��ȿ�ð�:3��");
			JOptionPane.showMessageDialog(null, "����� �̸��Ϸ� �����ڵ尡 ���۵Ǿ����ϴ�. ��ȿ�ð�:3��");
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
		JOptionPane.showMessageDialog(null, "�ش� ���̵� ��ϵ� �̸����� �����ϴ�.");
		return false;
		
	}
	
	
	public boolean frameTimer(int sec,CodeFrame window) {
		long starTime;
		SimpleDateFormat format = new SimpleDateFormat ( "mm:ss");
		starTime=System.currentTimeMillis();
		int currentTime;
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
		}while(currentTime<=sec&&!(window.closeWin));
		window.dispose();	//â�ݱ�
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
			
			this.setLayout(new GridLayout(3,1));
			this.add(time);
			this.add(panel);
			//this.pack();
			this.setSize(300, 170);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("�����ڵ� Ȯ��");
			this.setResizable(false);
			
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
		private JLabel text1 = new JLabel("   �� ��й�ȣ: ");
		private JLabel text2 = new JLabel("��й�ȣ Ȯ��:");
		private JPasswordField pwtext = new JPasswordField(12);
		private JPasswordField repwtext = new JPasswordField(12);
		private JPanel panel = new JPanel();
		private JButton complete = new JButton("�Ϸ�");
		private String id;
		private JPanel panel2 = new JPanel();
		private JPanel panelBu = new JPanel();
		
		NewPassword(String id){
			this.id = id;
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
			panel.add(text1);
			panel.add(pwtext);
			panel2.add(text2, new FlowLayout());
			panel2.add(repwtext, new FlowLayout());
			this.setLayout(new GridLayout(3,0));
			this.add(panel);getContentPane().add(panel2);
			this.add(panelBu);
			panelBu.add(complete);

			pwtext.addKeyListener(this);
			repwtext.addKeyListener(this);
			complete.addKeyListener(this);
			complete.addActionListener(this);
			
			this.setSize(350, 200);
			this.setResizable(false);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("��й�ȣ ����");
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
	public void getSignUpFrame() { new SignUpFrame(); }
	private class SignUpFrame extends JFrame implements ActionListener, KeyListener, MouseListener, FocusListener{	//�����ڵ� â
		private JLabel idLabel = new JLabel("���̵�: ");
		private JLabel pwLabel = new JLabel("��й�ȣ:");
		private JLabel checkPwLabel = new JLabel("��й�ȣ Ȯ��:");
		private JLabel emailLabel = new JLabel("�̸���:");
		private JPasswordField pwField = new JPasswordField(12);
		private JPasswordField repwField = new JPasswordField(12);
		private JTextField id = new JTextField(12);
		private JTextField email = new JTextField(12);
		private JButton checkId = new JButton("�ߺ��˻�");
		private JButton signUpBt = new JButton("�����ϱ�");

		private JPanel idPanel = new JPanel();//id
		private JPanel pwPanel = new JPanel();//pw
		private JPanel checkpwPanel = new JPanel();//checkpw
		private JPanel emailPanel = new JPanel();//email
		private JPanel signbtPanel = new JPanel();//sign
		
		private String useId;
		private boolean existId = true;	//false �̿��� �ߺ����� �ʴ´ٴ� ��!
		private boolean comparePw = false;
		SignUpFrame(){
			idPanel.setLayout(null);
			idLabel.setBounds(50, 25, 70, 15);
			idPanel.add(idLabel);
			id.setBounds(145, 25, 138, 21);
			idPanel.add(id);
			pwPanel.setLayout(null);
			pwLabel.setBounds(50, 25, 83, 15);
			pwPanel.add(pwLabel);
			pwField.setBounds(145, 25, 138, 21);
			pwPanel.add(pwField);
			checkpwPanel.setLayout(null);
			checkPwLabel.setBounds(50, 25, 93, 15);
			checkpwPanel.add(checkPwLabel);
			repwField.setBounds(145, 25, 138, 21);
			checkpwPanel.add(repwField);
			emailPanel.setLayout(null);
			emailLabel.setBounds(50, 25, 83, 15);
			emailPanel.add(emailLabel);
			email.setBounds(145, 25, 138, 21);
			emailPanel.add(email);
			signbtPanel.add(signUpBt);
			
			email.setText("(����)��й�ȣ �нǽ� �ʼ�!");
			
			this.setLayout(new GridLayout(0,1));
			this.add(idPanel);
			checkId.setFont(new Font("����", Font.PLAIN, 11));
			checkId.setBounds(295, 25, 81, 23);
			idPanel.add(checkId);
			this.add(pwPanel);
			this.add(checkpwPanel);
			this.add(emailPanel);
			this.add(signbtPanel);
			
			id.addFocusListener(this);
			id.addKeyListener(this);
			email.addMouseListener(this);
			checkId.addActionListener(this);
			signUpBt.addActionListener(this);
			
			this.setResizable(false);
			this.setSize(400, 360);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("ȸ������");
		} 
			
		public boolean specialCharCheck(String compare) {
			char special[] = "~!@#$%^&*()+|`=\\[]{};:'\".<>/?".toCharArray();
			for(char spchar:special) {
				if(compare.contains(spchar+"")) {	//Ư�����ڰ� �������
					return true;
				}
			}
			return false;	//Ư������ ����
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==checkId) {
				if(!id.getText().equals("")) {
					useId = id.getText();
					existId = idOrPwCheck(useId);	//�ߺ��ɰ�� true
					if(!existId) {	//��ġġ���� ������
						if (!specialCharCheck(id.getText())) {	//Ư������ �˻�
							JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�!");
							checkId.setText("��밡��");
							checkId.setEnabled(false);
						}else {
							JOptionPane.showMessageDialog(null, "Ư�����ڸ� �������ֽʽÿ�!");
						}
					}else {	JOptionPane.showMessageDialog(null, "�̹� �ִ� ���̵��Դϴ�!");	}
				}else {	JOptionPane.showMessageDialog(null, "���̵� �Է����ֽʽÿ�!");	}
			}
			
			if(e.getSource()==signUpBt) {
				if(!existId) {
					if(!pwField.getText().equals("")) {
						if(pwField.getText().equals(repwField.getText())) {
							signUp(id.getText(),pwField.getText(),email.getText());
							JOptionPane.showMessageDialog(null, "ȸ������ �Ϸ�!");
							this.dispose();
						}else {	JOptionPane.showMessageDialog(null, "�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�!");	}
					}else {	JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��Ͻʽÿ�!");	}
			}else {	JOptionPane.showMessageDialog(null, "���̵� �ߺ��˻縦 ���ֽʽÿ�!");	}
				}
			//JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է��Ͻʽÿ�.");
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getSource()==id && !existId) {
				if(!useId.equals(id.getText())) {//�ߺ��˻��� 
					existId = true;
					checkId.setText("�ߺ��˻�");
					checkId.setEnabled(true);
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()==email) {
				email.setText("");	
				}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource()==id && !existId) {
				if(!useId.equals(id.getText())) {//�ߺ��˻��� 
					existId = true;
					checkId.setText("�ߺ��˻�");
					checkId.setEnabled(true);
				}
			}
		}

	}
	
	class FindIdOrPwFrame extends JFrame implements ActionListener, KeyListener{	//�����ڵ� â
		
		private JLabel label = new JLabel("");
		private JTextField contents = new JTextField(12);
		private JPanel panel = new JPanel();
		private JButton confirm = new JButton("Ȯ��");
		private String mode;
		
		FindIdOrPwFrame(String mode){
			this.mode = mode;	//find id or pw
			this.setLayout(new GridLayout(3,1));
			
			if(mode.equals("ID")) {
				label.setText("���� ����� �̸����� �Է��� �ּ���.");
				this.setTitle("���̵� ã��");
			}else if(mode.equals("PW")) {
				label.setText("��й�ȣ�� ã���� �ϴ� ���̵� �Է��� �ּ���.");
				this.setTitle("��й�ȣ ã��");
			}else {
				JOptionPane.showMessageDialog(null, "��� ���� �� ����!");
				this.dispose();
			}
			panel.add(contents);
			panel.add(confirm);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			
			this.add(label);
			this.add(panel);
			this.setSize(300, 170);
			
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			
			confirm.addActionListener(this);
			confirm.addKeyListener(this);
			contents.addKeyListener(this);   
		} 
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==confirm) {	//press confirm button
				if(mode.equals("ID")) { findId(contents.getText());	this.dispose();}
				else {	this.dispose();findPwAndReset(contents.getText());	}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(mode.equals("ID")) { findId(contents.getText());	this.dispose();}
				else {	this.dispose();findPwAndReset(contents.getText());	}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
}