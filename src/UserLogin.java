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
import java.util.Random;

public class UserLogin {
	private String username = null; // null 이면 logout 상태
	private String password = null;
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//정보저장 경로
	BufferedReader in = null;
	PrintWriter out = null;//out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.bin")));//true없으므로 덮어쓰기
	String userData[][];
	String tmpUserData = "";
	
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
			userData = new String[splitTmp.length][];
			
			for(int i=0;i<splitTmp.length;i++) {
				userData[i] = splitTmp[i].split("##");//아이디 비번으로 분리  0 id, 1 pw, 2 email
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
			savefile(id+"##"+pw);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("중복된 ID");
		}
		return false;
	}
	
	public boolean signUp(String id,String pw,String email) {//가입시 중복체크밑 정보입력  @이메일에 따라 오버로딩
		if(!idOrPwCheck(id)) {	//중복이 아닐경우
			//this.username=id;
			//this.password=pw;
			savefile(id+"##"+pw+"##"+email);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("중복된 ID");
		}
		return false;
	}
	
	
	public void savefile(String str) {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt",true)));//true=없으므로 덮어쓰기, true면 append
			out.write(str+"\n");	//줄넘김 없으니까 추가
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	
	public boolean idOrPwCheck(String username) {	//아이디만 있는지 확인
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				System.out.println("사용불가능한 ID");
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
	
	public String checkEmail(String email) {//얘도 findId와 합치기
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//이메일 있는게 길이가 3
				if(email.equals(userData[i][2])) {//이메일 일치 여부 확인
					return userData[i][0];
				}
			}
		}
		return null;	//불일치
	}
	
	public String findEmail(String id) {//findPw메소드와 합치기
		String tmp="";
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//이메일 있는게 길이가 3
				if(id.equals(userData[i][0])) {//이메일 일치 여부 확인
					tmp+= userData[i][2]+" ";	//한개의 이메일로 2개 이상 가입했을수도있으므로
				}
			}
		}
		if(!tmp.equals(""))	
		{
			return tmp;	// 아이디들 보내기
		}
		return null;	//불일치
	}
	
	public void findId(String email) {	//이메일이 등록된 경우만 가능
		String id = checkEmail(email);
		if(id !=null) {//이메일 일치 여부 확인
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "당신의 아이디는 "+id+" 입니다");
			System.out.println("당신의 이메일로 아이디가 전송되었습니다.");
			return;
		}else {
		System.out.println("해당 이메일로 가입된 계정이 없습니다.");
		}
	}
	
	public String sendVerificationCode(String email) {	//찾을 id 입력받은후
		System.out.println("이메일로 인증코드발송");
		SendEmail mail = new SendEmail();
		String verificationCode="";
		
		for(int j=0;j<6;j++) {
		verificationCode+=""+(int)Math.random()*10;
		}
		
		mail.sender(email, "File-Encrypt-Program", "인증코드: "+verificationCode);
		System.out.println("당신의 이메일로 인증코드가 전송되었습니다.");
		return verificationCode;
	}
	
	public void findPw(String id) {
		String email = findEmail(id);
		String code;
		if(email != null ) {//이메일 일치 여부 확인
			code=sendVerificationCode(email);
		}else {
			System.out.println("해당 아이디에 등록된 이메일이 없습니다.");
		}
	}
	
}
