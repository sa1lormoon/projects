package mainApp;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class MainApp extends JFrame{
	private JLabel user = new JLabel();
	private JLabel message = new JLabel("Under construction");
	private JButton back = new JButton("Back");
public MainApp(String username) {
	super("Main");
	try {
		this.setBounds(350, 200, 600, 320);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(3,1,2,2));
		user.setText(username);
		user.setForeground(Color.RED);
		user.setHorizontalAlignment(user.CENTER);
		message.setForeground(Color.BLACK);
		message.setHorizontalAlignment(message.CENTER);
		container.add(user);
		container.add(message);
		container.add(back);
		back.addActionListener(new BackEventListener());
	} catch (Exception e) {
		String message = "Please close other SignUp window";
		JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
		throw new RuntimeException();
	}
}
	public static void main(String[] args) {
		MainApp app = new MainApp("admin");
		app.setVisible(true);

	}
	class BackEventListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			app.MainGUI app = new app.MainGUI();
			app.setVisible(true);
		}
	}

}
