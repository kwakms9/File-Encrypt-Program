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
	private String username = null; // null 이면 logout 상태
	//private String password = null;
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//정보저장 경로
	private String divUnit = "##";
	//out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.bin")));//true없으므로 덮어쓰기
	private String userData[][];
	String tmpUserData = "";
	/**********저장형식 id##pw##email\n************/
	UserLogin() {	//로그인을 위한 파일 불러오기
		
		File Folder = new File(savePath);
		File data = new File(savePath+"\\data.txt");
		if (!Folder.exists()) {
			try {
				Folder.mkdir();	//폴더가 없을 경우 생성
				data.createNewFile(); // 파일 생성
			} catch (Exception e) {
				e.getStackTrace();
			}
		}else if(!data.exists()) {//폴더있지만 파일 없을시
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
			while((temp=in.readLine()) != null) { tmpUserData+=temp+"\n"; }	//사용자 정보 읽기
			//System.out.println(userData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(!tmpUserData.equals("")) {//파일이 비었는지 확인
			String splitTmp[] =tmpUserData.split("\n");
			userData = new String[splitTmp.length][];	//사용자 수만큼 길이 할당
			
			for(int i=0;i<splitTmp.length;i++) {
				userData[i] = splitTmp[i].split(divUnit);//아이디 비번으로 분리  0 id, 1 pw, 2 email
			}
		}
	}
	
	
	public void login(String username, String password) {
		this.username = username;
		//this.password = password;//gui전임시
		
		if(idOrPwCheck(username,password)) {
			System.out.println("로그인 성공");
		}else {
			System.out.println("실패");
		}
	}
	
	
	public boolean signUp(String id,String pw) {//가입시 중복체크밑 정보입력 
		if(!idOrPwCheck(id)) {	//중복이 아닐경우
			//this.username=id;
			//this.password=pw;
			savefile(id+divUnit+pw);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("중복된 ID");
		}
		return false;
	}
	
	public boolean signUp(String id,String pw,String email) {//가입시 중복체크밑 정보입력  @이메일에 따라 오버로딩
		if(!idOrPwCheck(id)) {	//중복이 아닐경우
			savefile(id+divUnit+pw+divUnit+email);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("중복된 ID");
		}
		return false;
	}
	
	
	public void savefile(String str) {
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt",true)));//true면 append
			out.write(str+"\n");	//줄넘김 없으니까 추가
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	public void saveUpdatefile() {//파일 update
		try {
			PrintWriter out = null;
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt")));//true=없으므로 덮어쓰기, true면 append
			String tmp=null;
			for(int i=0;i<userData.length;i++) {
				tmp="";
				for(int j=0;j<userData[i].length;j++){
					tmp+=userData[i][j];
					if(j<1) {	tmp+=divUnit;
					}else if (userData[i].length==3&&j==1){ tmp+=divUnit; }	//이메일 있는 것일 경우 하나더 
				}
				out.write(tmp+"\n");
			}
			//out.write(tmp);	//줄넘김 없으니까 추가
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	public boolean idOrPwCheck(String username) {	//아이디만 있는지 확인
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				System.out.println("있는아이디 ID");
				return true;	//있다
				}
		}
		return false;	//못찾았을경우 중복되지 않는 아이디
	}
	
	public boolean idOrPwCheck(String username, String password) {	//아이 비번 확인
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				if(userData[i][1].equals(password)) {
					return true;
				}
			}
		}
		return false;	//못찾았을경우
	}
	/******************************이거랑*****************/
	public String bringSignUpId(String email) {//이메일로 id 가져오기
		String tmp="";
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//이메일 있는게 길이가 3
				if(email.equals(userData[i][2])) {//이메일 일치 여부 확인
					tmp+= userData[i][0]+" ";
				}
			}
		}
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
					return userData[i][2];	//한개의 이메일로 2개 이상 가입했을수도있으므로
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
	/******************************이거 합치는 것도? +비밀번호 찾아 주는거 까지*****************/
	public void findId(String email) {	//이메일이 등록된 경우만 가능
		String id = bringSignUpId(email);
		if(id !=null) {//이메일 일치 여부 확인
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "당신의 아이디는 "+id+" 입니다");
			System.out.println("당신의 이메일로 아이디가 전송되었습니다.");
			return;
		}else {
		System.out.println("해당 이메일로 가입된 계정이 없습니다.");
		}
	}
	
	
	public void findPwAndReset(String id) {
		boolean result = false;
		result=VerificationCode(id);	//인증코드 결과
		if(result) {
			new NewPassword(id);
		}
	}
	
	
	public boolean VerificationCode(String id) {	//찾을 id 입력받은후
		System.out.println("이메일로 인증코드발송");
		SendEmail mail = new SendEmail();
		String email = bringEmail(id);
		String verificationCode="";
		
		if(email != null ) {//이메일 일치 여부 확인
			for(int j=0;j<6;j++) {
			verificationCode+=""+(int)Math.random()*10;
			}
			mail.sender(email, "File-Encrypt-Program", "인증코드: "+verificationCode);
			System.out.println("당신의 이메일로 인증코드가 전송되었습니다. 유효시간:3분");
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
		return false;
		
	}
	
	public void comfirmCode(String verificationCode) {		/*******GUi에서 인증확인구연하기*********완료*/
		
	}
	
	public boolean frameTimer(int sec,CodeFrame window) {
		long starTime;
		SimpleDateFormat format = new SimpleDateFormat ( "mm:ss");
		int currentTime;
		starTime=System.currentTimeMillis();
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
		}while(currentTime<=sec&&!(window.closeWin));	//종료버튼 누를시
		window.dispose();
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
			
			this.setLayout(new GridLayout(3,3));
			this.add(time);
			this.add(panel);
			this.pack();
			this.setSize(300, 170);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setTitle("인증코드 확인");
			
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
		JLabel text1 = new JLabel("새 비밀번호:");
		JLabel text2 = new JLabel("비밀번호 확인:");
		JPasswordField pwtext = new JPasswordField(10);
		JPasswordField repwtext = new JPasswordField(10);
		JPanel panel = new JPanel(new GridLayout(2,2));
		JButton complete = new JButton("완료");
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
			this.setTitle("비밀번호 변경");
			
			complete.addActionListener(this);
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
}