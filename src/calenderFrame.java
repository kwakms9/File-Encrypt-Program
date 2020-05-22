import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.List;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

public class calenderFrame extends JFrame  implements ActionListener{
	public calenderFrame() {
		super("ดÞทย"); 
		super.setVisible(true); 
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
	    JPanel jp = new JPanel();
	    jp.setLayout(new GridLayout(0, 7)); 
	    
	    JButton[] jdata = new JButton[49]; 
	    for (int i = 0; i < 49; i++) { 
		jdata[i] = new JButton();
	    jdata[i].setText("");
        
		}
	    
		setVisible(true);
		this.add(jp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new calenderFrame();
	}
}
