package login;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LogIn extends JFrame {
	private JButton LogIn = new JButton("Log in");
	private JLabel appLabel = new JLabel("SYSTEM");
	private JLabel passwrdDiscription = new JLabel("Enter your password");
	private JTextField user = new JTextField();
	private JPasswordField passwrd = new JPasswordField();
	private JButton back = new JButton("Back");

	public LogIn(String username) {
		super("LogIn");
		try {
			this.setBounds(350, 200, 200, 200);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			user.setText(username);
			user.setEnabled(false);
			appLabel.setHorizontalAlignment(appLabel.CENTER);
			passwrdDiscription.setHorizontalAlignment(passwrdDiscription.CENTER);
			Container container = this.getContentPane();
			container.setLayout(new GridLayout(6, 1, 2, 2));
			appLabel.setForeground(Color.RED);
			passwrd.setEchoChar('*');
			container.add(appLabel);
			container.add(user);
			container.add(passwrdDiscription);
			container.add(passwrd);
			container.add(LogIn);
			container.add(back);
			LogIn.addActionListener(new LogInEventListener());
			back.addActionListener(new BackEventListener());
		} catch (Exception e) {
			String message = "Please close other LogIn window";
			JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
			throw new RuntimeException();
		}
	}

	class LogInEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String finalUser = user.getText();
				char[] arrayPassword = passwrd.getPassword();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < arrayPassword.length; i++) {
					sb.append(arrayPassword[i]);
				}
				String finalPassword = sb.toString();
				int result = utilities.MysqlConnect.login(finalUser, finalPassword);
				if (result == 0) {
					String message = "Incorrect password";
					passwrd.setText("");
					JOptionPane.showMessageDialog(null, message, "NOTIFICATION", JOptionPane.PLAIN_MESSAGE);
					throw new RuntimeException();
				} else {
					dispose();
					mainApp.MainApp app = new mainApp.MainApp(finalUser);
					app.setVisible(true);
				}
			} catch (Exception error) {

			}
		}
	}
	
	class BackEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			app.MainGUI app = new app.MainGUI();
			app.setVisible(true);
	}
	}

	public static void main(String[] args) {
		LogIn app = new LogIn("admin");
		app.setVisible(true);
	}
}
