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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class UserLogin {
	private String username = null; // null 이면 logout 상태
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//정보저장 경로
	private String divUnit = "#%&&!#";	//데이터 구분자
	private String userData[][];
	String tmpUserData = "";
	/**********저장형식 divUnit************/
	UserLogin() {	//로그인을 위한 파일 불러오기
		
		File Folder = new File(savePath);
		File data = new File(savePath+"\\data.enc");
		if (!Folder.exists()) {
			try {
				Folder.mkdir();	//폴더가 없을 경우 생성
				PrintWriter out = null;
				new File(savePath+"\\data.tmp").createNewFile();
				out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));
				out.write("test"+divUnit+"test");
				out.flush();
				out.close();
				EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//암호화		
			} catch (Exception e) {
				e.getStackTrace();
			}
		}else if(!data.exists()) {//폴더있지만 파일 없을시
			try {
				PrintWriter out = null;
				new File(savePath+"\\data.tmp").createNewFile();
				out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));
				out.write("test"+divUnit+"test");
				out.flush();
				out.close();
				EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//암호화
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				new File(savePath+"\\data.tmp").delete();	//암호화후 제거
			} 
			
		}
		
		fileLoad();
		
	}


	public void fileLoad() {
		try {
			userData =null;	//기존에 있던 데이터 지우기
			BufferedReader in = null;
			EncTool.decryptAESCTR(savePath+"\\data.enc", savePath+"\\data.tmp");	//암호화된 정보 복호화
			
			in = new BufferedReader(new FileReader(savePath+"\\data.tmp"));	
			String temp;
			while((temp=in.readLine()) != null) { if(temp.contains(divUnit)) tmpUserData+=temp+"\n"; }	//사용자 정보 읽기
			//System.out.println(userData);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("파일없는 초기상태");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.gc();
			new File(savePath+"\\data.tmp").delete();	//파일로드가 끝나면 노출된 데이터 파기
		} 
		
		if(!tmpUserData.equals("")) {//파일이 비었는지 확인
			String splitTmp[] =tmpUserData.split("\n");
			userData = new String[splitTmp.length][];	//사용자 수만큼 길이 할당
			//System.out.println(userData.length);
			for(int i=0;i<splitTmp.length;i++) {
				//System.out.println(splitTmp[i]);
				userData[i] = splitTmp[i].split(divUnit);//아이디 비번으로 분리  0 id, 1 pw, 2 email
			}
		}
		
	}
	
	public String getUsername() {
		return username;
	}

	public boolean login(String username, String password) {
		this.username = username;
		
		if(idOrPwCheck(username,password)) {
			System.out.println("로그인 성공");
			JOptionPane.showMessageDialog(null, "로그인 성공!");
			return true;	//로그인성공
		}else {
			System.out.println("실패");
			JOptionPane.showMessageDialog(null, "로그인 실패!");
			return false;
		}
		
	}
	
	public void signUp(String id,String pw,String email) {  //@이메일에 따라 저장
		if(email.contains("@")) {
			saveUpdatefile(id+divUnit+pw+divUnit+email);
		}else {
			saveUpdatefile(id+divUnit+pw);
		}
			fileLoad();	//reload file
	}
	
	
	public void saveUpdatefile(String str) {//파일 update 덮어쓰기
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));//true=없으므로 덮어쓰기, true면 append
			String tmp=null;
			try {
				for(int i=0;i<userData.length;i++) {
					
					for(int j=0;j<userData[i].length;j++){
						tmp="";
						tmp+=userData[i][j];
						if(j<1) {	tmp+=divUnit;
						}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//이메일 있는 것일 경우 하나더 
					}
					out.write(tmp+"\n");
				}
				out.write(str+"\n");
			}catch(NullPointerException e) {}	//아무 아이디도  없는상태
			out.flush();
			out.close();
			EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//암호화
			new File(savePath+"\\data.tmp").delete();	//암호화 후 제거
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				new File(savePath+"\\data.tmp").delete();	//암호화후 제거
			} 
	}
	
	public void saveUpdatefile() {//파일 update 덮어쓰기
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.tmp")));//true=없으므로 덮어쓰기, true면 append
			String tmp=null;
			try {
				for(int i=0;i<userData.length;i++) {
					tmp="";
					for(int j=0;j<userData[i].length;j++){
						tmp+=userData[i][j];
						if(j<1) {	tmp+=divUnit;
						}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//이메일 있는 것일 경우 하나더 
					}
					out.write(tmp+"\n");
				}
			}catch(NullPointerException e) {}	//아무 아이디도  없는상태
			out.flush();
			out.close();
			EncTool.encryptAESCTR(savePath+"\\data.tmp", savePath+"\\data.enc");	//암호화
			}catch(IOException e) { e.printStackTrace(); 
			}finally {
				System.gc();
				new File(savePath+"\\data.tmp").delete();	//암호화후 제거
			} 
	}
	
	public boolean idOrPwCheck(String username) {	//아이디만 있는지 확인
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i][0].equals(username)) {
					System.out.println("있는아이디 ID");
					return true;	//있다
					}
			}
		} catch(NullPointerException e) {}	//아무 아이디도  없는상태
			return false;	//못찾았을경우 중복되지 않는 아이디
	}
	
	public boolean idOrPwCheck(String username, String password) {	//아이 비번 확인
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i][0].equals(username)) {
					if(userData[i][1].equals(password)) {
						return true;
					}
				}
			}
		} catch(NullPointerException e) {}	//아무 아이디도  없는상태
		return false;	//못찾았을경우
	}
	/******************************가입된 이메일과 일치한 id가져오기*****************/
	public String bringSignUpId(String email) {//이메일로 id 가져오기
		String tmp="";
		try {
			for(int i=0;i<userData.length;i++) {
				if(userData[i].length == 3) {	//이메일 있는게 길이가 3
					if(email.equals(userData[i][2])) {//이메일 일치 여부 확인
						tmp+= userData[i][0]+" ";//한개의 이메일로 2개 이상 가입했을수도있으므로
					}
				}
			}
		} catch(NullPointerException e) {}	//아무 아이디도  없는상태
		if(!tmp.equals(""))	
		{
			return tmp;	// 아이디들 보내기
		}
		return null;	//불일치
	}
	
	public String bringEmail(String id) {//id로 이메일 찾기
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//이메일 있는게 길이가 3
				if(id.equals(userData[i][0])) {//이메일 일치 여부 확인
					return userData[i][2];	
				}
			}
		}
		return null;
	}
	
	private void setPw(String id,String newPw) {
		for(int i=0;i<userData.length;i++) {
			if(id.equals(userData[i][0])) {//이메일 일치 여부 확인
				userData[i][1] = newPw;
				saveUpdatefile();
				fileLoad();
				break;
			}
		}

	}
	
	
	private void setEmail(String id,String newEmail) {	//이메일 설명 및 저장
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//이메일 있는게 길이가 3
				if(id.equals(userData[i][0])) {//이메일 일치 여부 확인
					userData[i][2] = newEmail;
					saveUpdatefile();
					fileLoad();
					break;
				}
			}
		}

	}
	
	
	/******************************아이디나 비밀번호 찾기 *****************/
	public void findData(String mode) {
		if(mode.equals("ID")) {	new FindIdOrPwFrame("ID");
		}else if(mode.equals("PW")) { new FindIdOrPwFrame("PW");
		}else { System.out.println("잘못된 입력");}
	}
	
	public void findId(String email) {	//이메일이 등록된 경우만 가능
		String id = bringSignUpId(email);
		if(id !=null) {//이메일 일치 여부 확인
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "당신의 아이디는 "+id+" 입니다");
			System.out.println("당신의 이메일로 아이디가 전송되었습니다.");
			JOptionPane.showMessageDialog(null, "당신의 이메일로 아이디가 전송되었습니다.");
			return;
		}else {
		System.out.println("해당 이메일로 가입된 계정이 없습니다.");
		JOptionPane.showMessageDialog(null, "해당 이메일로 가입된 계정이 없습니다.");
		}
	}
	
	
	public void findPwAndReset(String id) {	//비밀번호 찾기 및 재설정
		Thread pwThread =new Thread()
		{
			@Override
			public void run()
			{
				boolean result = false;
				result=VerificationCode(id);	//인증코드 결과
				if(result) {
					new NewPassword(id);
				}
			}
		};
		pwThread.start();
	}
	
	
	public void logedInPwReset(String id,String pw) {	//로그인후 비밀번호 변경
		if(idOrPwCheck(id,pw)) {	//일치하면
			new NewPassword(id);	//비밀번호 변
		}
	}
	
	
	public void emailReset(String id,String newEmail) {	//이메일 재설정
		Thread emailThread =new Thread()
		{
			@Override
			public void run()
			{
				boolean result = false;
				result=VerificationCode(id,newEmail);	//인증코드 결과
				if(result) {
					String email =bringEmail(id);
					if(email == null) {	//이메일이 없다면 배열 공간이 부족하므로 새로 할당
						for(int i=0;i<userData.length;i++) {
							if(id.equals(userData[i][0])) {//이메일 일치 여부 확인
								userData[i]=(userData[i][0]+divUnit+userData[i][1]+divUnit+"temp").split(divUnit);	//칸 다시 확장
							}
						}
					}
					setEmail(id,newEmail);	//이메일 설정
					JOptionPane.showMessageDialog(null, "이메일이 등록되었습니다!");
				}
			}
		};
		emailThread.start();
			
		
	}
	
	public boolean VerificationCode(String id) {	//찾을 id 입력받은후
		SendEmail mail = new SendEmail();
		String email = bringEmail(id);
		String verificationCode="";
		
		if(email != null ) {//이메일 일치 여부 확인
			for(int j=0;j<6;j++) {
				verificationCode+=""+(int)(Math.random()*10);
			}
			mail.sender(email, "File-Encrypt-Program", "인증코드: "+verificationCode);
			System.out.println("당신의 이메일로 인증코드가 전송되었습니다. 유효시간:3분");
			JOptionPane.showMessageDialog(null, "당신의 이메일로 인증코드가 전송되었습니다. 유효시간:3분");
			CodeFrame confirmWindow =new CodeFrame(verificationCode);
			verificationCode = null;	//인증코드 초기화
			
			if(!frameTimer(180,confirmWindow)) {	//시간 내에 입력하지 못 했을 경우 3분
				JOptionPane.showMessageDialog(null, "인증코드 유효시간이 초과하였습니다.");
				System.out.println("인증코드 유효기간이 초과하였습니다.");
				return false;	// fail
			}
			System.out.println("finish");
			return true;	//success
		}
		System.out.println("해당 아이디에 등록된 이메일이 없습니다.");
		JOptionPane.showMessageDialog(null, "해당 아이디에 등록된 이메일이 없습니다.");
		return false;
		
	}
	
	public boolean VerificationCode(String id,String newEmail) {	//찾을 id 입력받은후 새이메일
		SendEmail mail = new SendEmail();
		String email = newEmail;
		String verificationCode="";
		
		if(email != null ) {//이메일 일치 여부 확인
			for(int j=0;j<6;j++) {
				verificationCode+=""+(int)(Math.random()*10);
			}
			mail.sender(email, "File-Encrypt-Program", "인증코드: "+verificationCode);
			System.out.println("당신의 이메일로 인증코드가 전송되었습니다. 유효시간:3분");
			JOptionPane.showMessageDialog(null, "당신의 이메일로 인증코드가 전송되었습니다. 유효시간:3분");
			CodeFrame confirmWindow =new CodeFrame(verificationCode);
			verificationCode = null;	//인증코드 초기화
			
			if(!frameTimer(180,confirmWindow)) {	//시간 내에 입력하지 못 했을 경우 3분
				JOptionPane.showMessageDialog(null, "인증코드 유효시간이 초과하였습니다.");
				System.out.println("인증코드 유효기간이 초과하였습니다.");
				return false;	// fail
			}
			System.out.println("finish");
			return true;	//success
		}
		System.out.println("해당 아이디에 등록된 이메일이 없습니다.");
		JOptionPane.showMessageDialog(null, "해당 아이디에 등록된 이메일이 없습니다.");
		return false;
		
	}
	
	
	public boolean frameTimer(int sec,CodeFrame window) {
		long starTime;
		SimpleDateFormat format = new SimpleDateFormat ( "mm:ss");
		starTime=System.currentTimeMillis();
		int currentTime;
		do {
			currentTime = (int)(System.currentTimeMillis()-starTime)/1000;
			window.time.setText("  남은시간 "+format.format((sec-currentTime)*1000));
			System.out.println(format.format((sec-currentTime)*1000)+"남음"+window.closeWin+window.recognized);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(currentTime<=sec&&!(window.closeWin));
		window.dispose();	//창닫기
		return window.recognized;
	}

	
	class CodeFrame extends JFrame implements ActionListener, KeyListener, WindowListener{	//인증코드 창
		private boolean recognized = false;
		boolean closeWin = false;
		JLabel text = new JLabel("인증코드:");
		JLabel time = new JLabel("");
		JTextField textCode = new JTextField(10);
		JPanel panel = new JPanel();
		JButton confirm = new JButton("확인");
		private String verificationCode;
		
		
		CodeFrame(String verificationCode){
			this.verificationCode = verificationCode;
			panel.add(text);
			panel.add(textCode);
			panel.add(confirm);
			
			this.setLayout(new GridLayout(3,1));
			this.add(time);
			this.add(panel);
			this.setSize(300, 170);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("인증코드 확인");
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
					JOptionPane.showMessageDialog(null, "인증성공!");
					closeWin = true;
					recognized = true;
				}else {
					JOptionPane.showMessageDialog(null, "인증번호가 일치하지 않습니다.");
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
					JOptionPane.showMessageDialog(null, "인증성공!");
					closeWin = true;
					recognized = true;
				}else {
					JOptionPane.showMessageDialog(null, "인증번호가 일치하지 않습니다.");
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

	class NewPassword extends JFrame implements ActionListener, KeyListener{	//인증코드 창
		private JLabel text1 = new JLabel("   새 비밀번호: ");
		private JLabel text2 = new JLabel("비밀번호 확인:");
		private JPasswordField pwtext = new JPasswordField(12);
		private JPasswordField repwtext = new JPasswordField(12);
		private JPanel panel = new JPanel();
		private JButton complete = new JButton("완료");
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
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("비밀번호 변경");
		} 
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==complete) {	//press confirm button
				if(!pwtext.getText().equals("")) {
					if(pwtext.getText().equals(repwtext.getText())) {	//compare code
						dispose();
						setPw(id,pwtext.getText());//기존 pw값  가져오고 그위치에 새로운 pw save
						System.out.println("변경완료");
						JOptionPane.showMessageDialog(null, "변경완료!");
						
					}else {
						JOptionPane.showMessageDialog(null, "두 비밀번호가 일치하지 않습니다!");
					}
				}else { JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오."); }
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
						setPw(id,pwtext.getText());//기존 pw값  가져오고 그위치에 새로운 pw save
						System.out.println("변경완료");
						JOptionPane.showMessageDialog(null, "변경완료!");
						
					}else {
						JOptionPane.showMessageDialog(null, "두 비밀번호가 일치하지 않습니다!");
					}
				}else { JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오."); }
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	public void getSignUpFrame() { new SignUpFrame(); }
	private class SignUpFrame extends JFrame implements ActionListener, KeyListener, MouseListener, FocusListener{	//인증코드 창
		private JLabel idLabel = new JLabel("아이디: ");
		private JLabel pwLabel = new JLabel("비밀번호:");
		private JLabel checkPwLabel = new JLabel("비밀번호 확인:");
		private JLabel emailLabel = new JLabel("이메일:");
		private JPasswordField pwField = new JPasswordField(12);
		private JPasswordField repwField = new JPasswordField(12);
		private JTextField id = new JTextField(12);
		private JTextField email = new JTextField(12);
		private JButton checkId = new JButton("중복검사");
		private JButton signUpBt = new JButton("가입하기");

		private JPanel idPanel = new JPanel();//id
		private JPanel pwPanel = new JPanel();//pw
		private JPanel checkpwPanel = new JPanel();//checkpw
		private JPanel emailPanel = new JPanel();//email
		private JPanel signbtPanel = new JPanel();//sign
		
		private String useId;
		private boolean existId = true;	//false 이여야 중복되지 않는다는 뜻!
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
			
			email.setText("(선택)비밀번호 분실시 필수!");
			
			this.setLayout(new GridLayout(0,1));
			this.add(idPanel);
			checkId.setFont(new Font("굴림", Font.PLAIN, 11));
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
			this.setTitle("회원가입");
		} 
			
		public boolean specialCharCheck(String compare) {
			char special[] = "~!@#$%^&*()+|`=\\[]{};:'\".<>/?".toCharArray();
			for(char spchar:special) {
				if(compare.contains(spchar+"")) {	//특수문자가 있을경우
					return true;
				}
			}
			return false;	//특수문자 없음
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==checkId) {
				if(!id.getText().equals("")) {
					useId = id.getText();
					existId = idOrPwCheck(useId);	//중복될경우 true
					if(!existId) {	//일치치하지 않을때
						if (!specialCharCheck(id.getText())) {	//특수문자 검사
							JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다!");
							checkId.setText("사용가능");
							checkId.setEnabled(false);
						}else {
							JOptionPane.showMessageDialog(null, "특수문자를 제외해주십시오!");
						}
					}else {	JOptionPane.showMessageDialog(null, "이미 있는 아이디입니다!");	}
				}else {	JOptionPane.showMessageDialog(null, "아이디를 입력해주십시오!");	}
			}
			
			if(e.getSource()==signUpBt) {
				if(!existId) {
					if(!pwField.getText().equals("")) {
						if(pwField.getText().equals(repwField.getText())) {
							signUp(id.getText(),pwField.getText(),email.getText());
							JOptionPane.showMessageDialog(null, "회원가입 완료!");
							this.dispose();
						}else {	JOptionPane.showMessageDialog(null, "두 비밀번호가 일치하지 않습니다!");	}
					}else {	JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오!");	}
			}else {	JOptionPane.showMessageDialog(null, "아이디 중복검사를 해주십시오!");	}
				}
			//JOptionPane.showMessageDialog(null, "비밀번호를 입력하십시오.");
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getSource()==id && !existId) {
				if(!useId.equals(id.getText())) {//중복검사후 
					existId = true;
					checkId.setText("중복검사");
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
				if(!useId.equals(id.getText())) {//중복검사후 
					existId = true;
					checkId.setText("중복검사");
					checkId.setEnabled(true);
				}
			}
		}

	}
	
	class FindIdOrPwFrame extends JFrame implements ActionListener, KeyListener{	//인증코드 창
		
		private JLabel label = new JLabel("");
		private JTextField contents = new JTextField(12);
		private JPanel panel = new JPanel();
		private JButton confirm = new JButton("확인");
		private String mode;
		
		FindIdOrPwFrame(String mode){
			this.mode = mode;	//find id or pw
			this.setLayout(new GridLayout(3,1));
			
			if(mode.equals("ID")) {
				label.setText("가입 당시의 이메일을 입력해 주세요.");
				this.setTitle("아이디 찾기");
			}else if(mode.equals("PW")) {
				label.setText("비밀번호를 찾고자 하는 아이디를 입력해 주세요.");
				this.setTitle("비밀번호 찾기");
			}else {
				JOptionPane.showMessageDialog(null, "모드 설정 값 오류!");
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