import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class FileEncryptProgramMain {

	public static void main(String[] args) {
		new loginFrame();	
	}
	
/*
public static void main(String[] args) throws IOException { 
	BufferedReader in = null;
	PrintWriter out = null;
	 String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";
	try { 
	
	out = new PrintWriter(new BufferedWriter(new FileWriter(savePath+"\\data.bin")));
	String l; 
	out.write("slnnsklefndksenlfdsnelfkn\n");
	out.write("eddfdfdfdfdfkn");
	out.flush();
	 in = new BufferedReader(new FileReader(savePath+"\\data.bin")); 
	while ((l = in.readLine()) != null) {  // 한 줄 단위로 입출력
	 System.out.println(l);
	 System.out.println("2222");
	 }
	} finally {
	 if (in != null) { 
	in.close(); 
	} if (out != null) { 
	out.close(); } }
}*/
}
/**************인증시간 테스트
SimpleDateFormat format1 = new SimpleDateFormat ( "mm:ss");
long time1 = System.currentTimeMillis();System.out.println(format1.format(System.currentTimeMillis()));
S1 s =new S1();while((System.currentTimeMillis()-time1)<10*1000 && !s.flag) {
	if((System.currentTimeMillis()-time1)==5000) {s.flag=true;}
} s.dis();
System.out.println(format1.format(System.currentTimeMillis()));

class S1 extends JFrame {
	boolean flag = false;
	JPanel panel = new JPanel();
	JButton helpbt = new JButton("도움말");
	JButton comfirmLogin = new JButton("현재 계정확인");
	JButton sendbt = new JButton("메일 작성");
	JButton logoutbt = new JButton("로그아웃");
	JButton exitbt = new JButton("종료");
	
S1(){
	
	panel.add(helpbt);
	panel.add(comfirmLogin);
	panel.add(sendbt);
	panel.add(logoutbt);
	panel.add(exitbt);
	this.add(panel);
	this.pack();
	this.setSize(300, 300);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    
    this.setTitle("Google Email sender");
    
}void dis(){this.dispose();}}   */