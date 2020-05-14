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
	private String username = null; // null �̸� logout ����
	private String password = null;
	private String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//�������� ���
	BufferedReader in = null;
	PrintWriter out = null;//out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.bin")));//true�����Ƿ� �����
	String userData[][];
	String tmpUserData = "";
	
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
			userData = new String[splitTmp.length][];
			
			for(int i=0;i<splitTmp.length;i++) {
				userData[i] = splitTmp[i].split("##");//���̵� ������� �и�  0 id, 1 pw, 2 email
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
			savefile(id+"##"+pw);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("�ߺ��� ID");
		}
		return false;
	}
	
	public boolean signUp(String id,String pw,String email) {//���Խ� �ߺ�üũ�� �����Է�  @�̸��Ͽ� ���� �����ε�
		if(!idOrPwCheck(id)) {	//�ߺ��� �ƴҰ��
			//this.username=id;
			//this.password=pw;
			savefile(id+"##"+pw+"##"+email);
			fileLoad();	//reload file
			return true;
		}else {
			System.out.println("�ߺ��� ID");
		}
		return false;
	}
	
	
	public void savefile(String str) {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.txt",true)));//true=�����Ƿ� �����, true�� append
			out.write(str+"\n");	//�ٳѱ� �����ϱ� �߰�
			out.flush();
			out.close();
			}catch(IOException e) { e.printStackTrace(); }
	}
	
	
	public boolean idOrPwCheck(String username) {	//���̵� �ִ��� Ȯ��
		for(int i=0;i<userData.length;i++) {
			if(userData[i][0].equals(username)) {
				System.out.println("���Ұ����� ID");
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
	
	public String checkEmail(String email) {//�굵 findId�� ��ġ��
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
				if(email.equals(userData[i][2])) {//�̸��� ��ġ ���� Ȯ��
					return userData[i][0];
				}
			}
		}
		return null;	//����ġ
	}
	
	public String findEmail(String id) {//findPw�޼ҵ�� ��ġ��
		String tmp="";
		for(int i=0;i<userData.length;i++) {
			if(userData[i].length == 3) {	//�̸��� �ִ°� ���̰� 3
				if(id.equals(userData[i][0])) {//�̸��� ��ġ ���� Ȯ��
					tmp+= userData[i][2]+" ";	//�Ѱ��� �̸��Ϸ� 2�� �̻� �����������������Ƿ�
				}
			}
		}
		if(!tmp.equals(""))	
		{
			return tmp;	// ���̵�� ������
		}
		return null;	//����ġ
	}
	
	public void findId(String email) {	//�̸����� ��ϵ� ��츸 ����
		String id = checkEmail(email);
		if(id !=null) {//�̸��� ��ġ ���� Ȯ��
			SendEmail mail = new SendEmail();
			mail.sender(email, "File-Encrypt-Program - your ID", "����� ���̵�� "+id+" �Դϴ�");
			System.out.println("����� �̸��Ϸ� ���̵� ���۵Ǿ����ϴ�.");
			return;
		}else {
		System.out.println("�ش� �̸��Ϸ� ���Ե� ������ �����ϴ�.");
		}
	}
	
	public String sendVerificationCode(String email) {	//ã�� id �Է¹�����
		System.out.println("�̸��Ϸ� �����ڵ�߼�");
		SendEmail mail = new SendEmail();
		String verificationCode="";
		
		for(int j=0;j<6;j++) {
		verificationCode+=""+(int)Math.random()*10;
		}
		
		mail.sender(email, "File-Encrypt-Program", "�����ڵ�: "+verificationCode);
		System.out.println("����� �̸��Ϸ� �����ڵ尡 ���۵Ǿ����ϴ�.");
		return verificationCode;
	}
	
	public void findPw(String id) {
		String email = findEmail(id);
		String code;
		if(email != null ) {//�̸��� ��ġ ���� Ȯ��
			code=sendVerificationCode(email);
		}else {
			System.out.println("�ش� ���̵� ��ϵ� �̸����� �����ϴ�.");
		}
	}
	
}
