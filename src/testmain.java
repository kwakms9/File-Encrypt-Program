import java.io.*;
public class testmain {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.home")+"\\Documents");
		new UserLogin();
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