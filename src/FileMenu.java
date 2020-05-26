import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FileMenu extends JFrame {
   Container contentPane;
   JLabel imageLabel = new JLabel();

   String filepath;
   FileMenu(){
      setTitle("���ϼ���");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = getContentPane();
      contentPane.add(imageLabel);
      createMenu();
      setSize(300,200);
      setVisible(true);
    }
   
   void createMenu() {
      JMenuBar mb = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      JMenuItem openltem = new JMenuItem("OPen");
      
      openltem.addActionListener(new OpenActionListener());
      fileMenu.add(openltem);
      mb.add(fileMenu);
      this.setJMenuBar(mb);
      
   }
   
   class OpenActionListener implements ActionListener{
      JFileChooser chooser;
      
      OpenActionListener(){
         chooser = new JFileChooser();
      }
      public void actionPerformed(ActionEvent e) {
       
         int ret = chooser.showOpenDialog(null);
         if(ret != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null,"������ �������� �ʾҽ��ϴ�", "���",
               JOptionPane.WARNING_MESSAGE);
            return;
         }
         String filePath = chooser.getSelectedFile().getPath();
         imageLabel.setIcon(new ImageIcon(filePath));
         File dir = new File(filePath);
         filepath = dir.getAbsolutePath();// ���� ������ (filepath�� ��θ� �����ϴ� ����)
         
      }
   }
   public static void main(String [] args) {
      new FileMenu();
   }


}