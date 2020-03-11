package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class MainGUI extends JFrame {
	private JLabel appLabel = new JLabel("SYSTEM");
	private JLabel userLabel = new JLabel("Enter your username");
	private JTextField user = new JTextField();
	private JButton push = new JButton("Push");

	public MainGUI() {
		super("Intelligent System");
		try {
			this.setBounds(400, 200, 500, 120);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container container = this.getContentPane();
			container.setLayout(new GridLayout(4, 1, 2, 2));
			appLabel.setForeground(Color.RED);
			appLabel.setHorizontalAlignment(appLabel.CENTER);
			userLabel.setHorizontalAlignment(userLabel.CENTER);
			push.addActionListener(new PushEventListener());
			container.add(appLabel);
			container.add(userLabel);
			container.add(user);
			container.add(push);
		} catch (Exception error) {
			String message = "Please close other windows(not the APP one)";
			JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
			throw new RuntimeException();
		}

	}

	class PushEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = user.getText();
			int result = utilities.MysqlConnect.checkUser(username);
			if (result == 0) {
				dispose();
				registration.SignUp app = new registration.SignUp(username);
				app.setVisible(true);
			} else {
				dispose();
				login.LogIn app = new login.LogIn(username);
				app.setVisible(true);
			}
		}
	}
	public static void main(String[] args) {
		MainGUI app = new MainGUI();
		app.setVisible(true);
	}
}
