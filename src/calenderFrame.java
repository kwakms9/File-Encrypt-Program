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
		
		super("�޷�"); 
		 
		super.setVisible(true); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		 JPanel jp = new JPanel();
	    jp.setLayout(new GridLayout(0, 7)); 
	    
	     jdata[0] = new JButton("��");
	    jdata[1] = new JButton("��");
	    jdata[2] = new JButton("ȭ");
	    jdata[3] = new JButton("��");
	    jdata[4] = new JButton("��");
	    jdata[5] = new JButton("��");
	    jdata[6] = new JButton("��");
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
			if (e.getSource() == jdata[i]) { //1�Ϲ�ư,  1�Ϲ�ư�� ������ �ű⿡ �޸����� ������ �� ��ȹ
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
