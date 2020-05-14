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

public class UserLogin {
	private String username = null; // null 이면 logout 상태
	private String password = null;

	String savePath = System.getProperty("user.home") + "\\Documents\\FileEncrytProgram";//정보저장 경로

	//File file = new File(savePath);
	
	BufferedReader in = null;
	PrintWriter out = null;
	
	UserLogin() {
		
		File Folder = new File(savePath);
		
		if (!Folder.exists()) {
			try {
				Folder.mkdir();	//폴더가 없을 경우 생성
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		

	}
}
