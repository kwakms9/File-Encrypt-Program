import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


public class SendEmail {
    private String username = null; // null 이면 logout 상태
    private String password = null;
    String yesorno;
	Scanner scan = new Scanner(System.in);
	boolean success = false;
	boolean guimode = false;
    SendEmail(){
    }
    
	
	
    public void clearScreen() {
        for (int i = 0; i < 80; i++)
          System.out.println("");
      }
    
    public void menu() {
	        System.out.println("사용하실 옵션을 선택해 주십시오.");
	        System.out.println("=========================");
	        if(username==null) { System.out.println("1.로그인"); }
	        else { System.out.println("1.다른계정으로 로그인"); }
	        System.out.println("2.메일 작성");
	        System.out.println("3.로그아웃");
	        System.out.println("4.종료");
	        System.out.println("========================="); 
	        System.out.printf("입력: ");
	        
        }
    public void login() {
    	do {
    		username=checkId();
    		if(guimode==false) {
		    	System.out.printf("비밀번호를 입력하시오: ");
		    	password = scan.next();
		    	clearScreen();	//화면 올리기
    		}
	    	tryLogin();
	    	if(success == true) {
	    		break;
	    	}
    	}while(!guimode);
    }
    
    public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public void logout() {
    	if(username!=null) {
    		username = null;
    		password = null;
    	}else {
    		System.out.println("현재 로그아웃 상태입니다.");
    		System.out.println("");
    	}
    }
    
    public String checkId() {
    	String tmp[] = new String[2];
    	
    	do {
    		if(guimode==false) {
    			System.out.printf("구글 이메일을 입력하시오: ");
    			username = scan.next();
    			}
        	
        	if(!username.contains("@")) {
        		username= username+"@gmail.com";
        	}
        	tmp = username.split("@");
    		
	    	if(tmp[1].equalsIgnoreCase("gmail.com")) {	//포털 주소 확인
	    		username = tmp[0]+"@gmail.com";
	    		break;
	    	}else {
	    		System.out.println("사용할 수 없는 이메일주소입니다.");
	    		System.out.println("입력된 포털주소: "+tmp[1]);
	    	}
    	}while(!guimode);
    	return username;
    }
    public void tryLogin() {
    	String receiver;
    	receiver = "test@lagsixtome.com";	//없는 이메일
    	sender(receiver,"","");
    	if(success) {
    		System.out.println("로그인 성공");
    	}
    	
    }
    
    public void sender(String receiver, String title, String content) {
    	try{
    		
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("NotUsed@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(receiver)
            );
            message.setSubject(title);

            message.setContent(content, "text/html;charset=UTF-8");

            Transport.send(message);

            //System.out.println("Email sent.");
            success = true;

        } catch (AuthenticationFailedException e) {
        	System.out.println("로그인 실패!\n아이디와 비밀번호를 다시 확인해 보십시오.");
        	success = false;
            //e.printStackTrace();
        } catch(Exception e) {
        	System.out.println("메일 전송중 에러 발생!");
        	success = false;
        }
    }

    public void writeMessage() {
    	String title;
    	String content;
    	
    	if(username!=null) {
	    	while(true) {
		    	System.out.printf("보낼사람의 이메일 주소를 입력하십시오.\n: ");
		    	scan.nextLine();
		    	String receiver [] = scan.nextLine().replace(" ", "").split(","); //이메일 주소들 입력
		    	
		    	System.out.printf("메일의 제목을 입력하십시오.\n: ");
		    	title = scan.nextLine();
		    	System.out.println("내용을 입력하시오.\n: ");
				content = scan.nextLine();
				System.out.println("전송하시겠습니까?(Y:전송 N:취소):");
				yesorno = scan.next();
				if(yesorno.equalsIgnoreCase("Y") || yesorno.contentEquals("전송")) {
					System.out.println("메일 전송중...");
					for(int i=0;i<receiver.length;i++) {
						System.out.println(receiver[i]);
						sender(receiver[i],title,content);
					}
					System.out.println("모든 메일을 발송하였습니다. 더 보내시겠습니까?(Y:전송 N:돌아가기)");
					yesorno = scan.next();
					if(!(yesorno.equalsIgnoreCase("Y") || yesorno.contentEquals("전송"))) { break; }
					
				}else {
					System.out.println("메일 전송이 중단되었습니다. 처음으로 이동");
				}
	    	}
    	}else {
    		System.out.println("현재 로그인 상태가 아닙니다.");
    		System.out.println("");
    	}
    }
    
    public String guiMessage(String receiverarry, String title, String content) {
    	String errreceiver="";
    	String receiver [] = receiverarry.replace(" ", "").split(","); //이메일 주소들 입력
		System.out.println("메일 전송중...");
		
		for(int i=0;i<receiver.length;i++) {
			sender(receiver[i],title,content);	//전송
			if(success==false) {
				errreceiver+=receiver[i]+" ";
			}
		}
	    	return errreceiver;
    }
}
