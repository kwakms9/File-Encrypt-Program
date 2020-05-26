import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;



public class calenderFrame extends JFrame  implements ActionListener{
	JButton[] jdata = new JButton[49];
	calender ca = new calender();
	public calenderFrame() {
		
		super("달력"); 
		 
		super.setVisible(true); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		 JPanel jp = new JPanel();
	    jp.setLayout(new GridLayout(0, 7)); 
	    
	     jdata[0] = new JButton("일");
	    jdata[1] = new JButton("월");
	    jdata[2] = new JButton("화");
	    jdata[3] = new JButton("수");
	    jdata[4] = new JButton("목");
	    jdata[5] = new JButton("금");
	    jdata[6] = new JButton("토");
	    for (int i = 7; i < 49; i++) { 
		jdata[i] = new JButton();
	    jdata[i].setText("");
	    }
	    
		setVisible(true);
		this.add(jp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		while(true){
			for(int i=7;i<49;i++) {
			if (e.getSource() == jdata[i]) { //1일버튼,  1일버튼을 누르면 거기에 메모장이 열리게 할 계획
				if(ca.calendate.equals(ca.creatDate)) {
					ca.FileCreationDate();
				}
			}
		}
	}
}	
	
	
	public static void main(String[] args) {
		new calenderFrame();
	}
}
